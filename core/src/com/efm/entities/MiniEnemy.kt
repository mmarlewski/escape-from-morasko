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

class MiniEnemy : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 5
    override var healthPoints = 5
    override var alive = true
    override val detectionRange = 3
    override val attackRange = 1
    override val stepsInOneTurn = 3
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.miniEnemy
    }
    
    override fun getOutlineYellowTile() : TiledMapTile
    {
        return Tiles.miniEnemyOutlineYellow
    }
    
    override fun getOutlineRedTile(n:Int) : TiledMapTile
    {
        return Tiles.miniEnemyOutlineRed
    }
    
    override fun getIIdleTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.miniEnemy
            2    -> Tiles.miniEnemy
            3    -> Tiles.miniEnemy
            4    -> Tiles.miniEnemy
            else -> Tiles.miniEnemy
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.miniEnemy
            2    -> Tiles.miniEnemy
            3    -> Tiles.miniEnemy
            4    -> Tiles.miniEnemy
            else -> Tiles.miniEnemy
        }
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return Tiles.miniEnemy
    }
    
    override fun enemyAttack()
    {
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(this.position, heroPosition)
        val swordTile = if (heroDirection == null) null else Tiles.getSwordTile(heroDirection)
    
        val animations = mutableListOf<Animation>()
    
        animations += Animation.descendTile(swordTile, heroPosition.copy(), 0.2f, 0.10f)
        animations += Animation.action { playSoundOnce(Sounds.woodenSword) }
        animations += Animation.showTile(Tiles.impact, heroPosition.copy(), 0.1f)
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
