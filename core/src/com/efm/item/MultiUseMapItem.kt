package com.efm.item

import com.efm.room.RoomPosition

/**
 * Swords, bows, other weapons
 */
interface MultiUseMapItem : Item
{
    var durability : Int
    val durabilityUseCost : Int
    
    fun affectedSpaces() : MutableList<RoomPosition>
}