package com.efm.entities.enemies

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entities.Hero
import com.efm.entity.*
import com.efm.level.World
import com.efm.room.RoomPosition

class EnemyPlant : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 25
    override var healthPoints = 25
    override var alive = true
    override val detectionRange = 2
    override val attackRange = 1
    override var attackDamage = 20
    override val stepsInOneTurn = 0
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override var isFrozen = false
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.plantIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.plantIdle1OutlineYellow
            2    -> Tiles.plantIdle2OutlineYellow
            else -> Tiles.plantIdle1OutlineYellow
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.plantIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.plantIdle1
            2    -> Tiles.plantIdle2
            else -> Tiles.plantIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return Tiles.plantIdle1
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return Tiles.plantAttack
    }
    
    override fun getMoveSound() : Sound?
    {
        return Sounds.mushroomMove
    }
    
    override fun enemyAttack()
    {
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(this.position, heroPosition)
        val impactTile = if (heroDirection == null) null else Tiles.getImpactTile(heroDirection)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.simultaneous(
                listOf(
                        Animation.cameraShake(1, 0.5f),
                        Animation.action { playSoundOnce(Sounds.mushroomAttack) },
                        Animation.showTile(impactTile, heroPosition.copy(), 0.5f)
                      )
                                            )
        animations += Animation.action {
            
            val attackedPosition = World.hero.position
            val attackedSpace = World.currentRoom?.getSpace(attackedPosition)
            val attackedEntity = attackedSpace?.getEntity()
            when (attackedEntity)
            {
                is Hero ->
                {
                    attackedEntity.canMoveNextTurn = false
                }
            }
        }
        Animating.executeAnimations(animations)
    }
    
    override fun getCorpse() : EnemyCorpse = EnemyPlantCorpse(this.position)
}