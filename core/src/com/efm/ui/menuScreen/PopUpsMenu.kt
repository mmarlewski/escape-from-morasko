package com.efm.ui.menuScreen

import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.Map
import com.efm.level.World
import com.efm.screens.GameScreen
import com.efm.screens.MenuScreen
import com.efm.skill.BodyPart
import com.efm.stackableSelfItems.Apple
import com.efm.state.State
import com.efm.state.setState
import com.efm.ui.gameScreen.ItemsStructure

object PopUpsMenu
{
    var overwriteSave : com.badlogic.gdx.scenes.scene2d.ui.Window
    fun overwriteSave() : com.badlogic.gdx.scenes.scene2d.ui.Window
    {
        val overwriteSavePopup = yesNoPopup(
                "Overwrite save?",
                "You're about to overwrite your existing save. Your progress will be lost. Do you want to continue?",
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
        TitleAndButtons.setButtonsVisibility(true)
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
        //World.createWorldPrototypeTwo()
        //World.createWorldBoarTest()
        World.createWorldPrototypeThree()
        // set currentLevel and currentRoom
        val startingLevel = World.levels.first()
        val startingRoom = startingLevel.startingRoom
        World.changeCurrentLevel(startingLevel)
        if (startingRoom != null) World.changeCurrentRoom(startingRoom)
        // add Hero to currentRoom
        World.currentRoom?.addEntityAt(World.hero, startingLevel.startingPosition)
        // reset Hero
        World.hero.alive = true
        World.hero.healthPoints = World.hero.maxHealthPoints
        World.hero.healCharacter(0)
        World.hero.abilityPoints = World.hero.maxAbilityPoints
        World.hero.gainAP(0)
        World.hero.apDrainInNextTurn = 0
        World.hero.canMoveNextTurn = true
        World.hero.isVisible = true
        World.hero.turnsElapsed = 0
        World.hero.weaponDamageMultiplier = 1
        World.hero.inventory.items.clear()
        BodyPart.values().forEach { World.hero.bodyPartMap[it] = null }
        // add Items to Hero
        World.hero.inventory.addItem(Apple(2))
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