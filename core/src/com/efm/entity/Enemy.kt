package com.efm.entity

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.*
import com.efm.Map
import com.efm.assets.Tiles
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.room.Space

/**
 * Enemy has its own turn and can attack the Hero.
 */
interface Enemy : Character
{
    override val position : RoomPosition
    val detectionRange : Int
    val attackRange : Int
    val stepsInOneTurn : Int
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.bladeEnemy
    }
    
    fun performTurn()
    {
        var decision = -1
        val pathSpaces = Pathfinding.findPathWithGivenRoom(position, World.hero.position, World.currentRoom)
        for (pos in getSquareAreaPositions(position, attackRange))
        {
            if (pos == World.hero.position)
            {
                decision = 0
            }
        }
        if (decision != 0)
        {
            if (pathSpaces != null)
            {
                decision = 1
            }
        }
        
        when (decision)
        {
            0 ->
            {
                //attack
                enemyAttack()
            }
            1 ->
            {
                val stepsSpaces = pathSpaces?.take(stepsInOneTurn)
                if (stepsSpaces != null) {
                    val stepsIndex = if (stepsSpaces.size == pathSpaces.size) {stepsSpaces.size - 1} else stepsSpaces.size
                    moveEnemy(position, pathSpaces[stepsIndex].position, stepsSpaces, this)
                }
            }
        }
        
    }
    
    fun enemyAttack()
    {
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