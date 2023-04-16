package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.room.RoomPosition

class MetalWall(val direction : Direction?) : Entity
{
    override val position = RoomPosition()
    
    override fun getTile() : TiledMapTile?
    {
        return when (direction)
        {
            Direction.up    -> Tiles.metalWallUp
            Direction.right -> Tiles.metalWallRight
            Direction.down  -> Tiles.metalWallDown
            Direction.left  -> Tiles.metalWallLeft
            null            -> null
        }
    }
    
    override fun getOutlineTile() : TiledMapTile?
    {
        return null
    }
}
