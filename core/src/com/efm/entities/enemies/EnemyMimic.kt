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

class EnemyMimic : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 7
    override var healthPoints = 7
    override var alive = true
    override val detectionRange = 1
    override val attackRange = 1
    override val stepsInOneTurn = 2
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.mimicIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.mimicIdle1OutlineYellow
            2    -> Tiles.mimicIdle2OutlineYellow
            3    -> Tiles.mimicIdle1OutlineYellow
            4    -> Tiles.mimicIdle2OutlineYellow
            else -> Tiles.mimicIdle1OutlineYellow
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.mimicIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.mimicIdle1
            2    -> Tiles.mimicIdle2
            3    -> Tiles.mimicIdle1
            4    -> Tiles.mimicIdle2
            else -> Tiles.mimicIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.mimicMove1
            2    -> Tiles.mimicMove2
            3    -> Tiles.mimicMove1
            4    -> Tiles.mimicMove2
            else -> Tiles.mimicMove1
        }
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return Tiles.mimicAttack
    }
    
    override fun getMoveSound() : Sound?
    {
        return Sounds.mimicMove
    }
    
    override fun enemyAttack()
    {
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(this.position, heroPosition)
        val swordTile = if (heroDirection == null) null else Tiles.getSwordTile(heroDirection)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.descendTile(swordTile, heroPosition.copy(), 0.2f, 0.25f)
        animations += Animation.action { playSoundOnce(Sounds.mimicAttack) }
        animations += Animation.simultaneous(
                listOf(
                        Animation.showTile(Tiles.impact, heroPosition.copy(), 0.2f),
                        Animation.cameraShake(1, 0.5f)
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
                    attackedEntity.damageCharacter(2)
                }
            }
        }
        
        Animating.executeAnimations(animations)
    }
}
