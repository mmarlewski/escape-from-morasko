package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.entity.Entity
import com.efm.room.RoomPosition

class Trap : Entity
{
    override val position = RoomPosition()
    
    override fun getTile() : TiledMapTile?
    {
        return null
    }
    
    override fun getOutlineYellowTile() : TiledMapTile?
    {
        return null
    }
}
