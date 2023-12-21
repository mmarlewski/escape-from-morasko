package com.efm.exit

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.Direction4
import com.efm.entity.Interactive
import com.efm.level.World
import com.efm.room.RoomPosition

/**
 * Interactive object found in a Room used to leave it. Created based on a Passage.
 */
interface Exit : Interactive
{
    var activeWhenNoEnemiesAreInRoom : Boolean
    override val position : RoomPosition
    var direction : Direction4
    var style : ExitStyle
    var active : Boolean
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile? = null
    
    fun isOpen() : Boolean
    {
        val worldCurrentRoom = World.currentRoom
        return !activeWhenNoEnemiesAreInRoom || worldCurrentRoom?.areEnemiesInRoom() != true
    }
    
    // for serializing
    
    override fun write(json : Json?)
    {
        super.write(json)
        
        if (json != null)
        {
            json.writeValue("activeWhenNoEnemiesAreInRoom", this.activeWhenNoEnemiesAreInRoom)
            json.writeValue("position", this.position)
            json.writeValue("direction", this.direction)
            json.writeValue("style", this.style.name)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super.read(json, jsonData)
        
        if (json != null)
        {
            val jsonActiveWhenNoEnemiesAreInRoom = json.readValue("activeWhenNoEnemiesAreInRoom", Boolean::class.java, jsonData)
            if (jsonActiveWhenNoEnemiesAreInRoom != null) this.activeWhenNoEnemiesAreInRoom = jsonActiveWhenNoEnemiesAreInRoom
            val jsonPosition = json.readValue("position", RoomPosition::class.java, jsonData)
            if (jsonPosition != null) this.position.set(jsonPosition)
            val jsonDirection = json.readValue("direction", Direction4::class.java, jsonData)
            if (jsonDirection != null) this.direction = jsonDirection
            val jsonStyle = json.readValue("style", String::class.java, jsonData)
            if (jsonStyle != null) this.style = ExitStyle.valueOf(jsonStyle)
        }
    }
}
