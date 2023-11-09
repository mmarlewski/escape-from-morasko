package com.efm.state

import com.efm.*
import com.efm.Map
import com.efm.assets.Tiles
import com.efm.level.World
import com.efm.screens.GameScreen
import com.efm.skill.ActiveSkill
import com.efm.ui.gameScreen.ItemsStructure
import com.efm.ui.gameScreen.RightStructure

fun updateCombatEnemiesEnemyUnselected(currState : State.combat.enemies.enemyUnselected) : State
{
    if (!Animating.isAnimating())
    {
        val currEnemy = currState.currEnemy
        if (currEnemy != null)
        {
            val currEnemyPosition = currEnemy.position
            Map.changeTile(MapLayer.select, currEnemyPosition, Tiles.selectRed)
            Map.changeTile(MapLayer.entity, currEnemy.position, currEnemy.getIdleTile(1))
            Map.changeTile(MapLayer.outline, currEnemyPosition, currEnemy.getOutlineRedTile())
            val animations = mutableListOf<Animation>()
            animations.add(Animation.moveCameraSmoothlyWithRoomPositions(null, currEnemyPosition.copy(), 0.1f))
            animations.add(Animation.focusCamera(currEnemyPosition.copy(), 0.01f))
            Animating.executeAnimations(animations)
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
        if (currEnemy != null && !World.currentRoom.getFrozenEnemies().contains(currEnemy))
        {
            Map.clearLayer(MapLayer.select)
            Map.clearLayer(MapLayer.outline)
            Map.changeTile(MapLayer.entity, currEnemy.position, currEnemy.getAttackTile())
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
        World.currentRoom.removeKilledCharacters()
        World.currentRoom.addToBeAddedEntitiesToRoom()
        World.currentRoom.updateSpacesEntities()
        GameScreen.updateMapEntityLayer()
        
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
                World.hero.regainAllAP()
                GameScreen.focusCameraOnRoomPosition(World.hero.position)
                Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
                Map.changeTile(MapLayer.outline, World.hero.position, Tiles.heroIdle1OutlineGreen)
                
                RightStructure.xButtonVisibility(true)
                
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
                    val animation = Animation.focusCamera(currEnemyPosition.copy(), 1f)
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
