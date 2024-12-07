package com.example.cleartrip_social_media;

import com.example.cleartrip_social_media.exceptions.InvalidDateOfBirthException;
import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class test {
    public static void main(String[] args) throws InvalidDateOfBirthException {
        UUID uuid1 = Generators.timeBasedEpochGenerator().generate(); // Version 7
// With JUG 5.0 added variation:
        UUID uuid2 = Generators.timeBasedEpochRandomGenerator().generate(); // Version 7 with per-call random values
        System.out.println(uuid1); // "0193a377-1f4e-7281-bb54-87c309a9a9cf"
        System.out.println(uuid2);
    }
}
