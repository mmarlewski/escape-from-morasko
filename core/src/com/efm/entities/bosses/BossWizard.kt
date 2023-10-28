package com.efm.entities.bosses

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entities.enemies.EnemyBat
import com.efm.entity.*
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.state.State
import com.efm.state.getState

class BossWizard : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 10
    override var healthPoints = 10
    override var alive = true
    override val detectionRange = 5
    override val attackRange = 5
    override val stepsInOneTurn = 3
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.wizardEnemy
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return Tiles.wizardEnemyOutlineYellow
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.wizardEnemyOutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile?
    {
        return when(n)
        {
            1->Tiles.skeletonIdle1
            2->Tiles.skeletonIdle2
            3->Tiles.skeletonIdle3
            else->null
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return when(n)
        {
            1->Tiles.skeletonMove1
            2->Tiles.skeletonMove2
            3->Tiles.skeletonMove3
            else->null
        }
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return Tiles.skeletonAttack
    }
    
    override fun getMoveSound() : Sound?
    {
        return Sounds.wizardMove
    }
    
    override fun performTurn()
    {
        val pathToHero = PathFinding.findPathInRoomForEntity(position, World.hero.position, World.currentRoom,this)
        if(pathToHero != null && pathToHero.size < 4)
        {
            areaOfEffectAttack()
        }
        else if (World.currentRoom.getEnemies().size <= 2)
        {
            summonMinions()
        }
        else
        {
            enemyAttack()
        }
    }
    
    override fun enemyAttack()
    {
        val heroPosition = World.hero.position.copy()
        val linePositions =
                LineFinding.findLineWithGivenRoom(position, heroPosition.copy(), World.currentRoom)
        if (linePositions != null)
        {
            val targetDirection = getDirection8(position, heroPosition.copy())
            val staffTile = if (targetDirection == null) null else Tiles.getStaffTile(targetDirection)
            val arrowTile = if (targetDirection == null) null else Tiles.getArrowTile(targetDirection)
            val impactTile = if (targetDirection == null) null else Tiles.getImpactTile(targetDirection)
        
            val animations = mutableListOf<Animation>()
        
            val animationSeconds = linePositions.size * 0.05f
            animations.add(Animation.showTile(staffTile, heroPosition.copy(), 0.2f))
            animations.add(Animation.action { playSoundOnce(Sounds.beam) })
            animations.add(Animation.moveTile(arrowTile, position, heroPosition.copy(), animationSeconds))
            animations.add(Animation.action { playSoundOnce(Sounds.beam) })
            animations.add(
                    Animation.simultaneous(
                            listOf(
                                    Animation.showTile(impactTile, heroPosition.copy(), 0.2f),
                                    Animation.cameraShake(1, 0.5f)
                                  )
                                          )
                          )
            animations.add(Animation.action {
            
                val attackedPosition = heroPosition.copy()
                val attackedSpace = World.currentRoom.getSpace(attackedPosition)
                val attackedEntity = attackedSpace?.getEntity()
                when (attackedEntity)
                {
                    is Character ->
                    {
                        attackedEntity.damageCharacter(5)
                    }
                }
            })
        
            Animating.executeAnimations(animations)
        }
    }
    
    fun areaOfEffectAttack()
    {
        val attackedPositions = mutableListOf<RoomPosition>()
        attackedPositions.add(position)
        attackedPositions.addAll(getSquarePerimeterPositions(position, 2))
    
        for (attackedPosition in attackedPositions)
        {
            if (attackedPosition == World.hero.position)
            {
                World.hero.damageCharacter(10)
            }
        }
    }
    
    fun summonMinions()
    {
        var count = 0
        while (count < 2)
        {
            val posX = (0 until World.currentRoom.heightInSpaces - 2).random()
            val posY = (0 until World.currentRoom.widthInSpaces - 2).random()
            if (World.currentRoom.isPositionWithinBounds(posX, posY) && World.currentRoom.getSpace(posX, posY)?.getEntity() == null
                    && World.currentRoom.getSpace(posX, posY)?.isTraversableFor(this) != false)
            {
                val minion = EnemyBat()
                minion.createOwnHealthBar()
                World.currentRoom.addEntityAt(minion, posX, posY)
                val currState = getState()
                if (currState is State.combat.enemies)
                {
                    currState.enemies = currState.enemies?.plus(minion)
                    currState.enemyIterator = currState.enemies?.iterator()
                    currState.currEnemy = when (val enemyIterator = currState.enemyIterator)
                    {
                        null -> null
                        else -> when (enemyIterator.hasNext())
                        {
                            true  -> enemyIterator.next()
                            false -> null
                        }
                    }
                }
                count ++
            }
            else
            {
                continue
            }
        }
    }
}
