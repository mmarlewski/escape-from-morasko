package com.efm.entity

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.room.RoomCoordinates

/**
 * Entity inhabits a Room, things happen to Entities and Entities make things happen
 */
interface Entity
{
    val roomCoordinates : RoomCoordinates
    fun getTile() : TiledMapTile?
    fun getOutlineTile() : TiledMapTile?
}
