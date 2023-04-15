package com.efm.passage

import com.efm.room.Room
import com.efm.room.RoomCoordinates

/**
 * Possibility to move between Rooms. Hero can be passing it in both ways. Used to put Exits in Rooms.
 * @param roomA One of the two Rooms that the RoomPassage is in.
 * @param roomCoordinatesA Position within roomA.
 * @param roomB Other one of the two Rooms that the RoomPassage is in.
 * @param roomCoordinatesB Position within roomB.
 * @param isActive Can the RoomPassage be used.
 */
class RoomPassage(
        val roomA : Room,
        val roomCoordinatesA : RoomCoordinates,
        val roomB : Room,
        val roomCoordinatesB : RoomCoordinates,
        var isActive : Boolean
                 )