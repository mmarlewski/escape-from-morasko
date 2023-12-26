package com.efm.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.room.RoomPosition
import com.efm.ui.gameScreen.EquipmentStructure

class DroppedPockets(position : RoomPosition = RoomPosition()) : EnemyCorpse(position)
{
    override fun getTile() : TiledMapTile = Tiles.droppedPockets
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.droppedPocketsOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile = Tiles.droppedPocketsOutlineTeal
    
    /**When opened for the first time, do not draw random items from possibleItems. Size is assigned later.**/
    override fun interact()
    {
        EquipmentStructure.showHeroAndContainerEquipments(this)
    }
}
