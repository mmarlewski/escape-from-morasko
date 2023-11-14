package com.efm.ui.gameScreen

import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.assets.*
import com.efm.screens.GameScreen
import com.efm.screens.MenuScreen
import com.efm.ui.menuScreen.TitleAndButtons

object PopUps
{
    
    lateinit var endTurn : com.badlogic.gdx.scenes.scene2d.ui.Window
    lateinit var menuPause : com.badlogic.gdx.scenes.scene2d.ui.Window
    lateinit var settings : com.badlogic.gdx.scenes.scene2d.ui.Window
    lateinit var overwriteSave : com.badlogic.gdx.scenes.scene2d.ui.Window
    
    fun setBackgroundVisibility(boolean : Boolean)
    {
        GameScreen.canBeInteractedWith = boolean
        
        RightStructure.setVisibility(boolean)
        
        ItemsStructure.fillItemsStructureWithItemsAndSkills()
        ItemsStructure.setVisibility(boolean)
        if (boolean) ItemsStructure.setWeaponDisplay()
        
        LeftStructure.setVisibility(false)
    }
    
    fun overwriteSave() : com.badlogic.gdx.scenes.scene2d.ui.Window
    {
        val overwriteSavePopup = windowAreaOf(
                "You're about to overwrite your\nexisting save. Continue?",
                Fonts.pixeloid20,
                Colors.black,
                Textures.pauseBackgroundNinePatch,
                { TitleAndButtons.setButtonsVisibility(true) },
                { TitleAndButtons.setButtonsVisibility(true) }
                                             )
        
        overwriteSavePopup.isVisible = false
        
        return overwriteSavePopup
    }
    
    fun endTurn() : com.badlogic.gdx.scenes.scene2d.ui.Window
    {
        val endTurnPopUp = windowAreaOf(
                "End turn?\n\nYou still have some AP left",
                Fonts.pixeloid20,
                Colors.black,
                Textures.pauseBackgroundNinePatch,
                { endCurrentTurn() },
                {}
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
    
    init
    {
        endTurn = endTurn()
        menuPause = menuPause()
        settings = settings()
        overwriteSave = overwriteSave()
    }
    
    fun setOverwriteSaveVisibility(visibility : Boolean)
    {
        overwriteSave.isVisible = visibility
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
    
    fun display()
    {
        val endTurnWindow = columnOf(rowOf(endTurn)).align(Align.center)
        val pauseWindow = columnOf(rowOf(menuPause)).align(Align.center)
        val settingsWindow = columnOf(rowOf(settings)).align(Align.center)
        val overwriteSaveWindow = columnOf(rowOf(overwriteSave)).align(Align.center)
        
        
        settingsWindow.setFillParent(true)
        endTurnWindow.setFillParent(true)
        pauseWindow.setFillParent(true)
        overwriteSaveWindow.setFillParent(true)
        
        GameScreen.stage.addActor(endTurnWindow)
        GameScreen.stage.addActor(settingsWindow)
        GameScreen.stage.addActor(pauseWindow)
        MenuScreen.stage.addActor(overwriteSaveWindow)
        
    }
}