package com.efm.entities.bosses

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entities.ExplodingBarrel
import com.efm.entity.*
import com.efm.item.PossibleItems
import com.efm.level.World
import com.efm.room.Base
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

class BossGoblinBarrel : BaseBoss()
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
    var isBarrel = true
    
    override fun getTile() : TiledMapTile
    {
        return if (isBarrel) Tiles.goblinBarrelIdle1 else Tiles.goblinEmptyIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return if (isBarrel)
        {
            when (n)
            {
                1    -> Tiles.goblinBarrelIdle1OutlineYellow
                2    -> Tiles.goblinBarrelIdle2OutlineYellow
                3    -> Tiles.goblinBarrelIdle1OutlineYellow
                4    -> Tiles.goblinBarrelIdle2OutlineYellow
                else -> Tiles.goblinBarrelIdle1OutlineYellow
            }
        }
        else
        {
            when (n)
            {
                1    -> Tiles.goblinEmptyIdle1OutlineYellow
                2    -> Tiles.goblinEmptyIdle2OutlineYellow
                3    -> Tiles.goblinEmptyIdle1OutlineYellow
                4    -> Tiles.goblinEmptyIdle2OutlineYellow
                else -> Tiles.goblinEmptyIdle1OutlineYellow
            }
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return if (isBarrel) Tiles.goblinBarrelIdle1OutlineRed else Tiles.goblinEmptyIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile
    {
        return if (isBarrel)
        {
            when (n)
            {
                1    -> Tiles.goblinBarrelIdle1
                2    -> Tiles.goblinBarrelIdle2
                3    -> Tiles.goblinBarrelIdle1
                4    -> Tiles.goblinBarrelIdle2
                else -> Tiles.goblinBarrelIdle1
            }
        }
        else
        {
            when (n)
            {
                1    -> Tiles.goblinEmptyIdle1
                2    -> Tiles.goblinEmptyIdle2
                3    -> Tiles.goblinEmptyIdle1
                4    -> Tiles.goblinEmptyIdle2
                else -> Tiles.goblinEmptyIdle1
            }
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile
    {
        return if (isBarrel)
        {
            when (n)
            {
                1    -> Tiles.goblinBarrelMove1
                2    -> Tiles.goblinBarrelMove1
                3    -> Tiles.goblinBarrelMove2
                4    -> Tiles.goblinBarrelMove2
                else -> Tiles.goblinBarrelMove1
            }
        }
        else
        {
            when (n)
            {
                1    -> Tiles.goblinEmptyMove1
                2    -> Tiles.goblinEmptyMove1
                3    -> Tiles.goblinEmptyMove2
                4    -> Tiles.goblinEmptyMove2
                else -> Tiles.goblinEmptyMove1
            }
        }
    }
    
    override fun getAttackTile() : TiledMapTile
    {
        return if (isBarrel) Tiles.goblinBarrelAttack else Tiles.goblinEmptyAttack
    }
    
    override fun getMoveSound() : Sound
    {
        return Sounds.goblinMove
    }
    
    override fun enemyAttack()
    {
        if (isBarrel)
        {
            val squareAreaPositions = getSquareAreaPositions(World.hero.position.copy(), 2)
            val availablePositions = mutableListOf<RoomPosition>()
            for (position in squareAreaPositions)
            {
                val space = World.currentRoom?.getSpace(position)
                val entity = space?.getEntity()
                
                if (space != null && entity == null)
                {
                    availablePositions.add(position)
                }
            }
            val randomAvailablePosition = availablePositions.randomOrNull()
            
            if (randomAvailablePosition != null)
            {
                val animations = mutableListOf<Animation>()
                
                animations.add(
                        Animation.moveTileWithArch(
                                Tiles.explodingBarrel,
                                this.position.copy(),
                                randomAvailablePosition.copy(),
                                1.0f,
                                1.0f
                                                  )
                              )
                animations.add(
                        Animation.simultaneous(
                                listOf(
                                        Animation.action { playSoundOnce(Sounds.barrelDrop) },
                                        Animation.showTile(Tiles.explodingBarrel, randomAvailablePosition.copy(), 0.5f),
                                        Animation.showTile(Tiles.impact, randomAvailablePosition.copy(), 0.5f)
                                      )
                                              )
                              )
                animations.add(Animation.action
                {
                    World.currentRoom?.addEntityAt(ExplodingBarrel(), randomAvailablePosition.copy())
                })
                
                isBarrel = false
                
                Animating.executeAnimations(animations)
            }
        }
        else
        {
            isBarrel = true
        }
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
