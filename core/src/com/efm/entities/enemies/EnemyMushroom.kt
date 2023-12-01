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

class EnemyMushroom : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 10
    override var healthPoints = 10
    override var alive = true
    override val detectionRange = 1
    override val attackRange = 1
    override var attackDamage = 2
    override val stepsInOneTurn = 2
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override var isFrozen = false
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.mushroomIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.mushroomIdle1OutlineYellow
            2    -> Tiles.mushroomIdle2OutlineYellow
            3    -> Tiles.mushroomIdle1OutlineYellow
            4    -> Tiles.mushroomIdle2OutlineYellow
            else -> Tiles.mushroomIdle1OutlineYellow
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.mushroomIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.mushroomIdle1
            2    -> Tiles.mushroomIdle2
            3    -> Tiles.mushroomIdle1
            4    -> Tiles.mushroomIdle2
            else -> Tiles.mushroomIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.mushroomMove1
            2    -> Tiles.mushroomMove2
            3    -> Tiles.mushroomMove1
            4    -> Tiles.mushroomMove2
            else -> Tiles.mushroomMove1
        }
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return Tiles.mushroomAttack
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
                        Animation.cameraShake(3, 1.0f),
                        Animation.action { playSoundOnce(Sounds.mushroomAttack) },
                        Animation.showTile(impactTile, heroPosition.copy(), 1.0f)
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
    
    override fun getCorpse() : EnemyCorpse = EnemyMushroomCorpse(this.position)
}
