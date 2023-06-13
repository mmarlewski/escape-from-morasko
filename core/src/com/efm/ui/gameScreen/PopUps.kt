package com.efm.ui.gameScreen

import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.assets.*
import com.efm.screens.GameScreen

object PopUps
{
    
    lateinit var endTurn : com.badlogic.gdx.scenes.scene2d.ui.Window
    lateinit var menuPause : com.badlogic.gdx.scenes.scene2d.ui.Window
    lateinit var settings : com.badlogic.gdx.scenes.scene2d.ui.Window
    lateinit var nextLevel : com.badlogic.gdx.scenes.scene2d.ui.Window
    
    fun endTurn() : com.badlogic.gdx.scenes.scene2d.ui.Window
    {
        val endTurnPopUp = windowAreaOf(
                "End turn?\n\nYou still have some AP left",
                Fonts.pixeloid20,
                Colors.black,
                Textures.pauseBackgroundNinePatch,
                                       )
        endTurnPopUp.isVisible = false
        
        return endTurnPopUp
        
    }
    
    fun settings() : com.badlogic.gdx.scenes.scene2d.ui.Window
    {
        val settingsPopUp = settingsPause(
                "SETTINGS",
                Fonts.pixeloid30, Colors.black, Textures.pauseBackgroundNinePatch
                                         )
        settingsPopUp.isVisible = false
        
        return settingsPopUp
    }
    
    fun menuPause() : com.badlogic.gdx.scenes.scene2d.ui.Window
    {
    
        val menuPausePopUp = menuPopup(
                "PAUSE",
                Fonts.pixeloid30,
                Colors.black,
                Textures.pauseBackgroundNinePatch
                                      )
        menuPausePopUp.isVisible = false
    
    
        return menuPausePopUp
    }
    
    fun nextLevel() : Window
    {
        val nextLevelPopUp = windowAreaOf(
                "You are about to leave this level\n\nAre you sure?",
                Fonts.pixeloid20,
                Colors.black,
                Textures.pauseBackgroundNinePatch
                                         )
        nextLevelPopUp.isVisible = false
        return nextLevelPopUp
    }
    
    init
    {
        endTurn = endTurn()
        menuPause = menuPause()
        settings = settings()
        nextLevel = nextLevel()
    }
    
    fun setEndTurnVisibility(visibility : Boolean)
    {
        endTurn.isVisible = visibility
    }
    
    fun setSettingsVisibility(visibility : Boolean)
    {
        settings.isVisible = visibility
    }
    
    fun setMenuVisibility(visibility : Boolean)
    {
        menuPause.isVisible = visibility
    }
    
    fun setNextLevelVisibility(visibility : Boolean)
    {
        nextLevel.isVisible = visibility
    }
    
    fun display()
    {
        val endTurnWindow = columnOf(rowOf(endTurn)).align(Align.center)
        val pauseWindow = columnOf(rowOf(menuPause)).align(Align.center)
        val settingsWindow = columnOf(rowOf(settings)).align(Align.center)
        val nextLevelWindow = columnOf(rowOf(nextLevel)).align(Align.center)
        
        settingsWindow.setFillParent(true)
        endTurnWindow.setFillParent(true)
        pauseWindow.setFillParent(true)
        nextLevelWindow.setFillParent(true)
        
        GameScreen.stage.addActor(endTurnWindow)
        GameScreen.stage.addActor(settingsWindow)
        GameScreen.stage.addActor(pauseWindow)
        GameScreen.stage.addActor(nextLevelWindow)
        
    }
}