package com.efm.room

/**
 * Interactive object found in a Room used to move to another Room.
 * @param originRoomCoordinates Position within the Room that the Passage is in.
 * @param endRoom Room that the Passage is leading into.
 * @param endRoomCoordinates Position where hero is transported within endRoom.
 * @param isActive Can the Passage be used.
 */
open class Passage(
        val originRoomCoordinates : RoomCoordinates,
        val endRoom : Room,
        val endRoomCoordinates : RoomCoordinates,
        var isActive : Boolean
                  )