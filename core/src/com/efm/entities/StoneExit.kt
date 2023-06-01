package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.passage.*
import com.efm.room.RoomPosition

class StoneExit(override val direction : Direction, val passage : Passage) : Exit
{
    override val position = RoomPosition()
    override val exitPassage = passage
    
    override fun getTile() : TiledMapTile?
    {
        return when (passage)
        {
            is RoomPassage  -> when (direction)
            {
                Direction.up    -> Tiles.stoneExitUp
                Direction.right -> Tiles.stoneExitRight
                Direction.down  -> Tiles.stoneExitDown
                Direction.left  -> Tiles.stoneExitLeft
            }
            
            is LevelPassage -> when (direction)
            {
                Direction.up    -> Tiles.stoneExitLevelUp
                Direction.right -> Tiles.stoneExitLevelRight
                Direction.down  -> Tiles.stoneExitLevelDown
                Direction.left  -> Tiles.stoneExitLevelLeft
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
                Direction.up    -> Tiles.stoneExitUpOutlineTeal
                Direction.right -> Tiles.stoneExitRightOutlineTeal
                Direction.down  -> Tiles.stoneExitDownOutlineTeal
                Direction.left  -> Tiles.stoneExitLeftOutlineTeal
            }
        
            is LevelPassage -> when (direction)
            {
                Direction.up    -> Tiles.stoneExitLevelUpOutlineTeal
                Direction.right -> Tiles.stoneExitLevelRightOutlineTeal
                Direction.down  -> Tiles.stoneExitLevelDownOutlineTeal
                Direction.left  -> Tiles.stoneExitLevelLeftOutlineTeal
            }
        
            else            -> null
        }
    }
}
