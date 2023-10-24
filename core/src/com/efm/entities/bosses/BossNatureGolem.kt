package com.efm.entities.bosses

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.*
import com.efm.level.World
import com.efm.room.Base
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

class BossNatureGolem : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 100
    override var healthPoints = 10
    override var alive = true
    override val detectionRange = 1
    override val attackRange = 1
    override val stepsInOneTurn = 2
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    private var previousPosition : RoomPosition? = null
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.golemIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.golemIdle1OutlineYellow
            2    -> Tiles.golemIdle2OutlineYellow
            3    -> Tiles.golemIdle1OutlineYellow
            4    -> Tiles.golemIdle2OutlineYellow
            else -> Tiles.golemIdle1OutlineYellow
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.golemIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.golemIdle1
            2    -> Tiles.golemIdle2
            3    -> Tiles.golemIdle1
            4    -> Tiles.golemIdle2
            else -> Tiles.golemIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.golemIdle1
            2    -> Tiles.golemIdle2
            3    -> Tiles.golemIdle1
            4    -> Tiles.golemIdle2
            else -> Tiles.golemIdle1
        }
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return Tiles.golemAttack
    }
    
    override fun getMoveSound() : Sound?
    {
        return Sounds.golemMove
    }
    
    override fun enemyAttack()
    {
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(this.position, heroPosition)
        val swordTile = if (heroDirection == null) null else Tiles.getSwordTile(heroDirection)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.descendTile(swordTile, heroPosition.copy(), 0.2f, 0.25f)
        animations += Animation.action { playSoundOnce(Sounds.golemAttack) }
        animations += Animation.simultaneous(
                listOf(
                        Animation.showTile(Tiles.impact, heroPosition.copy(), 0.2f),
                        Animation.cameraShake(1, 0.5f)
                      )
                                            )
        animations += Animation.action {
            
            val attackedPosition = World.hero.position
            val attackedSpace = World.currentRoom.getSpace(attackedPosition)
            val attackedEntity = attackedSpace?.getEntity()
            when (attackedEntity)
            {
                is Character ->
                {
                    attackedEntity.damageCharacter(75)
                }
            }
        }
        
        Animating.executeAnimations(animations)
    }
    
    override fun performTurn()
    {
        var decision = -1
        
        val directPathSpaces = PathFinding.findPathWithGivenRoom(position, World.hero.position, World.currentRoom)
        
        var minPathLength = directPathSpaces?.size ?: Int.MAX_VALUE
        var minPathSpaces = directPathSpaces
        
        if (minPathSpaces == null)
        {
            val squarePositions = getSquareAreaPositions(World.hero.position, 2)
            for (squarePosition in squarePositions)
            {
                val squareSpace = World.currentRoom.getSpace(squarePosition)
                
                if (squareSpace != null && squareSpace.isTraversable())
                {
                    val pathSpaces = PathFinding.findPathWithGivenRoom(position, squarePosition, World.currentRoom)
                    
                    if (!pathSpaces.isNullOrEmpty() && pathSpaces.size < minPathLength)
                    {
                        minPathLength = pathSpaces.size
                        minPathSpaces = pathSpaces
                    }
                }
            }
        }
        
        for (pos in getSquareAreaPositions(position, attackRange))
        {
            if (pos == World.hero.position)
            {
                decision = 0
            }
        }
        
        if (decision != 0)
        {
            if (minPathSpaces != null)
            {
                val pathForThisTurn = minPathSpaces.take(stepsInOneTurn + 1)
                decision = 1
                
                // Replace the tiles that golem goes through in this turn
                for (space in pathForThisTurn)
                {
                    replaceTileWithGrass(space.position)
                }
            }
        }
        
        when (decision)
        {
            0 ->
            {
                enemyAttack()
            }
            
            1 ->
            {
                moveTowardsHero(minPathSpaces)
            }
        }
    }
    
    private fun replaceTileWithGrass(tilePosition : RoomPosition)
    {
        val currentPosition = World.currentRoom.getSpace(tilePosition)
        currentPosition?.changeBase(Base.grass)
        GameScreen.updateMapBaseLayer()
    }
    
}
