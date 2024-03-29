package com.efm.entities.bosses.slime

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entities.bosses.Boss
import com.efm.entities.bosses.addBossToDefeatedBossesList
import com.efm.entity.*
import com.efm.item.PossibleItems
import com.efm.level.World
import com.efm.room.RoomPosition

class BossSlimeQuarter : BaseBoss()
{
    override val position = RoomPosition()
    override var maxHealthPoints = 10
    override var healthPoints = 10
    override var alive = true
    override val attackRange = 1
    override var attackDamage = 10
    override val stepsInOneTurn = 2
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override var isFrozen = false
    override var loot : PossibleItems = PossibleItems()
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.slimeGreenIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.slimeGreenIdle1OutlineYellow
            2    -> Tiles.slimeGreenIdle2OutlineYellow
            3    -> Tiles.slimeGreenIdle1OutlineYellow
            4    -> Tiles.slimeGreenIdle2OutlineYellow
            else -> Tiles.slimeGreenIdle1OutlineYellow
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.slimeGreenIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.slimeGreenIdle1
            2    -> Tiles.slimeGreenIdle2
            3    -> Tiles.slimeGreenIdle1
            4    -> Tiles.slimeGreenIdle2
            else -> Tiles.slimeGreenIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.slimeGreenIdle1
            2    -> Tiles.slimeGreenIdle2
            3    -> Tiles.slimeGreenIdle1
            4    -> Tiles.slimeGreenIdle2
            else -> Tiles.slimeGreenIdle1
        }
    }
    
    override fun getAttackTile() : TiledMapTile
    {
        return Tiles.slimeGreenAttack
    }
    
    override fun getMoveSound() : Sound
    {
        return Sounds.slimeMove
    }
    
    override fun enemyAttack()
    {
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(this.position, heroPosition)
        val impactTile = if (heroDirection == null) null else Tiles.getImpactTile(heroDirection)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.action { playSoundOnce(Sounds.slimeAttack) }
        animations += Animation.simultaneous(
                listOf(
                        Animation.showTile(impactTile, heroPosition.copy(), 0.2f),
                        Animation.cameraShake(1, 0.5f)
                      )
                                            )
        animations += Animation.action {
            
            val attackedPosition = World.hero.position
            val attackedSpace = World.currentRoom?.getSpace(attackedPosition)
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
    
    fun finalBossSlimeQuartersKilled()
    {
        if (World.currentRoom?.name != "finalRoom")
        {
            showSkillAssignPopUpAfterBossKill(this)
            addBossToDefeatedBossesList(Boss.Slime)
        }
    }
    
    override fun onDeath()
    {
        increaseHeroStats(2, 1)
    }
    
}
