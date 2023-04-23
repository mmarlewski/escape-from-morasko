package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.passage.Exit
import com.efm.passage.Passage
import com.efm.room.Room
import com.efm.room.RoomPosition

class RockExit(val direction : Direction, val passage : Passage, val room : Room) : Entity, Exit
{
    override val position = RoomPosition()
    override val exitPassage = passage
    override val currentRoom = room
    override fun getTile() : TiledMapTile
    {
        return when (direction)
        {
            Direction.up    -> Tiles.rockExitUp
            Direction.right -> Tiles.rockExitRight
            Direction.down  -> Tiles.rockExitDown
            Direction.left  -> Tiles.rockExitLeft
        }
    }
    
    override fun getOutlineTile() : TiledMapTile?
    {
        return null
    }
}
