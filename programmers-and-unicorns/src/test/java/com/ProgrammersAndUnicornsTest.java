package com;

import com.db.ProgrammersAndUnicorns;
import com.testdb.TestContext;
import com.testdb.testhelpers.Programmer;
import com.testdb.testhelpers.Unicorn;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.min;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestContext.class)
@Sql({"classpath:drop_schema.sql", "classpath:schema.sql"})
public class ProgrammersAndUnicornsTest {

    @Rule
    public TestName testName = new TestName();

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProgrammersAndUnicorns pairs;

    @Before
    public void printTestName() {
        pairs = new ProgrammersAndUnicorns(jdbcTemplate);
        System.out.println(testName.getMethodName());
    }

    @Test
    public void shouldBeAbleToAddAProgrammer() {
        String programmer = "Heather";
        pairs.add(programmer);

        List<String> programmers = jdbcTemplate.queryForList("select name from programmer",
                String.class);

        assertThat(programmers.size(), is(equalTo(1)));
        assertThat(programmers.get(0), is(equalTo(programmer)));
        System.out.println(programmers);
    }

    @Test
    public void shouldBeAbleToAddAUnicornToAProgrammer() throws InterruptedException {
        String programmer = "Partho";
        String unicorn = "Glitter Cup";
        pairs.add(unicorn, programmer);

        List<String> programmers = jdbcTemplate.queryForList("select name from programmer",
                String.class);

        Unicorn glitterCup = jdbcTemplate.queryForObject("select * from unicorn", (rs, rowNum) ->
                Unicorn.builder()
                        .withName(rs.getString("name"))
                        .withProgrammer(Programmer.builder()
                                .withName(rs.getString("programmer_name"))
                                .build())
                        .build());

        assertThat(programmers.size(), is(equalTo(1)));
        assertThat(programmers.get(0), is(equalTo(programmer)));
        assertThat(glitterCup.getName(), is(equalTo(unicorn)));
        System.out.println(glitterCup);
    }

    @Test
    public void shouldBeAbleToGetAllTheProgrammers() {
        List<String> names = asList("Heather", "Paul", "Anu", "Partho", "Jeff");
        names.forEach(name -> pairs.add(name));

        List<String> programmers = pairs.getAllProgrammers();

        assertThat(programmers.size(), is(5));
        assertThat(programmers, containsInAnyOrder(names.toArray()));
    }

    @Test
    public void shouldBeAbleToGetAllTheUnicorns() {
        List<String> programmers = asList("Heather", "Paul", "Anu", "Partho", "Jeff");
        List<String> unicorns = asList("Mo", "Larry", "Curly", "Shemp", "Bozo");
        zipTogether(programmers, unicorns)
                .forEach(pair -> pairs.add(pair.get(1), pair.get(0)));

        List<String> actual = pairs.getAllUnicorns();

        assertThat(actual.size(), is(unicorns.size()));
        assertThat(actual, containsInAnyOrder(unicorns.toArray()));

    }

    @Test
    public void shouldBeAbleToGetAllThePairs() {
        String programmer = "Heather";
        String newUnicornMane = "Cookie";
        List<String> programmers = asList(programmer, "Paul", "Anu", "Partho", "Jeff");
        List<String> unicorns = asList("Mo", "Larry", "Curly", "Shemp", "Bozo");
        zipTogether(programmers, unicorns)
                .forEach(pair -> pairs.add(pair.get(1), pair.get(0)));
        List<String> expected = asList(programmer, newUnicornMane);

        List<String> actual = pairs.update(newUnicornMane, programmer);

        assertThat(actual.size(), is(expected.size()));
        assertThat(actual, contains(expected.toArray()));
    }

    @Test
    public void shouldBeAbleToDeleteAProgrammer() {
        String programmerName = "Heather";
        List<String> names = asList(programmerName, "Paul", "Anu", "Partho", "Jeff");
        names.forEach(name -> pairs.add(name));

        pairs.delete(programmerName);

        List<String> programmers = jdbcTemplate.queryForList("select name from programmer",
                String.class);

        assertThat(programmers.size(), is(equalTo(names.size() - 1)));
        assertThat(programmers, not(hasItem(programmerName)));
        System.out.println(programmers);
    }


    @Test
    public void shouldBeDeleteTheUnicornOfTheProgrammerThatIsDeleted() {
        String programmer = "Heather";
        String unicorn = "Mo";
        List<String> programmers = asList(programmer, "Paul", "Anu", "Partho", "Jeff");
        List<String> unicorns = asList(unicorn, "Larry", "Curly", "Shemp", "Bozo");
        zipTogether(programmers, unicorns)
                .forEach(pair -> pairs.add(pair.get(1), pair.get(0)));

        pairs.delete(programmer);
        
        List<String> actual = jdbcTemplate.queryForList("select name from unicorn",
                String.class);

        assertThat(actual.size(), is(programmers.size() - 1));
        assertThat(actual, not(hasItem(unicorn)));
    }

    private <T> List<List<T>> zipTogether(List<T> left, List<T> right) {
        return IntStream.range(0, min(left.size(), right.size()))
                .mapToObj(i -> asList(left.get(i), right.get(i)))
                .collect(toList());
    }
}
