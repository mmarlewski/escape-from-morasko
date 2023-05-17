package com.efm.multiUseMapItems

import com.efm.item.MultiUseMapItem

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
}