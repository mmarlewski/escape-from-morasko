package com.efm.entities.enemies.chess

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entities.bosses.addBossToDefeatedBossesList
import com.efm.entity.Character
import com.efm.entity.Enemy
import com.efm.entity.Entity
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.room.Space

class King : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 5
    override var healthPoints = 5
    override var alive = true
    override var isFrozen = false
    override fun getTile() : TiledMapTile?
    {
        return Tiles.chessKingWhite
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile?
    {
        return Tiles.chessKingOutlineYellow
    }
    
    override val detectionRange = 1
    override val attackRange = 0
    override val stepsInOneTurn = 0
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    lateinit var direction : Direction4
    override fun getOutlineRedTile() : TiledMapTile?
    {
        return Tiles.chessKingOutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile?
    {
        return Tiles.chessKingWhite
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return Tiles.chessKingWhite
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return Tiles.chessKingWhite
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
                                if (checkNbrOfPiecesAround(pos) < checkNbrOfPiecesAround(bestMovePosition))
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
    
    override fun onDeath()
    {
        for (enemy in World.currentRoom.getEnemies())
        {
            enemy.damageCharacter(enemy.healthPoints)
        }
        if (World.currentRoom.name != "finalRoom")
        {
            showSkillAssignPopUpAfterBossKill(this)
        }
    }
    
    fun getPossibleAttackPositions() : List<RoomPosition>
    {
        return position.surroundingPositions(1)
    }
    
    fun getPossibleMovePositions() : List<RoomPosition>
    {
        return position.surroundingPositions(1)
    }
    
    fun setChessPieceDirection(direction4 : Direction4)
    {
        direction = direction4
    }
    
    fun checkNbrOfPiecesAround(pos : RoomPosition) : Int
    {
        var count = 0
        for (currPosBeingChecked in pos.surroundingPositions(2))
        {
            if (World.currentRoom.isPositionWithinBounds(currPosBeingChecked))
            {
                if (World.currentRoom.getSpace(currPosBeingChecked)?.getEntity() != null)
                {
                    count += 1
                }
            }
        }
        return count
    }
    
}