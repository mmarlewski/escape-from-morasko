package com.efm.stackableSelfItems

import com.efm.item.StackableSelfItem
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition

class Medkit(
        override var amount : Int = 1
            ) : StackableSelfItem
{
    override val name : String = "Medkit"
    override val maxAmount : Int = 64
    override val baseAPUseCost : Int = 4
    val healAmount : Int = 10
    
    override fun selected()
    {
    }
    
    override fun confirmed()
    {
    }
    
    override fun use()
    {
        World.hero.healCharacter(healAmount)
    }
}