package com.efm.item

import com.efm.room.Room
import com.efm.room.RoomPosition

/**
 * Item held in single unit, with durability, used on game map
 */
interface MultiUseMapItem : Item
{
    override val name : String
    var durability : Int
    var maxDurability : Int
    val durabilityUseCost : Int
    val statsDescription : String
    
    fun lowerDurability()
    {
        durability -= durabilityUseCost
        if (durability <= 0)
        {
            durability = 0
        }
    }
    
    fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    /** Logic executed after the use of Item has been confirmed */
    fun use(room : Room, targetPosition : RoomPosition)
    
    override fun clone() : MultiUseMapItem
    {
        val copy = Class.forName(this::class.qualifiedName).getConstructor().newInstance() as MultiUseMapItem
        copy.durability = this.durability
        return copy
    }
}
