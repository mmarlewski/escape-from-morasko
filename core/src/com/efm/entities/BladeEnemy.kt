package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.entity.Enemy
import com.efm.entity.Entity
import com.efm.room.RoomPosition

class BladeEnemy : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 50
    override var healthPoints = 50
    override var alive = true
    override val detectionRange = 1
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.bladeEnemy
    }
    
    override fun getOutlineTile() : TiledMapTile
    {
        return Tiles.bladeEnemyOutlineYellow
    }
    
    override fun detectedSpaces() : MutableList<RoomPosition>
    {
        val detectedSpaces = mutableListOf<RoomPosition>()
        for (i in -this.detectionRange..this.detectionRange)
        {
            for (j in -this.detectionRange .. this.detectionRange)
            {
                detectedSpaces.add((this.position.positionOffsetBy(i, Direction.up)).positionOffsetBy(j, Direction.left))
            }
        }
        
        return detectedSpaces
    }
}
