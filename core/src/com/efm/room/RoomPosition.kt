package com.efm.room

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.Direction4
import com.efm.Direction8

/**
 * Position within a Room starting at top left corner.
 * @param x horizontal
 * @param y vertical
 */
data class RoomPosition(var x : Int = 0, var y : Int = 0) : Json.Serializable
{
    fun set(newX : Int, newY : Int)
    {
        x = newX
        y = newY
    }
    
    fun set(roomPosition : RoomPosition)
    {
        set(roomPosition.x, roomPosition.y)
    }
    
    fun positionOffsetBy(by : Int, direction : Direction4) : RoomPosition
    {
        return when (direction)
        {
            Direction4.up    -> RoomPosition(x, y - by)
            Direction4.right -> RoomPosition(x + by, y)
            Direction4.down  -> RoomPosition(x, y + by)
            Direction4.left  -> RoomPosition(x - by, y)
        }
    }
    
    fun adjacentPosition(direction : Direction4) : RoomPosition
    {
        return positionOffsetBy(1, direction)
    }
    
    fun positionOffsetBy(by : Int, direction : Direction8) : RoomPosition
    {
        return when (direction)
        {
            Direction8.up        -> RoomPosition(x, y - by)
            Direction8.upRight   -> RoomPosition(x + by, y - by)
            Direction8.right     -> RoomPosition(x + by, y)
            Direction8.downRight -> RoomPosition(x + by, y + by)
            Direction8.down      -> RoomPosition(x, y + by)
            Direction8.downLeft  -> RoomPosition(x - by, y + by)
            Direction8.left      -> RoomPosition(x - by, y)
            Direction8.upLeft    -> RoomPosition(x - by, y - by)
        }
    }
    
    fun adjacentPosition(direction : Direction8) : RoomPosition
    {
        return positionOffsetBy(1, direction)
    }
    
    fun surroundingPositions(radius : Int) : List<RoomPosition>
    {
        var result = mutableListOf<RoomPosition>()
        for (i in -radius..radius)
        {
            for (j in -radius..radius)
            {
                result.add(RoomPosition(x + i, y + j))
            }
        }
        return result
    }
    
    // for serializing
    
    constructor() : this(0, 0)
    
    override fun write(json : Json?)
    {
        if (json != null)
        {
            json.writeValue("x", this.x)
            json.writeValue("y", this.y)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        if (json != null)
        {
            val jsonX = json.readValue("x", Int::class.java, jsonData)
            if (jsonX != null) this.x = jsonX
            val jsonY = json.readValue("y", Int::class.java, jsonData)
            if (jsonY != null) this.y = jsonY
        }
    }
}

fun RoomPosition.toVector2() : Vector2
{
    return Vector2(x.toFloat(), y.toFloat())
}

fun Vector2.toRoomPosition() : RoomPosition
{
    return RoomPosition(x.toInt(), y.toInt())
}
