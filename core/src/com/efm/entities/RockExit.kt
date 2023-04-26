package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.passage.*
import com.efm.room.Room
import com.efm.room.RoomPosition

class RockExit(val direction : Direction, val passage : Passage, val room : Room) : Entity, Exit
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
                Direction.up    -> Tiles.rockExitUp
                Direction.right -> Tiles.rockExitRight
                Direction.down  -> Tiles.rockExitDown
                Direction.left  -> Tiles.rockExitLeft
            }
            
            is LevelPassage -> when (direction)
            {
                Direction.up    -> Tiles.rockExitLevelUp
                Direction.right -> Tiles.rockExitLevelRight
                Direction.down  -> Tiles.rockExitLevelDown
                Direction.left  -> Tiles.rockExitLevelLeft
            }
            
            else            -> null
        }
    }
    
    override fun getOutlineTile() : TiledMapTile?
    {
        return null
    }
}
