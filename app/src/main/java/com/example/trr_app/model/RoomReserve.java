package com.example.trr_app.model;

import androidx.annotation.NonNull;

public class RoomReserve {
    public Boolean room01,room02,room03,room04,room05,room06,room07;

    public RoomReserve() {
    }

    public RoomReserve(Boolean room01, Boolean room02, Boolean room03, Boolean room04, Boolean room05, Boolean room06, Boolean room07) {
        this.room01 = room01;
        this.room02 = room02;
        this.room03 = room03;
        this.room04 = room04;
        this.room05 = room05;
        this.room06 = room06;
        this.room07 = room07;
    }

//    public RoomReserve(Boolean room01, Boolean room02, Boolean room03, Boolean room04, Boolean room05) {
//        this.room01 = room01;
//        this.room02 = room02;
//        this.room03 = room03;
//        this.room04 = room04;
//        this.room05 = room05;
//    }

    public Boolean getRoom01() {
        return room01;
    }

    public void setRoom01(Boolean room01) {
        this.room01 = room01;
    }

    public Boolean getRoom02() {
        return room02;
    }

    public void setRoom02(Boolean room02) {
        this.room02 = room02;
    }

    public Boolean getRoom03() {
        return room03;
    }

    public void setRoom03(Boolean room03) {
        this.room03 = room03;
    }

    public Boolean getRoom04() {
        return room04;
    }

    public void setRoom04(Boolean room04) {
        this.room04 = room04;
    }

    public Boolean getRoom05() {
        return room05;
    }

    public void setRoom05(Boolean room05) {
        this.room05 = room05;
    }

    public Boolean getRoom06() {
        return room06;
    }

    public void setRoom06(Boolean room06) {
        this.room06 = room06;
    }

    public Boolean getRoom07() {
        return room07;
    }

    public void setRoom07(Boolean room07) {
        this.room07 = room07;
    }

    @Override
    public String toString() {
        return "RoomReserve{" +
                "room01=" + room01 +
                ", room02=" + room02 +
                ", room03=" + room03 +
                ", room04=" + room04 +
                ", room05=" + room05 +
                ", room06=" + room06 +
                ", room07=" + room07 +
                '}';
    }
}
