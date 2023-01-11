package com.hackaton.kurly;

import lombok.Getter;

@Getter
public class TestDto {
    private String name;
    private int number;

    public TestDto(String name, int number) {
        this.name = name;
        this.number = number;
    }
}
