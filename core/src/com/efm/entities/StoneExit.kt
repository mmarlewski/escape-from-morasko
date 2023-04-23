package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.passage.Exit
import com.efm.passage.Passage
import com.efm.room.Room
import com.efm.room.RoomPosition

class StoneExit(val direction : Direction, val passage : Passage, val room : Room) : Exit
{
    override val position = RoomPosition()
    override val exitPassage = passage
    override val currentRoom = room
    
    override fun getTile() : TiledMapTile
    {
        return when (direction)
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
