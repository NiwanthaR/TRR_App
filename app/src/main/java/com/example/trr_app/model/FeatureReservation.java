package com.example.trr_app.model;

public class FeatureReservation {
    public Boolean Chip01,Chip02,Chip03,Chip04,Chip05,Chip06;

    public Boolean getChip01() {
        return Chip01;
    }

    public void setChip01(Boolean chip01) {
        Chip01 = chip01;
    }

    public Boolean getChip02() {
        return Chip02;
    }

    public void setChip02(Boolean chip02) {
        Chip02 = chip02;
    }

    public Boolean getChip03() {
        return Chip03;
    }

    public void setChip03(Boolean chip03) {
        Chip03 = chip03;
    }

    public Boolean getChip04() {
        return Chip04;
    }

    public void setChip04(Boolean chip04) {
        Chip04 = chip04;
    }

    public Boolean getChip05() {
        return Chip05;
    }

    public void setChip05(Boolean chip05) {
        Chip05 = chip05;
    }

    public Boolean getChip06() {
        return Chip06;
    }

    public void setChip06(Boolean chip06) {
        Chip06 = chip06;
    }

    public FeatureReservation(Boolean chip01, Boolean chip02, Boolean chip03, Boolean chip04, Boolean chip05, Boolean chip06) {
        Chip01 = chip01;
        Chip02 = chip02;
        Chip03 = chip03;
        Chip04 = chip04;
        Chip05 = chip05;
        Chip06 = chip06;
    }

    @Override
    public String toString() {
        return "FeatureReservation{" +
                "Chip01=" + Chip01 +
                ", Chip02=" + Chip02 +
                ", Chip03=" + Chip03 +
                ", Chip04=" + Chip04 +
                ", Chip05=" + Chip05 +
                ", Chip06=" + Chip06 +
                '}';
    }
}
