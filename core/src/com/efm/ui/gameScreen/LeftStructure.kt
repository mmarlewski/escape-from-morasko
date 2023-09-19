package com.efm.ui.gameScreen

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Textures
import com.efm.screens.GameScreen

object LeftStructure
{
    private const val BUTTON_PADDING_TOP = -8f
    
    private val buttonTextures = mapOf(
            "menuButton" to Textures.menuList,
            "weaponItemsButton" to Textures.itemWeapon,
            "healingItemsButton" to Textures.itemHealing,
            "skillsItemsButton" to Textures.itemSkill,
            "usableItemsButton" to Textures.itemUsable
                                      )
    
    fun createButton(buttonName : String, isVisible : Boolean) : ImageButton
    {
        val buttonTexture = buttonTextures[buttonName]
                ?: throw IllegalArgumentException("Invalid button name: $buttonName")
        
        val button = imageButtonOf(
                buttonTexture,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                  ) {
            if (buttonName == "menuButton")
            {
                if (PopUps.menuPause.isVisible)
                {
                    PopUps.setMenuVisibility(false)
                    PopUps.setBackgroundVisibility(true)
                }
                else
                {
                    PopUps.setMenuVisibility(true)
                    PopUps.setBackgroundVisibility(false)
                }
            }
            if (buttonName == "usableItemsButton")
            {
                ItemsStructure.setUsableDisplay()
            }
            if (buttonName == "skillsItemsButton")
            {
                ItemsStructure.setSkillDisplay()
            }
            if (buttonName == "healingItemsButton")
            {
                ItemsStructure.setPotionDisplay()
            }
            if (buttonName == "weaponItemsButton")
            {
                ItemsStructure.setWeaponDisplay()
            }
            playSoundOnce(Sounds.blop)
        }
        button.isVisible = isVisible
        return button
    }
    
    val menuButton = createButton("menuButton", isVisible = true)
    val usableItemsButton = createButton("usableItemsButton", isVisible = true)
    val skillsItemsButton = createButton("skillsItemsButton", isVisible = true)
    val healingItemsButton = createButton("healingItemsButton", isVisible = true)
    val weaponItemsButton = createButton("weaponItemsButton", isVisible = true)
    
    fun setVisibility(visibility : Boolean)
    {
        usableItemsButton.isVisible = visibility
        skillsItemsButton.isVisible = visibility
        healingItemsButton.isVisible = visibility
        weaponItemsButton.isVisible = visibility
    }
    
    fun display()
    {
        
        val columnTopLeft = columnOf(
                rowOf(
                        menuButton
                     ),
                rowOf(
                        usableItemsButton
                     ).padTop(120f),
                rowOf(
                        skillsItemsButton
                     ).padTop(BUTTON_PADDING_TOP),
                rowOf(
                        healingItemsButton
                     ).padTop(BUTTON_PADDING_TOP),
                rowOf(
                        weaponItemsButton
                     ).padTop(BUTTON_PADDING_TOP)
                                    ).align(Align.topLeft)
        
        setVisibility(false)
        
        columnTopLeft.pad(16f)
        columnTopLeft.setFillParent(true)
        GameScreen.stage.addActor(columnTopLeft)
    }
}
