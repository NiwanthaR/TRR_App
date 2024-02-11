package com.example.trr_app.model;

public class QuickBooking {
    public String customerName,booking_dateRange,checkIn,checkOut,customerContact,headCount,specialNote,bookingType,uniqueKey;
    public RoomReserve roomReserve;

    public String contact,firstName,secondName;

    public QuickBooking() {
    }

    public QuickBooking(String customerName, String booking_dateRange, String checkIn, String checkOut, String customerContact, String headCount, String specialNote, String bookingType, String uniqueKey, RoomReserve roomReserve) {
        this.customerName = customerName;
        this.booking_dateRange = booking_dateRange;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.customerContact = customerContact;
        this.headCount = headCount;
        this.specialNote = specialNote;
        this.bookingType = bookingType;
        this.uniqueKey = uniqueKey;
        this.roomReserve = roomReserve;
    }

    public QuickBooking(String customerName, String booking_dateRange, String checkIn, String checkOut, String customerContact, String headCount, String specialNote, String bookingType, String uniqueKey, RoomReserve roomReserve, String contact, String firstName, String secondName) {
        this.customerName = customerName;
        this.booking_dateRange = booking_dateRange;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.customerContact = customerContact;
        this.headCount = headCount;
        this.specialNote = specialNote;
        this.bookingType = bookingType;
        this.uniqueKey = uniqueKey;
        this.roomReserve = roomReserve;
        this.contact = contact;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBooking_dateRange() {
        return booking_dateRange;
    }

    public void setBooking_dateRange(String booking_dateRange) {
        this.booking_dateRange = booking_dateRange;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
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

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public RoomReserve getRoomReserve() {
        return roomReserve;
    }

    public void setRoomReserve(RoomReserve roomReserve) {
        this.roomReserve = roomReserve;
    }
}
