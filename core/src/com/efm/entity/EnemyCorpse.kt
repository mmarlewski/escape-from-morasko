package com.efm.entity

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.item.*
import com.efm.room.RoomPosition
import com.efm.ui.gameScreen.EquipmentStructure

open class EnemyCorpse(
        override val position : RoomPosition
                      ) : Interactive, Container, Character
{
    /** can be empty PossibleItems() **/
    var loot : PossibleItems? = null
    /** items = loot.drawItems() **/
    override val items = mutableListOf<Item>()
    override var maxItems : Int = 1
    
    override var maxHealthPoints : Int = 1
    override var healthPoints : Int = 1
    override var alive : Boolean = true
    
    override fun getTile() : TiledMapTile?
    {
        return null
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile?
    {
        return null
    }
    
    override fun getOutlineTealTile() : TiledMapTile?
    {
        return null
    }
    
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
}
