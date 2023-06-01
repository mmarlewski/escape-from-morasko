package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.room.RoomPosition

class MetalWall(vararg val directions : Direction) : Entity
{
    override val position = RoomPosition()
    
    val up = Direction.up in directions
    val right = Direction.right in directions
    val down = Direction.down in directions
    val left = Direction.left in directions
    
    override fun getTile() : TiledMapTile?
    {
        return when
        {
            up && right && down && left     -> Tiles.metalWallUpRightDownLeft
            up && right && down && !left    -> Tiles.metalWallUpRightDown
            up && right && !down && left    -> Tiles.metalWallUpRightLeft
            up && right && !down && !left   -> Tiles.metalWallUpRight
            up && !right && down && left    -> Tiles.metalWallUpDownLeft
            up && !right && down && !left   -> Tiles.metalWallUpDown
            up && !right && !down && left   -> Tiles.metalWallUpLeft
            up && !right && !down && !left  -> Tiles.metalWallUp
            !up && right && down && left    -> Tiles.metalWallRightDownLeft
            !up && right && down && !left   -> Tiles.metalWallRightDown
            !up && right && !down && left   -> Tiles.metalWallRightLeft
            !up && right && !down && !left  -> Tiles.metalWallRight
            !up && !right && down && left   -> Tiles.metalWallDownLeft
            !up && !right && down && !left  -> Tiles.metalWallDown
            !up && !right && !down && left  -> Tiles.metalWallLeft
            !up && !right && !down && !left -> Tiles.metalWall
            else                            -> null
        }
    }
    
    override fun getOutlineYellowTile() : TiledMapTile?
    {
        return null
    }
}
