package com.efm.ui.gameScreen

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Textures
import com.efm.screens.GameScreen

object EquipmentStructure
{
    lateinit var deleteButton : ImageButton
    lateinit var returnButton : ImageButton
    lateinit var overlay : Window
    
    fun createOverlay() : Window
    {
        val overlay = equipmentOverlay(Textures.pauseBackgroundNinePatch)
        overlay.isVisible = false
        
        return overlay
    }
    
    fun createReturnButton() : ImageButton
    {
    
        val returnButton = imageButtonOf(
                Textures.close,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                        )
        {
            Sounds.blop.playOnce()
            ProgressBars.setVisibilty(true)
            PopUps.setBackgroundVisibility(true)
            LeftStructure.menuButton.isVisible = true
            setVisibility(false)
        }
        returnButton.isVisible = false
        return returnButton
    }
    
    fun createDeleteButton() : ImageButton
    {
        val deleteButton = imageButtonOf(
                Textures.delete,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                        )
        {
            Sounds.blop.playOnce()
        }
        deleteButton.isVisible = false
    
        return deleteButton
    }
    
    init
    {
        returnButton = createReturnButton()
        deleteButton = createDeleteButton()
        overlay = createOverlay()
    }
    
    fun setVisibility(boolean : Boolean)
    {
        returnButton.isVisible = boolean
        deleteButton.isVisible = boolean
        overlay.isVisible = boolean
    }
    
    fun display()
    {
        val buttons = columnOf(returnButton, deleteButton).align(Align.bottomRight)
    
        val equipment = columnOf(overlay).align(Align.center).fill(1.9f)
    
    
        buttons.padBottom(overlay.height - 16f).padRight(24f)
    
        buttons.setFillParent(true)
        equipment.setFillParent(true)
    
        GameScreen.stage.addActor(buttons)
        GameScreen.stage.addActor(equipment)
    }
}