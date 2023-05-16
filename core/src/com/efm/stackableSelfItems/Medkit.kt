package com.efm.stackableSelfItems

import com.efm.item.StackableSelfItem
import com.efm.level.World

class Medkit(
        override val amount : Int = 1
            ) : StackableSelfItem
{
    override val name : String = "Medkit"
    override val maxAmount : Int = 64
    override var baseAPUseCost : Int = 4
    
    val healAmount : Int = 10
    
    override fun selected()
    {
        //migaj paskiem zycia
        // pasek.migaj()
        // stan.zmien
    }
    
    override fun confirmed()
    {
        //wodotryski
        use()
    }
    
    override fun use()
    {
        World.hero.healCharacter(healAmount)
    }
}