package com.efm.entities.walls

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction4
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.room.RoomPosition

class Wall(
        private val style : WallStyle, vararg directions : Direction4
          ) : Entity
{
    override val position : RoomPosition = RoomPosition()
    
    val up = Direction4.up in directions
    val right = Direction4.right in directions
    val down = Direction4.down in directions
    val left = Direction4.left in directions
    
    override fun getTile() : TiledMapTile? = when
    {
        right && down -> style.tiles.wallRightDown
        right         -> style.tiles.wallRight
        down          -> style.tiles.wallDown
        else          -> null
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile? = null
}

internal class WallTiles(val wallRight : TiledMapTile?, val wallDown : TiledMapTile?, val wallRightDown : TiledMapTile?)

enum class WallStyle(internal val tiles : WallTiles)
{
    stone(WallTiles(Tiles.stoneWallRight, Tiles.stoneWallDown, Tiles.stoneWallRightDown)),
    metal(WallTiles(Tiles.metalWallRight, Tiles.metalWallDown, Tiles.metalWallRightDown)),
    rock(WallTiles(Tiles.rockWallRight, Tiles.rockWallDown, Tiles.rockWallRightDown)),
    cobblestoneDark(WallTiles(Tiles.cobblestoneDarkWallRight, Tiles.cobblestoneDarkWallDown, null)),
    cobblestoneLight(
            WallTiles(
                    Tiles.cobblestoneLightWallRight,
                    Tiles.cobblestoneLightWallDown,
                    Tiles.cobblestoneLightWallRightDown
                     )
                    ),
    cobblestoneDarkTall(WallTiles(Tiles.cobblestoneDarkTallWallRight, Tiles.cobblestoneDarkTallWallDown, null)),
    cobblestoneLightTall(WallTiles(Tiles.cobblestoneLightTallWallRight, Tiles.cobblestoneLightTallWallDown, null)),
    brickOrangeDark(WallTiles(Tiles.brickOrangeDarkWallRight, Tiles.brickOrangeDarkWallDown, null)),
    brickOrangeLight(WallTiles(Tiles.brickOrangeLightWallRight, Tiles.brickOrangeLightWallDown, null)),
    brickOrangeDarkTall(WallTiles(Tiles.brickOrangeDarkTallWallRight, Tiles.brickOrangeDarkTallWallDown, null)),
    brickOrangeLightTall(WallTiles(Tiles.brickOrangeLightTallWallRight, Tiles.brickOrangeLightTallWallDown, null)),
    brickRedDark(WallTiles(Tiles.brickRedDarkWallRight, Tiles.brickRedDarkWallDown, null)),
    brickRedLight(WallTiles(Tiles.brickRedLightWallRight, Tiles.brickRedLightWallDown, null)),
    brickRedDarkTall(WallTiles(Tiles.brickRedDarkTallWallRight, Tiles.brickRedDarkTallWallDown, null)),
    brickRedLightTall(WallTiles(Tiles.brickRedLightTallWallRight, Tiles.brickRedLightTallWallDown, null));
    
    companion object
    {
        fun getOrdinal(wallStyle : WallStyle) : Int = wallStyle.ordinal
        fun getWallStyle(wallStyleNumber : Int) = values()[wallStyleNumber]
    }
}
