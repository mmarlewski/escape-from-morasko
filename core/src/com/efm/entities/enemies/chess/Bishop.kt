package com.efm.entities.enemies.chess

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.Character
import com.efm.entity.Enemy
import com.efm.entity.Entity
import com.efm.level.World
import com.efm.room.*
import kotlin.math.pow

class Bishop: Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 5
    override var healthPoints = 5
    override var alive = true
    override var isFrozen = false
    override fun getTile() : TiledMapTile?
    {
        return Tiles.chessBishopWhite
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile?
    {
        return Tiles.chessBishopOutlineYellow
    }
    
    override val detectionRange = 1
    override val attackRange = 0
    override val stepsInOneTurn = 0
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    lateinit var direction : Direction4
    override fun getOutlineRedTile() : TiledMapTile?
    {
        return Tiles.chessBishopOutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile?
    {
        return Tiles.chessBishopWhite
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return Tiles.chessBishopWhite
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return Tiles.chessBishopWhite
    }
    
    override fun getMoveSound() : Sound?
    {
        return Sounds.chessMove
    }
    
    override fun performTurn()
    {
        if (!isFrozen)
        {
            val heroPos = World.hero.position
            var attacked = false
            for (pos in getPossibleAttackPositions())
            {
                if (pos == heroPos)
                {
                    enemyAttack()
                    attacked = true
                }
            }
            if (!attacked)
            {
                var bestMovePosition : RoomPosition = getPossibleMovePositions()[0]
                for (pos in getPossibleMovePositions())
                {
                    if (World.currentRoom.isPositionWithinBounds(pos.x, pos.y))
                    {
                        var space = World.currentRoom.getSpace(pos)
                        if (space != null)
                        {
                            if (space.getEntity() == null && space.isTraversableFor(this))
                            {
                                if (checkPositionDistanceFromHero(pos) < checkPositionDistanceFromHero(bestMovePosition))
                                {
                                    bestMovePosition = pos
                                }
                            }
                        }
                    }
                }
                val path : List<Space?> =
                        listOf(World.currentRoom.getSpace(position), World.currentRoom.getSpace(bestMovePosition))
                moveEnemy(position, bestMovePosition, path, this)
            }
        } else
        {
            isFrozen = false
        }
    }
    
    override fun enemyAttack()
    {
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(this.position, heroPosition)
        val impactTile = if (heroDirection == null) null else Tiles.getSwordTile(heroDirection)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.simultaneous(
                listOf(
                        Animation.cameraShake(1, 0.5f),
                        Animation.action { playSoundOnce(Sounds.chessAttack) },
                        Animation.showTile(impactTile, heroPosition.copy(), 0.5f)
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
                    attackedEntity.damageCharacter(5)
                }
            }
        }
        Animating.executeAnimations(animations)
    }
    
    fun getPossibleAttackPositions() : List<RoomPosition>
    {
        return position.surroundingPositions(1)
    }
    
    fun getPossibleMovePositions() : MutableList<RoomPosition>
    {
        val result = mutableListOf<RoomPosition>()
        var upLeftFound = false
        var upRightFound = false
        var downLeftFound = false
        var downRightFound = false
        var spaceBeingChecked : Space
        for (i in 1..100)
        {
            if (!upLeftFound)
            {
                if (World.currentRoom.isPositionWithinBounds(RoomPosition(position.x - i, position.y - i)))
                {
                    spaceBeingChecked = World.currentRoom.getSpace(RoomPosition(position.x - i, position.y - i))!!
                    if (spaceBeingChecked.getEntity() == null && spaceBeingChecked.isTraversableFor(this))
                    {
                        result.add(RoomPosition(position.x - i, position.y - i))
                    } else
                    {
                        upLeftFound = true
                    }
                } else
                {
                    upLeftFound = true
                }
            }
            if (!upRightFound)
            {
                if (World.currentRoom.isPositionWithinBounds(RoomPosition(position.x + i, position.y - i)))
                {
                    spaceBeingChecked = World.currentRoom.getSpace(RoomPosition(position.x + i, position.y - i))!!
                    if (spaceBeingChecked.getEntity() == null && spaceBeingChecked.isTraversableFor(this))
                    {
                        result.add(RoomPosition(position.x + i, position.y - i))
                    } else
                    {
                        upRightFound = true
                    }
                } else
                {
                    upRightFound = true
                }
            }
            if (!downLeftFound)
            {
                if (World.currentRoom.isPositionWithinBounds(RoomPosition(position.x - i, position.y + i)))
                {
                    spaceBeingChecked = World.currentRoom.getSpace(RoomPosition(position.x - i, position.y + i))!!
                    if (spaceBeingChecked.getEntity() == null && spaceBeingChecked.isTraversableFor(this))
                    {
                        result.add(RoomPosition(position.x - i, position.y + i))
                    } else
                    {
                        downLeftFound = true
                    }
                } else
                {
                    downLeftFound = true
                }
            }
            if (!downRightFound)
            {
                if (World.currentRoom.isPositionWithinBounds(RoomPosition(position.x + i, position.y + i)))
                {
                    spaceBeingChecked = World.currentRoom.getSpace(RoomPosition(position.x + i, position.y + i))!!
                    if (spaceBeingChecked.getEntity() == null && spaceBeingChecked.isTraversableFor(this))
                    {
                        result.add(RoomPosition(position.x + i, position.y + i))
                    } else
                    {
                        downRightFound = true
                    }
                } else
                {
                    downRightFound = true
                }
            }
        }
        return result
    }
    
    fun checkPositionDistanceFromHero(pos : RoomPosition) : Double
    {
        return kotlin.math.sqrt(
                (kotlin.math.abs(pos.x - World.hero.position.x)
                        .toDouble()).pow(2.0) + (kotlin.math.abs(pos.y - World.hero.position.y).toDouble().pow(2.0)
                        ))
    }
    
    fun setChessPieceDirection(direction4 : Direction4)
    {
        direction = direction4
    }
}