package com.efm.entities.exits

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction4
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.passage.*
import com.efm.room.RoomPosition

class StoneExit(dir : Direction4, pass : Passage?) : Exit(dir, pass)
{
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
    
    // for serialization
    constructor() : this(Direction4.up, null)
}

class StoneExitActiveWhenNoEnemiesAreInRoom(dir : Direction4, pass : Passage?) : ExitActiveWhenNoEnemiesAreInRoom(dir, pass)
{
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
    
    // for serialization
    constructor() : this(Direction4.up, null)
}
