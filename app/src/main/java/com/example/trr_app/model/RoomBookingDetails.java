package com.example.trr_app.model;

import java.util.HashMap;
import java.util.Map;

public class RoomBookingDetails{
    private Map<String, Boolean> roomStatus = new HashMap<>();
    private Map<String, String> roomBookedIds = new HashMap<>();

    public RoomBookingDetails() {
        // Initialize rooms as not reserved
        roomStatus.put("R001", false);
        roomStatus.put("R002", false);
        roomStatus.put("R003", false);
        roomStatus.put("R004", false);
        roomStatus.put("R005", false);
        roomStatus.put("R006", false);
        roomStatus.put("R007", false);
    }

    public void reserveRoom(String room, String bookedId) {
        // Update room reservation status
        roomStatus.put(room, true);
        // Associate bookedId with the reserved room
        roomBookedIds.put(room, bookedId);
    }

    public boolean isRoomReserved(String room) {
        return roomStatus.getOrDefault(room, false);
    }

    public String getBookedIdForRoom(String room) {
        return roomBookedIds.get(room);
    }

    // Other methods and getters /setters as needed
}
