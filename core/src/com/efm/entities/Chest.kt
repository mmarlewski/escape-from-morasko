package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.entity.Interactive
import com.efm.item.Item
import com.efm.item.StackableItem
import com.efm.level.World
import com.efm.room.RoomPosition
import kotlin.math.min

class Chest : Entity, Interactive
{
    override val position = RoomPosition()
    val items = mutableListOf<Item>()
    
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
        // otworz widok zawartosci skrzyni (chest ui)
    }
    
    fun takeItemFromChest(item : Item)
    {
        val equipment = World.hero.equipment
        
        // jezeli przedmiot jest stackowalny
        if (item is StackableItem)
        {
            // jezeli przedmiot jest w ekwipunku
            val itemInEq : StackableItem? = equipment.find { it::class == item::class } as StackableItem?
            if (itemInEq != null)
            {
                // maksymalna liczba przedmiotow jaka mozna zabrac ze skrzynki
                val amountToTake = min(itemInEq.amountPossibleToAdd(), item.amount)
                // dodaj przedmioty do ekwipunku
                itemInEq.add(amountToTake)
                // usun przedmioty ze skrzynki
                item.remove(amountToTake)
                if (item.amount == 0)
                {
                    items.remove(item)
                }
            }
        }
        // jezeli przedmiot nie jest stackowalny sproboj dodac go do ekwipunku
        else
        {
            try
            {
                equipment.add(item)
                items.remove(item)
            } catch (e : EquipmentFullException)
            {
                // nie mozna zabrac przedmiotu ze skrzynki
            }
        }
    }
}
