package com.example.cleartrip_social_media.models;

import lombok.Getter;
import lombok.Setter;

import java.time.Month;

@Getter
@Setter
public class DateOfBirth {
    private int day;
    private Month month;
    private int year;
}
