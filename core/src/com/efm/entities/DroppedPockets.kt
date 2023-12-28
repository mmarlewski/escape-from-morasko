package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.room.RoomPosition
import com.efm.ui.gameScreen.EquipmentStructure

/**
 * Interactive Container spawned next to Hero when HeroInventory size is decreased.
 * Holds items that no longer fit in HeroInventory.
 */
class DroppedPockets(position : RoomPosition = RoomPosition()) : EnemyCorpse(position)
{
    override fun getTile() : TiledMapTile = Tiles.droppedPockets
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.droppedPocketsOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile = Tiles.droppedPocketsOutlineTeal
    
    /** Shows Hero's and Container's equipment view. Does not change */
    override fun interact()
    {
        EquipmentStructure.showHeroAndContainerEquipments(this)
    }
}
