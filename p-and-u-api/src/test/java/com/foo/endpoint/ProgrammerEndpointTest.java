package com.foo.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foo.PAndUApiApplication;
import com.foo.doamins.Pair;
import com.foo.doamins.Programmer;
import com.foo.doamins.Unicorn;
import com.testdb.TestContext;
import com.testdb.testhelpers.ProgrammerTestFixture;
import com.testdb.testhelpers.UnicornTestFixture;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.foo.services.UnicornGenerator.FIRST_NAME;
import static com.foo.services.UnicornGenerator.LAST_NAME;
import static com.testdb.testhelpers.Programmer.builder;
import static java.lang.Math.min;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT,
        classes = {TestContext.class, PAndUApiApplication.class}
)
@Sql({"classpath:drop_schema.sql", "classpath:schema.sql"})
public class ProgrammerEndpointTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProgrammerTestFixture programmerTestFixture;

    @Autowired
    private UnicornTestFixture unicornTestFixture;

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

        Pair actual = mapResponse(result, Pair.class);
        String[] split = actual.getUnicorn().getName().split(" ");

        assertThat(actual.getProgrammer(), is(programmer));
        assertThat(split.length, is(2));
        assertThat(FIRST_NAME, hasItem(split[0]));
        assertThat(LAST_NAME, hasItem(split[1]));
    }

    @Test
    public void shouldBeAbleToGetAllProgrammers() throws Exception {
        List<Programmer> programmers = getProgrammers("Heather", "Anu", "Paul");
        MvcResult result = mockMvc.perform(get("/programmers")
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        List<Programmer> actual = asList(mapResponse(result, Programmer[].class));

        assertThat(actual.size(), is(programmers.size()));
        assertThat(actual, containsInAnyOrder(programmers.toArray()));
    }

    @Test
    public void shouldBeAbleToGetAllUnicorns() throws Exception {
        List<Programmer> programmers = getProgrammers("Heather", "Anu", "Paul");
        List<Unicorn> unicorns = getUnicorns(programmers, "Mo", "Larry", "Curly");

        MvcResult result = mockMvc.perform(get("/unicorns")
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        List<Unicorn> actual = asList(mapResponse(result, Unicorn[].class));

        assertThat(actual.size(), is(min(programmers.size(), unicorns.size())));
        assertThat(actual, containsInAnyOrder(unicorns.toArray()));
    }

    @Test
    public void shouldBeAbleToGetAllPairs() throws Exception {
        List<Programmer> programmers = getProgrammers("Heather", "Anu", "Paul");
        List<Unicorn> unicorns = getUnicorns(programmers, "Mo", "Larry", "Curly");
        List<Pair> pairs = getPairs(programmers, unicorns);

        MvcResult result = mockMvc.perform(get("/pairs")
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        List<Pair> actual = asList(mapResponse(result, Pair[].class));

        assertThat(actual.size(), is(min(programmers.size(), unicorns.size())));
        assertThat(actual, containsInAnyOrder(pairs.toArray()));
    }

    @Test
    public void shouldBeAbleToChangeAProgrammersUnicorn() throws Exception {
        String programmerName = "Josh";
        String unicornName = "Dandelion Velvet Hooves";
        String newUnicornName = "Starflower Darling Moon";
        List<Programmer> programmers = getProgrammers(programmerName);
        getUnicorns(programmers, unicornName);

        Pair expected = Pair.builder()
                .withProgrammer(programmers.get(0))
                .withUnicorn(Unicorn.builder().withName(newUnicornName).build())
                .build();

        MvcResult result = mockMvc.perform(put("/programmers/" + programmerName)
                .content("{\"name\": \"" + newUnicornName + "\"}")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        Pair actual = mapResponse(result, Pair.class);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldBeAbleToDeleteAProgrammer() throws Exception {
        String programmerName = "Jeff";
        List<Programmer> programmers = getProgrammers("Heather", "Anu", "Paul", "Jeff");
        getUnicorns(programmers, "Mo", "Larry", "Curly", "Bozo");

        mockMvc.perform(delete("/programmers/" + programmerName))
                .andExpect(status().isOk());

        List<String> programmerNames = jdbcTemplate.query("select name from programmer",
                (r, rowNum) -> r.getString("name"));

        assertThat(programmerNames, not(hasItem(programmerName)));
    }

    private <T> T mapResponse(MvcResult result, Class<T> valueType) throws java.io.IOException {
        String body = result.getResponse().getContentAsString();
        return mapper.readValue(body, valueType);
    }

    private List<Pair> getPairs(List<Programmer> programmers, List<Unicorn> unicorns) {
        return IntStream.range(
                0, min(programmers.size(), unicorns.size()))
                .mapToObj(i -> Pair.builder()
                        .withProgrammer(programmers.get(i))
                        .withUnicorn(unicorns.get(i)).build()).collect(toList());
    }

    private List<Programmer> getProgrammers(String... names) {
        return Arrays.stream(names).map(name -> builder().withName(name).build())
                .map(programmer -> {
                    programmerTestFixture.insert(programmer);
                    return Programmer.builder().withName(programmer.getName()).build();
                }).collect(toList());
    }

    private List<Unicorn> getUnicorns(List<Programmer> programmers, String... names) {
        return IntStream.range(
                0, min(programmers.size(), names.length))
                .mapToObj(i -> com.testdb.testhelpers.Unicorn.builder()
                        .withName(names[i]).withProgrammer(builder()
                                .withName(programmers.get(i).getName()).build()).build())
                .map(unicorn -> {
                    unicornTestFixture.insert(unicorn);
                    return Unicorn.builder().withName(unicorn.getName()).build();
                }).collect(toList());
    }


}
