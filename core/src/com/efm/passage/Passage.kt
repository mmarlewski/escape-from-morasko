package com.efm.passage

import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.Direction4
import com.efm.level.Level
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition

interface Passage : Json.Serializable

/**
 * Possibility to end current Level and load next Level. Used to put Exits in Rooms.
 * @param originRoom Room that the Passage is in.
 * @param originPosition Position within originRoom.
 * @param targetLevel Level that should be loaded.
 * @param isActive Can the RoomPassage be used.
 */
class LevelPassage(
        var originRoomName : String,
        var originPosition : RoomPosition,
        var originDirection : Direction4,
        var targetLevelName : String,
        var isActive : Boolean = true
                  ) : Passage
{
    // for serializing
    
    constructor() : this("", RoomPosition(-1, -1), Direction4.up, "", false)
    
    override fun write(json : Json?)
    {
        if (json != null)
        {
            json.writeValue("originRoomName", this.originRoomName)
            json.writeValue("originPosition", this.originPosition)
            json.writeValue("originDirection", this.originDirection)
            json.writeValue("targetLevelName", this.targetLevelName)
            json.writeValue("isActive", this.isActive)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        if (json != null)
        {
            val jsonOriginRoomName = json.readValue("originRoomName", String::class.java, jsonData)
            if (jsonOriginRoomName != null) this.originRoomName = jsonOriginRoomName
            
            val jsonOriginPosition = json.readValue("originPosition", RoomPosition::class.java, jsonData)
            if (jsonOriginPosition != null) this.originPosition = jsonOriginPosition
            
            val jsonOriginDirection = json.readValue("originDirection", Direction4::class.java, jsonData)
            if (jsonOriginDirection != null) this.originDirection = jsonOriginDirection
            
            val jsonTargetLevelName = json.readValue("targetLevelName", String::class.java, jsonData)
            if (jsonTargetLevelName != null) this.targetLevelName = jsonTargetLevelName
            
            val jsonIsActive = json.readValue("isActive", Boolean::class.java, jsonData)
            if (jsonIsActive != null) this.isActive = jsonIsActive
        }
    }
}

/**
 * Possibility to move between Rooms. Hero can be passing it in both ways. Used to put Exits in Rooms.
 * @param roomA One of the two Rooms that the RoomPassage is in.
 * @param positionA Position within roomA.
 * @param roomB Other one of the two Rooms that the RoomPassage is in.
 * @param positionB Position within roomB.
 * @param isActive Can the RoomPassage be used.
 */
class RoomPassage(
        var levelName : String,
        var roomAName : String,
        var positionA : RoomPosition,
        var directionA : Direction4,
        var roomBName : String,
        var positionB : RoomPosition,
        var directionB : Direction4 = directionA.opposite(),
        var isActive : Boolean = true,
                 ) : Passage
{
    // for serializing
    
    constructor() : this(
            "", "", RoomPosition(-1, -1), Direction4.up,
            "", RoomPosition(-1, -1), Direction4.up, false
                        )
    
    override fun write(json : Json?)
    {
        if (json != null)
        {
            json.writeValue("levelName", this.levelName)
            json.writeValue("roomAName", this.roomAName)
            json.writeValue("positionA", this.positionA)
            json.writeValue("directionA", this.directionA)
            json.writeValue("roomBName", this.roomBName)
            json.writeValue("positionB", this.positionB)
            json.writeValue("directionB", this.directionB)
            json.writeValue("isActive", this.isActive)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        if (json != null)
        {
            val jsonLevelName = json.readValue("levelName", String::class.java, jsonData)
            if (jsonLevelName != null) this.levelName = jsonLevelName
            
            val jsonRoomAName = json.readValue("roomAName", String::class.java, jsonData)
            if (jsonRoomAName != null) this.roomAName = jsonRoomAName
            
            val jsonPositionA = json.readValue("positionA", RoomPosition::class.java, jsonData)
            if (jsonPositionA != null) this.positionA.set(jsonPositionA)
            
            val jsonDirectionA = json.readValue("directionA", Direction4::class.java, jsonData)
            if (jsonDirectionA != null) this.directionA = jsonDirectionA
            
            val jsonRoomBName = json.readValue("roomBName", String::class.java, jsonData)
            if (jsonRoomBName != null) this.roomBName = jsonRoomBName
            
            val jsonPositionB = json.readValue("positionB", RoomPosition::class.java, jsonData)
            if (jsonPositionB != null) this.positionB.set(jsonPositionB)
            
            val jsonDirectionB = json.readValue("directionB", Direction4::class.java, jsonData)
            if (jsonDirectionB != null) this.directionB = jsonDirectionB
            
            val jsonIsActive = json.readValue("isActive", Boolean::class.java, jsonData)
            if (jsonIsActive != null) this.isActive = jsonIsActive
        }
    }
}
