package com.efm.entities.enemies.chess

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.*
import com.efm.item.PossibleItems
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.room.Space
import kotlin.math.pow

class Rook: Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 25
    override var healthPoints = 25
    override var alive = true
    override var isFrozen = false
    override var loot : PossibleItems = PossibleItems()
    override val roamingChance : Float = 0.0f
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.chessRookWhite
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return Tiles.chessRookOutlineYellow
    }
    
    override val detectionRange = 1
    override val attackRange = 0
    override var attackDamage = 20
    override val stepsInOneTurn = 0
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    lateinit var direction : Direction4
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.chessRookOutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile
    {
        return Tiles.chessRookWhite
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile
    {
        return Tiles.chessRookWhite
    }
    
    override fun getAttackTile() : TiledMapTile
    {
        return Tiles.chessRookWhite
    }
    
    override fun getMoveSound() : Sound
    {
        return Sounds.chessMove
    }
    
    override fun performTurn()
    {
        val worldCurrentRoom = World.currentRoom ?: return
        
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
                    if (worldCurrentRoom.isPositionWithinBounds(pos.x, pos.y))
                    {
                        val space = worldCurrentRoom.getSpace(pos)
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
                        listOf(worldCurrentRoom.getSpace(position), worldCurrentRoom.getSpace(bestMovePosition))
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
            val attackedSpace = World.currentRoom?.getSpace(attackedPosition)
            val attackedEntity = attackedSpace?.getEntity()
            when (attackedEntity)
            {
                is Character ->
                {
                    attackedEntity.damageCharacter(attackDamage)
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
        val worldCurrentRoom = World.currentRoom ?: return mutableListOf()
        
        val result = mutableListOf<RoomPosition>()
        result.add(position)
        for (posX in position.x - 1 downTo 1)
        {
            val space = worldCurrentRoom.getSpace(RoomPosition(posX, position.y))
            if (space != null)
            {
                if (space.getEntity() == null && space.isTraversableFor(this))
                {
                    result.add(RoomPosition(posX, position.y))
                } else
                {
                    break
                }
            }
        }
        for (posX in position.x + 1 until  worldCurrentRoom.widthInSpaces)
        {
            val space = worldCurrentRoom.getSpace(RoomPosition(posX, position.y))
            if (space != null)
            {
                if (space.getEntity() == null && space.isTraversableFor(this))
                {
                    result.add(RoomPosition(posX, position.y))
                } else
                {
                    break
                }
            }
        }
        for (posY in position.y - 1 downTo 1)
        {
            val space = worldCurrentRoom.getSpace(RoomPosition(position.x, posY))
            if (space != null)
            {
                if (space.getEntity() == null && space.isTraversableFor(this))
                {
                    result.add(RoomPosition(position.x, posY))
                } else
                {
                    break
                }
            }
        }
        for (posY in position.y + 1 until worldCurrentRoom.heightInSpaces)
        {
            val space = worldCurrentRoom.getSpace(RoomPosition(position.x, posY))
            if (space != null)
            {
                if (space.getEntity() == null && space.isTraversableFor(this))
                {
                    result.add(RoomPosition(position.x, posY))
                } else
                {
                    break
                }
            }
        }
        return result
    }
    
    fun setChessPieceDirection(direction4 : Direction4)
    {
        direction = direction4
    }
    
    fun checkPositionDistanceFromHero(pos : RoomPosition) : Double
    {
        return kotlin.math.sqrt(
                (kotlin.math.abs(pos.x - World.hero.position.x)
                        .toDouble()).pow(2.0) + (kotlin.math.abs(pos.y - World.hero.position.y).toDouble().pow(2.0)
                        ))
    }
}