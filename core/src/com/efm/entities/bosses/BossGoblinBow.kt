package com.efm.entities.bosses

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.*
import com.efm.item.PossibleItems
import com.efm.level.World
import com.efm.room.Base
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

class BossGoblinBow : BaseBoss()
{
    override val position = RoomPosition()
    override var maxHealthPoints = 20
    override var healthPoints = 20
    override var alive = true
    override val attackRange = 5
    override var attackDamage = 10
    override val stepsInOneTurn = 2
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override var isFrozen = false
    override var loot : PossibleItems = PossibleItems()
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.goblinBowIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.goblinBowIdle1OutlineYellow
            2    -> Tiles.goblinBowIdle2OutlineYellow
            3    -> Tiles.goblinBowIdle1OutlineYellow
            4    -> Tiles.goblinBowIdle2OutlineYellow
            else -> Tiles.goblinBowIdle1OutlineYellow
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.goblinBowIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.goblinBowIdle1
            2    -> Tiles.goblinBowIdle2
            3    -> Tiles.goblinBowIdle1
            4    -> Tiles.goblinBowIdle2
            else -> Tiles.goblinBowIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.goblinBowMove1
            2    -> Tiles.goblinBowMove1
            3    -> Tiles.goblinBowMove2
            4    -> Tiles.goblinBowMove2
            else -> Tiles.goblinBowMove1
        }
    }
    
    override fun getAttackTile() : TiledMapTile
    {
        return Tiles.goblinBowAttack
    }
    
    override fun getMoveSound() : Sound
    {
        return Sounds.goblinMove
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
//        if (World.currentRoom?.name != "finalRoom")
//        {
//            showSkillAssignPopUpAfterBossKill(this)
//            addBossToDefeatedBossesList(Boss.NatureGolem)
//        }
//        increaseHeroStats(5, 3)
    }
}
