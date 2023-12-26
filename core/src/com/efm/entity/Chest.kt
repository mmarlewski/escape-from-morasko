package com.efm.entity

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.item.*
import com.efm.level.World
import com.efm.room.RoomPosition
import kotlin.random.Random

/**
 * InteractiveContainer found in rooms.
 * When created, if supplied with PossibleItems, random items are drawn.
 * During loading, Chest is created using default constructor, possibleItems is null, so no items are drawn.
 */
open class Chest(
        possibleItems : PossibleItems? = null, seed : Int = Random.nextInt()
                ) : InteractiveContainerWithPossibleItems(possibleItems, seed)
{
    override val position = RoomPosition()
    override val items : MutableList<Item> = mutableListOf()
    override var maxItems : Int = 5
    
    override fun getTile() : TiledMapTile = Tiles.chest
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.chestOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile = Tiles.chestOutlineTeal
    
    /** Simulates using UI to transfer items from Containers to Hero's inventory. */
    fun takeItemFromChest(item : Item)
    {
        moveItem(item, this, World.hero.inventory)
    }
    
    /** When created, if supplied with PossibleItems, draw random items.
     *  During loading possibleItems is null, so no items are drawn.
     */
    init
    {
        if (possibleItems != null) drawItems()
    }
}
