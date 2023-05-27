package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.passage.*
import com.efm.room.RoomPosition

class MetalExit(override val direction : Direction, val passage : Passage) : Entity, Exit
{
    override val position = RoomPosition()
    override val exitPassage = passage
    
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
        return when (passage)
        {
            is RoomPassage  -> when (direction)
            {
                Direction.up    -> Tiles.metalExitUpOutlineTeal
                Direction.right -> Tiles.metalExitRightOutlineTeal
                Direction.down  -> Tiles.metalExitDownOutlineTeal
                Direction.left  -> Tiles.metalExitLeftOutlineTeal
            }
        
            is LevelPassage -> when (direction)
            {
                Direction.up    -> Tiles.metalExitLevelUpOutlineTeal
                Direction.right -> Tiles.metalExitLevelRightOutlineTeal
                Direction.down  -> Tiles.metalExitLevelDownOutlineTeal
                Direction.left  -> Tiles.metalExitLevelLeftOutlineTeal
            }
        
            else            -> null
        }
    }
}
