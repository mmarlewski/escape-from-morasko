package com.efm.stackableMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.Direction4
import com.efm.item.StackableMapItem
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition

class StunTrap(
        override var amount : Int = 1
              ) : StackableMapItem
{
    override val name : String = "Stun Trap"
    override val maxAmount : Int = 10
    override val baseAPUseCost : Int = 5
    
    override fun getTexture() : Texture?
    {
        return null
    }
    
    override fun selected()
    {
    }
    
    override fun confirmed()
    {
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        //stop enemies from moving when they step on it
    }
    
    fun possiblePlacementSpaces() : MutableList<RoomPosition>
    {
        val possibleSpaces = mutableListOf<RoomPosition>()
        val heroPos = World.hero.position
        for (i in -1..1)
        {
            for (j in -1..1)
            {
                possibleSpaces.add((heroPos.positionOffsetBy(i, Direction4.up)).positionOffsetBy(j, Direction4.left))
            }
        }
        
        return possibleSpaces
    }
    
    override fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    {
        val positions = mutableListOf<RoomPosition>()
        
        return positions.toList()
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        val positions = mutableListOf<RoomPosition>()
        
        return positions.toList()
    }
}
