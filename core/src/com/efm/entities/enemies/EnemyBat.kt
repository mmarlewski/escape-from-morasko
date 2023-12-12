package com.efm.entities.enemies

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.*
import com.efm.level.World
import com.efm.room.RoomPosition

class EnemyBat : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 5
    override var healthPoints = 5
    override var alive = true
    override val detectionRange = 3
    override val attackRange = 1
    override var attackDamage = 2
    override val stepsInOneTurn = 3
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override var isFrozen = false
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.batIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.batIdle1OutlineYellow
            2    -> Tiles.batIdle2OutlineYellow
            3    -> Tiles.batIdle3OutlineYellow
            4    -> Tiles.batIdle2OutlineYellow
            else -> Tiles.batIdle1OutlineYellow
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.batIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.batIdle1
            2    -> Tiles.batIdle2
            3    -> Tiles.batIdle3
            4    -> Tiles.batIdle2
            else -> Tiles.batIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.batMove1
            2    -> Tiles.batMove2
            3    -> Tiles.batMove3
            4    -> Tiles.batMove2
            else -> Tiles.batMove1
        }
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return Tiles.batAttack
    }
    
    override fun getMoveSound() : Sound?
    {
        return Sounds.batMove
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
                        Animation.action { playSoundOnce(Sounds.batAttack) },
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
                    attackedEntity.damageCharacter(attackDamage)
                }
            }
        }
        Animating.executeAnimations(animations)
    }
    
    override fun getCorpse() : EnemyCorpse = EnemyBatCorpse(this.position)
}
