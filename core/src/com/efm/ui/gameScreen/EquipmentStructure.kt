package com.efm.ui.gameScreen

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Textures
import com.efm.level.World
import com.efm.screens.GameScreen

object EquipmentStructure
{
    lateinit var equipment : HorizontalGroup
    lateinit var deleteButton : ImageButton
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
            
            if (GameScreen.selectedHeroItem != null)
            {
                World.hero.inventory.removeItem(GameScreen.selectedHeroItem!!)
                GameScreen.fillEquipmentWithItems(true, World.hero.inventory.items)
                
                GameScreen.selectedHeroItem = null
                GameScreen.selectedHeroButton = null
            }
            
            deleteButton.isVisible = (GameScreen.selectedHeroButton != null)
        }
        
        deleteButton.isVisible = false
        
        return deleteButton
    }
    
    init
    {
        returnButton = createReturnButton()
        deleteButton = createDeleteButton()
        
        heroOverlay = equipmentOverlay("HERO'S EQUIPMENT")
        heroOverlay.isVisible = false
        containerOverlay = equipmentOverlay("CONTAINER'S EQUIPMENT")
        containerOverlay.isVisible = false
    }
    
    fun setVisibility(boolean : Boolean)
    {
        returnButton.isVisible = boolean
        deleteButton.isVisible = boolean
        heroOverlay.isVisible = boolean
        containerOverlay.isVisible = boolean
    }
    
    fun display()
    {
        val buttons = columnOf(returnButton, deleteButton)
        
        equipment = rowOf(heroOverlay, buttons, containerOverlay)
        
        equipment.setFillParent(true)
        
        GameScreen.stage.addActor(equipment)
    }
}