package com.efm.level

import com.efm.passage.LevelPassage
import com.efm.passage.RoomPassage
import com.efm.room.Room
import com.efm.room.RoomPosition

/**
 * Consists of Rooms connected with Passages. Part of a world.
 * @property rooms List of Rooms in the Level.
 * @property startingRoom Room in which the Hero is at the beginning of the Level.
 * @property startingPosition Position in startingRoom where the Hero is at the beginning of the Level.
 * @property roomPassages RoomPassages between Rooms in the Level. Used to put Exits in Rooms.
 * @property levelPassages LevelPassages in Rooms that lead to another Levels. Used to put Exits in Rooms.
 */
class Level(
        val name : String,
        private val rooms : MutableList<Room> = mutableListOf(),
        private val roomPassages : MutableList<RoomPassage> = mutableListOf(),
        private val levelPassages : MutableList<LevelPassage> = mutableListOf()
           )
{
    private lateinit var startingRoom : Room
    private val startingPosition = RoomPosition()
    
    fun getRooms() : List<Room>
    {
        return rooms
    }
    
    fun addRoom(room : Room)
    {
        rooms.add(room)
    }
    
    fun getStartingRoom() : Room
    {
        return startingRoom
    }
    
    fun changeStartingRoom(room : Room)
    {
        startingRoom = room
    }
    
    fun getStartingPosition() : RoomPosition
    {
        return startingPosition
    }
    
    fun changeStartingPosition(x : Int, y : Int)
    {
        startingPosition.x = x
        startingPosition.y = y
    }
    
    fun changeStartingPosition(position : RoomPosition)
    {
        startingPosition.set(position)
    }
}
