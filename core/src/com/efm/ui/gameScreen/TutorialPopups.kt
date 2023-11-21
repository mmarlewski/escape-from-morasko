package com.efm.ui.gameScreen

import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.screens.GameScreen

object TutorialPopups
{
    var welcomePopup : Window
    fun welcomePopup() : Window
    {
        val welcomePopup = tutorialPopup(
                "Welcome to Escape From Morasko",
                "In this tutorial you will learn the basics of the game - movement and looting",
                                        )
        {
            welcomePopup.isVisible = false
        }
        
        return welcomePopup
        
    }
    
    init
    {
        welcomePopup = welcomePopup()
    }
    
    fun addPopupToDisplay(popup : Window)
    {
        val window = columnOf(rowOf(popup)).align(Align.center)
        window.setFillParent(true)
        GameScreen.stage.addActor(window)
    }
    
    fun display()
    {
        addPopupToDisplay(welcomePopup)
    }
    
}