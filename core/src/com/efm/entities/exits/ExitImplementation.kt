package com.efm.entities.exits

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.Direction4
import com.efm.assets.Tiles
import com.efm.entities.walls.WallStyle
import com.efm.entity.Entity
import com.efm.passage.*
import com.efm.room.RoomPosition

class ExitImplementation(var style : ExitStyle, dir : Direction4, pass : Passage?) : Exit(dir, pass)
{
    override val position : RoomPosition = RoomPosition()
    
    override fun getTile() : TiledMapTile?
    {
        return when (passage)
        {
            is RoomPassage  -> when (direction)
            {
                Direction4.up    -> style.tiles.exitUp
                Direction4.right -> style.tiles.exitRight
                Direction4.down  -> style.tiles.exitDown
                Direction4.left  -> style.tiles.exitLeft
            }
            
            is LevelPassage -> when (direction)
            {
                Direction4.up    -> style.tiles.exitLevelUp
                Direction4.right -> style.tiles.exitLevelRight
                Direction4.down  -> style.tiles.exitLevelDown
                Direction4.left  -> style.tiles.exitLevelLeft
            }
            
            else            -> null
        }
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile? = null
    
    override fun getOutlineTealTile() : TiledMapTile?
    {
        return when (passage)
        {
            is RoomPassage  -> when (direction)
            {
                Direction4.up    -> style.tiles.exitUpOutlineTeal
                Direction4.right -> style.tiles.exitRightOutlineTeal
                Direction4.down  -> style.tiles.exitDownOutlineTeal
                Direction4.left  -> style.tiles.exitLeftOutlineTeal
            }
            
            is LevelPassage -> when (direction)
            {
                Direction4.up    -> style.tiles.exitLevelUpOutlineTeal
                Direction4.right -> style.tiles.exitLevelRightOutlineTeal
                Direction4.down  -> style.tiles.exitLevelDownOutlineTeal
                Direction4.left  -> style.tiles.exitLevelLeftOutlineTeal
            }
            
            else            -> null
        }
    }
    
    // for serialization
    
    constructor() : this(ExitStyle.stone, Direction4.up, null)
    
    override fun write(json : Json?)
    {
        super.write(json)
        
        if (json != null)
        {
            json.writeValue("style", this.style.name)
            json.writeValue("direction", this.direction)
            json.writeValue("passage", listOf(this.passage))
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super.read(json, jsonData)
        
        if (json != null)
        {
            val jsonStyle = json.readValue("style", String::class.java, jsonData)
            if (jsonStyle != null) this.style = ExitStyle.valueOf(jsonStyle)
            
            val jsonDirection = json.readValue("direction", Direction4::class.java, jsonData)
            if (jsonDirection != null) this.direction = jsonDirection
            
            val jsonPassage = json.readValue("passage", List::class.java, jsonData)
            if (jsonPassage != null) this.passage = jsonPassage.first() as? Passage
        }
    }
}

internal class ExitTiles(
        val exitUp : TiledMapTile?,
        val exitRight : TiledMapTile?,
        val exitDown : TiledMapTile?,
        val exitLeft : TiledMapTile?,
        val exitLevelUp : TiledMapTile?,
        val exitLevelRight : TiledMapTile?,
        val exitLevelDown : TiledMapTile?,
        val exitLevelLeft : TiledMapTile?,
        val exitUpOutlineTeal : TiledMapTile?,
        val exitRightOutlineTeal : TiledMapTile?,
        val exitDownOutlineTeal : TiledMapTile?,
        val exitLeftOutlineTeal : TiledMapTile?,
        val exitLevelUpOutlineTeal : TiledMapTile?,
        val exitLevelRightOutlineTeal : TiledMapTile?,
        val exitLevelDownOutlineTeal : TiledMapTile?,
        val exitLevelLeftOutlineTeal : TiledMapTile?
                        )

enum class ExitStyle(internal val tiles : ExitTiles)
{
    stone(
            ExitTiles(
                    Tiles.stoneExitUp,
                    Tiles.stoneExitRight,
                    Tiles.stoneExitDown,
                    Tiles.stoneExitLeft,
                    Tiles.stoneExitLevelUp,
                    Tiles.stoneExitLevelRight,
                    Tiles.stoneExitLevelDown,
                    Tiles.stoneExitLevelLeft,
                    Tiles.stoneExitUpOutlineTeal,
                    Tiles.stoneExitRightOutlineTeal,
                    Tiles.stoneExitDownOutlineTeal,
                    Tiles.stoneExitLeftOutlineTeal,
                    Tiles.stoneExitLevelUpOutlineTeal,
                    Tiles.stoneExitLevelRightOutlineTeal,
                    Tiles.stoneExitLevelDownOutlineTeal,
                    Tiles.stoneExitLevelLeftOutlineTeal
                     )
         ),
    metal(
            ExitTiles(
                    Tiles.metalExitUp,
                    Tiles.metalExitRight,
                    Tiles.metalExitDown,
                    Tiles.metalExitLeft,
                    Tiles.metalExitLevelUp,
                    Tiles.metalExitLevelRight,
                    Tiles.metalExitLevelDown,
                    Tiles.metalExitLevelLeft,
                    Tiles.metalExitUpOutlineTeal,
                    Tiles.metalExitRightOutlineTeal,
                    Tiles.metalExitDownOutlineTeal,
                    Tiles.metalExitLeftOutlineTeal,
                    Tiles.metalExitLevelUpOutlineTeal,
                    Tiles.metalExitLevelRightOutlineTeal,
                    Tiles.metalExitLevelDownOutlineTeal,
                    Tiles.metalExitLevelLeftOutlineTeal
                     )
         ),
    rock(
            ExitTiles(
                    Tiles.rockExitUp,
                    Tiles.rockExitRight,
                    Tiles.rockExitDown,
                    Tiles.rockExitLeft,
                    Tiles.rockExitLevelUp,
                    Tiles.rockExitLevelRight,
                    Tiles.rockExitLevelDown,
                    Tiles.rockExitLevelLeft,
                    Tiles.rockExitUpOutlineTeal,
                    Tiles.rockExitRightOutlineTeal,
                    Tiles.rockExitDownOutlineTeal,
                    Tiles.rockExitLeftOutlineTeal,
                    Tiles.rockExitLevelUpOutlineTeal,
                    Tiles.rockExitLevelRightOutlineTeal,
                    Tiles.rockExitLevelDownOutlineTeal,
                    Tiles.rockExitLevelLeftOutlineTeal
                     )
        );
    
    companion object
    {
        fun getOrdinal(exitStyle : ExitStyle) : Int = exitStyle.ordinal
        fun getWallStyle(exitStyleNumber : Int) = values()[exitStyleNumber]
    }
}