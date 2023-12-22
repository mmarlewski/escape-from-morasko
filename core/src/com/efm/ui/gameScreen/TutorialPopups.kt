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
    var lastPopup : Window
    var cantLeavePopup : Window
    var bossWarningPopup : Window
    var closedExitPopup : Window
    
    fun welcomePopup() : Window
    {
        val welcomePopup = tutorialPopup(
                "Welcome to Escape From Morasko",
                "In this tutorial you will learn the basics of the game - movement and looting.",
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
                "Explore the game environment by dragging to move the camera. " +
                        "Pinch to zoom in and out."
                                       )
        {
            Sounds.ui_2.playOnce()
            cameraPopup.isVisible = false
            movementPopup.isVisible = true
        }
        cameraPopup.isVisible = false
        return cameraPopup
    }
    
    fun movementPopup() : Window
    {
        val movementPopup = tutorialPopup(
                "Movement",
                "To move, tap the button in the bottom right, " +
                        "and then select a tile to move to."
                                         )
        {
            Sounds.ui_2.playOnce()
            movementPopup.isVisible = false
            interfaceVisibilityWithTutorial()
        }
        movementPopup.isVisible = false
        
        return movementPopup
    }
    
    fun lootingPopup() : Window
    {
        val lootingPopup = tutorialPopup(
                "Looting",
                "To loot, click the chest nearby. " +
                        "Select items and transfer them to your inventory by clicking the arrow."
                                        )
        {
            Sounds.ui_2.playOnce()
            interfaceVisibilityWithTutorial()
            lootingPopup.isVisible = false
        }
        lootingPopup.isVisible = false
        
        return lootingPopup
    }
    
    fun equipmentPopup() : Window
    {
        val equipmentPopup = tutorialPopup(
                "Inventory",
                "Your inventory is divided into four tabs - " +
                        "map-usables, skills, self-usables, and weapons.\n" +
                        "Pick something from your inventory!"
                                          )
        {
            Sounds.ui_2.playOnce()
            interfaceVisibilityWithTutorial()
            ItemsStructure.setWeaponDisplay()
            equipmentPopup.isVisible = false
        }
        equipmentPopup.isVisible = false
        
        return equipmentPopup
        
    }
    
    fun healthAndAbilityPopup() : Window
    {
        val healthAndAbilityPopup = tutorialPopup(
                "Health and Action Points",
                "Using items and moving consumes AP. Your HP and AP are displayed at the top of the screen. " +
                        "Right now you have 0 Action Points!"
                                                 )
        {
            Sounds.ui_2.playOnce()
            healthAndAbilityPopup.isVisible = false
            ProgressBars.setVisibilty(true)
            RightStructure.display()
            turnsPopup.isVisible = true
        }
        healthAndAbilityPopup.isVisible = false
        
        return healthAndAbilityPopup
    }
    
    fun turnsPopup() : Window
    {
        val turnsPopup = tutorialPopup(
                "Turns",
                "In the top right, find the 'End Turn' button.\n" +
                        "End your turn to regain AP!"
                                      )
        {
            Sounds.ui_2.playOnce()
            turnsPopup.isVisible = false
            interfaceVisibilityWithTutorial()
        }
        turnsPopup.isVisible = false
        
        return turnsPopup
    }
    
    fun combatPopup() : Window
    {
        val combatPopup = tutorialPopup(
                "Combat",
                "Click the enemy to check if they can spot you. If they do, expect attacks!"
                                       )
        {
            Sounds.ui_2.playOnce()
            combatPopup.isVisible = false
            interfaceVisibilityWithTutorial()
            LeftStructure.menuButton.isVisible = true
            lastPopup.isVisible = true
        }
        combatPopup.isVisible = false
    
        return combatPopup
    }
    
    fun lastPopup() : Window
    {
        val lastPopup = tutorialPopup(
                "Good Luck!",
                "Defeat enemies and loot chests in any order you want. When you are ready, take on the boss to move to the next level!"
                                     )
        {
            Sounds.ui_2.playOnce()
            lastPopup.isVisible = false
        }
        lastPopup.isVisible = false
        
        return lastPopup
    }
    
    fun cantLeavePopup() : Window
    {
        val cantLeavePopup = tutorialPopup(
                "Sorry, can't let you leave just yet",
                "Try looting first!",
                                          )
        {
            Sounds.ui_2.playOnce()
            interfaceVisibilityWithTutorial()
            cantLeavePopup.isVisible = false
    
        }
        cantLeavePopup.isVisible = false
    
        return cantLeavePopup
    }
    
    fun bossWarningPopup() : Window
    {
        val bossWarningPopup = tutorialPopup(
                "Boss Ahead!",
                "These doors lead to a very powerful enemy. Once you go through, you will not be able to go back until you defeat them!",
                                            )
        {
            Sounds.ui_2.playOnce()
            interfaceVisibilityWithTutorial()
            bossWarningPopup.isVisible = false
            
        }
        bossWarningPopup.isVisible = false
        
        return bossWarningPopup
    }
    
    fun closedExitPopup() : Window
    {
        val closedExitPopup = tutorialPopup(
                "Locked Doors",
                "The enemies in this room closed the door! Defeat them to open it!",
                                           )
        {
            Sounds.ui_2.playOnce()
            interfaceVisibilityWithTutorial()
            closedExitPopup.isVisible = false
            
        }
        closedExitPopup.isVisible = false
        
        return closedExitPopup
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
        lastPopup = lastPopup()
        cantLeavePopup = cantLeavePopup()
        bossWarningPopup = bossWarningPopup()
        closedExitPopup = closedExitPopup()
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
        addPopupToDisplay(lastPopup)
        addPopupToDisplay(cantLeavePopup)
        addPopupToDisplay(bossWarningPopup)
        addPopupToDisplay(closedExitPopup)
    }
    
}