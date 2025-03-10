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

class BossOctopusTentacle : BaseBoss()
{
    override val position = RoomPosition()
    override var maxHealthPoints = 25
    override var healthPoints = 25
    override var alive = true
    override val attackRange = 2
    override var attackDamage = 26
    override val stepsInOneTurn = 0
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override var isFrozen = false
    override var loot : PossibleItems = PossibleItems()
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.octopusTentacleIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.octopusTentacleIdle1OutlineYellow
            2    -> Tiles.octopusTentacleIdle2OutlineYellow
            3    -> Tiles.octopusTentacleIdle3OutlineYellow
            4    -> Tiles.octopusTentacleIdle2OutlineYellow
            else -> Tiles.octopusTentacleIdle1OutlineYellow
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.octopusTentacleIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.octopusTentacleIdle1
            2    -> Tiles.octopusTentacleIdle2
            3    -> Tiles.octopusTentacleIdle3
            4    -> Tiles.octopusTentacleIdle2
            else -> Tiles.octopusTentacleIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return null
    }
    
    override fun getAttackTile() : TiledMapTile
    {
        return Tiles.octopusTentacleAttack
    }
    
    override fun getMoveSound() : Sound?
    {
        return null
    }
    
    override fun enemyAttack()
    {
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(this.position, heroPosition)
        val impactTile = if (heroDirection == null) null else Tiles.getImpactTile(heroDirection)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.action { playSoundOnce(Sounds.octopusTentacleAttack) }
        animations += Animation.showTile(impactTile, heroPosition.copy(), 0.5f)
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
}
