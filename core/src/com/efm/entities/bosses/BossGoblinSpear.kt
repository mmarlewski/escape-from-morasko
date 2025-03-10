package com.efm.entities.bosses

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.BaseBoss
import com.efm.entity.Character
import com.efm.item.PossibleItems
import com.efm.level.World
import com.efm.room.RoomPosition

class BossGoblinSpear : BaseBoss()
{
    override val position = RoomPosition()
    override var maxHealthPoints = 20
    override var healthPoints = 20
    override var alive = true
    override val attackRange = 1
    override var attackDamage = 13
    override val stepsInOneTurn = 2
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override var isFrozen = false
    override var loot : PossibleItems = PossibleItems()
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.goblinSpearIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.goblinSpearIdle1OutlineYellow
            2    -> Tiles.goblinSpearIdle2OutlineYellow
            3    -> Tiles.goblinSpearIdle1OutlineYellow
            4    -> Tiles.goblinSpearIdle2OutlineYellow
            else -> Tiles.goblinSpearIdle1OutlineYellow
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.goblinSpearIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.goblinSpearIdle1
            2    -> Tiles.goblinSpearIdle2
            3    -> Tiles.goblinSpearIdle1
            4    -> Tiles.goblinSpearIdle2
            else -> Tiles.goblinSpearIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.goblinSpearMove1
            2    -> Tiles.goblinSpearMove1
            3    -> Tiles.goblinSpearMove2
            4    -> Tiles.goblinSpearMove2
            else -> Tiles.goblinSpearMove1
        }
    }
    
    override fun getAttackTile() : TiledMapTile
    {
        return Tiles.goblinSpearAttack
    }
    
    override fun getMoveSound() : Sound
    {
        return Sounds.goblinMove
    }
    
    override fun enemyAttack()
    {
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(this.position, heroPosition)
        val impactTile = if (heroDirection == null) null else Tiles.getImpactTile(heroDirection)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.simultaneous(
                listOf(
                        Animation.cameraShake(3, 0.5f),
                        Animation.action { playSoundOnce(Sounds.spear) },
                        Animation.showTile(impactTile, heroPosition.copy(), 0.5f)
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
    
    override fun onDeath()
    {
        if (World.currentRoom?.name != "finalRoom")
        {
            showSkillAssignPopUpAfterBossKill(this)
            addBossToDefeatedBossesList(Boss.NatureGolem)
        }
        increaseHeroStats(5, 3)
    }
}
