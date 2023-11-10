package com.efm.item

interface StackableItem : Item
{
    val maxAmount : Int
    var amount : Int
    
    fun lowerAmountByOne()
    {
        amount -= 1
        if (amount <= 0)
        {
            amount = 0
        }
    }
    
    fun add(amountToAdd : Int)
    {
        if (amountToAdd < 0)
            throw Exception("amountToAdd can not be negative")
        if (amount + amountToAdd > maxAmount)
            throw AmountOverflowException("After adding amountToAdd amount would exceed maxAmount.")
        else
            amount += amountToAdd
    }
    
    fun remove(amountToRemove : Int)
    {
        if (amountToRemove < 0)
            throw Exception("amountToRemove can not be negative")
        if (amount - amountToRemove < 0)
            throw AmountUnderflowException("After removing amountToRemove amount would be negative.")
        else
            amount -= amountToRemove
    }
    
    fun amountPossibleToAdd() : Int
    {
        return maxAmount - amount
    }
    
    override fun clone() : StackableItem
    {
        //return Class.forName(this::class.qualifiedName).getConstructor(Int::class.java).newInstance(amount) as StackableItem
        val copy = Class.forName(this::class.qualifiedName).getConstructor().newInstance() as StackableItem
        copy.amount = this.amount
        return copy
    }
}

class AmountOverflowException(message : String? = null, cause : Throwable? = null) : Exception(message, cause)

class AmountUnderflowException(message : String? = null, cause : Throwable? = null) : Exception(message, cause)

