package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.passage.Exit
import com.efm.room.RoomPosition

class RockWall(vararg val directions : Direction) : Entity
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
    
    override fun getOutlineTile() : TiledMapTile?
    {
        return null
    }
}
