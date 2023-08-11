package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.room.RoomPosition

class Chest : Entity
{
    override val position = RoomPosition()
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.chest
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return Tiles.chestOutlineYellow
    }
}
