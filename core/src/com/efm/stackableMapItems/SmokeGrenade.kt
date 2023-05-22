package com.efm.stackableMapItems

import com.efm.item.StackableMapItem

class SmokeGrenade(
        override var amount : Int = 1
                  ) : StackableMapItem
{
    override val name : String = "Smoke Grenade"
    override val maxAmount : Int = 4
    override val baseAPUseCost : Int = 6
    override fun selected()
    {
    }
    
    override fun confirmed()
    {
    }
    
    override fun use()
    {
        //disable detection range of enemies
    }
    
}