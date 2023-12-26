package com.efm.item

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.entity.Interactive
import com.efm.ui.gameScreen.EquipmentStructure
import kotlin.math.min
import kotlin.random.Random

/**
 * Container Entity holds Items
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

abstract class InteractiveContainerWithPossibleItems(val possibleItems : PossibleItems? = null,
                                                     private var seed : Int = Random.nextInt()) : Container, Interactive
{
    override val items : MutableList<Item> = mutableListOf()
    var opened : Boolean = false
    
    /**When opened for the first time, draw random items from possibleItems**/
    override fun interact()
    {
        Gdx.app.log("corpse", "$maxItems")
        if (!opened)
        {
            drawItems()
            opened=true
        }
        EquipmentStructure.showHeroAndContainerEquipments(this)
    }
    
    fun drawItems()
    {
        if (possibleItems!=null)
        {
            val drawnItems = possibleItems.drawItems(seed)
            for (item in drawnItems)
            {
                try
                {
                    addItem(item)
                }
                catch (e : ContainerFullException)
                {
                    // filled container
                    break
                }
            }
        }
    }
    
    // for serializing
    
    override fun write(json : Json?)
    {
        super<Container>.write(json)
        super<Interactive>.write(json)
        
        if (json != null)
        {
            json.writeValue("seed", this.seed)
            json.writeValue("opened", this.opened)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super<Container>.read(json, jsonData)
        super<Interactive>.read(json, jsonData)
        
        if (json != null)
        {
            val jsonSeed = json.readValue("seed", Int::class.java, jsonData)
            if (jsonSeed != null) this.seed = jsonSeed
            val jsonOpened = json.readValue("opened", Boolean::class.java, jsonData)
            if (jsonOpened != null) this.opened = jsonOpened
        }
    }
}
