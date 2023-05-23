package com.efm.multiUseMapItems

import com.badlogic.gdx.math.Vector2
import com.efm.item.MultiUseMapItem
import com.efm.level.World
import com.efm.room.*
import kotlin.math.abs

class Sledgehammer : MultiUseMapItem
{
    override val name : String = "Sledgehammer"
    override var baseAPUseCost : Int = 6
    override var durability : Int = 50
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
        //use()
    }
    
    override fun use(room: Room, targetPosition : RoomPosition)
    {
        //odejmij ap -= baseAPUseCost
        //odejmij durability -= durabilityCost
        //zadaj obrażenia przeciwnikom w zasięgu
    }
    
    override fun getTargetPositions(source:RoomPosition) : List<RoomPosition>
    {
        val possiblePositions = mutableListOf<RoomPosition>()
        
        return possiblePositions.toList()
    }
    
    override fun getAffectedPositions(targetPosition:RoomPosition) : List<RoomPosition>
    {
        val affectedSpaces = mutableListOf<RoomPosition>()
        val heroPos = World.hero.position
        for (posX in 0..World.currentRoom.heightInSpaces)
        {
            for (posY in 0..World.currentRoom.widthInSpaces)
            {
                val distance = abs(heroPos.x - posX).coerceAtLeast(abs(heroPos.y - posY))
                if (distance in 1..3)
                {
                    affectedSpaces.add(Vector2(posX.toFloat(), posY.toFloat()).toRoomPosition())
                }
            }
        }
        print(affectedSpaces)
        return affectedSpaces
    }
}