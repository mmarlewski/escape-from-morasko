package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.passage.Exit
import com.efm.room.RoomPosition

class RockWall(val direction : Direction?) : Entity
{
    override val position = RoomPosition()
    
    override fun getTile() : TiledMapTile?
    {
        return when (direction)
        {
            Direction.up    -> Tiles.rockWallUp
            Direction.right -> Tiles.rockWallRight
            Direction.down  -> Tiles.rockWallDown
            Direction.left  -> Tiles.rockWallLeft
            null            -> null
        }
    }
    
    override fun getOutlineTile() : TiledMapTile?
    {
        return null
    }
}
