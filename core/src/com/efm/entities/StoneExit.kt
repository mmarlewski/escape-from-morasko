package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction4
import com.efm.assets.Tiles
import com.efm.passage.*
import com.efm.room.RoomPosition

class StoneExit(override val direction : Direction4, val passage : Passage) : Exit
{
    override val position = RoomPosition()
    override val exitPassage = passage
    
    override fun getTile() : TiledMapTile?
    {
        return when (passage)
        {
            is RoomPassage  -> when (direction)
            {
                Direction4.up    -> Tiles.stoneExitUp
                Direction4.right -> Tiles.stoneExitRight
                Direction4.down  -> Tiles.stoneExitDown
                Direction4.left  -> Tiles.stoneExitLeft
            }
            
            is LevelPassage -> when (direction)
            {
                Direction4.up    -> Tiles.stoneExitLevelUp
                Direction4.right -> Tiles.stoneExitLevelRight
                Direction4.down  -> Tiles.stoneExitLevelDown
                Direction4.left  -> Tiles.stoneExitLevelLeft
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
                Direction4.up    -> Tiles.stoneExitUpOutlineTeal
                Direction4.right -> Tiles.stoneExitRightOutlineTeal
                Direction4.down  -> Tiles.stoneExitDownOutlineTeal
                Direction4.left  -> Tiles.stoneExitLeftOutlineTeal
            }
        
            is LevelPassage -> when (direction)
            {
                Direction4.up    -> Tiles.stoneExitLevelUpOutlineTeal
                Direction4.right -> Tiles.stoneExitLevelRightOutlineTeal
                Direction4.down  -> Tiles.stoneExitLevelDownOutlineTeal
                Direction4.left  -> Tiles.stoneExitLevelLeftOutlineTeal
            }
    
            else            -> null
        }
    }
}

class StoneExitActiveWhenNoEnemiesAreInRoom(override val direction : Direction4, val passage : Passage) :
        ExitActiveWhenNoEnemiesAreInRoom
{
    override val position = RoomPosition()
    override val exitPassage = passage
    
    override fun getTile() : TiledMapTile?
    {
        return when (passage)
        {
            is RoomPassage  -> when (direction)
            {
                Direction4.up    -> Tiles.stoneExitUp
                Direction4.right -> Tiles.stoneExitRight
                Direction4.down  -> Tiles.stoneExitDown
                Direction4.left  -> Tiles.stoneExitLeft
            }
            
            is LevelPassage -> when (direction)
            {
                Direction4.up    -> Tiles.stoneExitLevelUp
                Direction4.right -> Tiles.stoneExitLevelRight
                Direction4.down  -> Tiles.stoneExitLevelDown
                Direction4.left  -> Tiles.stoneExitLevelLeft
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
                Direction4.up    -> Tiles.stoneExitUpOutlineTeal
                Direction4.right -> Tiles.stoneExitRightOutlineTeal
                Direction4.down  -> Tiles.stoneExitDownOutlineTeal
                Direction4.left  -> Tiles.stoneExitLeftOutlineTeal
            }
            
            is LevelPassage -> when (direction)
            {
                Direction4.up    -> Tiles.stoneExitLevelUpOutlineTeal
                Direction4.right -> Tiles.stoneExitLevelRightOutlineTeal
                Direction4.down  -> Tiles.stoneExitLevelDownOutlineTeal
                Direction4.left  -> Tiles.stoneExitLevelLeftOutlineTeal
            }
            
            else            -> null
        }
    }
}
