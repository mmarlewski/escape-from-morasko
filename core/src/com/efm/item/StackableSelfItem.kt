package com.efm.item

/**
 * Item held in stacks, used on Hero
 */
interface StackableSelfItem : StackableItem
{
    val hpBoost : Int?
    val apBoost : Int?
    
    /** Logic executed after the use of Item has been confirmed */
    fun use()
}
