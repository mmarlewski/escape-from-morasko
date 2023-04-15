package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Enemy
import com.efm.entity.Entity
import com.efm.room.RoomPosition

class CrossbowEnemy : Entity, Enemy
{
    override val position = RoomPosition()
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.crossbowEnemy
    }
    
    override fun getOutlineTile() : TiledMapTile
    {
        return Tiles.crossbowEnemyOutline
    }
}
