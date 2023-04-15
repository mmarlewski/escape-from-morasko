package com.efm.level

import com.efm.passage.LevelPassage
import com.efm.passage.RoomPassage
import com.efm.room.Room
import com.efm.room.RoomCoordinates

/**
 * Consists of Rooms connected with Passages. Part of a world.
 * @param rooms List of Rooms in the Level.
 * @param startingRoom Room in which the Hero is at the beginning of the Level.
 * @param startingRoomHeroCoordinates Position in startingRoom where the Hero is at the beginning of the Level.
 * @param roomPassages RoomPassages between Rooms in the Level. Used to put Exits in Rooms.
 * @param levelPassages LevelPassages in Rooms that lead to another Levels. Used to put Exits in Rooms.
 */
class Level(
        val rooms : MutableList<Room>,
        val startingRoom : Room,
        val startingRoomHeroCoordinates : RoomCoordinates,
        val roomPassages : MutableList<RoomPassage>,
        val levelPassages : MutableList<LevelPassage>
           )