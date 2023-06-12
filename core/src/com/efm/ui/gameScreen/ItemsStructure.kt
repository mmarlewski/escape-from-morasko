package com.efm.ui.gameScreen

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.Map
import com.efm.assets.*
import com.efm.level.World
import com.efm.multiUseMapItems.*
import com.efm.screens.GameScreen
import com.efm.stackableMapItems.Bomb
import com.efm.stackableSelfItems.Mushroom
import com.efm.state.*

object ItemsStructure
{
    var mushroomButton : ImageButton
    var swordButton : ImageButton
    var axeButton : ImageButton
    var hammerButton : ImageButton
    var bombButton : ImageButton
    
    fun bombAttack()
    {
        val bomb = Bomb()
        val targetPositions = bomb.getTargetPositions(World.hero.position)
        
        Map.clearLayer(MapLayer.select)
        for (position in targetPositions)
        {
            Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
        }
        
        val newState = when (val currState = getState())
        {
            is State.free        -> State.free.stackableMapItemChosen.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.chosenStackableMapItem = bomb
                this.targetPositions = targetPositions
            }
            
            is State.constrained -> State.constrained.stackableMapItemChosen.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.isHeroDetected = currState.isHeroDetected
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                this.chosenStackableMapItem = bomb
                this.targetPositions = targetPositions
            }
            
            is State.combat.hero -> State.combat.hero.stackableMapItemChosen.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                this.chosenStackableMapItem = bomb
                this.targetPositions = targetPositions
            }
            
            else                 -> currState
        }
        setState(newState)
    }
    
    fun swordAttack()
    {
        val sword = WoodenSword()
        
        val currState = getState()
        
        val canBeUsed = when (currState)
        {
            is State.free                              -> true
            is State.constrained, is State.combat.hero ->
            {
                World.hero.abilityPoints >= sword.baseAPUseCost
            }
            
            else                                       -> false
        }
        
        if (canBeUsed)
        {
            val targetPositions = sword.getTargetPositions(World.hero.position)
            
            Map.clearLayer(MapLayer.select)
            for (position in targetPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
            }
            
            val newState = when (currState)
            {
                is State.free        -> State.free.multiUseMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.chosenMultiUseItem = sword
                    this.targetPositions = targetPositions
                }
                
                is State.constrained -> State.constrained.multiUseMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenMultiUseItem = sword
                    this.targetPositions = targetPositions
                }
                
                is State.combat.hero -> State.combat.hero.multiUseMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenMultiUseItem = sword
                    this.targetPositions = targetPositions
                }
                
                else                 -> currState
            }
            setState(newState)
        }
    }
    
    fun axeAttack()
    {
        val axe = SmallAxe()
        
        val currState = getState()
        
        val canBeUsed = when (currState)
        {
            is State.free                              -> true
            is State.constrained, is State.combat.hero ->
            {
                World.hero.abilityPoints >= axe.baseAPUseCost
            }
            
            else                                       -> false
        }
        
        if (canBeUsed)
        {
            val targetPositions = axe.getTargetPositions(World.hero.position)
            
            Map.clearLayer(MapLayer.select)
            for (position in targetPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
            }
            
            val newState = when (currState)
            {
                is State.free        -> State.free.multiUseMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.chosenMultiUseItem = axe
                    this.targetPositions = targetPositions
                }
                
                is State.constrained -> State.constrained.multiUseMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenMultiUseItem = axe
                    this.targetPositions = targetPositions
                }
                
                is State.combat.hero -> State.combat.hero.multiUseMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenMultiUseItem = axe
                    this.targetPositions = targetPositions
                }
                
                else                 -> currState
            }
            setState(newState)
        }
    }
    
    fun hammerAttack()
    {
        val hammer = Sledgehammer()
        
        val currState = getState()
        
        val canBeUsed = when (currState)
        {
            is State.free                              -> true
            is State.constrained, is State.combat.hero ->
            {
                World.hero.abilityPoints >= hammer.baseAPUseCost
            }
            
            else                                       -> false
        }
        
        if (canBeUsed)
        {
            val targetPositions = hammer.getTargetPositions(World.hero.position)
            
            Map.clearLayer(MapLayer.select)
            for (position in targetPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
            }
            
            val newState = when (currState)
            {
                is State.free        -> State.free.multiUseMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.chosenMultiUseItem = hammer
                    this.targetPositions = targetPositions
                }
                
                is State.constrained -> State.constrained.multiUseMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenMultiUseItem = hammer
                    this.targetPositions = targetPositions
                }
                
                is State.combat.hero -> State.combat.hero.multiUseMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenMultiUseItem = hammer
                    this.targetPositions = targetPositions
                }
                
                else                 -> currState
            }
            setState(newState)
        }
    }
    
    fun createItemWithHealthbar(
            maxHealth : Float,
            currentHealth : Float,
            texture : Texture,
            action : () -> Unit
                               ) : ImageButton
    {
        val button = itemButtonWithHealthbar(
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
    
    fun createItemWithLabel(amountOfUses : Int, texture : Texture, action : () -> Unit) : ImageButton
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
    
    init
    {
        swordButton = createItemWithHealthbar(100f, 100f, Textures.sword, { swordAttack() })
        axeButton = createItemWithHealthbar(100f, 100f, Textures.axe, { axeAttack() })
        hammerButton = createItemWithHealthbar(100f, 100f, Textures.hammer, { hammerAttack() })
        mushroomButton = createItemWithLabel(5, Textures.mushroom, { Mushroom().use() })
        bombButton = createItemWithLabel(10, Textures.bomb, { bombAttack() })
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
    
    val buttonsAssignment : List<Pair<String, ImageButton>> = listOf(
            Pair("weapon", swordButton),
            Pair("weapon", axeButton),
            Pair("weapon", hammerButton),
            Pair("potion", mushroomButton),
            Pair("usable", bombButton)
                                                                    )
    
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
    
}