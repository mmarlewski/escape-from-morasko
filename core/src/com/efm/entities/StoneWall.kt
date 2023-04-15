package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.room.RoomPosition

class StoneWall(
        val isVisibleUp : Boolean,
        val isVisibleRight : Boolean,
        val isVisibleDown : Boolean,
        val isVisibleLeft : Boolean
               ) : Entity
{
    override val position = RoomPosition()
    
    override fun getTile() : TiledMapTile
    {
        return when
        {
            isVisibleUp && !isVisibleRight && !isVisibleDown && !isVisibleLeft -> Tiles.stoneWallUp
            !isVisibleUp && isVisibleRight && !isVisibleDown && !isVisibleLeft -> Tiles.stoneWallRight
            !isVisibleUp && !isVisibleRight && isVisibleDown && !isVisibleLeft -> Tiles.stoneWallDown
            !isVisibleUp && !isVisibleRight && !isVisibleDown && isVisibleLeft -> Tiles.stoneWallLeft
            isVisibleUp && isVisibleRight && !isVisibleDown && !isVisibleLeft  -> Tiles.stoneWallUpRight
            !isVisibleUp && isVisibleRight && isVisibleDown && !isVisibleLeft  -> Tiles.stoneWallRightDown
            !isVisibleUp && !isVisibleRight && isVisibleDown && isVisibleLeft  -> Tiles.stoneWallDownLeft
            isVisibleUp && !isVisibleRight && !isVisibleDown && isVisibleLeft  -> Tiles.stoneWallLeftUp
            else                                                               -> Tiles.stoneWallUpRightDownLeft
        }
    }
    
    override fun getOutlineTile() : TiledMapTile?
    {
        return null
    }
}
