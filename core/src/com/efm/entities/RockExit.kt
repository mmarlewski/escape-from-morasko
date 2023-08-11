package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction4
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.passage.*
import com.efm.room.RoomPosition

class RockExit(override val direction : Direction4, val passage : Passage) : Entity, Exit
{
    override val position = RoomPosition()
    override val exitPassage = passage
    
    override fun getTile() : TiledMapTile?
    {
        return when (passage)
        {
            is RoomPassage  -> when (direction)
            {
                Direction4.up    -> Tiles.rockExitUp
                Direction4.right -> Tiles.rockExitRight
                Direction4.down  -> Tiles.rockExitDown
                Direction4.left  -> Tiles.rockExitLeft
            }
            
            is LevelPassage -> when (direction)
            {
                Direction4.up    -> Tiles.rockExitLevelUp
                Direction4.right -> Tiles.rockExitLevelRight
                Direction4.down  -> Tiles.rockExitLevelDown
                Direction4.left  -> Tiles.rockExitLevelLeft
            }
            
            else            -> null
        }
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile?
    {
        return null
    }

    override fun getOutlineTealTile() : TiledMapTile?
    {
        return when (passage)
        {
            is RoomPassage  -> when (direction)
            {
                Direction4.up    -> Tiles.rockExitUpOutlineTeal
                Direction4.right -> Tiles.rockExitRightOutlineTeal
                Direction4.down  -> Tiles.rockExitDownOutlineTeal
                Direction4.left  -> Tiles.rockExitLeftOutlineTeal
            }

            is LevelPassage -> when (direction)
            {
                Direction4.up    -> Tiles.rockExitLevelUpOutlineTeal
                Direction4.right -> Tiles.rockExitLevelRightOutlineTeal
                Direction4.down  -> Tiles.rockExitLevelDownOutlineTeal
                Direction4.left  -> Tiles.rockExitLevelLeftOutlineTeal
            }

            else            -> null
        }
    }
}
