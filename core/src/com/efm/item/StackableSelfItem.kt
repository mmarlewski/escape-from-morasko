package com.efm.item

import com.efm.level.World

interface StackableSelfItem : Item
{
    val maxAmount : Int
    var amount : Int
    
    /** Logic executed after the use of Item has been confirmed */
    fun use()
}
