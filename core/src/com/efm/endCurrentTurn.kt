package com.efm

import com.efm.level.World
import com.efm.screens.GameScreen
import com.efm.state.*
import com.efm.ui.gameScreen.*

fun endCurrentTurn()
{
    saveGame()
    
    val isHeroVisible = World.hero.isVisible
    val currState = getState()
    // tutorial popups
    if (currState.tutorialFlags.tutorialOn && currState.tutorialFlags.turnsPopupShown)
        currState.tutorialFlags.playerEndedTurn = true
    var newState = currState
    
    when (currState)
    {
        is State.constrained ->
        {
            World.hero.regainAllAP()
            World.hero.updateActiveSkillCoolDown()
            World.hero.incrementTurnsElapsed()
            ItemsStructure.fillItemsStructureWithItemsAndSkills()
            
            if (!isHeroVisible)
            {
                newState = State.free.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                }
            }
            // enemies roaming
            for (enemy in World.currentRoom.getEnemies())
            {
                enemy.roam()
            }
            // tutorial popups
            if (newState.tutorialFlags.tutorialOn && newState.tutorialFlags.playerEndedTurn && !newState.tutorialFlags.combatPopupShown)
            {
                TutorialPopups.combatPopup.isVisible = true
                PopUps.setBackgroundVisibility(false)
                LeftStructure.menuButton.isVisible = false
                newState.tutorialFlags.combatPopupShown = true
            }
        }
        
        is State.combat.hero ->
        {
            World.hero.updateActiveSkillCoolDown()
            World.hero.incrementTurnsElapsed()
            ItemsStructure.fillItemsStructureWithItemsAndSkills()
            
            Map.clearLayer(MapLayer.select)
            Map.clearLayer(MapLayer.outline)
            
            if (!isHeroVisible)
            {
                newState = State.free.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                }
            }
            else
            {
                val animation = Animation.showTileWithCameraFocus(null, World.hero.position.copy(), 1f)
                Animating.executeAnimations(mutableListOf(animation))
    
                newState = State.combat.enemies.enemyUnselected.apply {
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
            }
        }
        
        else                 ->
        {
            if (!isHeroVisible)
            {
                newState = State.free.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                }
            }
        }
    }
    
    setState(newState)
    
    World.hero.isVisible = true
    World.hero.setCanMoveToTrue()
    GameScreen.updateMapEntityLayer()
}
