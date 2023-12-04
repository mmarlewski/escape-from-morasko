package com.efm.ui.gameScreen

import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.assets.Sounds
import com.efm.screens.GameScreen

object TutorialPopups
{
    var welcomePopup : Window
    var cameraPopup : Window
    var movementPopup : Window
    var lootingPopup : Window
    var equipmentPopup : Window
    var healthAndAbilityPopup : Window
    var turnsPopup : Window
    var combatPopup : Window
    var cantLeavePopup : Window
    
    fun welcomePopup() : Window
    {
        val welcomePopup = tutorialPopup(
                "Welcome to Escape From Morasko",
                "In this tutorial you will learn the basics of the game - movement and looting",
                                        )
        {
            Sounds.ui_2.playOnce()
            welcomePopup.isVisible = false
            cameraPopup.isVisible = true
        }
        welcomePopup.isVisible = false
        return welcomePopup
        
    }
    
    fun cameraPopup() : Window
    {
        val cameraPopup = tutorialPopup(
                "Camera",
                "Explore the game environment by dragging to move the camera. Additionally, " +
                        "use a pinching motion to zoom in and out for a closer or wider view."
                                       )
        {
            Sounds.ui_2.playOnce()
            cameraPopup.isVisible = false
            LeftStructure.menuButton.isVisible = true
            GameScreen.canBeInteractedWith = true
            movementPopup.isVisible = true
        }
        cameraPopup.isVisible = false
        return cameraPopup
    }
    
    fun movementPopup() : Window
    {
        val movementPopup = tutorialPopup(
                "Movement",
                "Navigate through the room by tapping the movement button located in the bottom right, " +
                        "and then select a tile to move to."
                                         )
        {
            Sounds.ui_2.playOnce()
            movementPopup.isVisible = false
            RightStructure.moveButton.isVisible = true
        }
        movementPopup.isVisible = false
        
        return movementPopup
    }
    
    fun lootingPopup() : Window
    {
        val lootingPopup = tutorialPopup(
                "Looting",
                "To loot, click the chest or enemy corpse nearby. " +
                        "Select items and transfer them to your equipment by clicking the arrow."
                                        )
        {
            Sounds.ui_2.playOnce()
            lootingPopup.isVisible = false
        }
        lootingPopup.isVisible = false
        
        return lootingPopup
    }
    
    fun equipmentPopup() : Window
    {
        val equipmentPopup = tutorialPopup(
                "Equipment",
                "Check your current equipment is visible in pause menu and at the bottom left with tabs for " +
                        "map-usables, skills, self-usables, and weapons."
                                          )
        {
            Sounds.ui_2.playOnce()
            equipmentPopup.isVisible = false
        }
        equipmentPopup.isVisible = false
        
        return equipmentPopup
        
    }
    
    fun healthAndAbilityPopup() : Window
    {
        val healthAndAbilityPopup = tutorialPopup(
                "Health and Action Points",
                "Every action consumes action points. Enemy attacks reduce your health, but specific items can restore it."
                                                 )
        {
            Sounds.ui_2.playOnce()
            healthAndAbilityPopup.isVisible = false
            ProgressBars.setVisibilty(true)
            turnsPopup.isVisible = true
        }
        healthAndAbilityPopup.isVisible = false
        
        return healthAndAbilityPopup
    }
    
    fun turnsPopup() : Window
    {
        val turnsPopup = tutorialPopup(
                "Turns",
                "In the top right, find the 'End Turn' button. Decide when to end your turn, even with remaining action points."
                                      )
        {
            Sounds.ui_2.playOnce()
            turnsPopup.isVisible = false
            RightStructure.endTurnButton.isVisible = true
        }
        turnsPopup.isVisible = false
        
        return turnsPopup
    }
    
    fun combatPopup() : Window
    {
        val combatPopup = tutorialPopup(
                "Combat",
                "Check an enemy's range by clicking on them. Once detected, expect attacks. " +
                        "Counter with a selected weapon by choosing the enemy or tile."
                                       )
        {
            Sounds.ui_2.playOnce()
            combatPopup.isVisible = false
        }
        combatPopup.isVisible = false
        
        return combatPopup
    }
    
    fun cantLeavePopup() : Window
    {
        val cantLeavePopup = tutorialPopup(
                "Sorry, can't let you leave just yet",
                "Try looting first!",
                                          )
        {
            Sounds.ui_2.playOnce()
            LeftStructure.menuButton.isVisible = true
            RightStructure.moveButton.isVisible = true
            cantLeavePopup.isVisible = false
            
        }
        cantLeavePopup.isVisible = false
        
        return cantLeavePopup
    }
    
    init
    {
        welcomePopup = welcomePopup()
        cameraPopup = cameraPopup()
        movementPopup = movementPopup()
        lootingPopup = lootingPopup()
        equipmentPopup = equipmentPopup()
        healthAndAbilityPopup = healthAndAbilityPopup()
        turnsPopup = turnsPopup()
        combatPopup = combatPopup()
        cantLeavePopup = cantLeavePopup()
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
        addPopupToDisplay(cameraPopup)
        addPopupToDisplay(movementPopup)
        addPopupToDisplay(lootingPopup)
        addPopupToDisplay(equipmentPopup)
        addPopupToDisplay(healthAndAbilityPopup)
        addPopupToDisplay(turnsPopup)
        addPopupToDisplay(combatPopup)
        addPopupToDisplay(cantLeavePopup)
    }
    
}