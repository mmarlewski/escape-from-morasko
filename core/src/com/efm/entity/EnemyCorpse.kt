package com.efm.entity

import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.item.*
import com.efm.room.RoomPosition
import com.efm.ui.gameScreen.EquipmentStructure

/**
 * EnemyCorpse is a Container generated after Enemy death
 */
abstract class EnemyCorpse(
        override val position : RoomPosition = RoomPosition(),
        /** can be empty PossibleItems() **/
        val loot : PossibleItems = PossibleItems()
                          ) : Interactive, Container, Character
{
    /** items = loot.drawItems() **/
    override val items = mutableListOf<Item>()
    override var maxItems : Int = loot.items.size + 2
    
    override var maxHealthPoints : Int = 1
    override var healthPoints : Int = 1
    override var alive : Boolean = true
    
    override fun interact()
    {
        EquipmentStructure.showHeroAndContainerEquipments(this)
    }
    
    // for serializing
    
    override fun write(json : Json?)
    {
        super<Interactive>.write(json)
        super<Character>.write(json)
        
        if (json != null)
        {
            json.writeValue("items", this.items)
            json.writeValue("maxItems", this.maxItems)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super<Interactive>.read(json, jsonData)
        super<Character>.read(json, jsonData)
        
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
    
    // fixes issue with using addItem() in init
    final override fun addItem(item : Item)
    {
        super.addItem(item)
    }
    
    init
    {
        val drawnItems = loot.drawItems()
        for (item in drawnItems)
        {
            try
            {
                addItem(item)
            } catch (e : ContainerFullException)
            {
                // filled container
                break
            }
        }
    }
}
