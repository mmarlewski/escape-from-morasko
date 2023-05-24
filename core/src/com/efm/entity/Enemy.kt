package com.efm.entity

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.room.RoomPosition

/**
 * Enemy has its own turn and can attack the Hero.
 */
interface Enemy : Character
{
    override val position : RoomPosition
    val detectionRange : Int
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.bladeEnemy
    }
    
    fun getDetectionPositions() : MutableList<RoomPosition>
    {
        val detectionPositions = mutableListOf<RoomPosition>()
        for (i in -detectionRange..detectionRange)
        {
            for (j in -detectionRange .. detectionRange)
            {
                detectionPositions.add((position.positionOffsetBy(i, Direction.up)).positionOffsetBy(j, Direction.left))
            }
        }
    
        return detectionPositions
    }
}