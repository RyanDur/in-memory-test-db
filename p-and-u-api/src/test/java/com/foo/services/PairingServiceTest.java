package com.foo.services;

import com.db.ProgrammersAndUnicorns;
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
        String programmer = "Eric";
        String unicorn = "Sassy Fancy Feet";
        Pair expeted = Pair.builder().withProgrammer(programmer).withUnicorn(unicorn).build();
        UnicornGenerator unicornGenerator = mock(UnicornGenerator.class);
        ProgrammersAndUnicorns programmersAndUnicorns = mock(ProgrammersAndUnicorns.class);

        when(unicornGenerator.create()).thenReturn(unicorn);
        PairingService service = new PairingService(programmersAndUnicorns, unicornGenerator);

        Pair actual = service.createPairFor(programmer);

        assertThat(actual, is(equalTo(expeted)));
        verify(programmersAndUnicorns).add(unicorn, programmer);
    }
}