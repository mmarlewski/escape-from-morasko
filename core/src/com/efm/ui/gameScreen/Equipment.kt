package com.efm.ui.gameScreen

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Textures
import com.efm.screens.EquipmentScreen
import com.efm.screens.GameScreen

object Equipment
{
    lateinit var deleteButton : ImageButton
    lateinit var returnButton : ImageButton
    
    fun createOverlay() : Window
    {
        val overlay = equipmentOverlay(Textures.pauseBackgroundNinePatch)
        
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
            changeScreen(GameScreen)
        }
        
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
        return deleteButton
    }
    
    init
    {
        returnButton = createReturnButton()
        deleteButton = createDeleteButton()
    }
    
    fun display()
    {
        val buttons = rowOf(columnOf(createOverlay()), columnOf(returnButton, deleteButton)).align(Align.bottomRight)
        
        buttons.pad(16f)
        
        buttons.setFillParent(true)
//        overlay.setFillParent(true)
        
        EquipmentScreen.stage.addActor(buttons)
//        EquipmentScreen.stage.addActor(overlay)
    }
}