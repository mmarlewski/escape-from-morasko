package com.efm

import com.efm.room.RoomPosition

fun getSquarePerimeterPositions(center : RoomPosition, radius : Int) : List<RoomPosition>
{
    if (radius < 0)
    {
        return listOf()
    }
    
    val positions = mutableListOf<RoomPosition>()
    
    val newPosition = center.positionOffsetBy(radius,Direction.up).positionOffsetBy(radius,Direction.left)
    for (i in 0..radius*2)
    {
        positions.add(newPosition.positionOffsetBy(i,Direction.right))
    }
    newPosition.set(newPosition.positionOffsetBy(radius*2,Direction.right))
    for (i in 0..radius*2)
    {
        positions.add(newPosition.positionOffsetBy(i,Direction.down))
    }
    newPosition.set(newPosition.positionOffsetBy(radius*2,Direction.down))
    for (i in 0..radius*2)
    {
        positions.add(newPosition.positionOffsetBy(i,Direction.left))
    }
    newPosition.set(newPosition.positionOffsetBy(radius*2,Direction.left))
    for (i in 0..radius*2)
    {
        positions.add(newPosition.positionOffsetBy(i,Direction.up))
    }
    
    return positions.toList()
}

fun getSquareAreaPositions(center : RoomPosition, radius : Int) : List<RoomPosition>
{
    if (radius < 0)
    {
        return listOf()
    }
    
    val positions = mutableListOf<RoomPosition>()
    
    for (i in 0..radius)
    {
        positions.addAll(getSquarePerimeterPositions(center, i))
    }
    
    return positions.toList()
}
