package com.example.cleartrip_social_media;

import com.example.cleartrip_social_media.exceptions.InvalidDateOfBirthException;

import java.util.HashSet;
import java.util.Set;

public class test {
    public static void main(String[] args) throws InvalidDateOfBirthException {
        Simple pivot = new Simple("harish");
        Set<Simple> set = new HashSet<>();

//        set.add(pivot);
        set.add(new Simple("harish"));
        set.add(new Simple("kanishka"));

        Simple pivot2 = new Simple("harish");

        System.out.println(pivot.equals(pivot2));
    }
}
