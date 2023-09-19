package com.efm.ui.gameScreen

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.Map
import com.efm.assets.*
import com.efm.item.*
import com.efm.level.World
import com.efm.multiUseMapItems.*
import com.efm.screens.GameScreen
import com.efm.stackableMapItems.*
import com.efm.stackableSelfItems.*
import com.efm.state.*

object ItemsStructure
{
    var swordButton : ImageButton
    var axeButton : ImageButton
    var hammerButton : ImageButton
    var bowButton : ImageButton
    var staffButton : ImageButton
    var appleButton : ImageButton
    var fishButton : ImageButton
    var mushroomButton : ImageButton
    var bombButton : ImageButton
    var explosiveButton : ImageButton
    var shurikenButton : ImageButton
    val buttonsAssignment : MutableList<Pair<String, ImageButton>> = mutableListOf()
    
    init
    {
        // buttons now have 4 types: weapon, potion, usable and skill based on the category within equipment display
        swordButton = createItemWithHealthbar("weapon", 100, 100, WoodenSword().getTexture(), { attack(WoodenSword()) })
        axeButton = createItemWithHealthbar("weapon", 100, 0, SmallAxe().getTexture(), { attack(SmallAxe()) })
        hammerButton = createItemWithHealthbar("weapon", 100, 50, Sledgehammer().getTexture(), { attack(Sledgehammer()) })
        bowButton = createItemWithHealthbar("weapon", 100, 75, Bow().getTexture(), { attack(Bow()) })
        staffButton = createItemWithHealthbar("weapon", 100, 25, Staff().getTexture(), { attack(Staff()) })
        appleButton = createItemWithLabel("potion", 5, Apple().getTexture(), { attack(Apple()) })
        fishButton = createItemWithLabel("potion", 5, Fish().getTexture(), { attack(Fish()) })
        mushroomButton = createItemWithLabel("potion", 5, Mushroom().getTexture(), { attack(Mushroom()) })
        bombButton = createItemWithLabel("usable", 10, Bomb().getTexture(), { attack(Bomb()) })
        explosiveButton = createItemWithLabel("usable", 10, Explosive().getTexture(), { attack(Explosive()) })
        shurikenButton = createItemWithLabel("usable", 10, Shuriken().getTexture(), { attack(Shuriken()) })
    }
    
    fun setVisibility(boolean : Boolean)
    {
        for (item : Actor in weaponDisplay.children)
        {
            item.isVisible = boolean
        }
        
        for (item : Actor in potionDisplay.children)
        {
            item.isVisible = boolean
        }
        
        for (item : Actor in skillDisplay.children)
        {
            item.isVisible = boolean
        }
        
        for (item : Actor in usableDisplay.children)
        {
            item.isVisible = boolean
        }
        
        equipmentDisplay.isVisible = boolean
        
    }
    
    fun attack(item : Item)
    {
        val currState = getState()
        
        val canBeUsed = when (currState)
        {
            is State.free                              -> true
            is State.constrained, is State.combat.hero ->
            {
                World.hero.abilityPoints >= item.baseAPUseCost
            }
            
            else                                       -> false
        }
        
        if (canBeUsed)
        {
            if (item is StackableSelfItem) item.use()
            
            val targetPositions = when (item)
            {
                is MultiUseMapItem  -> item.getTargetPositions(World.hero.position)
                is StackableMapItem -> item.getTargetPositions(World.hero.position)
                else                -> emptyList()
            }
            
            Map.clearLayer(MapLayer.select)
            Map.clearLayer(MapLayer.outline)
            for (position in targetPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
            }
            
            val newState = when (currState)
            {
                is State.free        -> when (item)
                {
                    is MultiUseMapItem   -> State.free.multiUseMapItemChosen.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.chosenMultiUseItem = item
                        this.targetPositions = targetPositions
                    }
                    
                    is StackableMapItem  -> State.free.stackableMapItemChosen.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.chosenStackableMapItem = item
                        this.targetPositions = targetPositions
                    }
                    
                    is StackableSelfItem -> State.free.stackableSelfItemChosen.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.chosenStackableSelfItem = item
                    }
                    
                    else                 -> currState
                }
                
                is State.constrained -> when (item)
                {
                    is MultiUseMapItem   -> State.constrained.multiUseMapItemChosen.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.isHeroDetected = currState.isHeroDetected
                        this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                        this.chosenMultiUseItem = item
                        this.targetPositions = targetPositions
                    }
                    
                    is StackableMapItem  -> State.constrained.stackableMapItemChosen.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.isHeroDetected = currState.isHeroDetected
                        this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                        this.chosenStackableMapItem = item
                        this.targetPositions = targetPositions
                    }
                    
                    is StackableSelfItem -> State.constrained.stackableSelfItemChosen.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.isHeroDetected = currState.isHeroDetected
                        this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                        this.chosenStackableSelfItem = item
                    }
                    
                    else                 -> currState
                }
                
                is State.combat.hero -> when (item)
                {
                    is MultiUseMapItem   -> State.combat.hero.multiUseMapItemChosen.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                        this.chosenMultiUseItem = item
                        this.targetPositions = targetPositions
                    }
                    
                    is StackableMapItem  -> State.combat.hero.stackableMapItemChosen.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                        this.chosenStackableMapItem = item
                        this.targetPositions = targetPositions
                    }
                    
                    is StackableSelfItem -> State.combat.hero.stackableSelfItemChosen.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                        this.chosenStackableSelfItem = item
                    }
                    
                    else                 -> currState
                }
                
                else                 -> currState
            }
            setState(newState)
        }
    }
    
    fun createItemWithHealthbar(
            type : String,
            maxHealth : Int,
            currentHealth : Int,
            texture : Texture,
            action : () -> Unit
                               ) : ImageButton
    {
        val button = itemButtonWithHealthBar(
                texture, maxHealth, currentHealth,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                            ) {
            playSoundOnce(Sounds.blop)
            action()
        }
        
        buttonsAssignment.add(Pair(type, button))
        
        return button
    }
    
    fun createItemWithLabel(type : String, amountOfUses : Int, texture : Texture, action : () -> Unit) : ImageButton
    {
        val button = itemButtonWithLabel(
                texture, "$amountOfUses",
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                        ) {
            playSoundOnce(Sounds.blop)
            action()
        }
        buttonsAssignment.add(Pair(type, button))
        return button
    }
    
    val equipmentButton = imageButtonOf(
            Textures.backpack,
            Textures.upNinePatch,
            Textures.downNinePatch,
            Textures.overNinePatch,
            Textures.disabledNinePatch,
            Textures.focusedNinePatch
                                       ) {
        playSoundOnce(Sounds.blop)
        LeftStructure.setVisibility(!LeftStructure.healingItemsButton.isVisible)
    }
    
    init
    {
        buttonsAssignment.forEach { (category, button) ->
            if (category == "weapon")
            {
                button.isVisible = true
            }
            else
            {
                button.isVisible = false
            }
        }
        
    }
    
    val weaponButtons = buttonsAssignment.filter { (category, _) ->
        category == "weapon"
    }.map { (_, button) ->
        button
    }
    
    val potionButtons = buttonsAssignment.filter { (category, _) ->
        category == "potion"
    }.map { (_, button) ->
        button
    }
    
    val usableButtons = buttonsAssignment.filter { (category, _) ->
        category == "usable"
    }.map { (_, button) ->
        button
    }
    
    val skillButtons = buttonsAssignment.filter { (category, _) ->
        category == "skill"
    }.map { (_, button) ->
        button
    }
    
    fun setWeaponDisplay()
    {
        buttonsAssignment.forEach { (category, button) ->
            if (category == "weapon")
            {
                button.isVisible = true
            }
            else
            {
                button.isVisible = false
            }
        }
    }
    
    fun setPotionDisplay()
    {
        buttonsAssignment.forEach { (category, button) ->
            if (category == "potion")
            {
                button.isVisible = true
            }
            else
            {
                button.isVisible = false
            }
        }
    }
    
    fun setUsableDisplay()
    {
        buttonsAssignment.forEach { (category, button) ->
            if (category == "usable")
            {
                button.isVisible = true
            }
            else
            {
                button.isVisible = false
            }
        }
    }
    
    fun setSkillDisplay()
    {
        buttonsAssignment.forEach { (category, button) ->
            if (category == "skill")
            {
                button.isVisible = true
            }
            else
            {
                button.isVisible = false
            }
        }
    }
    
    val weaponDisplay = columnOf(rowOf(*weaponButtons.toTypedArray()).padLeft(112f)).align(Align.bottomLeft)
    val potionDisplay = columnOf(rowOf(*potionButtons.toTypedArray()).padLeft(112f)).align(Align.bottomLeft)
    val usableDisplay = columnOf(rowOf(*usableButtons.toTypedArray()).padLeft(112f)).align(Align.bottomLeft)
    val skillDisplay = columnOf(rowOf(*usableButtons.toTypedArray()).padLeft(112f)).align(Align.bottomLeft)
    
    val equipmentDisplay = columnOf(
            rowOf(equipmentButton)
                                   ).align(Align.bottomLeft)
    
    fun display()
    {
        equipmentDisplay.pad(16f)
        weaponDisplay.pad(16f)
        potionDisplay.pad(16f)
        usableDisplay.pad(16f)
        skillDisplay.pad(16f)
        
        equipmentDisplay.setFillParent(true)
        weaponDisplay.setFillParent(true)
        potionDisplay.setFillParent(true)
        usableDisplay.setFillParent(true)
        skillDisplay.setFillParent(true)
    
        GameScreen.stage.addActor(equipmentDisplay)
        GameScreen.stage.addActor(weaponDisplay)
        GameScreen.stage.addActor(potionDisplay)
        GameScreen.stage.addActor(usableDisplay)
        GameScreen.stage.addActor(skillDisplay)
    
    }
    
    fun weaponEqDisplay() : HorizontalGroup
    {
        val swordButton = createItemWithHealthbar("weapon", 100, 100, WoodenSword().getTexture()) { attack(WoodenSword()) }
        val axeButton = createItemWithHealthbar("weapon", 100, 0, SmallAxe().getTexture()) { attack(SmallAxe()) }
        val hammerButton = createItemWithHealthbar("weapon", 100, 50, Sledgehammer().getTexture()) { attack(Sledgehammer()) }
        val bowButton = createItemWithHealthbar("weapon", 100, 75, Bow().getTexture()) { attack(Bow()) }
        val staffButton = createItemWithHealthbar("weapon", 100, 25, Staff().getTexture()) { attack(Staff()) }
        
        val buttons = listOf(swordButton, axeButton, hammerButton, bowButton, staffButton)
        
        return rowOf(*buttons.toTypedArray()).align(Align.left)
    }
    
    fun potionEqDisplay() : HorizontalGroup
    {
        val appleButton = createItemWithLabel("potion", 5, Apple().getTexture()) { attack(Apple()) }
        val fishButton = createItemWithLabel("potion", 5, Fish().getTexture()) { attack(Fish()) }
        val mushroomButton = createItemWithLabel("potion", 5, Mushroom().getTexture()) { attack(Mushroom()) }
        
        val buttons = listOf(appleButton, fishButton, mushroomButton)
        
        return rowOf(*buttons.toTypedArray()).align(Align.left)
    }
    
    fun usableEqDisplay() : HorizontalGroup
    {
        val bombButton = createItemWithLabel("usable", 10, Bomb().getTexture()) { attack(Bomb()) }
        val explosiveButton = createItemWithLabel("usable", 10, Explosive().getTexture()) { attack(Explosive()) }
        val shurikenButton = createItemWithLabel("usable", 10, Shuriken().getTexture()) { attack(Shuriken()) }
        
        val buttons = listOf(bombButton, explosiveButton, shurikenButton)
        
        return rowOf(*buttons.toTypedArray()).align(Align.left)
    }
    
    fun equipmentDisplay() : VerticalGroup?
    {
        val weaponEqDisplay = weaponEqDisplay()
        val potionEqDisplay = potionEqDisplay()
        val usableEqDisplay = usableEqDisplay()
        
        return columnOf(rowOf(weaponEqDisplay), rowOf(potionEqDisplay), rowOf(usableEqDisplay))
    }
    
}