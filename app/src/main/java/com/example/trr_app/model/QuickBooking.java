package com.example.trr_app.model;

public class QuickBooking {
    public String customerName,bookingDateRange,customerContact,headCount,specialNote,bookingType;
    public RoomReserve roomReserve;

    public QuickBooking() {
    }

    public QuickBooking(String customerName, String bookingDateRange, String customerContact, String headCount, String specialNote, String bookingType, RoomReserve roomReserve) {
        this.customerName = customerName;
        this.bookingDateRange = bookingDateRange;
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

    @Override
    public String toString() {
        return "QuickBooking{" +
                "customerName='" + customerName + '\'' +
                ", bookingDateRange='" + bookingDateRange + '\'' +
                ", customerContact='" + customerContact + '\'' +
                ", headCount='" + headCount + '\'' +
                ", specialNote='" + specialNote + '\'' +
                ", bookingType='" + bookingType + '\'' +
                ", roomReserve=" + roomReserve +
                '}';
    }
}
