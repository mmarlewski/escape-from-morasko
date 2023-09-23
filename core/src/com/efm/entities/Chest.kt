package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.entity.Interactive
import com.efm.item.*
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.ui.gameScreen.EquipmentStructure

class Chest : Entity, Interactive, Container
{
    override val position = RoomPosition()
    override val items : MutableList<Item> = mutableListOf<Item>()
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
        World.hero.inventory.addItem(item)
        if (item is StackableItem && item.amount > 0)
        {
            // zostalo troche przedmiotu w skrzyni
        }
        else
        {
            items.remove(item)
        }
    }
}
