package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.room.RoomPosition

class DroppedPockets(position : RoomPosition = RoomPosition(0, 0)) : EnemyCorpse(position)
{
    override fun getTile() : TiledMapTile = Tiles.droppedPockets
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.droppedPocketsOutlineYellow
    
    override fun getOutlineTealTile() : TiledMapTile = Tiles.droppedPocketsOutlineTeal
}
