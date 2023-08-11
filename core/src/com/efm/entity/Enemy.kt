package com.efm.entity

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
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
    var healthStack : Stack
    
    fun getOutlineRedTile() : TiledMapTile?
    
    fun getIdleTile(n : Int) : TiledMapTile?
    
    fun getIdleTile() : TiledMapTile?
    {
        return getIdleTile(IdleAnimation.idleAnimationCount)
    }
    
    fun getMoveTile(n : Int) : TiledMapTile?
    
    fun getAttackTile() : TiledMapTile?
    
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
    
    fun createOwnHealthBar()
    {
        healthBar =
                ProgressBars.createBar(5f, Textures.knobEnemyHealthBarNinePatch, this.healthPoints, this.maxHealthPoints)
//        GameScreen.gameStage.addActor(healthBar)
        val healthLabel = ProgressBars.createLabel(this.healthPoints, this.maxHealthPoints)
        healthLabel.isVisible = true
        healthStack = ProgressBars.createEnemyHealthBarStack(healthBar, 50f, healthLabel)
        GameScreen.gameStage.addActor(healthStack)
        healthStack.isVisible = true
    }
    
    fun displayOwnHealthBar()
    {
        healthStack.isVisible = true
    }
    
    fun changeOwnHealthBarPos()
    {
        val orthoPosition = roomPositionToOrtho(position)
        val isoPosition = orthoToIso(orthoPosition)
        isoPosition.y += Map.tileLengthHalfInPixels
        healthStack.setPosition(isoPosition.x, isoPosition.y)
        healthBar.value = healthPoints.toFloat()
//        val orthoPos = roomPositionToOrtho(position)
//        val isoPos = GameScreen.gameViewport.project(orthoToIso(orthoPos))
//        healthStack.setPosition(isoPos.x * 0.5f + 25f, isoPos.y * 0.5f + 70f)
//        healthBar.value = healthPoints.toFloat()
    }
    
    fun hideOwnHealthBar()
    {
        healthStack.isVisible = false
    }
    
}
