package com.efm

import com.efm.room.RoomPosition

enum class DirectionX
{
    right, left
}

enum class DirectionY
{
    up, down
}

enum class Direction4
{
    up, right, down, left;
    
    fun opposite() : Direction4
    {
        return when (this)
        {
            up    -> down
            right -> left
            down  -> up
            left  -> right
        }
    }
    
    fun toDirection8() : Direction8
    {
        return when (this)
        {
            up    -> Direction8.up
            right -> Direction8.right
            down  -> Direction8.down
            left  -> Direction8.left
        }
    }
}

enum class Direction8
{
    up, upRight, right, downRight, down, downLeft, left, upLeft;
    
    fun opposite() : Direction8
    {
        return when (this)
        {
            up        -> down
            upRight   -> downLeft
            right     -> left
            downRight -> upLeft
            down      -> up
            downLeft  -> upRight
            left      -> right
            upLeft    -> downRight
        }
    }
    
    fun toDirection8() : Direction4?
    {
        return when (this)
        {
            up    -> Direction4.down
            right -> Direction4.left
            down  -> Direction4.up
            left  -> Direction4.right
            else  -> null
        }
    }
}

fun getDirectionX(from : RoomPosition, to : RoomPosition) : DirectionX?
{
    return when
    {
        (from.x < to.x) -> DirectionX.right
        (from.x > to.x) -> DirectionX.left
        else                              -> null
    }
}

fun getDirectionY(from : RoomPosition, to : RoomPosition) : DirectionY?
{
    return when
    {
        (from.y < to.y) -> DirectionY.down
        (from.y > to.y) -> DirectionY.up
        else                              -> null
    }
}

fun getDirection8(from : RoomPosition, to : RoomPosition) : Direction8?
{
    val directionX = getDirectionX(from, to)
    val directionY = getDirectionY(from, to)
    
    return when
    {
        directionY == DirectionY.up && directionX == null               -> Direction8.up
        directionY == DirectionY.up && directionX == DirectionX.right   -> Direction8.upRight
        directionY == null && directionX == DirectionX.right            -> Direction8.right
        directionY == DirectionY.down && directionX == DirectionX.right -> Direction8.downRight
        directionY == DirectionY.down && directionX == null             -> Direction8.down
        directionY == DirectionY.down && directionX == DirectionX.left  -> Direction8.downLeft
        directionY == null && directionX == DirectionX.left             -> Direction8.left
        directionY == DirectionY.up && directionX == DirectionX.left    -> Direction8.upLeft
        else                                                            -> null
    }
}

fun getDirection4(from : RoomPosition, to : RoomPosition) : Direction4?
{
    val directionX = getDirectionX(from, to)
    val directionY = getDirectionY(from, to)
    
    return when
    {
        directionY == DirectionY.up && directionX == null               -> Direction4.up
        directionY == null && directionX == DirectionX.right            -> Direction4.right
        directionY == DirectionY.down && directionX == null             -> Direction4.down
        directionY == null && directionX == DirectionX.left             -> Direction4.left
        else                                                            -> null
    }
}
