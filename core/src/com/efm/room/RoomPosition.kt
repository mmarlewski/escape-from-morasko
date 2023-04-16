package com.efm.room

import com.badlogic.gdx.math.Vector2

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
}

fun RoomPosition.toVector2() : Vector2
{
    return Vector2(x.toFloat(), y.toFloat())
}

fun Vector2.toRoomPosition() : RoomPosition
{
    return RoomPosition(x.toInt(), y.toInt())
}
