package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction4
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.room.RoomPosition

class RockWall(vararg val directions : Direction4) : Entity
{
    override val position = RoomPosition()
    
    val up = Direction4.up in directions
    val right = Direction4.right in directions
    val down = Direction4.down in directions
    val left = Direction4.left in directions
    
    override fun getTile() : TiledMapTile?
    {
        return when
        {
            up && right && down && left     -> Tiles.rockWallUpRightDownLeft
            up && right && down && !left    -> Tiles.rockWallUpRightDown
            up && right && !down && left    -> Tiles.rockWallUpRightLeft
            up && right && !down && !left   -> Tiles.rockWallUpRight
            up && !right && down && left    -> Tiles.rockWallUpDownLeft
            up && !right && down && !left   -> Tiles.rockWallUpDown
            up && !right && !down && left   -> Tiles.rockWallUpLeft
            up && !right && !down && !left  -> Tiles.rockWallUp
            !up && right && down && left    -> Tiles.rockWallRightDownLeft
            !up && right && down && !left   -> Tiles.rockWallRightDown
            !up && right && !down && left   -> Tiles.rockWallRightLeft
            !up && right && !down && !left  -> Tiles.rockWallRight
            !up && !right && down && left   -> Tiles.rockWallDownLeft
            !up && !right && down && !left  -> Tiles.rockWallDown
            !up && !right && !down && left  -> Tiles.rockWallLeft
            !up && !right && !down && !left -> Tiles.rockWall
            else                            -> null
        }
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile?
    {
        return null
    }
}
