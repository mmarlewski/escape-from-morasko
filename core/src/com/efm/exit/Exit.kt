package com.efm.exit

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.Direction4
import com.efm.entity.Interactive
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
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile? = null
    
    override fun write(json : Json?)
    {
        super.write(json)
        if (json != null)
        {
            json.writeValue("direction", this.direction)
            json.writeValue("style", this.style.name)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super.read(json, jsonData)
        if (json != null)
        {
            val jsonDirection = json.readValue("direction", Direction4::class.java, jsonData)
            if (jsonDirection != null) this.direction = jsonDirection
            val jsonStyle = json.readValue("style", String::class.java, jsonData)
            if (jsonStyle != null) this.style = ExitStyle.valueOf(jsonStyle)
        }
    }
}
