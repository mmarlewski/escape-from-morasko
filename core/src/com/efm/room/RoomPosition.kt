package com.efm.room

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
