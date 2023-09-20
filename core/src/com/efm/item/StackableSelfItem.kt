package com.efm.item

interface StackableSelfItem : StackableItem
{
    val hpBoost : Int?
    val apBoost : Int?
    
    /** Logic executed after the use of Item has been confirmed */
    fun use()
}
