package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.*
import com.efm.level.World
import com.efm.room.RoomPosition

class EnemySkeleton : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 10
    override var healthPoints = 10
    override var alive = true
    override val detectionRange = 2
    override val attackRange = 3
    override val stepsInOneTurn = 1
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.skeletonIdle1
    }
    
    override fun getOutlineYellowTile() : TiledMapTile
    {
        return Tiles.crossbowEnemyOutlineYellow
    }
    
    override fun getOutlineRedTile(n : Int) : TiledMapTile?
    {
        return Tiles.crossbowEnemyOutlineRed
    }
    
    override fun getIIdleTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.skeletonIdle1
            2    -> Tiles.skeletonIdle3
            3    -> Tiles.skeletonIdle2
            4    -> Tiles.skeletonIdle1
            else -> Tiles.skeletonIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.skeletonMove1
            2    -> Tiles.skeletonMove2
            3    -> Tiles.skeletonMove2
            4    -> Tiles.skeletonMove3
            else -> Tiles.skeletonMove1
        }
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return Tiles.skeletonAttack
    }
    
    override fun enemyAttack()
    {
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(this.position, heroPosition)
        val arrowTile = if (heroDirection == null) null else Tiles.getArrowTile(heroDirection)
        val impactTile = if (heroDirection == null) null else Tiles.getImpactTile(heroDirection)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.action { playSoundOnce(Sounds.bowShot) }
        animations += Animation.moveTile(arrowTile, position, World.hero.position, 0.2f)
        animations += Animation.action { playSoundOnce(Sounds.bowImpact) }
        animations += Animation.simultaneous(
                listOf(
                        Animation.showTile(impactTile, heroPosition.copy(), 0.2f),
                        Animation.cameraShake(1, 0.5f)
                      )
                                            )
        animations += Animation.action {
            val attackedPosition = heroPosition
            val attackedSpace = World.currentRoom.getSpace(attackedPosition)
            val attackedEntity = attackedSpace?.getEntity()
            when (attackedEntity)
            {
                is Character ->
                {
                    attackedEntity.damageCharacter(2)
                }
            }
        }
        
        Animating.executeAnimations(animations)
    }
}
