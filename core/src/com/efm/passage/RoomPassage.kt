package com.efm.passage

import com.efm.room.*

/**
 * Possibility to move between Rooms. Hero can be passing it in both ways. Used to put Exits in Rooms.
 * @param roomA One of the two Rooms that the RoomPassage is in.
 * @param positionA Position within roomA.
 * @param roomB Other one of the two Rooms that the RoomPassage is in.
 * @param positionB Position within roomB.
 * @param isActive Can the RoomPassage be used.
 */
class RoomPassage(
        val roomA : Room,
        val positionA : RoomPosition,
        val roomB : Room,
        val positionB : RoomPosition,
        var isActive : Boolean
                 )