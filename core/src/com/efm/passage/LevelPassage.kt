package com.efm.passage

import com.efm.level.Level
import com.efm.room.Room
import com.efm.room.RoomCoordinates

/**
 * Possibility to end current Level and load next Level. Used to put Exits in Rooms.
 * @param originRoom Room that the Passage is in.
 * @param originRoomCoordinates Position within originRoom.
 * @param targetLevel Level that should be loaded.
 * @param isActive Can the RoomPassage be used.
 */
class LevelPassage(
        val originRoom : Room,
        val originRoomCoordinates : RoomCoordinates,
        val targetLevel : Level,
        var isActive : Boolean
                  )