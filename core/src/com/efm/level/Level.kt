package com.efm.level

import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.Direction4
import com.efm.entities.exits.ExitImplementation
import com.efm.entities.exits.ExitStyle
import com.efm.entity.Enemy
import com.efm.passage.LevelPassage
import com.efm.passage.RoomPassage
import com.efm.room.*

/**
 * Consists of Rooms connected with Passages. Part of a world.
 * @property rooms List of Rooms in the Level.
 * @property startingRoom Room in which the Hero is at the beginning of the Level.
 * @property startingPosition Position in startingRoom where the Hero is at the beginning of the Level.
 * @property roomPassages RoomPassages between Rooms in the Level. Used to put Exits in Rooms.
 * @property levelPassages LevelPassages in Rooms that lead to another Levels. Used to put Exits in Rooms.
 */
class Level(var name : String) : Json.Serializable
{
    private lateinit var startingRoom : Room
    private val startingPosition = RoomPosition()
    val rooms : MutableList<Room> = mutableListOf()
    val roomPassages : MutableList<RoomPassage> = mutableListOf()
    val levelPassages : MutableList<LevelPassage> = mutableListOf()
    
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
    
    fun addRoomPassage(
            roomA : Room,
            positionA : RoomPosition,
            directionA : Direction4,
            roomB : Room,
            positionB : RoomPosition,
            exitStyle : ExitStyle = ExitStyle.stone,
            isActive : Boolean = true,
            exitABase : Base? = null,
            exitBBase : Base? = null
                      )
    {
        val passage = RoomPassage(this.name, roomA.name, positionA, directionA, roomB.name, positionB, isActive = isActive)
        val exitA = ExitImplementation(exitStyle, passage.directionA, passage)
        val exitB = ExitImplementation(exitStyle, passage.directionB, passage)
        if (exitABase != null) rooms.find { it.name == passage.roomAName }?.changeBaseAt(exitABase, passage.positionA)
        rooms.find { it.name == passage.roomAName }?.replaceEntityAt(exitA, passage.positionA)
        if (exitBBase != null) rooms.find { it.name == passage.roomBName }?.changeBaseAt(exitBBase, passage.positionB)
        rooms.find { it.name == passage.roomBName }?.replaceEntityAt(exitB, passage.positionB)
        this.roomPassages.add(passage)
    }
    
    // for serializing
    
    constructor() : this("")
    
    override fun write(json : Json?)
    {
        if (json != null)
        {
            json.writeValue("name", this.name)
            json.writeValue("startingRoomName", this.startingRoom.name)
            json.writeValue("startingPosition", this.startingPosition)
            json.writeValue("rooms", this.rooms)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        if (json != null)
        {
            val jsonName = json.readValue("name", String::class.java, jsonData)
            if (jsonName != null) this.name = jsonName
            
            val jsonStartingPosition = json.readValue("startingPosition", RoomPosition::class.java, jsonData)
            if (jsonStartingPosition is RoomPosition) this.startingPosition.set(jsonStartingPosition)
            
            val jsonRooms = json.readValue("rooms", List::class.java, jsonData)
            if (jsonRooms != null)
            {
                for (jsonRoom in jsonRooms)
                {
                    val room = jsonRoom as? Room
                    if (room != null) this.rooms.add(room)
                }
            }
            
            val jsonStartingRoomName = json.readValue("startingRoomName", String::class.java, jsonData)
            if (jsonStartingRoomName != null) this.startingRoom = this.rooms.find { it.name == jsonStartingRoomName }!!
        }
    }
}
