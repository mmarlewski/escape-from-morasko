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
    rock(WallTiles(Tiles.rockWallRight, Tiles.rockWallDown, Tiles.rockWallRightDown));
    
    companion object
    {
        fun getOrdinal(wallStyle : WallStyle) : Int = wallStyle.ordinal
        fun getWallStyle(wallStyleNumber : Int) = values()[wallStyleNumber]
    }
}