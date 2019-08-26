package com.github.biuld.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class Time {

    private final String beginTime = " 00:00:00";
    private final String endTime = " 11:59:59";
    private final DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDate today;

    public Time() {
        today=LocalDate.now();
    }

    public Time(LocalDate today) {
        this.today=today;
    }

    public String getTodayBegin() {
        return today.format(dateFormatter)+beginTime;
    }

    public String getTodayEnd() {
        return today.format(dateFormatter)+endTime;
    }

    public String getWeekBegin() {
        return today.minus(getWeekBeginOffset(today.getDayOfWeek()), ChronoUnit.DAYS).format(dateFormatter)+beginTime;
    }

    public String getWeekEnd() {
        return today.plus(getWeekEndOffset(today.getDayOfWeek()), ChronoUnit.DAYS).format(dateFormatter)+endTime;
    }

    public String getMonthBegin() {
        return today.with(TemporalAdjusters.firstDayOfMonth()).format(dateFormatter)+beginTime;
    }

    public String getMonthEnd() {
        return today.with(TemporalAdjusters.lastDayOfMonth()).format(dateFormatter)+endTime;
    }

    public String getYearBegin() {
        return today.with(TemporalAdjusters.firstDayOfYear()).format(dateFormatter)+beginTime;
    }

    public String getYearEnd() {
        return today.with(TemporalAdjusters.lastDayOfYear()).format(dateFormatter)+endTime;
    }

    private int getWeekBeginOffset(DayOfWeek dayOfWeek) {
        int offset=0;

        switch (dayOfWeek) {
            case MONDAY:
                break;
            case TUESDAY:
                offset=1;
                break;
            case WEDNESDAY:
                offset=2;
                break;
            case THURSDAY:
                offset=3;
                break;
            case FRIDAY:
                offset=4;
                break;
            case SATURDAY:
                offset=5;
                break;
            case SUNDAY:
                offset=6;
                break;
        }

        return offset;
    }

    private int getWeekEndOffset(DayOfWeek dayOfWeek) {
        int offset=6;

        switch (dayOfWeek) {
            case MONDAY:
                break;
            case TUESDAY:
                offset=5;
                break;
            case WEDNESDAY:
                offset=4;
                break;
            case THURSDAY:
                offset=3;
                break;
            case FRIDAY:
                offset=2;
                break;
            case SATURDAY:
                offset=1;
                break;
            case SUNDAY:
                offset=0;
                break;
        }

        return offset;
    }
}
