package com.example.cleartrip_social_media.utilities;

import java.time.Duration;
import java.time.LocalDateTime;

public class RelativeTimeFormatter {

    public static String getRelativeTime(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(dateTime, now);

        long seconds = duration.getSeconds();
        if (seconds < 60) {
            return seconds + " sec" + (seconds == 1 ? "" : "s") + " ago";
        }

        long minutes = duration.toMinutes();
        if (minutes < 60) {
            return minutes + " min" + (minutes == 1 ? "" : "s") + " ago";
        }

        long hours = duration.toHours();
        if (hours < 24) {
            return hours + " hour" + (hours == 1 ? "" : "s") + " ago";
        }

        long days = duration.toDays();
        if (days < 7) {
            return days + " day" + (days == 1 ? "" : "s") + " ago";
        }

        if (days < 30) {
            return (days / 7) + " week" + ((days / 7) == 1 ? "" : "s") + " ago";
        }

        if (days < 365) {
            return (days / 30) + " month" + ((days / 30) == 1 ? "" : "s") + " ago";
        }

        return (days / 365) + " year" + ((days / 365) == 1 ? "" : "s") + " ago";
    }

}
