package com.example.trr_app.model;

import java.util.ArrayList;

public class SubmitBooking {
    public String booking_dateRange,checkIn,checkOut,headCount,roomsCount,bookingType,firstName,secondName,address,area,city,contact,identity,specialNote,mealList,featureList,roomList;
    public RoomReserve roomReserve;
    public SubmitBooking() {
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

    public String getHeadCount() {
        return headCount;
    }

    public void setHeadCount(String headCount) {
        this.headCount = headCount;
    }

    public String getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(String roomsCount) {
        this.roomsCount = roomsCount;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getSpecialNote() {
        return specialNote;
    }

    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }

    public String getMealList() {
        return mealList;
    }

    public void setMealList(String mealList) {
        this.mealList = mealList;
    }

    public String getFeatureList() {
        return featureList;
    }

    public void setFeatureList(String featureList) {
        this.featureList = featureList;
    }

    public String getRoomList() {
        return roomList;
    }

    public void setRoomList(String roomList) {
        this.roomList = roomList;
    }

    public RoomReserve getRoomReserve() {
        return roomReserve;
    }

    public void setRoomReserve(RoomReserve roomReserve) {
        this.roomReserve = roomReserve;
    }

    public SubmitBooking(String booking_dateRange, String checkIn, String checkOut, String headCount, String roomsCount, String bookingType, String firstName, String secondName, String address, String area, String city, String contact, String identity, String specialNote, String mealList, String featureList, String roomList, RoomReserve roomReserve) {
        this.booking_dateRange = booking_dateRange;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.headCount = headCount;
        this.roomsCount = roomsCount;
        this.bookingType = bookingType;
        this.firstName = firstName;
        this.secondName = secondName;
        this.address = address;
        this.area = area;
        this.city = city;
        this.contact = contact;
        this.identity = identity;
        this.specialNote = specialNote;
        this.mealList = mealList;
        this.featureList = featureList;
        this.roomList = roomList;
        this.roomReserve = roomReserve;
    }

    @Override
    public String toString() {
        return "SubmitBooking{" +
                "booking_dateRange='" + booking_dateRange + '\'' +
                ", checkIn='" + checkIn + '\'' +
                ", checkOut='" + checkOut + '\'' +
                ", headCount='" + headCount + '\'' +
                ", roomsCount='" + roomsCount + '\'' +
                ", bookingType='" + bookingType + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", address='" + address + '\'' +
                ", area='" + area + '\'' +
                ", city='" + city + '\'' +
                ", contact='" + contact + '\'' +
                ", identity='" + identity + '\'' +
                ", specialNote='" + specialNote + '\'' +
                ", mealList='" + mealList + '\'' +
                ", featureList='" + featureList + '\'' +
                ", roomList='" + roomList + '\'' +
                ", roomReserve=" + roomReserve +
                '}';
    }
}
