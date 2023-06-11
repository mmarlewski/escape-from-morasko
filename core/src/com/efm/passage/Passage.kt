package com.efm.passage

import com.efm.Direction4
import com.efm.level.Level
import com.efm.room.Room
import com.efm.room.RoomPosition

interface Passage

/**
 * Possibility to end current Level and load next Level. Used to put Exits in Rooms.
 * @param originRoom Room that the Passage is in.
 * @param originPosition Position within originRoom.
 * @param targetLevel Level that should be loaded.
 * @param isActive Can the RoomPassage be used.
 */
class LevelPassage(
        val originRoom : Room,
        val originDirection : Direction4,
        val targetLevel : Level,
        var isActive : Boolean = true
                  ) : Passage

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
        val directionA : Direction4,
        val roomB : Room,
        val positionB : RoomPosition,
        val directionB : Direction4 = directionA.opposite(),
        var isActive : Boolean = true,
                 ) : Passage
