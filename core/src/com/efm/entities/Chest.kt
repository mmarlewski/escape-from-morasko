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

open class Chest(possibleItems : PossibleItems? = null,
                 seed : Int = Random.nextInt()) : InteractiveContainerWithPossibleItems(possibleItems,seed)
{
    override val position = RoomPosition()
    override var items : MutableList<Item> = mutableListOf()
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
    
    fun takeItemFromChest(item : Item)
    {
        moveItem(item, this, World.hero.inventory)
    }
}

class TutorialChest : Chest()
{
    override fun interact()
    {
        if (!getState().tutorialFlags.tutorialActive || getState().tutorialFlags.lootingPopupShown) super.interact()
    }
}
