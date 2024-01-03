package com.efm

import com.badlogic.gdx.Gdx
import com.efm.entity.Enemy
import com.efm.level.World
import com.efm.screens.GameScreen
import com.efm.screens.SettingsScreen
import com.efm.state.*
import com.efm.ui.gameScreen.*

/**
 * ends Hero turn and then acts accordingly to game and world state
 */
fun endCurrentTurn()
{
    saveGame()
    
    val isHeroVisible = World.hero.isVisible
    val currState = getState()
    // tutorial popups
    if (currState.tutorialFlags.tutorialActive && currState.tutorialFlags.turnsPopupShown)
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
                newState = when(currState)
                {
                    State.constrained.heroSelected -> State.free.heroSelected
                    State.constrained.nothingSelected -> State.free.nothingSelected
                    State.constrained.noSelection -> State.free.noSelection
                    else -> State.free.noSelection
                }.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                }
            }
            // do not roam in tutorial before showing combatPopup
            if (!currState.tutorialFlags.tutorialActive || currState.tutorialFlags.combatPopupShown)
            {
                // enemies roaming
                val enemyRoamingAnimations = mutableListOf<Animation>()
                val enemyIterator = World.currentRoom?.getEnemies()?.iterator() ?: listOf<Enemy>().iterator()
                while (enemyIterator.hasNext())
                {
                    val enemy = enemyIterator.next()
                    //Gdx.app.log("Roaming position before", "$enemy.position")
                    if (enemyIterator.hasNext())
                    {
                        Gdx.app.log("Roaming", enemy::class.simpleName)
                        enemyRoamingAnimations += enemy.getRoamingAnimations()
                    }
                    else
                    {
                        Gdx.app.log("Roaming last", enemy::class.simpleName)
                        enemyRoamingAnimations += enemy.getRoamingAnimations(focusCameraOnHero = true)
                    }
                    // executeAnimations() is async so enemy changes its position in getRoamingAnimations()
                    //Gdx.app.log("Roaming position after", "$enemy.position")
                    // detection
                    if (World.hero.position in enemy.getDetectionPositions())
                    {
                        newState = when (currState)
                        {
                            State.constrained.heroSelected    -> State.combat.hero.heroSelected
                            State.constrained.nothingSelected -> State.combat.hero.nothingSelected
                            State.constrained.noSelection     -> State.combat.hero.noSelection
                            else                              -> State.combat.hero.noSelection
                        }.apply {
                            this.isHeroAlive = currState.isHeroAlive
                            this.areEnemiesInRoom = currState.areEnemiesInRoom
                            this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                            this.tutorialFlags = currState.tutorialFlags
                        }
                    }
                }
                Animating.executeAnimations(enemyRoamingAnimations)
            }
            // tutorial popups
            if (newState.tutorialFlags.tutorialActive && newState.tutorialFlags.playerEndedTurn && !newState.tutorialFlags.combatPopupShown)
            {
                TutorialPopups.combatPopup.isVisible = true
                PopUps.setBackgroundVisibility(false)
                LeftStructure.menuButton.isVisible = false
                newState.tutorialFlags.combatPopupShown = true
                // skip tutorial next time when starting new game
                SettingsScreen.skipTutorialCheckbox.isChecked = true
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
                newState = when(currState)
                {
                    State.combat.hero.heroSelected -> State.free.heroSelected
                    State.combat.hero.nothingSelected -> State.free.nothingSelected
                    State.combat.hero.noSelection -> State.free.noSelection
                    else -> State.free.noSelection
                }.apply {
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
                    this.enemies = World.currentRoom?.getEnemies() ?: listOf()
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
