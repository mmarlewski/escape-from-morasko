package com.efm.stackableMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.assets.Textures
import com.efm.item.StackableMapItem
import com.efm.room.Room
import com.efm.room.RoomPosition

class TeleportationTool(
        override var amount : Int = 1
                       ) : StackableMapItem
{
    override val name : String = "Teleportation Tool"
    override val maxAmount : Int = 1
    override val baseAPUseCost : Int = 0
    
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
        //teleport to chosen space within the room
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
