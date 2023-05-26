package com.efm.stackableSelfItems

import com.efm.item.StackableSelfItem
import com.efm.level.World

class Shrooms(
        override var amount : Int = 1
             ) : StackableSelfItem
{
    override val name : String = "Shrooms"
    override val maxAmount : Int = 64
    override val baseAPUseCost : Int = 1
    val healAmount : Int = 2
    
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