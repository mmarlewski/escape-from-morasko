package com.efm.entities.walls

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.Direction4
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.room.RoomPosition

class Wall(var style : WallStyle, vararg dirs : Direction4) : Entity
{
    override val position : RoomPosition = RoomPosition()
    val directions = mutableListOf(*dirs)
    
    override fun getTile() : TiledMapTile?
    {
        val up = Direction4.up in directions
        val right = Direction4.right in directions
        val down = Direction4.down in directions
        val left = Direction4.left in directions
        
        return when
        {
            right && down -> style.tiles.wallRightDown
            right         -> style.tiles.wallRight
            down          -> style.tiles.wallDown
            else          -> null
        }
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile? = null
    
    // for serialization
    
    constructor() : this(WallStyle.stone)
    
    override fun write(json : Json?)
    {
        super.write(json)
        
        if (json != null)
        {
            json.writeValue("style", this.style.name)
            json.writeValue("directions", this.directions)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super.read(json, jsonData)
        
        if (json != null)
        {
            val jsonStyle = json.readValue("style", String::class.java, jsonData)
            if (jsonStyle != null) this.style = WallStyle.valueOf(jsonStyle)
            
            val jsonDirections = json.readValue("directions", List::class.java, jsonData)
            if (jsonDirections != null)
            {
                this.directions.clear()
                for (direction in jsonDirections)
                {
                    if (direction is Direction4)
                    {
                        this.directions.add(direction)
                    }
                }
            }
        }
    }
}

internal class WallTiles(val wallRight : TiledMapTile?, val wallDown : TiledMapTile?, val wallRightDown : TiledMapTile?)

enum class WallStyle(internal val tiles : WallTiles)
{
    stone(WallTiles(Tiles.stoneWallRight, Tiles.stoneWallDown, Tiles.stoneWallRightDown)),
    metal(WallTiles(Tiles.metalWallRight, Tiles.metalWallDown, Tiles.metalWallRightDown)),
    rock(WallTiles(Tiles.rockWallRight, Tiles.rockWallDown, Tiles.rockWallRightDown)),
    cobblestoneDark(
            WallTiles(
                    Tiles.cobblestoneDarkWallRight,
                    Tiles.cobblestoneDarkWallDown,
                    Tiles.cobblestoneDarkWallRightDown
                     )
                   ),
    cobblestoneLight(
            WallTiles(
                    Tiles.cobblestoneLightWallRight,
                    Tiles.cobblestoneLightWallDown,
                    Tiles.cobblestoneLightWallRightDown
                     )
                    ),
    cobblestoneDarkTall(
            WallTiles(
                    Tiles.cobblestoneDarkTallWallRight,
                    Tiles.cobblestoneDarkTallWallDown,
                    Tiles.cobblestoneDarkTallWallRightDown
                     )
                       ),
    cobblestoneLightTall(
            WallTiles(
                    Tiles.cobblestoneLightTallWallRight,
                    Tiles.cobblestoneLightTallWallDown,
                    Tiles.cobblestoneLightTallWallRightDown
                     )
                        ),
    brickOrangeDark(
            WallTiles(
                    Tiles.brickOrangeDarkWallRight,
                    Tiles.brickOrangeDarkWallDown,
                    Tiles.brickOrangeDarkWallRightDown
                     )
                   ),
    brickOrangeLight(
            WallTiles(
                    Tiles.brickOrangeLightWallRight,
                    Tiles.brickOrangeLightWallDown,
                    Tiles.brickOrangeLightWallRightDown
                     )
                    ),
    brickOrangeDarkTall(
            WallTiles(
                    Tiles.brickOrangeDarkTallWallRight,
                    Tiles.brickOrangeDarkTallWallDown,
                    Tiles.brickOrangeDarkTallWallRightDown
                     )
                       ),
    brickOrangeLightTall(
            WallTiles(
                    Tiles.brickOrangeLightTallWallRight,
                    Tiles.brickOrangeLightTallWallDown,
                    Tiles.brickOrangeLightTallWallRightDown
                     )
                        ),
    brickRedDark(WallTiles(Tiles.brickRedDarkWallRight, Tiles.brickRedDarkWallDown, Tiles.brickRedDarkWallRightDown)),
    brickRedLight(WallTiles(Tiles.brickRedLightWallRight, Tiles.brickRedLightWallDown, Tiles.brickRedLightWallRightDown)),
    brickRedDarkTall(
            WallTiles(
                    Tiles.brickRedDarkTallWallRight,
                    Tiles.brickRedDarkTallWallDown,
                    Tiles.brickRedDarkTallWallRightDown
                     )
                    ),
    brickRedLightTall(
            WallTiles(
                    Tiles.brickRedLightTallWallRight,
                    Tiles.brickRedLightTallWallDown,
                    Tiles.brickRedLightTallWallRightDown
                     )
                     );
    
    companion object
    {
        val stoneWalls = listOf(
                rock,
                cobblestoneLightTall,
                cobblestoneLight,
                cobblestoneDarkTall,
                cobblestoneDark
                               )
    
        val brickWalls = listOf(
                brickOrangeDark,
                brickOrangeLight,
                brickOrangeDarkTall,
                brickOrangeLightTall,
                brickRedDark,
                brickRedLight,
                brickRedDarkTall,
                brickRedLightTall
                               )
    }
}
