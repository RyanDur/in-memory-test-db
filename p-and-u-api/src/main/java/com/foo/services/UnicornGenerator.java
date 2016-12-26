package com.foo.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Service
public class UnicornGenerator {
    public static List<String> FIRST_NAME = asList("Sassy", "Flirty", "Sparkley", "Shy", "Fruity", "Tangy", "Flashy", "Wild", "Perfect", "Funny", "Silly", "Cutey", "Jazzy", "Twinkle", "Bubbly", "Lovely", "Crazy", "Dreamy", "Steamy", "Dainty", "Classy", "Sappy", "Troubled", "Fizzy", "Dippy", "Flaky");
    public static List<String> LAST_NAME = asList("Sugar-lips", "Honey-bunch", "Snookie-poo", "Toodle-bugs", "Baby-cakes", "Pumpkin-butt", "Skilly-willy", "Cutie-pie", "Bunny-boo", "Snicker-doo", "Lovey-dovey", "Cuddle-bug");
    private List<String> names;
    private Random random = new Random();

    public UnicornGenerator() {
        this.names = FIRST_NAME.stream()
                .flatMap(firstName -> LAST_NAME.stream()
                        .map(lastName -> firstName + " " + lastName))
                .collect(toList());
    }

    public String create() {
        int index = random.nextInt(names.size());
        return names.get(index);
    }
}
