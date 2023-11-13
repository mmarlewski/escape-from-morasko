package com.efm.entities.walls

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction4
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.room.RoomPosition

class MetalWall(vararg dirs : Direction4) : Entity
{
    override val position = RoomPosition()
    val directions = mutableListOf(*dirs)
    
    override fun getTile() : TiledMapTile?
    {
        val up = Direction4.up in directions
        val right = Direction4.right in directions
        val down = Direction4.down in directions
        val left = Direction4.left in directions
        
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
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile?
    {
        return null
    }
    
    // for serialization
    constructor() : this(Direction4.up)
}
