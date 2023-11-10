package com.efm

import com.efm.level.World
import com.efm.screens.GameScreen
import com.efm.state.*
import com.efm.ui.gameScreen.ItemsStructure

fun endCurrentTurn()
{
    val isHeroVisible = World.hero.isVisible
    val currState = getState()
    
    when (currState)
    {
        is State.constrained ->
        {
            World.hero.regainAllAP()
            World.hero.updateActiveSkillCoolDown()
            ItemsStructure.fillItemsStructureWithItemsAndSkills()
            
            if (!isHeroVisible)
            {
                setState(State.free.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                })
            }
        }
        
        is State.combat.hero ->
        {
            World.hero.updateActiveSkillCoolDown()
            ItemsStructure.fillItemsStructureWithItemsAndSkills()
            
            Map.clearLayer(MapLayer.select)
            Map.clearLayer(MapLayer.outline)
            
            if (!isHeroVisible)
            {
                setState(State.free.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                })
            }
            else
            {
                val animation = Animation.showTileWithCameraFocus(null, World.hero.position.copy(), 1f)
                Animating.executeAnimations(mutableListOf(animation))
                
                val newState = State.combat.enemies.enemyUnselected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.enemies = World.currentRoom.getEnemies()
                    this.enemyIterator = this.enemies?.iterator()
                    this.currEnemy = when (val enemyIterator = this.enemyIterator)
                    {
                        null -> null
                        else -> when (enemyIterator.hasNext())
                        {
                            true  -> enemyIterator.next()
                            false -> null
                        }
                    }
                }
                setState(newState)
            }
        }
        
        else                 ->
        {
            if (!isHeroVisible)
            {
                setState(State.free.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                })
            }
        }
    }
    
    World.hero.isVisible = true
    World.hero.setCanMoveToTrue()
    World.currentRoom.clearFrozenEnemiesList()
    GameScreen.updateMapEntityLayer()
}
