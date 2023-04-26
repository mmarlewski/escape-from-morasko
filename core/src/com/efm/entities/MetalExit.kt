package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.passage.*
import com.efm.room.Room
import com.efm.room.RoomPosition

class MetalExit(val direction : Direction, val passage : Passage, val room : Room) : Entity, Exit
{
    override val position = RoomPosition()
    override val exitPassage = passage
    override val currentRoom = room
    
    override fun getTile() : TiledMapTile?
    {
        return when (passage)
        {
            is RoomPassage  -> when (direction)
            {
                Direction.up    -> Tiles.metalExitUp
                Direction.right -> Tiles.metalExitRight
                Direction.down  -> Tiles.metalExitDown
                Direction.left  -> Tiles.metalExitLeft
            }
            
            is LevelPassage -> when (direction)
            {
                Direction.up    -> Tiles.metalExitLevelUp
                Direction.right -> Tiles.metalExitLevelRight
                Direction.down  -> Tiles.metalExitLevelDown
                Direction.left  -> Tiles.metalExitLevelLeft
            }
            
            else            -> null
        }
    }
    
    override fun getOutlineTile() : TiledMapTile?
    {
        return null
    }
}
