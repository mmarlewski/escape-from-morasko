package com.efm.entity

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.IdleAnimation
import com.efm.room.RoomPosition

/**
 * Entity inhabits a Room, things happen to Entities and Entities make things happen
 */
interface Entity
{
    fun getTile() : TiledMapTile?
    
    fun getOutlineYellowTile(n : Int) : TiledMapTile?
    
    fun getOutlineYellowTile() : TiledMapTile?
    {
        return getOutlineYellowTile(IdleAnimation.idleAnimationCount)
    }
    
    val position : RoomPosition
    
    fun setPosition(x : Int, y : Int)
    {
        position.x = x
        position.y = y
    }
    
    fun setPosition(position : RoomPosition)
    {
        setPosition(position.x, position.y)
    }
}
