package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.passage.Exit
import com.efm.room.RoomCoordinates

class StoneExit(val exitDirection : Direction) : Exit
{
    override val roomCoordinates = RoomCoordinates(0, 0)
    
    override fun getTile() : TiledMapTile
    {
        return when (exitDirection)
        {
            Direction.up    -> Tiles.stoneExitUp
            Direction.right -> Tiles.stoneExitRight
            Direction.down  -> Tiles.stoneExitDown
            Direction.left  -> Tiles.stoneExitLeft
        }
    }
    
    override fun getOutlineTile() : TiledMapTile?
    {
        return null
    }
}
