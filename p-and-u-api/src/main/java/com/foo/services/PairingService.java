package com.foo.services;

import com.db.ProgrammersAndUnicorns;
import com.foo.doamins.Pair;
import com.foo.doamins.Programmer;
import com.foo.doamins.Unicorn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;
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
        return createPair(asList(programmerName, unicornName));
    }

    public List<Programmer> getProgrammers() {
        return programmersAndUnicorns.getAllProgrammers().stream().map(this::createProgrammer).collect(toList());
    }

    public List<Unicorn> getUnicorns() {
        return programmersAndUnicorns.getAllUnicorns().stream().map(this::createUnicorn).collect(toList());
    }

    public List<Pair> getPairs() {
        return programmersAndUnicorns.getAllPairs().stream().map(this::createPair).collect(toList());
    }

    public Pair update(Unicorn unicorn, String programmerName) {
        return createPair(programmersAndUnicorns.update(unicorn.getName(), programmerName));
    }

    private Pair createPair(List<String> pair) {
        return Pair.builder()
                .withProgrammer(createProgrammer(pair.get(0)))
                .withUnicorn(createUnicorn(pair.get(1)))
                .build();
    }

    private Programmer createProgrammer(String name) {
        return Programmer.builder().withName(name).build();
    }

    private Unicorn createUnicorn(String name) {
        return Unicorn.builder().withName(name).build();
    }

    public void delete(String programmerName) {
        programmersAndUnicorns.delete(programmerName);
    }
}
