package com.efm.ui.gameScreen

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.*
import com.badlogic.gdx.utils.Scaling
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Textures
import com.efm.item.*
import com.efm.item.Container
import com.efm.level.World
import com.efm.screens.GameScreen

object EquipmentStructure
{
    lateinit var equipment : HorizontalGroup
    lateinit var deleteButton : ImageButton
    lateinit var arrowButton : ImageButton
    lateinit var returnButton : ImageButton
    lateinit var heroOverlay : Table
    lateinit var containerOverlay : Table
    
    var isHeroEquipmentOnly = true
    var currEquipment : Container? = null
    var heroEquipment : Container = World.hero.inventory
    lateinit var containerEquipment : Container
    var selectedItem : Item? = null
    var selectedButton : ImageButton? = null
    
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
            Sounds.ui_3.playOnce()
            interfaceVisibilityWithTutorial()
            setVisibility(false)
            LeftStructure.menuButton.isVisible = true
            
            saveGame()
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
            Sounds.ui_2.playOnce()
            
            this.selectedButton?.style?.up = NinePatchDrawable(Textures.upNinePatch)
            
            if (this.selectedItem != null)
            {
                this.currEquipment?.items?.remove(this.selectedItem)
                this.fillEquipmentStructureWithItems(this.currEquipment!!)
                
                this.selectedItem = null
                this.selectedButton = null
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
            Sounds.ui_2.playOnce()
            
            this.selectedButton?.style?.up = NinePatchDrawable(Textures.upNinePatch)
            
            val selectedItem = this.selectedItem
            val currEquipment = this.currEquipment
            val otherEquipment = when (currEquipment)
            {
                this.heroEquipment -> this.containerEquipment
                else               -> this.heroEquipment
            }
            
            if (selectedItem != null && currEquipment != null)
            {
                moveItem(selectedItem, currEquipment, otherEquipment)
                
                this.fillEquipmentStructureWithItems(currEquipment)
                this.fillEquipmentStructureWithItems(otherEquipment)
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
        
        heroOverlay = equipmentOverlay("EQUIPMENT")
        heroOverlay.isVisible = false
        containerOverlay = equipmentOverlay("")
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
        val buttons = columnOf(returnButton, arrowButton, deleteButton)
        
        equipment = rowOf(heroOverlay, buttons, containerOverlay)
        
        equipment.setFillParent(true)
        
        GameScreen.stage.addActor(equipment)
    }
    
    fun showHeroEquipment()
    {
        Sounds.ui_1.playOnce()
        if (containerOverlay in equipment.children)
        {
            equipment.removeActor(containerOverlay)
        }
        setVisibility(true)
        deleteButton.isVisible = false
        arrowButton.isVisible = false
        PopUps.setBackgroundVisibility(false)
        ProgressBars.setVisibilty(false)
        LeftStructure.menuButton.isVisible = false
        // GameScreen.heroEquipment.sortItems()
        this.fillEquipmentStructureWithItems(this.heroEquipment)
        this.isHeroEquipmentOnly = true
    }
    
    fun showHeroAndContainerEquipments(containerEquipment : Container)
    {
        Sounds.ui_1.playOnce()
        if (containerOverlay !in equipment.children)
        {
            equipment.addActor(containerOverlay)
        }
        setVisibility(true)
        deleteButton.isVisible = false
        arrowButton.isVisible = false
        PopUps.setBackgroundVisibility(false)
        ProgressBars.setVisibilty(false)
        LeftStructure.menuButton.isVisible = false
        // GameScreen.heroEquipment.sortItems()
        this.fillEquipmentStructureWithItems(this.heroEquipment)
        this.containerEquipment = containerEquipment
        // GameScreen.containerEquipment.sortItems()
        this.fillEquipmentStructureWithItems(this.containerEquipment)
        this.isHeroEquipmentOnly = false
    }
    
    fun fillEquipmentStructureWithItems(equipment : Container)
    {
        fun onClick(item : Item?, button : ImageButton?)
        {
            this.selectedButton?.style?.up = NinePatchDrawable(Textures.upNinePatch)
            
            deleteButton.isVisible = true
            
            if (this.selectedButton === button)
            {
                this.selectedItem = null
                this.selectedButton = null
                this.currEquipment = null
            }
            else
            {
                this.selectedItem = item
                this.selectedButton = button
                this.currEquipment = equipment
            }
            
            this.selectedButton?.style?.up = NinePatchDrawable(Textures.downNinePatch)
            
            deleteButton.isVisible = (this.selectedButton != null)
            arrowButton.isVisible = (this.selectedButton != null && !this.isHeroEquipmentOnly)
            arrowButton.style.imageUp = when (equipment === this.heroEquipment)
            {
                true  -> TextureRegionDrawable(Textures.arrowRight)
                false -> TextureRegionDrawable(Textures.arrowLeft)
            }
            arrowButton.style.imageDown = when (equipment === this.heroEquipment)
            {
                true  -> TextureRegionDrawable(Textures.arrowRight)
                false -> TextureRegionDrawable(Textures.arrowLeft)
            }
        }
        
        val table = if (equipment === this.heroEquipment) heroOverlay
        else containerOverlay
        
        val itemRows = mutableListOf<HorizontalGroup>()
        
        for (i in 0 until EQUIPMENT_ROWS)
        {
            val itemRow = table.getChild(1 + i) as HorizontalGroup
            itemRow.clear()
            itemRows.add(itemRow)
        }
        
        var itemCount = 0
        
        for (item in equipment.items)
        {
            when (item)
            {
                is MultiUseMapItem   ->
                {
                    val button =
                            ItemsStructure.createItemWithHealthbar(item.durability, item.maxDurability, item.getTexture(), item.name, item.statsDescription) {}
                    button.addListener(object : ClickListener()
                                       {
                                           override fun clicked(event : InputEvent?, x : Float, y : Float)
                                           {
                                               onClick(item, button)
                                           }
                                       })
                    itemRows[itemCount / EQUIPMENT_ROW_MAX].addActor(button)
                }
                
                is StackableMapItem  ->
                {
                    val button = ItemsStructure.createItemWithLabel(
                            item.amount,
                            item.getTexture(),
                            item.name,
                            item.statsDescription
                                                                   ) {}
                    button.addListener(object : ClickListener()
                                       {
                                           override fun clicked(event : InputEvent?, x : Float, y : Float)
                                           {
                                               onClick(item, button)
                                           }
                                       })
                    itemRows[itemCount / EQUIPMENT_ROW_MAX].addActor(button)
                }
                
                is StackableSelfItem ->
                {
                    val button = ItemsStructure.createItemWithLabel(
                            item.amount,
                            item.getTexture(),
                            item.name,
                            item.statsDescription
                                                                   ) {}
                    button.addListener(object : ClickListener()
                                       {
                                           override fun clicked(event : InputEvent?, x : Float, y : Float)
                                           {
                                               onClick(item, button)
                                           }
                                       })
                    itemRows[itemCount / EQUIPMENT_ROW_MAX].addActor(button)
                }
            }
            
            itemCount++
        }
        
        for (i in itemCount until equipment.maxItems)
        {
            val image = imageOf(Textures.down, Scaling.fill)
            itemRows[i / EQUIPMENT_ROW_MAX].addActor(image)
        }
        
        for (i in equipment.maxItems until EQUIPMENT_ROWS * EQUIPMENT_ROW_MAX)
        {
            val image = imageOf(Textures.disabled, Scaling.fill)
            itemRows[i / EQUIPMENT_ROW_MAX].addActor(image)
        }
    }
}