package com.efm.ui.gameScreen

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Timer
import com.efm.*
import com.efm.Map
import com.efm.assets.*
import com.efm.item.MultiUseMapItem
import com.efm.level.World
import com.efm.multiUseMapItems.*
import com.efm.screens.GameScreen
import com.efm.state.*

object RightStructure
{
    lateinit var moveButton : ImageButton
    
    fun setVisibility(boolean : Boolean)
    {
        moveButtonVisibility(boolean)
        endTurnButton.isVisible = boolean
    }
    
    fun createMoveButton() : ImageButton
    {
        moveButton = imageButtonOf(
                Textures.move,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                  )
        {
            playSoundOnce(Sounds.ui_3)
            
            Map.clearLayer(MapLayer.select)
            Map.clearLayer(MapLayer.outline)
            
            val newState = when (val currState = getState())
            {
                is State.free        ->
                {
                    when (currState)
                    {
                        is State.free.heroSelected -> State.free.nothingSelected.apply {
                            this.isHeroAlive = currState.isHeroAlive
                            this.areEnemiesInRoom = currState.areEnemiesInRoom
                        }
            
                        else                       -> State.free.heroSelected.apply {
                            Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
                            Map.changeTile(MapLayer.outline, World.hero.position, World.hero.getOutlineGreenTile())
                            this.isHeroAlive = currState.isHeroAlive
                            this.areEnemiesInRoom = currState.areEnemiesInRoom
                        }
                    }
                }
    
                is State.constrained ->
                {
                    when (currState)
                    {
                        is State.constrained.heroSelected -> State.constrained.nothingSelected.apply {
                            this.isHeroAlive = currState.isHeroAlive
                            this.areEnemiesInRoom = currState.areEnemiesInRoom
                            this.isHeroDetected = currState.isHeroDetected
                            this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                        }
            
                        else                              -> State.constrained.heroSelected.apply {
                            Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
                            Map.changeTile(MapLayer.outline, World.hero.position, World.hero.getOutlineGreenTile())
                            this.isHeroAlive = currState.isHeroAlive
                            this.areEnemiesInRoom = currState.areEnemiesInRoom
                            this.isHeroDetected = currState.isHeroDetected
                            this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                        }
                    }
                }
    
                is State.combat.hero ->
                {
                    when (currState)
                    {
                        is State.combat.hero.heroSelected -> State.combat.hero.nothingSelected.apply {
                            this.isHeroAlive = currState.isHeroAlive
                            this.areEnemiesInRoom = currState.areEnemiesInRoom
                            this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                        }
            
                        else                              -> State.combat.hero.heroSelected.apply {
                            Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
                            Map.changeTile(MapLayer.outline, World.hero.position, World.hero.getOutlineGreenTile())
                            this.isHeroAlive = currState.isHeroAlive
                            this.areEnemiesInRoom = currState.areEnemiesInRoom
                            this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                        }
                    }
                }
    
                else                 -> currState
            }
            setState(newState)
        }.also { moveButton = it }
        
        return moveButton
    }
    
    init
    {
        moveButton = createMoveButton()
    }
    
    fun moveButtonVisibility(visibility : Boolean)
    {
        moveButton.isVisible = visibility
    }
    
    val endTurnButton = imageButtonOf(
            Textures.nextTurn,
            Textures.upNinePatch,
            Textures.downNinePatch,
            Textures.overNinePatch,
            Textures.disabledNinePatch,
            Textures.focusedNinePatch
                                     ) {
        
        if (World.hero.abilityPoints > 0 && getState() !is State.free)
        {
            if (PopUps.endTurn.isVisible)
            {
                PopUps.setEndTurnVisibility(false)
                PopUps.setBackgroundVisibility(true)
                LeftStructure.menuButton.isVisible = true
            }
            else
            {
                PopUps.setEndTurnVisibility(true)
                PopUps.setBackgroundVisibility(false)
                LeftStructure.menuButton.isVisible = false
            }
        }
        else
        {
            endCurrentTurn()
        }
        playSoundOnce(Sounds.ui_5)
    
    }
    
    fun statsPopup(itemName : String, itemStats : String) : Window
    {
        val popup = statisticsPopup(itemName,
                                    itemStats)
        popup.isVisible = true
        
        return popup
    }
    
    var displayedStats: Actor? = null
    
    fun displayWeaponStats(itemName: String, itemStats: String) {
        // Check if stats are already displayed
        if (displayedStats != null) {
            // Remove the existing stats
            displayedStats?.remove()
            displayedStats = null
        }
        
        // Display the new stats
        val statsPlace = columnOf(statsPopup(itemName, itemStats)).align(Align.bottomRight)
        statsPlace.padBottom(128f).padRight(16f)
        statsPlace.setFillParent(true)
        GameScreen.stage.addActor(statsPlace)
        
        // Set the reference to the displayed stats actor
        displayedStats = statsPlace
        
        // Schedule a task to remove the actor after 2 seconds
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                // Check if the stats are still the same as when the timer was scheduled
                if (displayedStats == statsPlace) {
                    // Remove the actor from the stage
                    statsPlace.remove()
                    // Reset the reference after removal
                    displayedStats = null
                }
            }
        }, 2f)  // 2f represents the delay in seconds
    }

    
    fun display()
    {
        val nextTurnPlace = columnOf(
                rowOf(endTurnButton)
                                    ).align(Align.topRight)
        val moveButtonPlace = columnOf(
                rowOf(moveButton)
                                      ).align(Align.bottomRight)
        
        nextTurnPlace.pad(16f)
        moveButtonPlace.pad(16f)
        
        nextTurnPlace.setFillParent(true)
        moveButtonPlace.setFillParent(true)
        
        GameScreen.stage.addActor(nextTurnPlace)
        GameScreen.stage.addActor(moveButtonPlace)
    }
    
    fun displayMoveButton()
    {
        val moveButtonPlace = columnOf(
                rowOf(moveButton)
                                      ).align(Align.bottomRight)
        
        moveButtonPlace.pad(16f)
        
        moveButtonPlace.setFillParent(true)
        
        GameScreen.stage.addActor(moveButtonPlace)
    }
}