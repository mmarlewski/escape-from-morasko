package com.efm.room

import com.badlogic.gdx.math.Vector2
import com.efm.Direction4

/**
 * Position within a Room starting at top left corner.
 * @param x horizontal
 * @param y vertical
 */
data class RoomPosition(var x : Int = 0, var y : Int = 0)
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
}

fun RoomPosition.toVector2() : Vector2
{
    return Vector2(x.toFloat(), y.toFloat())
}

fun Vector2.toRoomPosition() : RoomPosition
{
    return RoomPosition(x.toInt(), y.toInt())
}
