package com.example.cleartrip_social_media.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateOfBirthDTO {
    private int day;
    private String month;
    private int year;
}
