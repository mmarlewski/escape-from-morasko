package com.efm.entities.enemies.mimic

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.Map
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.*
import com.efm.item.PossibleItem
import com.efm.item.PossibleItems
import com.efm.level.World
import com.efm.multiUseMapItems.*
import com.efm.room.RoomPosition
import com.efm.stackableMapItems.Bomb
import com.efm.stackableMapItems.Explosive
import com.efm.stackableSelfItems.*
import com.efm.state.State
import com.efm.state.getState

class EnemyMimic : Enemy, Interactive
{
    override fun getOutlineTealTile() = Tiles.chestOutlineTeal
    
    override fun interact()
    {
        Map.clearLayer(MapLayer.select)
        Map.clearLayer(MapLayer.outline)
        this.displayOwnHealthBar()
        playSoundOnce(Sounds.mimicAppear)
    }
    
    override val position = RoomPosition()
    override var maxHealthPoints = 20
    override var healthPoints = 20
    override var alive = true
    override val detectionRange = 1
    override val attackRange = 1
    override var attackDamage = 26
    override val stepsInOneTurn = 2
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override var isFrozen = false
    override val roamingChance : Float = 0.0f
    override var loot : PossibleItems = PossibleItems(
            mutableListOf(
                    PossibleItem(APPotionSmall(), 0.33f, IntRange(0, 4)),
                    PossibleItem(APPotionBig(), 0.67f, IntRange(0, 2)),
                    PossibleItem(HPPotionSmall(), 0.33f, IntRange(0, 4)),
                    PossibleItem(HPPotionBig(), 0.67f, IntRange(0, 2)),
                    PossibleItem(Sledgehammer(), 0.2f, 1..1),
                    PossibleItem(Hatchet(), 0.5f, 1..1),
                    PossibleItem(IronSword(), 0.7f, 1..1),
                    PossibleItem(Bomb(), 0.5f, IntRange(1, 3)),
                    PossibleItem(Explosive(), 0.7f, IntRange(1, 5)),
                    PossibleItem(DoubleBitAxe(), 0.4f, IntRange(1, 1)),
                    PossibleItem(SunBow(), 0.3f, IntRange(1, 1))
                         ),
            7
                                                     )
    
    fun detected() = when (val currState = getState())
    {
        is State.free        -> false
        is State.constrained -> currState.isHeroDetected
        is State.combat      -> true
        else                 -> false
    }
    
    override fun getTile() : TiledMapTile
    {
        return if (detected()) Tiles.mimicIdle1
        else Tiles.chest
    }
    
    private fun getOutlineYellowTileAfterDetection(n : Int) : TiledMapTile
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
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return if (detected()) getOutlineYellowTileAfterDetection(n)
        else Tiles.chestOutlineYellow
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.mimicIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile
    {
        return if (detected()) getIdleTileAfterDetection(n)
        else Tiles.chest
    }
    
    private fun getIdleTileAfterDetection(n : Int) : TiledMapTile
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
    
    override fun getMoveTile(n : Int) : TiledMapTile
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
    
    override fun getAttackTile() : TiledMapTile
    {
        return Tiles.mimicAttack
    }
    
    override fun getMoveSound() : Sound
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
                        Animation.showTile(Tiles.impact, heroPosition.copy(), 0.2f), Animation.cameraShake(1, 0.5f)
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
    
    override fun getDetectionPositions() : MutableList<RoomPosition>
    {
        return mutableListOf()
    }
    
    override fun getCorpse() : EnemyCorpse = EnemyMimicCorpse(this.position, loot)
}
