package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.assets.Tiles
import com.efm.entity.Interactive
import com.efm.item.*
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.state.getState
import com.efm.ui.gameScreen.EquipmentStructure
import kotlin.random.Random

open class Chest(possibleItems : PossibleItems? = null, seed : Int = Random.nextInt()) : Interactive, Container
{
    override val position = RoomPosition()
    override var items : MutableList<Item> = mutableListOf<Item>()
    override var maxItems : Int = 5
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.chest
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return Tiles.chestOutlineYellow
    }
    
    override fun getOutlineTealTile() : TiledMapTile
    {
        return Tiles.chestOutlineTeal
    }
    
    override fun interact()
    {
        EquipmentStructure.showHeroAndContainerEquipments(this)
    }
    
    fun takeItemFromChest(item : Item)
    {
        moveItem(item, this, World.hero.inventory)
    }
    
    // fixes issue with using addItem() in init
    final override fun addItem(item : Item)
    {
        super.addItem(item)
    }
    
    init
    {
        if (possibleItems != null)
        {
            val drawnItems = possibleItems.drawItems(seed)
            for (item in drawnItems)
            {
                addItem(item)
            }
        }
    }
    
    // for serializing
    
    override fun write(json : Json?)
    {
        super.write(json)
        
        if (json != null)
        {
            json.writeValue("items", this.items)
            json.writeValue("maxItems", this.maxItems)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super.read(json, jsonData)
        
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

class TutorialChest : Chest()
{
    override fun interact()
    {
        if (!getState().tutorialFlags.tutorialActive || getState().tutorialFlags.lootingPopupShown) super.interact()
    }
}
