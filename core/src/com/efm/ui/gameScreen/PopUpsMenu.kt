package com.efm.ui.gameScreen

import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.assets.*
import com.efm.screens.MenuScreen
import com.efm.ui.menuScreen.TitleAndButtons

object PopUpsMenu
{
    lateinit var overwriteSave : com.badlogic.gdx.scenes.scene2d.ui.Window
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
    
    init
    {
        overwriteSave = overwriteSave()
    }
    
    fun setOverwriteSaveVisibility(visibility : Boolean)
    {
        overwriteSave.isVisible = visibility
    }
    
    fun display()
    {
        val overwriteSaveWindow = columnOf(rowOf(overwriteSave)).align(Align.center)
        
        overwriteSaveWindow.setFillParent(true)
        
        MenuScreen.stage.addActor(overwriteSaveWindow)
        
    }
}