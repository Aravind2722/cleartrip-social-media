package com.example.cleartrip_social_media.builders;


import com.example.cleartrip_social_media.exceptions.InvalidDateOfBirthException;
import com.example.cleartrip_social_media.models.DateOfBirth;

import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;

public class DateOfBirthBuilder {
    private int day;
    private String month;
    private int year;
    public static DateOfBirthBuilder getBuilder() {
        return new DateOfBirthBuilder();
    }

    public DateOfBirthBuilder setDay(int day) {
        this.day = day;
        return this;
    }

    public DateOfBirthBuilder setMonth(String month) {
        this.month = month;
        return this;
    }

    public DateOfBirthBuilder setYear(int year) {
        this.year = year;
        return this;
    }

    public DateOfBirth build() throws InvalidDateOfBirthException {

        if (!isYearValid()) throw new InvalidDateOfBirthException("Invalid year provided for date of birth: '" + this.year + "' !");
        Month monthType = getMonthType();
        if (monthType == null) throw new InvalidDateOfBirthException("Invalid month provided for date of birth: '" + this.month + "' !");
        if (!isDayValid(monthType)) throw new InvalidDateOfBirthException("Invalid day provided for date of birth: '" + this.day + "' !");

        DateOfBirth dob = new DateOfBirth();
        dob.setYear(this.year);
        dob.setMonth(monthType);
        dob.setDay(this.day);
        return dob;
    }
    private boolean isYearValid() {
        return (this.year <= LocalDate.now().getYear());
    }
    private Month getMonthType() {
        try {
            int monthNumber = Integer.parseInt(this.month);
            if ((monthNumber < 1) || (monthNumber > 12)) return null;
            return Month.of(monthNumber);
        } catch (NumberFormatException numberFormatException) {
            try {
                return Month.valueOf(this.month.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException illegalArgumentException) {
                return null;
            }
        }
    }
    private boolean isDayValid(Month monthType) {
        int maxDaysInMonth = getMaxDaysInMonth(monthType.ordinal()+1);

        return ((this.day >= 1) && (this.day <= maxDaysInMonth));
    }
    private int getMaxDaysInMonth(int monthNumber) {
        switch (monthNumber) {
            case 2:
                return (isLeapYear()) ? 29 : 28;
            case 4: case 6: case 9: case 11:
                return 30;
            default:
                return 31;
        }
    }

    private boolean isLeapYear() {
        return (
                ((this.year % 4 == 0) && (this.year % 100 != 0)) || (this.year % 400 == 0)
        );
    }
}
