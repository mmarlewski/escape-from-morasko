package com.efm.ui.gameScreen

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.Map
import com.efm.assets.Sounds
import com.efm.assets.Textures
import com.efm.level.World
import com.efm.screens.GameScreen
import com.efm.state.*

object RightStructure
{
    lateinit var xButton : ImageButton
    
    fun setVisibility(boolean : Boolean)
    {
        xButtonVisibility(boolean)
        endTurnButton.isVisible = boolean
    }
    
    fun createXButton() : ImageButton
    {
        xButton = imageButtonOf(
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
                is State.free        -> State.free.noSelection.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                }
                
                is State.constrained -> State.constrained.noSelection.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
                
                is State.combat.hero -> State.combat.hero.noSelection.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
                
                else                 -> currState
            }
            setState(newState)
        }.also { xButton = it }
        
        return xButton
    }
    
    init
    {
        xButton = createXButton()
    }
    
    fun xButtonVisibility(visibility : Boolean)
    {
        xButton.isVisible = visibility
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
    
    fun display()
    {
        val nextTurnPlace = columnOf(
                rowOf(endTurnButton)
                                    ).align(Align.topRight)
        val xButtonPlace = columnOf(
                rowOf(xButton)
                                   ).align(Align.bottomRight)
        
        nextTurnPlace.pad(16f)
        xButtonPlace.pad(16f)
        
        nextTurnPlace.setFillParent(true)
        xButtonPlace.setFillParent(true)
        
        GameScreen.stage.addActor(nextTurnPlace)
        GameScreen.stage.addActor(xButtonPlace)
    }
}