package com.efm.item

import com.efm.room.Room
import com.efm.room.RoomPosition

/**
 * Bombs etc
 */
interface StackableMapItem : Item
{
    val maxAmount : Int
    var amount : Int
    
    fun getTargetPositions(source: RoomPosition) : List<RoomPosition>
    fun getAffectedPositions(targetPosition: RoomPosition) : List<RoomPosition>
    fun use(room: Room, targetPosition : RoomPosition)
}
