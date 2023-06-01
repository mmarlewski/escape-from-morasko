package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.room.RoomPosition

class StoneWall(vararg val directions : Direction) : Entity
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
            up && right && down && left     -> Tiles.stoneWallUpRightDownLeft
            up && right && down && !left    -> Tiles.stoneWallUpRightDown
            up && right && !down && left    -> Tiles.stoneWallUpRightLeft
            up && right && !down && !left   -> Tiles.stoneWallUpRight
            up && !right && down && left    -> Tiles.stoneWallUpDownLeft
            up && !right && down && !left   -> Tiles.stoneWallUpDown
            up && !right && !down && left   -> Tiles.stoneWallUpLeft
            up && !right && !down && !left  -> Tiles.stoneWallUp
            !up && right && down && left    -> Tiles.stoneWallRightDownLeft
            !up && right && down && !left   -> Tiles.stoneWallRightDown
            !up && right && !down && left   -> Tiles.stoneWallRightLeft
            !up && right && !down && !left  -> Tiles.stoneWallRight
            !up && !right && down && left   -> Tiles.stoneWallDownLeft
            !up && !right && down && !left  -> Tiles.stoneWallDown
            !up && !right && !down && left  -> Tiles.stoneWallLeft
            !up && !right && !down && !left -> Tiles.stoneWall
            else                            -> null
        }
    }
    
    override fun getOutlineYellowTile() : TiledMapTile?
    {
        return null
    }
}
