package com.qaitsolutions.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;

@AllArgsConstructor @Getter
public enum PetType {
    BIRD("bird"),
    CAT("cat"),
    DOG("dog"),
    HAMSTER("hamster"),
    LIZARD("lizard"),
    SNAKE("snake");

    private final String identifier;

    public static PetType getRandom() {
        var list = Arrays.asList(values());
        Collections.shuffle(list);

        return list.get(0);
    }
}
