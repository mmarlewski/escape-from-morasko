package com.efm.stackableSelfItems

import com.efm.item.StackableSelfItem
import com.efm.level.World

class APPotion(
        override var amount : Int = 1
              ) : StackableSelfItem
{
    override val name : String = "Potion of Stamina"
    override val maxAmount : Int = 16
    override val baseAPUseCost : Int = 0
    val APRestoreAmount : Int = 4
    
    override fun selected()
    {
    }
    
    override fun confirmed()
    {
    }
    
    override fun use()
    {
        World.hero.spendAP(-APRestoreAmount)
    }
}