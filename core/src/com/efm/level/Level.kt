package com.efm.level

import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.room.Room
import com.efm.room.RoomPosition

/**
 * Level is a set of connected Rooms
 */
class Level(var name : String) : Json.Serializable
{
    var startingRoom : Room? = null
    val startingPosition = RoomPosition()
    val rooms : MutableList<Room> = mutableListOf()
    
    fun addRoom(room : Room)
    {
        rooms.add(room)
    }
    
    // for serializing
    
    constructor() : this("")
    
    override fun write(json : Json?)
    {
        if (json != null)
        {
            json.writeValue("name", this.name)
            json.writeValue("startingRoomName", this.startingRoom?.name ?: "")
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
            if (jsonStartingRoomName != null) this.startingRoom = this.rooms.find { it.name == jsonStartingRoomName }
        }
    }
}
