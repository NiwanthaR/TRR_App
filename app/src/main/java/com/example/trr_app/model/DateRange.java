package com.example.trr_app.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class DateRange {
    private String startDate;
    private String endDate;

    //not using

    public DateRange(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Date getStartDateAsDate() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(startDate);
    }

    public Date getEndDateAsDate() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(endDate);
    }

    public static void main(String[] args) throws ParseException {
        String startDate = "2024-02-01";
        String endDate = "2024-02-05";
        DateRange dateRange = new DateRange(startDate, endDate);
        System.out.println("Date Range: " + dateRange.getStartDate() + " to " + dateRange.getEndDate());
        System.out.println("Start Date as Date: " + dateRange.getStartDateAsDate());
        System.out.println("End Date as Date: " + dateRange.getEndDateAsDate());
    }
}
