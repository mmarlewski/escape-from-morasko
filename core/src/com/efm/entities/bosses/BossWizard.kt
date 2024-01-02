package com.efm.entities.bosses

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entities.enemies.bat.EnemyBat
import com.efm.entity.*
import com.efm.item.PossibleItems
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.state.State
import com.efm.state.getState

class BossWizard : BaseBoss()
{
    override val position = RoomPosition()
    override var maxHealthPoints = 30
    override var healthPoints = 30
    override var alive = true
    override val attackRange = 5
    override var attackDamage = 20
    override val stepsInOneTurn = 3
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override var isFrozen = false
    override var loot : PossibleItems = PossibleItems()
    
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
        return when (n)
        {
            1    -> Tiles.skeletonIdle1
            2    -> Tiles.skeletonIdle2
            3    -> Tiles.skeletonIdle3
            else -> null
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.skeletonMove1
            2    -> Tiles.skeletonMove2
            3    -> Tiles.skeletonMove1
            else -> null
        }
    }
    
    override fun getAttackTile() : TiledMapTile
    {
        return Tiles.skeletonAttack
    }
    
    override fun getMoveSound() : Sound
    {
        return Sounds.wizardMove
    }
    
    override fun performTurn()
    {
        if (!isFrozen)
        {
            val worldCurrentRoom = World.currentRoom
            val pathToHero = if (worldCurrentRoom != null) PathFinding.findPathInRoomForEntity(
                    position,
                    World.hero.position,
                    worldCurrentRoom,
                    this
                                                                                              )
            else null
            if (pathToHero != null && pathToHero.size < 4)
            {
                areaOfEffectAttack()
            }
            else if ((World.currentRoom?.getEnemies()?.size ?: 0) <= 2)
            {
                summonMinions()
            }
            else
            {
                enemyAttack()
            }
        }
        else
        {
            isFrozen = false
        }
    }
    
    override fun enemyAttack()
    {
        val heroPosition = World.hero.position.copy()
        val worldCurrentRoom = World.currentRoom
        val linePositions = if (worldCurrentRoom != null) LineFinding.findLineWithGivenRoom(
                position,
                heroPosition.copy(),
                worldCurrentRoom
                                                                                           )
        else null
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
                val attackedSpace = World.currentRoom?.getSpace(attackedPosition)
                val attackedEntity = attackedSpace?.getEntity()
                when (attackedEntity)
                {
                    is Character ->
                    {
                        attackedEntity.damageCharacter(attackDamage)
                    }
                }
            })
            
            Animating.executeAnimations(animations)
        }
    }
    
//    override fun onDeath()
//    {
//        if (World.currentRoom?.name != "finalRoom")
//        {
//            showSkillAssignPopUpAfterBossKill(this)
//            addBossToDefeatedBossesList(Boss.Wizard)
//        }
//        increaseHeroStats(5, 3)
//    }
    
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
        val worldCurrentRoom = World.currentRoom ?: return
        var count = 0
        while (count < 2)
        {
            val posX = (0 until worldCurrentRoom.heightInSpaces - 2).random()
            val posY = (0 until worldCurrentRoom.widthInSpaces - 2).random()
            if (worldCurrentRoom.isPositionWithinBounds(posX, posY) && worldCurrentRoom.getSpace(posX, posY)
                            ?.getEntity() == null
                    && worldCurrentRoom.getSpace(posX, posY)?.isTraversableFor(this) != false
            )
            {
                val minion = EnemyBat()
                minion.createOwnHealthBar()
                worldCurrentRoom.addEntityAt(minion, posX, posY)
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
                count++
            }
            else
            {
                continue
            }
        }
    }
}
