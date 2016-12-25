package com;

import com.testdb.TestContext;
import com.testdb.testhelpers.Programmer;
import com.testdb.testhelpers.ProgrammerTestFixture;
import com.testdb.testhelpers.Unicorn;
import com.testdb.testhelpers.UnicornTestFixture;
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

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestContext.class)
@Sql({"classpath:drop_schema.sql", "classpath:db/migration/V1__base.sql", "classpath:data.sql"})
public class SpringSqlAnnotationExample {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProgrammerTestFixture programmerTestFixture;

    @Autowired
    private UnicornTestFixture unicornTestFixture;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void printTestName() {
        System.out.println(testName.getMethodName());
    }

    @Test
    public void printRows() {
        List programmers = jdbcTemplate.queryForList("select name from programmer",
                String.class);
        assertThat(2, is(equalTo(programmers.size())));
        System.out.println(programmers);
    }

    @Test
    @Sql({"classpath:drop_schema.sql", "classpath:db/migration/V1__base.sql", "classpath:override_data.sql"})
    public void overrideSqlAndPrintRows() {
        List programmers = jdbcTemplate.queryForList("select name from programmer",
                String.class);
        assertThat(3, is(equalTo(programmers.size())));
        System.out.println(programmers);
    }

    @Test
    @Sql({"classpath:drop_schema.sql", "classpath:db/migration/V1__base.sql"})
    public void programmersNeedAUnicorn() throws SQLException {
        Programmer programmer = Programmer.builder().withName("Paul").build();
        Unicorn unicorn = Unicorn.builder().withName("Sparkle Mane").withProgrammer(programmer).build();

        programmerTestFixture.insert(programmer);
        unicornTestFixture.insert(unicorn);

        Unicorn sparkleMane = jdbcTemplate.queryForObject("select * from unicorn", (rs, rowNum) ->
                Unicorn.builder()
                        .withName(rs.getString("name"))
                        .withProgrammer(Programmer.builder()
                                .withName(rs.getString("programmer"))
                                .build())
                        .build());

        assertThat(unicorn, is(sparkleMane));
        System.out.println(sparkleMane);
    }
}
