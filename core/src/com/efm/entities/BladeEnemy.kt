package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Enemy
import com.efm.entity.Entity
import com.efm.room.RoomCoordinates

class BladeEnemy : Entity, Enemy
{
    override val roomCoordinates = RoomCoordinates(0, 0)
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.bladeEnemy
    }
    
    override fun getOutlineTile() : TiledMapTile
    {
        return Tiles.bladeEnemyOutline
    }
}
