package com.efm.item

import com.efm.room.Room
import com.efm.room.RoomPosition

/**
 * Swords, bows, other weapons
 */
interface MultiUseMapItem : Item
{
    var durability : Int
    val durabilityUseCost : Int
    
    fun getTargetPositions(source:RoomPosition) : List<RoomPosition>
    fun getAffectedPositions(targetPosition:RoomPosition) : List<RoomPosition>
    fun use(room: Room, targetPosition : RoomPosition)
}
