package com.foo.services;

import com.db.ProgrammersAndUnicorns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PairingService {

    private final ProgrammersAndUnicorns programmersAndUnicorns;
    private final UnicornGenerator unicornGenerator;

    @Autowired
    public PairingService(ProgrammersAndUnicorns programmersAndUnicorns,
                          UnicornGenerator unicornGenerator) {
        this.programmersAndUnicorns = programmersAndUnicorns;
        this.unicornGenerator = unicornGenerator;
    }

    public Pair createPairFor(String programmer) {
        String unicorn = unicornGenerator.create();
        programmersAndUnicorns.add(unicorn, programmer);
        return Pair.builder().withProgrammer(programmer).withUnicorn(unicorn).build();
    }
}
