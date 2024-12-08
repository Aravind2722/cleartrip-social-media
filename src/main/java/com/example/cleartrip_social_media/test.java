package com.example.cleartrip_social_media;

import com.example.cleartrip_social_media.utilities.RelativeTimeFormatter;

import java.time.LocalDateTime;

public class test {
    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.now().minusMonths(1).minusDays(30);
        System.out.println(RelativeTimeFormatter.getRelativeTime(time));
    }
}
