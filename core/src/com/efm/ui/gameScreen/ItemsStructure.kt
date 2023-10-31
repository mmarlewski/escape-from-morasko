package com.efm.ui.gameScreen

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.Map
import com.efm.assets.*
import com.efm.item.*
import com.efm.level.World
import com.efm.screens.GameScreen
import com.efm.skill.Skill
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
        playSoundOnce(Sounds.blop)
        LeftStructure.setVisibility(!LeftStructure.healingItemsButton.isVisible)
    }
    
    val weaponDisplay = rowOf(rowOf().padLeft(100f)).align(Align.bottomLeft)
    val potionDisplay = rowOf(rowOf().padLeft(100f)).align(Align.bottomLeft)
    val usableDisplay = rowOf(rowOf().padLeft(100f)).align(Align.bottomLeft)
    val skillDisplay = rowOf(rowOf().padLeft(100f)).align(Align.bottomLeft)
    val equipmentDisplay = rowOf(equipmentButton).align(Align.bottomLeft)
    
    fun setVisibility(boolean : Boolean)
    {
        weaponDisplay.isVisible = boolean
        potionDisplay.isVisible = boolean
        usableDisplay.isVisible = boolean
        skillDisplay.isVisible = boolean
        equipmentDisplay.isVisible = boolean
    }
    
    fun createActiveSkill(texture : Texture, action : () -> Unit) : ImageButton
    {
        val button = imageButtonOf(
                texture,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                  )
        {
            playSoundOnce(Sounds.blop)
            action()
        }
        
        return button
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
            playSoundOnce(Sounds.blop)
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
            playSoundOnce(Sounds.blop)
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
        
        setWeaponDisplay()
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
            if (item is StackableSelfItem)
            {
                item.use()
                item.lowerAmountByOne()
                if(item.amount < 1)
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
        
        for (skill in Skill.values())
        {
            if (World.hero.hasSkill(skill))
            {
                if (skill.isPassive)
                {
                    skillRow.addActor(createPassiveSkill(skill.texture))
                }
                else
                {
                    skillRow.addActor(createActiveSkill(skill.texture) {})
                }
            }
        }
    }
}