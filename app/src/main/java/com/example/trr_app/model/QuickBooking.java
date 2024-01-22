package com.example.trr_app.model;

public class QuickBooking {
    public String customerName,bookingDateRange,startDate,endDate,customerContact,headCount,specialNote,bookingType;
    public RoomReserve roomReserve;

    public QuickBooking() {
    }

    public QuickBooking(String customerName, String bookingDateRange, String startDate, String endDate, String customerContact, String headCount, String specialNote, String bookingType, RoomReserve roomReserve) {
        this.customerName = customerName;
        this.bookingDateRange = bookingDateRange;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerContact = customerContact;
        this.headCount = headCount;
        this.specialNote = specialNote;
        this.bookingType = bookingType;
        this.roomReserve = roomReserve;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBookingDateRange() {
        return bookingDateRange;
    }

    public void setBookingDateRange(String bookingDateRange) {
        this.bookingDateRange = bookingDateRange;
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


    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getHeadCount() {
        return headCount;
    }

    public void setHeadCount(String headCount) {
        this.headCount = headCount;
    }

    public String getSpecialNote() {
        return specialNote;
    }

    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public RoomReserve getRoomReserve() {
        return roomReserve;
    }

    public void setRoomReserve(RoomReserve roomReserve) {
        this.roomReserve = roomReserve;
    }
}
