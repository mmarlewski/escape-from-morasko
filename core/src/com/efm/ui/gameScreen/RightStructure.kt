package com.efm.ui.gameScreen

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Timer
import com.efm.*
import com.efm.Map
import com.efm.assets.*
import com.efm.level.World
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
        if (displayedStats != null) {
            displayedStats?.remove()
            displayedStats = null
        }
    
        val statsPlace = columnOf(statsPopup(itemName, itemStats)).align(Align.bottomRight)
        statsPlace.padBottom(128f).padRight(16f)
        statsPlace.setFillParent(true)
        GameScreen.stage.addActor(statsPlace)
    
        displayedStats = statsPlace
    
        Timer.schedule(object : Timer.Task()
                       {
                           override fun run()
                           {
                               if (displayedStats == statsPlace)
                               {
                                   statsPlace.remove()
                                   displayedStats = null
                               }
                           }
                       }, 2f)
    }
    
    fun displayStatsOnlyInEquipment(itemName : String, itemStats : String)
    {
        if (EquipmentStructure.heroOverlay.isVisible)
        {
            displayWeaponStats(itemName, itemStats)
        }
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
        moveButtonPlace.pad(14f)
        
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
        
        moveButtonPlace.pad(14f)
        
        moveButtonPlace.setFillParent(true)
        
        GameScreen.stage.addActor(moveButtonPlace)
    }
}