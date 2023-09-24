package com.efm.item

import kotlin.math.min

interface Container
{
    val items : MutableList<Item>
    var maxItems : Int
    
    fun findAllStacks(item : Item) : List<Item>
    {
        return items.filter { it::class == item::class }
    }
    
    fun addItem(item : Item)
    {
        if (item is StackableItem)
        {
            addStackableItem(item)
        }
        else
        {
            try
            {
                addItemToNewSlot(item)
            } catch (e : ContainerFullException)
            {
                // nie mozna zabrac dodac przedmiotu
            }
        }
    }
    
    fun removeItem(item : Item)
    {
        items.remove(item)
    }
    
    fun sortItems()
    {
        items.sortBy { it.javaClass.canonicalName }
    }
    
    private fun addItemToNewSlot(item : Item)
    {
        if (items.size < maxItems)
        {
            items.add(item)
        }
        else throw ContainerFullException("Cannot add any more items to container.")
    }
    
    private fun addStackableItem(item : StackableItem)
    {
        // jezeli sa niepelne stacki w skrzynce
        val stacksInContainer = findAllStacks(item)
        for (stackInContainer in stacksInContainer)
        {
            if (stackInContainer is StackableItem && stackInContainer.amountPossibleToAdd() > 0)
            {
                // dopelnij stack
                val amountToAdd = min(stackInContainer.amountPossibleToAdd(), item.amount)
                stackInContainer.add(amountToAdd)
                // zmniejsz liczbe przedmiotow do dodania
                item.remove(amountToAdd)
            }
        }
        // jezeli dalej sa przedmioty do dodania sproboj dodac do nowego slotu
        if (item.amount > 0)
        {
            try
            {
                addItemToNewSlot(item)
            } catch (e : ContainerFullException)
            {
                // nie mozna zabrac dodac przedmiotu
            }
        }
    }
}

class ContainerFullException(message : String? = null, cause : Throwable? = null) : Exception(message, cause)