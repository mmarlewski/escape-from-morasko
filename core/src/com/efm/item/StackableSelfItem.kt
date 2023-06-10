package com.efm.item

interface StackableSelfItem : Item
{
    val maxAmount : Int
    var amount : Int
    
    val hpBoost : Int?
    val apBoost : Int?
    
    fun lowerAmountByOne()
    {
        amount -= 1
        if (amount <= 0)
        {
            amount = 0
        }
    }
    
    /** Logic executed after the use of Item has been confirmed */
    fun use()
}
