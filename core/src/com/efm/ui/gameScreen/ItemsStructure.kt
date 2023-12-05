package com.efm.ui.gameScreen

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.Map
import com.efm.assets.*
import com.efm.item.*
import com.efm.level.World
import com.efm.screens.GameScreen
import com.efm.skill.ActiveSkill
import com.efm.state.*

object ItemsStructure
{
    val equipmentButton = imageButtonOf(
            Textures.backpack,
            Textures.upNinePatch,
            Textures.downNinePatch,
            Textures.overNinePatch,
            Textures.disabledNinePatch,
            Textures.focusedNinePatch
                                       ) {
        playSoundOnce(Sounds.ui_1)
        LeftStructure.setVisibility(!LeftStructure.healingItemsButton.isVisible)
    }
    
    val weaponDisplay = rowOf(rowOf().padLeft(100f)).align(Align.bottomLeft)
    val potionDisplay = rowOf(rowOf().padLeft(100f)).align(Align.bottomLeft)
    val usableDisplay = rowOf(rowOf().padLeft(100f)).align(Align.bottomLeft)
    val skillDisplay = rowOf(rowOf().padLeft(102f)).align(Align.bottomLeft)
    val equipmentDisplay = rowOf(equipmentButton).align(Align.bottomLeft)
    
    fun setVisibility(boolean : Boolean)
    {
        weaponDisplay.isVisible = boolean
        potionDisplay.isVisible = boolean
        usableDisplay.isVisible = boolean
        skillDisplay.isVisible = boolean
        equipmentDisplay.isVisible = boolean
    }
    
    fun createActiveSkill(currentCooldown : Int, texture : Texture, action : () -> Unit) : WidgetGroup
    {
        if (currentCooldown == 0)
        {
            val imageButton = imageButtonOf(
                    texture,
                    Textures.upNinePatch,
                    Textures.downNinePatch,
                    Textures.overNinePatch,
                    Textures.disabledNinePatch,
                    Textures.focusedNinePatch
                                           ) {
                playSoundOnce(Sounds.ui_4)
                action()
            }
            return imageButton
        }
        else
        {
            val cooldownButton = buttonWithTextOverlay(
                    texture,
                    currentCooldown.toString(),
                    Textures.downNinePatch,
                    Textures.downNinePatch,
                    Textures.downNinePatch,
                    Textures.downNinePatch,
                    Textures.downNinePatch
                                                      )
            {
            
            }
            return cooldownButton
        }
    }
    
    fun createPassiveSkill(texture : Texture) : ImageButton
    {
        val button = imageButtonOf(
                texture,
                Textures.downNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                  )
        {
        
        }
        return button
    }
    
    fun createItemWithHealthbar(
            currentHealth : Int,
            maxHealth : Int,
            texture : Texture?,
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
            playSoundOnce(Sounds.ui_4)
            action()
        }
        
        return button
    }
    
    fun createItemWithLabel(
            amountOfUses : Int,
            texture : Texture?,
            action : () -> Unit
                           ) : ImageButton
    {
        val button = itemButtonWithLabel(
                texture, "$amountOfUses",
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                        ) {
            playSoundOnce(Sounds.ui_4)
            action()
        }
        
        return button
    }
    
    fun setWeaponDisplay()
    {
        weaponDisplay.isVisible = true
        potionDisplay.isVisible = false
        usableDisplay.isVisible = false
        skillDisplay.isVisible = false
    }
    
    fun setPotionDisplay()
    {
        weaponDisplay.isVisible = false
        potionDisplay.isVisible = true
        usableDisplay.isVisible = false
        skillDisplay.isVisible = false
    }
    
    fun setUsableDisplay()
    {
        weaponDisplay.isVisible = false
        potionDisplay.isVisible = false
        usableDisplay.isVisible = true
        skillDisplay.isVisible = false
    }
    
    fun setSkillDisplay()
    {
        weaponDisplay.isVisible = false
        potionDisplay.isVisible = false
        usableDisplay.isVisible = false
        skillDisplay.isVisible = true
    }
    
    fun display()
    {
        equipmentDisplay.pad(24f)
        weaponDisplay.pad(16f)
        potionDisplay.pad(16f)
        usableDisplay.pad(16f)
        skillDisplay.pad(14f)
        
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
        
        setWeaponDisplay()
    }
    
    fun attack(item : Item)
    {
        val currState = getState()
    
        // tutorial popups
        if (currState.tutorialFlags.tutorialOn && currState.tutorialFlags.equipmentPopupShown)
            currState.tutorialFlags.playerSelectedSomethingFromEquipment = true
        if (currState.tutorialFlags.tutorialOn && currState.tutorialFlags.playerSelectedSomethingFromEquipment && !currState.tutorialFlags.healthAndAbilityPopupShown)
        {
            // turnsPopup is shown directly after closing healthAndAbilityPopup
            ProgressBars.display()
            TutorialPopups.healthAndAbilityPopup.isVisible = true
            PopUps.setBackgroundVisibility(false)
            ProgressBars.setVisibilty(true)
            LeftStructure.menuButton.isVisible = false
            currState.tutorialFlags.healthAndAbilityPopupShown = true
            currState.tutorialFlags.turnsPopupShown = true
        }
    
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
            if (item is StackableSelfItem)
            {
                item.use()
                item.lowerAmountByOne()
                if (item.amount < 1)
                {
                    World.hero.inventory.removeItem(item)
                }
                fillItemsStructureWithItemsAndSkills()
            }
            
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
    
    fun attack(activeSkill : ActiveSkill)
    {
        val currState = getState()
        
        val canBeUsed = when (currState)
        {
            is State.free                              -> true
            is State.constrained, is State.combat.hero ->
            {
                World.hero.abilityPoints >= activeSkill.apCost && !activeSkill.isInCoolDown
            }
            
            else                                       -> false
        }
        
        if (canBeUsed)
        {
            val targetPositions = activeSkill.getTargetPositions(World.hero.position)
            
            Map.clearLayer(MapLayer.select)
            Map.clearLayer(MapLayer.outline)
            for (position in targetPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
            }
            
            val newState = when (currState)
            {
                is State.free        -> State.free.activeSkillChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.chosenActiveSkill = activeSkill
                    this.targetPositions = targetPositions
                }
                
                is State.constrained -> State.constrained.activeSkillChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenActiveSkill = activeSkill
                    this.targetPositions = targetPositions
                }
                
                is State.combat.hero -> State.combat.hero.activeSkillChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenActiveSkill = activeSkill
                    this.targetPositions = targetPositions
                }
                
                else                 -> currState
            }
            setState(newState)
        }
    }
    
    fun fillItemsStructureWithItemsAndSkills()
    {
        val multiUseMapItemRow = weaponDisplay.children[0] as HorizontalGroup
        val stackableMapItemRow = usableDisplay.children[0] as HorizontalGroup
        val stackableSelfItemRow = potionDisplay.children[0] as HorizontalGroup
        val skillRow = skillDisplay.children[0] as HorizontalGroup
        
        multiUseMapItemRow.clear()
        stackableMapItemRow.clear()
        stackableSelfItemRow.clear()
        skillRow.clear()
        
        for (item in World.hero.inventory.items)
        {
            when (item)
            {
                is MultiUseMapItem   ->
                {
                    multiUseMapItemRow.addActor(
                            createItemWithHealthbar(item.durability, item.maxDurability, item.getTexture()) { attack(item) }
                                               )
                }
                
                is StackableMapItem  ->
                {
                    stackableMapItemRow.addActor(
                            createItemWithLabel(item.amount, item.getTexture()) { attack(item) }
                                                )
                }
                
                is StackableSelfItem ->
                {
                    stackableSelfItemRow.addActor(
                            createItemWithLabel(item.amount, item.getTexture()) { attack(item) }
                                                 )
                }
            }
        }
        
        for ((bodyPart, skill) in World.hero.bodyPartMap.entries)
        {
            when (skill)
            {
                null           ->
                {
                
                }
                is ActiveSkill ->
                {
                    // if turn ends refresh drawing
                    skillRow.addActor(createActiveSkill(skill.currCoolDown, skill.texture) { attack(skill) })
                }
                else           ->
                {
                    skillRow.addActor(createPassiveSkill(skill.texture))
                }
            }
        }
    }
}