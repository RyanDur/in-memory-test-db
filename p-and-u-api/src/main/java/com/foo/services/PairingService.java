package com.foo.services;

import com.db.ProgrammersAndUnicorns;
import com.foo.doamins.Pair;
import com.foo.doamins.Programmer;
import com.foo.doamins.Unicorn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

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

    public Pair createPairFor(String programmerName) {
        String unicornName = unicornGenerator.create();
        programmersAndUnicorns.add(unicornName, programmerName);
        Programmer programmer = Programmer.builder().withName(programmerName).build();
        Unicorn unicorn = Unicorn.builder().withName(unicornName).build();
        return Pair.builder().withProgrammer(programmer).withUnicorn(unicorn).build();
    }

    public List<Programmer> getProgrammers() {
        return programmersAndUnicorns.getAllProgrammers().stream()
                .map(name -> Programmer.builder().withName(name).build()).collect(toList());
    }

    public List<Unicorn> getUnicorns() {
        return programmersAndUnicorns.getAllUnicorns().stream()
                .map(name -> Unicorn.builder().withName(name).build()).collect(toList());
    }

    public List<Pair> getPairs() {
        return programmersAndUnicorns.getAllPairs().stream()
                .map(pair -> Pair.builder()
                        .withProgrammer(Programmer.builder().withName(pair.get(0)).build())
                        .withUnicorn(Unicorn.builder().withName(pair.get(1)).build())
                        .build()).collect(toList());
    }
}
