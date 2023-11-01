package com.example.trr_app.model;

public class RoomDetails {

    public String room_capacity,room_name,room_cost,room_condition,room_unic_code,washroom_type;

    public RoomDetails() {
    }

    public RoomDetails(String room_capacity, String room_name, String room_cost, String room_condition, String room_unic_code, String washroom_type) {
        this.room_capacity = room_capacity;
        this.room_name = room_name;
        this.room_cost = room_cost;
        this.room_condition = room_condition;
        this.room_unic_code = room_unic_code;
        this.washroom_type = washroom_type;
    }

    public String getRoom_capacity() {
        return room_capacity;
    }

    public void setRoom_capacity(String room_capacity) {
        this.room_capacity = room_capacity;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getRoom_cost() {
        return room_cost;
    }

    public void setRoom_cost(String room_cost) {
        this.room_cost = room_cost;
    }

    public String getRoom_condition() {
        return room_condition;
    }

    public void setRoom_condition(String room_condition) {
        this.room_condition = room_condition;
    }

    public String getRoom_unic_code() {
        return room_unic_code;
    }

    public void setRoom_unic_code(String room_unic_code) {
        this.room_unic_code = room_unic_code;
    }

    public String getWashroom_type() {
        return washroom_type;
    }

    public void setWashroom_type(String washroom_type) {
        this.washroom_type = washroom_type;
    }
}
