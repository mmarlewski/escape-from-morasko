package com.efm

import com.efm.room.RoomPosition

/**
 * returns positions corresponding to straight line on square grid
 */
fun getLineFromPositionInDirectionPositions(from : RoomPosition, direction : Direction8, length : Int) : List<RoomPosition>
{
    val positions = mutableListOf<RoomPosition>()
    
    val position = from.copy()
    val newPosition = from.copy()
    for (i in 0 until length)
    {
        newPosition.set(position.adjacentPosition(direction))
        positions.add(newPosition.copy())
        position.set(newPosition)
    }
    
    return positions
}

/**
 * returns positions corresponding to square perimeter on square grid
 */
fun getSquarePerimeterPositions(center : RoomPosition, radius : Int) : List<RoomPosition>
{
    if (radius < 0)
    {
        return listOf()
    }
    
    val positions = mutableListOf<RoomPosition>()
    
    val newPosition = center.positionOffsetBy(radius, Direction4.up).positionOffsetBy(radius, Direction4.left)
    for (i in 0 until radius * 2)
    {
        positions.add(newPosition.positionOffsetBy(i, Direction4.right))
    }
    newPosition.set(newPosition.positionOffsetBy(radius * 2, Direction4.right))
    for (i in 0 until radius * 2)
    {
        positions.add(newPosition.positionOffsetBy(i, Direction4.down))
    }
    newPosition.set(newPosition.positionOffsetBy(radius * 2, Direction4.down))
    for (i in 0 until radius * 2)
    {
        positions.add(newPosition.positionOffsetBy(i, Direction4.left))
    }
    newPosition.set(newPosition.positionOffsetBy(radius * 2, Direction4.left))
    for (i in 0 until radius * 2)
    {
        positions.add(newPosition.positionOffsetBy(i, Direction4.up))
    }
    
    return positions.toList()
}

/**
 * returns positions corresponding to square on square grid including perimeter
 */
fun getSquareAreaPositions(center : RoomPosition, radius : Int) : List<RoomPosition>
{
    if (radius < 0)
    {
        return listOf()
    }
    
    val positions = mutableListOf<RoomPosition>()
    positions.add(center)
    
    for (i in 0..radius)
    {
        positions.addAll(getSquarePerimeterPositions(center, i))
    }
    
    return positions.toList()
}
