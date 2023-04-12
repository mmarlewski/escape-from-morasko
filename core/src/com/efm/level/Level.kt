package com.efm.level

import com.efm.room.Room
import com.efm.room.RoomCoordinates

/**
 * Consists of Rooms connected with Passages. Part of a world.
 * @param rooms List of Rooms in the Level.
 * @param startingRoom Room in which the Hero is at the beginning of the Level.
 * @param startingRoomHeroCoordinates Position in startingRoom where the Hero is at the beginning of the Level.
 */
class Level(val rooms : List<Room>, val startingRoom : Room, val startingRoomHeroCoordinates : RoomCoordinates)