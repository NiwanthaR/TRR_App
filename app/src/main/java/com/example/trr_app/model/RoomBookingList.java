package com.example.trr_app.model;

public class RoomBookingList {
    //declaration
    public RoomReserve roomReserve;
    public String bookedId;

    public RoomBookingList() {
    }

    public RoomBookingList(RoomReserve roomReserve, String bookedId) {
        this.roomReserve = roomReserve;
        this.bookedId = bookedId;
    }

    public RoomReserve getRoomReserve() {
        return roomReserve;
    }

    public void setRoomReserve(RoomReserve roomReserve) {
        this.roomReserve = roomReserve;
    }

    public String getBookedId() {
        return bookedId;
    }

    public void setBookedId(String bookedId) {
        this.bookedId = bookedId;
    }
}
