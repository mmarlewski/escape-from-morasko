package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.entity.Entity
import com.efm.room.RoomCoordinates

// does not have texture?
class Trap(override var roomCoordinates : RoomCoordinates) : Entity
{
    override fun getTile() : TiledMapTile?
    {
        return null
    }
    
    override fun getOutlineTile() : TiledMapTile?
    {
        return null
    }
}