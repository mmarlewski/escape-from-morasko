package com.efm.state

import com.efm.*
import com.efm.Map
import com.efm.assets.Tiles
import com.efm.level.World
import com.efm.room.toVector2
import com.efm.screens.GameScreen

fun updateCombatEnemiesEnemyUnselected(currState : State.combat.enemies.enemyUnselected) : State
{
    if (!Animating.isAnimating())
    {
        val currEnemy = currState.currEnemy
        if (currEnemy != null)
        {
            val currEnemyPosition = currEnemy.position
            Map.changeTile(MapLayer.select, currEnemyPosition, Tiles.selectRed)
            Map.changeTile(MapLayer.outline, currEnemyPosition, currEnemy.getOutlineRedTile())
            val animation = Animation.showTileWithCameraFocus(null, currEnemyPosition.copy(), 1f)
            Animating.executeAnimations(mutableListOf(animation))
        }
        
        return State.combat.enemies.enemySelected.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
            this.enemies = currState.enemies
            this.enemyIterator = currState.enemyIterator
            this.currEnemy = currState.currEnemy
        }
    }
    
    return currState
}

fun updateCombatEnemiesEnemySelected(currState : State.combat.enemies.enemySelected) : State
{
    if (!Animating.isAnimating())
    {
        val currEnemy = currState.currEnemy
        if (currEnemy != null)
        {
            Map.clearLayer(MapLayer.select)
            Map.clearLayer(MapLayer.outline)
            currEnemy.performTurn()
        }
        
        return State.combat.enemies.enemyAction.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
            this.enemies = currState.enemies
            this.enemyIterator = currState.enemyIterator
            this.currEnemy = currState.currEnemy
        }
    }
    
    return currState
}

fun updateCombatEnemiesEnemyAction(currState : State.combat.enemies.enemyAction) : State
{
    if (!Animating.isAnimating())
    {
        val nextEnemy = when (val enemyIterator = currState.enemyIterator)
        {
            null -> null
            else -> when (enemyIterator.hasNext())
            {
                true  -> enemyIterator.next()
                false -> null
            }
        }
        
        when (nextEnemy)
        {
            null ->
            {
                World.hero.regainAP()
                GameScreen.focusCameraOnRoomPosition(World.hero.position)
                
                return State.combat.hero.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = true
                }
            }
            else ->
            {
                val currEnemy = currState.currEnemy
                if (currEnemy != null)
                {
                    val currEnemyPosition = currEnemy.position
                    val animation = Animation.showTileWithCameraFocus(null, currEnemyPosition.copy(), 1f)
                    Animating.executeAnimations(mutableListOf(animation))
                }
                
                return State.combat.enemies.enemyUnselected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.enemies = currState.enemies
                    this.enemyIterator = currState.enemyIterator
                    this.currEnemy = nextEnemy
                }
            }
        }
    }
    
    return currState
}
