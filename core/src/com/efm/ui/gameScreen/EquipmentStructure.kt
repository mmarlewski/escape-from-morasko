package com.efm.ui.gameScreen

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Textures
import com.efm.item.Container
import com.efm.level.World
import com.efm.screens.GameScreen

object EquipmentStructure
{
    lateinit var equipment : HorizontalGroup
    lateinit var deleteButton : ImageButton
    lateinit var arrowButton : ImageButton
    lateinit var returnButton : ImageButton
    lateinit var heroOverlay : Window
    lateinit var containerOverlay : Window
    
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
            
            if (GameScreen.selectedItem != null)
            {
                GameScreen.currEquipment?.items?.remove(GameScreen.selectedItem!!)
                GameScreen.fillEquipmentWithItems(GameScreen.currEquipment!!)
                
                GameScreen.selectedItem = null
                GameScreen.selectedButton = null
            }
    
            deleteButton.isVisible = false
            arrowButton.isVisible = false
        }
        
        deleteButton.isVisible = false
        
        return deleteButton
    }
    
    fun createArrowButton() : ImageButton
    {
        val arrowButton = imageButtonOf(
                Textures.arrowRight,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                        )
        {
            Sounds.blop.playOnce()
            
            val selectedItem = GameScreen.selectedItem
            val currEquipment = GameScreen.currEquipment
            val otherEquipment = when(currEquipment)
            {
                GameScreen.heroEquipment -> GameScreen.containerEquipment
                else -> GameScreen.heroEquipment
            }
            
            if(selectedItem != null && currEquipment != null && otherEquipment.items.size < otherEquipment.maxItems)
            {
                currEquipment.items.remove(selectedItem)
                otherEquipment.items.add(selectedItem)
    
                GameScreen.fillEquipmentWithItems(currEquipment)
                GameScreen.fillEquipmentWithItems(otherEquipment)
            }
    
            deleteButton.isVisible = false
            arrowButton.isVisible = false
        }
    
        arrowButton.isVisible = false
        
        return arrowButton
    }
    
    init
    {
        returnButton = createReturnButton()
        deleteButton = createDeleteButton()
        arrowButton = createArrowButton()
        
        heroOverlay = equipmentOverlay("HERO'S EQUIPMENT")
        heroOverlay.isVisible = false
        containerOverlay = equipmentOverlay("CONTAINER'S EQUIPMENT")
        containerOverlay.isVisible = false
    }
    
    fun setVisibility(boolean : Boolean)
    {
        returnButton.isVisible = boolean
        deleteButton.isVisible = boolean
        arrowButton.isVisible = boolean
        heroOverlay.isVisible = boolean
        containerOverlay.isVisible = boolean
    }
    
    fun display()
    {
        val buttons = columnOf(returnButton, deleteButton, arrowButton)
        
        equipment = rowOf(heroOverlay, buttons, containerOverlay)
        
        equipment.setFillParent(true)
        
        GameScreen.stage.addActor(equipment)
    }
    
    fun showHeroEquipment()
    {
        Sounds.blop.playOnce()
        if(containerOverlay in equipment.children)
        {
            equipment.removeActor(containerOverlay)
        }
        setVisibility(true)
        deleteButton.isVisible = false
        arrowButton.isVisible = false
        PopUps.setBackgroundVisibility(false)
        ProgressBars.setVisibilty(false)
        LeftStructure.menuButton.isVisible = false
        GameScreen.fillEquipmentWithItems(GameScreen.heroEquipment)
        GameScreen.isHeroEquipmentOnly = true
    }
    
    fun showHeroAndContainerEquipments(containerEquipment : Container)
    {
        Sounds.blop.playOnce()
        if(containerOverlay !in equipment.children)
        {
            equipment.addActor(containerOverlay)
        }
        setVisibility(true)
        deleteButton.isVisible = false
        arrowButton.isVisible = false
        PopUps.setBackgroundVisibility(false)
        ProgressBars.setVisibilty(false)
        LeftStructure.menuButton.isVisible = false
        GameScreen.fillEquipmentWithItems(GameScreen.heroEquipment)
        GameScreen.setNewContainerEquipment(containerEquipment)
        GameScreen.fillEquipmentWithItems(GameScreen.containerEquipment)
        GameScreen.isHeroEquipmentOnly = false
    }
}