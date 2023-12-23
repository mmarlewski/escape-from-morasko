package com.efm.entity

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.IdleAnimation
import com.efm.room.RoomPosition

/**
 * Entity has position and occupies Space
 */
interface Entity : Json.Serializable
{
    val position : RoomPosition
    
    fun getTile() : TiledMapTile?
    
    fun getOutlineYellowTile(n : Int) : TiledMapTile?
    
    fun getOutlineYellowTile() : TiledMapTile?
    {
        return getOutlineYellowTile(IdleAnimation.idleAnimationCount)
    }
    
    fun setPosition(x : Int, y : Int)
    {
        position.x = x
        position.y = y
    }
    
    fun setPosition(position : RoomPosition)
    {
        setPosition(position.x, position.y)
    }
    
    // for serializing
    
    override fun write(json : Json?)
    {
        if (json != null)
        {
            json.writeValue("position", this.position)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        if (json != null)
        {
            val jsonPosition = json.readValue("position", RoomPosition::class.java, jsonData)
            if (jsonPosition != null) this.position.set(jsonPosition)
        }
    }
}
