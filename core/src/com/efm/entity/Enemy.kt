package com.efm.entity

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.efm.*
import com.efm.Map
import com.efm.assets.Textures
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen
import com.efm.ui.gameScreen.ProgressBars

/**
 * Enemy has its own turn and can attack the Hero.
 */
interface Enemy : Character
{
    override val position : RoomPosition
    val detectionRange : Int
    val attackRange : Int
    val stepsInOneTurn : Int
    var healthBar : ProgressBar
    fun getOutlineRedTile() : TiledMapTile?
    
    fun performTurn()
    {
        var decision = -1
        val pathSpaces = PathFinding.findPathWithGivenRoom(position, World.hero.position, World.currentRoom)
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
                if (stepsSpaces != null)
                {
                    val stepsIndex = if (stepsSpaces.size == pathSpaces.size)
                    {
                        stepsSpaces.size - 1
                    }
                    else stepsSpaces.size
                    moveEnemy(position, pathSpaces[stepsIndex].position, stepsSpaces, this)
                }
            }
        }
        
    }
    
    fun enemyAttack()
    
    fun getDetectionPositions() : MutableList<RoomPosition>
    {
        val detectionPositions = mutableListOf<RoomPosition>()
        for (i in -detectionRange..detectionRange)
        {
            for (j in -detectionRange..detectionRange)
            {
                detectionPositions.add((position.positionOffsetBy(i, Direction4.up)).positionOffsetBy(j, Direction4.left))
            }
        }
        
        return detectionPositions
    }
    
    fun createOwnHealthBar() : ProgressBar
    {
        healthBar =
                ProgressBars.createBar(5f, Textures.knobEnemyHealthBarNinePatch, this.healthPoints, this.maxHealthPoints)
        GameScreen.gameStage.addActor(healthBar)
        healthBar.isVisible = true
        return healthBar
    }
    
    fun displayOwnHealthBar()
    {
        healthBar.isVisible = true
    }
    
    fun changeOwnHealthBarPos()
    {
        val orthoPosition = roomPositionToOrtho(position)
        val isoPosition = orthoToIso(orthoPosition)
        isoPosition.x
        isoPosition.y += Map.tileLengthHalfInPixels
        healthBar.setPosition(isoPosition.x, isoPosition.y)
        
        healthBar.value = healthPoints.toFloat()
    }
    
    fun hideOwnHealthBar()
    {
        healthBar.isVisible = false
    }
    
}
