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

class EnemyTurret : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 10
    override var healthPoints = 10
    override var alive = true
    override val detectionRange = 2
    override val attackRange = 3
    override var attackDamage = 5
    override val stepsInOneTurn = 0
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override var isFrozen = false
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.turretIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.turretIdle1OutlineYellow
            2    -> Tiles.turretIdle2OutlineYellow
            3    -> Tiles.turretIdle1OutlineYellow
            4    -> Tiles.turretIdle2OutlineYellow
            else -> Tiles.turretIdle1OutlineYellow
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile?
    {
        return Tiles.turretIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.turretIdle1
            2    -> Tiles.turretIdle2
            3    -> Tiles.turretIdle1
            4    -> Tiles.turretIdle2
            else -> Tiles.turretIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return null
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return Tiles.turretAttack
    }
    
    override fun getMoveSound() : Sound?
    {
        return null
    }
    
    override fun enemyAttack()
    {
        val heroPosition = World.hero.position.copy()
        val projectileTile = Tiles.turretProjectile
        
        val animations = mutableListOf<Animation>()
        
        val shot = listOf(
                Animation.action { playSoundOnce(Sounds.turretAttack) },
                Animation.cameraShake(1, 0.5f),
                Animation.moveTileWithArch(projectileTile, position, heroPosition, 1.0f, 1.0f)
                         )
        animations += Animation.simultaneous(shot)
        val impact = listOf(
                Animation.action { playSoundOnce(Sounds.explosive) },
                Animation.cameraShake(1, 0.5f),
                Animation.showTile(Tiles.impact, heroPosition, 0.5f)
                           )
        animations += Animation.simultaneous(impact)
        animations += Animation.action {
            val attackedPosition = heroPosition
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
}
