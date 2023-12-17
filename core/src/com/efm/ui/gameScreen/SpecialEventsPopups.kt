package com.efm.ui.gameScreen

import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.entities.Modifier
import com.efm.screens.GameScreen

object SpecialEventsPopups
{
    fun createPopup(subtitle : String, description : String, onYes : () -> Unit, onNo : () -> Unit) : Window
    {
        val modifierPopup = specialEventPopup(
                subtitle,
                description,
                onYes,
                onNo
                                             )
        return modifierPopup
    }
    
    fun addPopupToDisplay(popup : Window)
    {
        val window = columnOf(rowOf(popup)).align(Align.center)
        window.setFillParent(true)
        GameScreen.stage.addActor(window)
        popup.isVisible = false
    }
    
    fun display()
    {
        //
    }
}