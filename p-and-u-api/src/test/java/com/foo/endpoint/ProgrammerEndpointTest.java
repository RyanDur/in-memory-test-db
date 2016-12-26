package com.foo.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foo.PAndUApiApplication;
import com.foo.doamins.Pair;
import com.foo.doamins.Programmer;
import com.testdb.TestContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.foo.services.UnicornGenerator.FIRST_NAME;
import static com.foo.services.UnicornGenerator.LAST_NAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {TestContext.class, PAndUApiApplication.class})
@Sql({"classpath:drop_schema.sql", "classpath:schema.sql"})
public class ProgrammerEndpointTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    public void shouldMapProgrammerPostToAnEndpoint() throws Exception {
        mockMvc.perform(post("/programmers")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .content("{\"name\": \"Armin\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldSaveProgrammerToTheDataBase() throws Exception {
        String programmer = "Sean";

        mockMvc.perform(post("/programmers")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .content("{\"name\": \"" + programmer + "\"}"))
                .andExpect(status().isCreated());

        List<Programmer> programmers = jdbcTemplate.query("select name from programmer",
                (r, rowNum) -> Programmer.builder()
                        .withName(r.getString("name"))
                        .build());

        assertThat(programmers.size(), is(equalTo(1)));
        assertThat(programmers.get(0).getName(), is(equalTo(programmer)));
    }

    @Test
    public void shouldReturnAPair() throws Exception {
        String programmerName = "Eric";
        Programmer programmer = Programmer.builder().withName(programmerName).build();
        MvcResult result = mockMvc.perform(post("/programmers")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .content("{\"name\": \"" + programmerName + "\"}"))
                .andExpect(status().isCreated()).andReturn();

        String body = result.getResponse().getContentAsString();
        Pair actual = mapper.readValue(body, Pair.class);
        String[] split = actual.getUnicorn().getName().split(" ");

        assertThat(actual.getProgrammer(), is(programmer));
        assertThat(split.length, is(2));
        assertThat(FIRST_NAME, hasItem(split[0]));
        assertThat(LAST_NAME, hasItem(split[1]));
    }
}
