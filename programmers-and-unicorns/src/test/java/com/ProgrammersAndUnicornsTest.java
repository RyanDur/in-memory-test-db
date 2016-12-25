package com;

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

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

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
                                .withName(rs.getString("programmer"))
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
        assertThat(programmers, containsInAnyOrder("Heather", "Paul", "Anu", "Partho", "Jeff"));
    }
}
