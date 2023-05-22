package com.efm.multiUseMapItems

import com.efm.Direction
import com.efm.item.MultiUseMapItem
import com.efm.level.World
import com.efm.room.RoomPosition

class WoodenSword : MultiUseMapItem
{
    override val name : String = "Wooden Sword"
    override var baseAPUseCost : Int = 2
    override var durability : Int = 20
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
        for (i in -1..1)
        {
            for (j in -1..1)
            {
                affectedSpaces.add((heroPos.positionOffsetBy(i, Direction.up)).positionOffsetBy(j, Direction.left))
            }
        }
        
        return affectedSpaces
    }
}