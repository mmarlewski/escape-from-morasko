package com.efm.multiUseMapItems

import com.efm.item.MultiUseMapItem
import com.efm.level.World
import com.efm.room.RoomPosition


class BasicWhip : MultiUseMapItem
{
    override val name : String = "Basic Whip"
    override var baseAPUseCost : Int = 4
    override var durability : Int = 15
    override val durabilityUseCost : Int = 1
    
    override fun selected()
    {
        //podświetl zasięg ataku
        //życie przeciwników w zasięgu miga ?
        //pasek ap miga
        //zmien stan
    }
    
    override fun confirmed()
    {
        use()
    }
    
    override fun use()
    {
        //odejmij ap -= baseAPUseCost
        //odejmij durability -= durabilityCost
        //zadaj obrażenia przeciwnikom w zasięgu
    }
    
    override fun affectedSpaces() : MutableList<RoomPosition>
    {
        val affectedSpaces = mutableListOf<RoomPosition>()
        val heroPos = World.hero.position
        for (posX in 0..World.currentRoom.heightInSpaces)
        {
            for (posY in 0..World.currentRoom.widthInSpaces)
            {
                //need info about direction
            }
        }
        
        return affectedSpaces
    }
}