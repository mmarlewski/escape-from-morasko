package com.efm.ui.menuScreen

import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.Map
import com.efm.level.World
import com.efm.screens.GameScreen
import com.efm.screens.MenuScreen
import com.efm.state.State
import com.efm.state.setState
import com.efm.ui.gameScreen.ItemsStructure
import createProcGenWorld

object PopUpsMenu
{
    var overwriteSave : com.badlogic.gdx.scenes.scene2d.ui.Window
    fun overwriteSave() : com.badlogic.gdx.scenes.scene2d.ui.Window
    {
        val overwriteSavePopup = yesNoPopup(
                "Overwrite save?",
                "Your progress will be lost! Do you want to continue?",
                {
                    startNewGame()
                },
                {
                    TitleAndButtons.setButtonsVisibility(true)
                }
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
    
    fun startNewGame()
    {
        // remove enemy health stacks
        for (level in World.levels)
        {
            for (room in level.rooms)
            {
                for (enemy in room.getEnemies())
                {
                    enemy.healthStack.remove()
                }
            }
        }
        // create World
        World.levels.clear()
        //World.createWorldPrototype()
        World.createProcGenWorld()
        // set currentLevel and currentRoom
        val startingLevel = World.levels.first()
        val startingRoom = startingLevel.startingRoom
        World.changeCurrentLevel(startingLevel)
        if (startingRoom != null) World.changeCurrentRoom(startingRoom)
        // add Hero to currentRoom
        World.currentRoom?.addEntityAt(World.hero, startingLevel.startingPosition)
        // reset Hero
        World.hero.reset()
        // add Items to Hero
        World.hero.setStartingInventory()
        // set godmode
         World.hero.godMode()
        // add Skills to Hero
//                    World.hero.addSkill(LavaWalking)
//                    World.hero.addSkill(Push)
//                    World.hero.addSkill(Invisibility)
//                    World.hero.addSkill(Freeze)
//                    World.hero.addSkill(GrassHealing)
//                    World.hero.addSkill(Jump)
//                    World.hero.addSkill(Shield)
        // set State
        val areEnemiesInRoom = World.currentRoom?.areEnemiesInRoom() ?: false
        val initState = when (areEnemiesInRoom)
        {
            true -> State.constrained.noSelection
            false -> State.free.noSelection
        }
        initState.areEnemiesInRoom = areEnemiesInRoom
        initState.tutorialFlags.setDefault()
        setState(initState)
        // set new enemy health stacks
        for (level in World.levels)
        {
            for (room in level.rooms)
            {
                for (enemy in room.getEnemies())
                {
                    enemy.createOwnHealthBar()
                    enemy.hideOwnHealthBar()
                }
            }
        }
        // display new enemy health stacks
        for (enemy in World.currentRoom?.getEnemies() ?: listOf())
        {
            enemy.displayOwnHealthBar()
        }
        // update Room, Map, UI
        World.currentRoom?.updateSpacesEntities()
        Map.clearAllLayers()
        GameScreen.updateMapBaseLayer()
        GameScreen.updateMapEntityLayer()
        ItemsStructure.fillItemsStructureWithItemsAndSkills()
        // camera
        GameScreen.changeCameraZoom(GameScreen.currZoom)
        GameScreen.focusCameraOnRoomPosition(World.hero.position)
        
        changeScreen(GameScreen)
    }
    
    fun display()
    {
        val overwriteSaveWindow = columnOf(rowOf(overwriteSave)).align(Align.center)
        
        overwriteSaveWindow.setFillParent(true)
        
        MenuScreen.stage.addActor(overwriteSaveWindow)
        
    }
}
