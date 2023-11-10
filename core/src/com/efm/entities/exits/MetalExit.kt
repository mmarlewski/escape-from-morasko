package com.efm.entities.exits

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction4
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.passage.*
import com.efm.room.RoomPosition

class MetalExit(override val direction : Direction4, val passage : Passage) : Entity, Exit
{
    override val position = RoomPosition()
    override val exitPassage = passage
    
    override fun getTile() : TiledMapTile?
    {
        return when (passage)
        {
            is RoomPassage  -> when (direction)
            {
                Direction4.up    -> Tiles.metalExitUp
                Direction4.right -> Tiles.metalExitRight
                Direction4.down  -> Tiles.metalExitDown
                Direction4.left  -> Tiles.metalExitLeft
            }
            
            is LevelPassage -> when (direction)
            {
                Direction4.up    -> Tiles.metalExitLevelUp
                Direction4.right -> Tiles.metalExitLevelRight
                Direction4.down  -> Tiles.metalExitLevelDown
                Direction4.left  -> Tiles.metalExitLevelLeft
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
                Direction4.up    -> Tiles.metalExitUpOutlineTeal
                Direction4.right -> Tiles.metalExitRightOutlineTeal
                Direction4.down  -> Tiles.metalExitDownOutlineTeal
                Direction4.left  -> Tiles.metalExitLeftOutlineTeal
            }
        
            is LevelPassage -> when (direction)
            {
                Direction4.up    -> Tiles.metalExitLevelUpOutlineTeal
                Direction4.right -> Tiles.metalExitLevelRightOutlineTeal
                Direction4.down  -> Tiles.metalExitLevelDownOutlineTeal
                Direction4.left  -> Tiles.metalExitLevelLeftOutlineTeal
            }
        
            else            -> null
        }
    }
}
