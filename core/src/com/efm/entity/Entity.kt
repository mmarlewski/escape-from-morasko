package com.efm.entity

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.room.RoomPosition

/**
 * Entity inhabits a Room, things happen to Entities and Entities make things happen
 */
interface Entity
{
    val position : RoomPosition
    
    fun setPosition(x : Int, y : Int)
    {
        position.x = x
        position.y = y
    }
    
    fun setPosition(position : RoomPosition)
    {
        setPosition(position.x, position.y)
    }
    
    fun getTile() : TiledMapTile?
    fun getOutlineTile() : TiledMapTile?
}
