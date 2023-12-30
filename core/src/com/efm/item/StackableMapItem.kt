package com.efm.item

import com.efm.room.Room
import com.efm.room.RoomPosition

/**
 * Items held in stacks, used on game map
 */
interface StackableMapItem : StackableItem
{
    val statsDescription : String
    fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    /** Logic executed after the use of Item has been confirmed */
    fun use(room : Room, targetPosition : RoomPosition)
}
