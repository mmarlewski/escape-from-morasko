package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.passage.Exit
import com.efm.passage.Passage
import com.efm.room.Room
import com.efm.room.RoomPosition

class MetalExit(val direction : Direction, val passage : Passage, val room : Room) : Entity, Exit
{
    override val position = RoomPosition()
    override val exitPassage = passage
    override val currentRoom = room
    override fun getTile() : TiledMapTile
    {
        return when (direction)
        {
            Direction.up    -> Tiles.metalExitUp
            Direction.right -> Tiles.metalExitRight
            Direction.down  -> Tiles.metalExitDown
            Direction.left  -> Tiles.metalExitLeft
        }
    }
    
    override fun getOutlineTile() : TiledMapTile?
    {
        return null
    }
}
