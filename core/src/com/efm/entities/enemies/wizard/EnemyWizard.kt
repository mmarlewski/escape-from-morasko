package com.efm.entities.enemies.wizard

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entities.Hero
import com.efm.entity.Enemy
import com.efm.entity.EnemyCorpse
import com.efm.item.PossibleItem
import com.efm.item.PossibleItems
import com.efm.level.World
import com.efm.multiUseMapItems.Staff
import com.efm.room.RoomPosition
import com.efm.stackableSelfItems.*

class EnemyWizard : Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 15
    override var healthPoints = 15
    override var alive = true
    override val detectionRange = 2
    override val attackRange = 3
    override var attackDamage = 15
    override val stepsInOneTurn = 5
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override var isFrozen = false
    override val roamingChance : Float = 0.67f
    override var loot : PossibleItems = PossibleItems(
            mutableListOf(
                    PossibleItem(APPotionSmall(), 0.15f, IntRange(0, 4)),
                    PossibleItem(APPotionBig(), 0.30f, IntRange(0, 2)),
                    PossibleItem(HPPotionSmall(), 0.15f, IntRange(0, 4)),
                    PossibleItem(HPPotionBig(), 0.30f, IntRange(0, 2)),
                    PossibleItem(Staff(), 0.2f, 1..1),
                         )
                                                     )
    
    val apDrain = 5
    
    override fun getCorpse() : EnemyCorpse = EnemyWizardCorpse(this.position, loot)
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.wizardIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.wizardIdle1OutlineYellow
            2    -> Tiles.wizardIdle2OutlineYellow
            3    -> Tiles.wizardIdle3OutlineYellow
            4    -> Tiles.wizardIdle4OutlineYellow
            else -> Tiles.wizardIdle1OutlineYellow
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.wizardIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.wizardIdle1
            2    -> Tiles.wizardIdle2
            3    -> Tiles.wizardIdle3
            4    -> Tiles.wizardIdle4
            else -> Tiles.wizardIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.wizardMove1
            2    -> Tiles.wizardMove2
            3    -> Tiles.wizardMove3
            4    -> Tiles.wizardMove4
            else -> Tiles.wizardMove1
        }
    }
    
    override fun getAttackTile() : TiledMapTile
    {
        return Tiles.wizardAttack
    }
    
    override fun getMoveSound() : Sound
    {
        return Sounds.wizardMove
    }
    
    override fun enemyAttack()
    {
        val heroPosition = World.hero.position.copy()
        val projectileTile =  Tiles.wizardProjectile
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.action { playSoundOnce(Sounds.wizardAttack) }
        animations += Animation.moveTileWithArch(projectileTile, position, World.hero.position, 1.0f, 1.0f)
        animations += Animation.descendTile(Tiles.apMinus, heroPosition.copy(), 0.5f, 0.1f)
        animations += Animation.action {
            val attackedPosition = heroPosition
            val attackedSpace = World.currentRoom?.getSpace(attackedPosition)
            val attackedEntity = attackedSpace?.getEntity()
            when (attackedEntity)
            {
                is Hero ->
                {
                    attackedEntity.apDrainInNextTurn = apDrain
                }
            }
        }
        
        Animating.executeAnimations(animations)
    }
}
