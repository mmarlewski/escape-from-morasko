package com.efm.stackableMapItems

import com.efm.item.StackableMapItem

class TeleportationTool(
        override var amount : Int = 1
                       ) : StackableMapItem
{
    override val name : String = "Teleportation Tool"
    override val maxAmount : Int = 1
    override val baseAPUseCost : Int = 0
    override fun selected()
    {
    }
    
    override fun confirmed()
    {
    }
    
    override fun use()
    {
        //teleport to chosen space within the room
    }
    
}