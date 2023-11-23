package com.efm.entities.enemies.chess

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entities.walls.*
import com.efm.entity.Character
import com.efm.entity.Enemy
import com.efm.entity.Entity
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.room.Space

class Pawn: Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 15
    override var healthPoints = 15
    override var alive = true
    override var isFrozen = false
    override fun getTile() : TiledMapTile?
    {
        return Tiles.chessPawnWhite
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile?
    {
        return Tiles.chessPawnOutlineYellow
    }
    
    override val detectionRange = 1
    override val attackRange = 0
    override val stepsInOneTurn = 0
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    lateinit var direction : Direction4
    override fun getOutlineRedTile() : TiledMapTile?
    {
        return Tiles.chessPawnOutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile?
    {
        return Tiles.chessPawnWhite
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return Tiles.chessPawnWhite
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return Tiles.chessPawnWhite
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
                for (pos in getPossibleMovePositions())
                {
                    if (World.currentRoom.isPositionWithinBounds(pos.x, pos.y))
                    {
                        var space = World.currentRoom.getSpace(pos)
                        if (space != null)
                        {
                            var entity = space.getEntity()
                            if (!space.isTreadableFor(this))
                            {
                                val queen = Queen()
                                queen.setChessPieceDirection(direction)
                                queen.createOwnHealthBar()
                                World.currentRoom.replaceEntityAt(queen, position)
                                break
                            }
                            else if (entity == null && space.isTraversableFor(this))
                            {
                                val path : List<Space?> =
                                        listOf(World.currentRoom.getSpace(position), World.currentRoom.getSpace(pos))
                                moveEnemy(position, space.position, path, this)
                                break
                            }
                        }
                    }
                }
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
    
    fun getPossibleAttackPositions() : MutableList<RoomPosition>
    {
        when (direction)
        {
            Direction4.up ->
            {
                val attackPos1 = position.positionOffsetBy(1, Direction8.upLeft)
                val attackPos2= position.positionOffsetBy(1, Direction8.upRight)
                return mutableListOf(attackPos1, attackPos2)
            }
            Direction4.down ->
            {
                val attackPos1 = position.positionOffsetBy(1, Direction8.downLeft)
                val attackPos2= position.positionOffsetBy(1, Direction8.downRight)
                return mutableListOf(attackPos1, attackPos2)
            }
            Direction4.left ->
            {
                val attackPos1 = position.positionOffsetBy(1, Direction8.upLeft)
                val attackPos2= position.positionOffsetBy(1, Direction8.downLeft)
                return mutableListOf(attackPos1, attackPos2)
            }
            Direction4.right ->
            {
                val attackPos1 = position.positionOffsetBy(1, Direction8.downRight)
                val attackPos2= position.positionOffsetBy(1, Direction8.upRight)
                return mutableListOf(attackPos1, attackPos2)
            }
        }
    }
    
    fun getPossibleMovePositions() : MutableList<RoomPosition>
    {
        var result = mutableListOf(position.positionOffsetBy(1, direction))
        result.add(position)
        return result
    }
    
    fun setChessPieceDirection(direction4 : Direction4)
    {
        direction = direction4
    }
}