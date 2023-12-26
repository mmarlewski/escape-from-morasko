package com.efm.item

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import kotlin.math.min

/**
 * Holds items.
 * Sorting items is done automatically after adding or removing item from items
 */
interface Container : Json.Serializable
{
    val items : MutableList<Item>
    var maxItems : Int
    
    fun findAllStacks(item : Item) : List<Item>
    {
        return items.filter { it::class == item::class }
    }
    
    /** If item is Stackable, adds max possible amount to Container and removes from item.
     *  If still necessary and possible, adds a copy of item to a new slot and sets amount to 0 is item is Stackable.
     * */
    fun addItem(item : Item)
    {
        if (item is StackableItem) addStackableItem(item)
        else addItemToNewSlot(item)
    }
    
    fun removeItem(item : Item)
    {
        items.remove(item)
        sortItems()
    }
    
    private fun sortItems()
    {
        items.sortBy { it.javaClass.canonicalName }
    }
    
    private fun addItemToNewSlot(item : Item)
    {
        if (items.size < maxItems)
        {
            // add a copy
            val copiedItem = item.clone()
            items.add(copiedItem)
            sortItems()
            // added all of item to new slot
            // means original should have 0 amount
            if (item is StackableItem) item.amount = 0
        }
        else throw ContainerFullException("Cannot add any more items to container.")
    }
    
    private fun addStackableItem(item : StackableItem)
    {
        // check all stacks in container
        val stacksInContainer = findAllStacks(item)
        for (stackInContainer in stacksInContainer)
        {
            // if you can add to stack
            if (stackInContainer is StackableItem && stackInContainer.amountPossibleToAdd() > 0)
            {
                // fill stack
                val amountToAdd = min(stackInContainer.amountPossibleToAdd(), item.amount)
                stackInContainer.add(amountToAdd)
                // lower amount of item to be added
                item.remove(amountToAdd)
            }
        }
        // if there are still items to be added add them to new slot
        if (item.amount > 0)
        {
            addItemToNewSlot(item)
        }
    }
    
    // for serializing
    
    override fun write(json : Json?)
    {
        if (json != null)
        {
            json.writeValue("items", this.items)
            json.writeValue("maxItems", this.maxItems)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        if (json != null)
        {
            val jsonItems = json.readValue("items", List::class.java, jsonData)
            
            if (jsonItems != null)
            {
                for (jsonItem in jsonItems)
                {
                    if (jsonItem is Item)
                    {
                        this.items.add(jsonItem)
                    }
                }
            }
            val jsonMaxItems = json.readValue("maxItems", Int::class.java, jsonData)
            if (jsonMaxItems != null) this.maxItems = jsonMaxItems
        }
    }
}

fun moveItem(item : Item, from : Container, to : Container)
{
    try
    {
        to.addItem(item)
        if (item is StackableItem && item.amount > 0)
        {
            // some of item is left in container "from"
            // do not remove item from container "from"
        }
        else
        {
            // all of item has been moved to container "to"
            // remove item from container "from"
            from.removeItem(item)
        }
    } catch (e : ContainerFullException)
    {
        Gdx.app.log("moveItem", "Container \"to\" is full. Some of item is left in container \"from\"")
    }
}

class ContainerFullException(message : String? = null, cause : Throwable? = null) : Exception(message, cause)
