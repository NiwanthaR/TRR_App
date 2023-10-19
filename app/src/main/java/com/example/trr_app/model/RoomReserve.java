package com.example.trr_app.model;

import androidx.annotation.NonNull;

public class RoomReserve {
    public Boolean Room01,Room02,Room03,Room04,Room05;

    public RoomReserve() {
    }

    public RoomReserve(Boolean room01, Boolean room02, Boolean room03, Boolean room04, Boolean room05) {
        Room01 = room01;
        Room02 = room02;
        Room03 = room03;
        Room04 = room04;
        Room05 = room05;
    }

    public Boolean getRoom01() {
        return Room01;
    }

    public void setRoom01(Boolean room01) {
        Room01 = room01;
    }

    public Boolean getRoom02() {
        return Room02;
    }

    public void setRoom02(Boolean room02) {
        Room02 = room02;
    }

    public Boolean getRoom03() {
        return Room03;
    }

    public void setRoom03(Boolean room03) {
        Room03 = room03;
    }

    public Boolean getRoom04() {
        return Room04;
    }

    public void setRoom04(Boolean room04) {
        Room04 = room04;
    }

    public Boolean getRoom05() {
        return Room05;
    }

    public void setRoom05(Boolean room05) {
        Room05 = room05;
    }

    @NonNull
    @Override
    public String toString() {
        return "RoomReserve{" +
                "Room01=" + Room01 +
                ", Room02=" + Room02 +
                ", Room03=" + Room03 +
                ", Room04=" + Room04 +
                ", Room05=" + Room05 +
                '}';
    }
}
