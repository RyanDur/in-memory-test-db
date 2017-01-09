package com.foo.services;

import com.db.ProgrammersAndUnicorns;
import com.foo.doamins.Pair;
import com.foo.doamins.Programmer;
import com.foo.doamins.Unicorn;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PairingServiceTest {


    @Test
    public void createPairFor_shouldSaveTheUnicornAndTheProgrammer() {
        String programmerName = "Eric";
        String unicornName = "Sassy Fancy Feet";
        Programmer programmer = Programmer.builder().withName(programmerName).build();
        Unicorn unicorn = Unicorn.builder().withName(unicornName).build();
        Pair expected = Pair.builder().withProgrammer(programmer).withUnicorn(unicorn).build();

        UnicornGenerator unicornGenerator = mock(UnicornGenerator.class);
        ProgrammersAndUnicorns programmersAndUnicorns = mock(ProgrammersAndUnicorns.class);

        when(unicornGenerator.create()).thenReturn(unicornName);
        PairingService service = new PairingService(programmersAndUnicorns, unicornGenerator);

        Pair actual = service.createPairFor(programmerName);

        assertThat(actual, is(equalTo(expected)));
        verify(programmersAndUnicorns).add(unicornName, programmerName);
    }
}