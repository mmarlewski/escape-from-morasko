package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.passage.*
import com.efm.room.RoomPosition

class RockExit(override val direction : Direction, val passage : Passage) : Entity, Exit
{
    override val position = RoomPosition()
    override val exitPassage = passage
    
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
    
    override fun getOutlineYellowTile() : TiledMapTile?
    {
        return null
    }

    override fun getOutlineTealTile() : TiledMapTile?
    {
        return when (passage)
        {
            is RoomPassage  -> when (direction)
            {
                Direction.up    -> Tiles.rockExitUpOutlineTeal
                Direction.right -> Tiles.rockExitRightOutlineTeal
                Direction.down  -> Tiles.rockExitDownOutlineTeal
                Direction.left  -> Tiles.rockExitLeftOutlineTeal
            }

            is LevelPassage -> when (direction)
            {
                Direction.up    -> Tiles.rockExitLevelUpOutlineTeal
                Direction.right -> Tiles.rockExitLevelRightOutlineTeal
                Direction.down  -> Tiles.rockExitLevelDownOutlineTeal
                Direction.left  -> Tiles.rockExitLevelLeftOutlineTeal
            }

            else            -> null
        }
    }
}
