package com.efm.state

import com.efm.*
import com.efm.Map
import com.efm.assets.Tiles
import com.efm.entities.Hero
import com.efm.entities.enemies.mimic.EnemyMimic
import com.efm.entity.Enemy
import com.efm.entity.Interactive
import com.efm.exit.Exit
import com.efm.exit.LevelExit
import com.efm.level.World
import com.efm.screens.GameScreen
import com.efm.ui.gameScreen.*

fun updateFreeNoSelection(currState : State.free.noSelection) : State
{
    if (currState.tutorialFlags.tutorialActive && !currState.tutorialFlags.welcomePopupShown)
    {
        currState.tutorialFlags.welcomePopupShown = true
        currState.tutorialFlags.cameraPopupShown = true
        currState.tutorialFlags.movementPopupShown = true
        //hiding some ui
        PopUps.setBackgroundVisibility(false)
        LeftStructure.menuButton.isVisible = false
        // popup is shown directly after closing the previous one
        TutorialPopups.welcomePopup.isVisible = true
    }
    
    if (currState.areEnemiesInRoom)
    {
        World.hero.regainAllAP()
        ProgressBars.abilityBar.isVisible = true
        ProgressBars.abilityBarForFlashing.isVisible = true
        ProgressBars.abilityBarLabel.isVisible = true
        
        return State.constrained.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
            this.isHeroDetected = false
            this.areAnyActionPointsLeft = true
        }
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        val selectedSpace = World.currentRoom?.getSpace(selectedPosition)
        val selectedEntity = selectedSpace?.getEntity()
        
        Map.clearLayer(MapLayer.outline)
        
        when (selectedEntity)
        {
            null    ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                
                return State.free.nothingSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                }
            }
            
            is Hero ->
            {
                RightStructure.moveButtonVisibility(true)
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectGreen)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineGreenTile())
                
                return State.free.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                }
            }
            
            else    ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                if (selectedEntity is Interactive) Map.changeTile(
                        MapLayer.outline,
                        selectedPosition,
                        selectedEntity.getOutlineTealTile()
                                                                 )
                else Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
    
                return State.free.entitySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.selectedEntity = selectedEntity
                }
            }
        }
    }
    return currState
}

fun updateFreeNothingSelected(currState : State.free.nothingSelected) : State
{
    if (currState.areEnemiesInRoom)
    {
        World.hero.regainAllAP()
        ProgressBars.abilityBar.isVisible = true
        ProgressBars.abilityBarForFlashing.isVisible = true
        ProgressBars.abilityBarLabel.isVisible = true
    
        return State.constrained.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
            this.isHeroDetected = false
            this.areAnyActionPointsLeft = true
        }
    }
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        val selectedSpace = World.currentRoom?.getSpace(selectedPosition)
        val selectedEntity = selectedSpace?.getEntity()
        
        Map.clearLayer(MapLayer.outline)
        
        when (selectedEntity)
        {
            null    ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
            }
            
            is Hero ->
            {
                RightStructure.moveButtonVisibility(true)
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectGreen)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineGreenTile())
            
            return State.free.heroSelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
            }
        }
        
        is Enemy ->
        {
            Map.clearLayer(MapLayer.select)
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectRed)
            Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
            
            return State.free.entitySelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.selectedEntity = selectedEntity
            }
        }
        
        else     ->
        {
            Map.clearLayer(MapLayer.select)
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
            if (selectedEntity is Interactive) Map.changeTile(
                    MapLayer.outline,
                    selectedPosition,
                    selectedEntity.getOutlineTealTile()
                                                             )
            else Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
    
            return State.free.entitySelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.selectedEntity = selectedEntity
            }
        }
    }
    }
    return currState
}

fun updateFreeEntitySelected(currState : State.free.entitySelected) : State
{
    if (currState.areEnemiesInRoom)
    {
        World.hero.regainAllAP()
        ProgressBars.abilityBar.isVisible = true
        ProgressBars.abilityBarForFlashing.isVisible = true
        ProgressBars.abilityBarLabel.isVisible = true
        
        return State.constrained.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
            this.isHeroDetected = false
            this.areAnyActionPointsLeft = true
        }
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        val selectedSpace = World.currentRoom?.getSpace(selectedPosition)
        val selectedEntity = selectedSpace?.getEntity()
        Map.clearLayer(MapLayer.outline)
        when (selectedEntity)
        {
            null    ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
    
                return State.free.nothingSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                }
            }
            
            is Hero ->
            {
                RightStructure.moveButtonVisibility(true)
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectGreen)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineGreenTile())
                
                return State.free.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                }
            }
            
            else    ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                if (selectedEntity is Interactive)
                {
                    Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTealTile())
                    if (selectedEntity == currState.selectedEntity && World.hero.position.isAdjacentTo(selectedEntity.position))
                    {
                        // Using Exits this way would require more work
                        if (selectedEntity !is Exit)
                            selectedEntity.interact()
                    }
                    Map.clearLayer(MapLayer.select)
                    Map.clearLayer(MapLayer.outline)
                    return State.free.nothingSelected.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                    }
                }
                else Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
    
                return State.free.entitySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.selectedEntity = selectedEntity
                }
            }
        }
    }
    
    return currState
}

fun updateFreeHeroSelected(currState : State.free.heroSelected) : State
{
    if (currState.areEnemiesInRoom)
    {
        World.hero.regainAllAP()
        ProgressBars.abilityBar.isVisible = true
        ProgressBars.abilityBarForFlashing.isVisible = true
        ProgressBars.abilityBarLabel.isVisible = true
        
        return State.constrained.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
            this.isHeroDetected = false
            this.areAnyActionPointsLeft = true
        }
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        val selectedSpace = World.currentRoom?.getSpace(selectedPosition)
        val selectedEntity = selectedSpace?.getEntity()
        
        Map.clearLayer(MapLayer.outline)
        
        when (selectedEntity)
        {
            is Hero ->
            {
                Map.clearLayer(MapLayer.select)
    
                return State.free.nothingSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                }
            }
            
            else    ->
            {
                val worldCurrentRoom = World.currentRoom
                val pathSpaces = if (worldCurrentRoom != null) PathFinding.findPathInRoomForEntity(
                        World.hero.position,
                        selectedPosition,
                        worldCurrentRoom,
                        World.hero
                                                                                                  )
                else null
                if (pathSpaces != null)
                {
                    Map.clearLayer(MapLayer.select)
                    Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
                    for (space in pathSpaces)
                    {
                        Map.changeTile(MapLayer.select, space.position, Tiles.selectTeal)
                    }
                    Map.changeTile(MapLayer.select, GameScreen.roomTouchPosition, Tiles.selectYellow)
                    if (selectedEntity is Interactive)
                    {
                        Map.changeTile(MapLayer.outline, GameScreen.roomTouchPosition, selectedEntity.getOutlineTealTile())
                    }
                    
                    val isMoveToAnotherRoom = (selectedEntity is Exit)
                    val isMoveToAnotherLevel = when (selectedEntity)
                    {
                        is Exit -> selectedEntity is LevelExit
                        else    -> false
                    }
                    
                    return State.free.moveSelectedOnce.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.selectedPosition.set(selectedPosition)
                        this.pathSpaces = pathSpaces
                        this.isMoveToAnotherRoom = isMoveToAnotherRoom
                        this.isMoveToAnotherLevel = isMoveToAnotherLevel
                    }
                }
            }
        }
    }
    
    return currState
}

fun updateFreeMoveSelectedOnce(currState : State.free.moveSelectedOnce) : State
{
    if (currState.areEnemiesInRoom)
    {
        World.hero.regainAllAP()
        ProgressBars.abilityBar.isVisible = true
        ProgressBars.abilityBarForFlashing.isVisible = true
        ProgressBars.abilityBarLabel.isVisible = true
        
        return State.constrained.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
            this.isHeroDetected = false
            this.areAnyActionPointsLeft = true
        }
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        val selectedSpace = World.currentRoom?.getSpace(selectedPosition)
        val selectedEntity = selectedSpace?.getEntity()
        
        Map.clearLayer(MapLayer.outline)
        
        if (selectedPosition == currState.selectedPosition)
        {
            Map.clearLayer(MapLayer.select)
            moveHero(World.hero.position.copy(), currState.selectedPosition.copy(), currState.pathSpaces)
            
            return State.free.moveSelectedTwice.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.entityOnPosition = selectedEntity
                this.pathSpaces = currState.pathSpaces
                this.isMoveToAnotherRoom = currState.isMoveToAnotherRoom
                this.isMoveToAnotherLevel = currState.isMoveToAnotherLevel
                this.tutorialFlags.playerMoved = true
            }
        }
        else if (selectedPosition != World.hero.position)
        {
            val worldCurrentRoom = World.currentRoom
            val pathSpaces = if (worldCurrentRoom != null) PathFinding.findPathInRoomForEntity(
                    World.hero.position,
                    selectedPosition,
                    worldCurrentRoom,
                    World.hero
                                                                                              )
            else null
            if (pathSpaces != null)
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
                for (space in pathSpaces)
                {
                    Map.changeTile(MapLayer.select, space.position, Tiles.selectTeal)
                }
                Map.changeTile(MapLayer.select, GameScreen.roomTouchPosition, Tiles.selectYellow)
                if (selectedEntity is Interactive)
                {
                    Map.changeTile(MapLayer.outline, GameScreen.roomTouchPosition, selectedEntity.getOutlineTealTile())
                }
                
                val isMoveToAnotherRoom = (selectedEntity is Exit)
                val isMoveToAnotherLevel = when (selectedEntity)
                {
                    is Exit -> selectedEntity is LevelExit
                    else    -> false
                }
                
                return State.free.moveSelectedOnce.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.selectedPosition.set(selectedPosition)
                    this.pathSpaces = pathSpaces
                    this.isMoveToAnotherRoom = isMoveToAnotherRoom
                    this.isMoveToAnotherLevel = isMoveToAnotherLevel
                }
            }
        }
    }
    
    return currState
}

fun updateFreeMoveSelectedTwice(currState : State.free.moveSelectedTwice) : State
{
    if (!Animating.isAnimating())
    {
        // interact with Interactive Entity if it was selected in FreeMoveSelectedOnce
        val entityOnPositionHeroWalkedTowards = currState.entityOnPosition
        if (entityOnPositionHeroWalkedTowards is Interactive)
        {
            entityOnPositionHeroWalkedTowards.interact()
        }
        
        GameScreen.roomTouchPosition.set(World.hero.position)
        Map.changeTile(MapLayer.outline, World.hero.position, World.hero.getOutlineGreenTile())
        for (level in World.levels)
        {
            for (room in level.rooms)
            {
                for (enemy in room.getEnemies())
                {
                    enemy.hideOwnHealthBar()
                }
            }
        }
        for (enemy in World.currentRoom?.getEnemies() ?: listOf())
        {
            if (enemy !is EnemyMimic || enemy.detected()) enemy.displayOwnHealthBar()
        }
        val isMoveToAnotherRoom = currState.isMoveToAnotherRoom
        val isMoveToAnotherLevel = currState.isMoveToAnotherLevel
        val areEnemiesInRoom = World.currentRoom?.areEnemiesInRoom() ?: false
        
        if (isMoveToAnotherRoom)
        {
            when (areEnemiesInRoom)
            {
                true  ->
                {
                    World.hero.regainAllAP()
                    ProgressBars.abilityBar.isVisible = true
                    ProgressBars.abilityBarForFlashing.isVisible = true
                    ProgressBars.abilityBarLabel.isVisible = true
                }
                
                false ->
                {
                    ProgressBars.abilityBar.isVisible = false
                    ProgressBars.abilityBarForFlashing.isVisible = false
                    ProgressBars.abilityBarLabel.isVisible = false
                }
            }
        }
        if (currState.tutorialFlags.tutorialActive && currState.tutorialFlags.movementPopupShown && currState.tutorialFlags.playerMoved && !currState.tutorialFlags.lootingPopupShown)
        {
            TutorialPopups.lootingPopup.isVisible = true
            LeftStructure.menuButton.isVisible = false
            PopUps.setBackgroundVisibility(false)
            currState.tutorialFlags.lootingPopupShown = true
        }
        
        return when (areEnemiesInRoom)
        {
            true  -> State.constrained.heroSelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = areEnemiesInRoom
                this.isHeroDetected = false
                this.areAnyActionPointsLeft = true
            }
            
            false -> State.free.heroSelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = areEnemiesInRoom
            }
        }
    }
    
    return currState
}

fun updateFreeMoveSelectedTwiceToLevelExitWaiting(currState : State.free.moveSelectedTwiceToLevelExit.waiting) : State
{
    return currState
}

fun updateFreeMoveSelectedTwiceToLevelExitConfirmed(currState : State.free.moveSelectedTwiceToLevelExit.confirmed) : State
{
    return currState
}

fun updateFreeMoveSelectedTwiceToLevelExitCancelled(currState : State.free.moveSelectedTwiceToLevelExit.cancelled) : State
{
    return currState
}

fun updateFreeMultiUseMapItemChosen(currState : State.free.multiUseMapItemChosen) : State
{
    val multiUseMapItem = currState.chosenMultiUseItem
    
    if (multiUseMapItem == null)
    {
        return currState
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        val targetPositions = currState.targetPositions ?: emptyList()
        if (selectedPosition in targetPositions)
        {
            val affectedPositions = multiUseMapItem.getAffectedPositions(selectedPosition)
            
            for (position in affectedPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectOrange)
            }
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
            
            return State.free.multiUseMapItemTargetSelectedOnce.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.chosenMultiUseItem = multiUseMapItem
                this.targetPositions = targetPositions
                this.selectedPosition.set(selectedPosition)
                this.effectPositions = affectedPositions
            }
        }
    }
    
    return currState
}

fun updateFreeMultiUseMapItemTargetSelectedOnce(currState : State.free.multiUseMapItemTargetSelectedOnce) : State
{
    val multiUseMapItem = currState.chosenMultiUseItem
    
    if (multiUseMapItem == null)
    {
        return currState
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        Map.clearLayer(MapLayer.select)
        
        if (selectedPosition == currState.selectedPosition)
        {
            val worldCurrentRoom = World.currentRoom
            if (worldCurrentRoom != null)
            {
                multiUseMapItem.use(worldCurrentRoom, selectedPosition)
            }
            
            return State.free.multiUseMapItemTargetSelectedTwice.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.chosenMultiUseItem = currState.chosenMultiUseItem
            }
        }
        else
        {
            currState.selectedPosition.set(selectedPosition)
            
            val targetPositions = currState.targetPositions ?: emptyList()
            
            for (position in targetPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
            }
            
            if (selectedPosition in targetPositions)
            {
                val affectedPositions = multiUseMapItem.getAffectedPositions(selectedPosition)
                
                for (position in affectedPositions)
                {
                    Map.changeTile(MapLayer.select, position, Tiles.selectOrange)
                }
            }
            else
            {
                return State.free.multiUseMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.chosenMultiUseItem = currState.chosenMultiUseItem
                    this.targetPositions = currState.targetPositions
                }
            }
        }
        
        Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
    }
    
    return currState
}

fun updateFreeMultiUseMapItemTargetSelectedTwice(currState : State.free.multiUseMapItemTargetSelectedTwice) : State
{
    if (!Animating.isAnimating())
    {
        // in free state you can destroy corpses
        World.currentRoom?.removeKilledCharacters()
        World.currentRoom?.addToBeAddedEntitiesToRoom()
        World.currentRoom?.updateSpacesEntities()
        GameScreen.updateMapEntityLayer()
        
        val item = currState.chosenMultiUseItem
        item?.lowerDurability()
        if (item != null && item.durability < 1)
        {
            World.hero.inventory.removeItem(item)
            ItemsStructure.fillItemsStructureWithItemsAndSkills()
            
            return State.free.heroSelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
            }
        }
        ItemsStructure.fillItemsStructureWithItemsAndSkills()
        
        val targetPositions = State.free.multiUseMapItemChosen.targetPositions ?: emptyList()
        for (position in targetPositions)
        {
            Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
        }
        
        return State.free.multiUseMapItemChosen.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
        }
    }
    
    return currState
}

fun updateFreeStackableMapItemChosen(currState : State.free.stackableMapItemChosen) : State
{
    val stackableMapItem = currState.chosenStackableMapItem
    
    if (stackableMapItem == null)
    {
        return currState
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        val targetPositions = currState.targetPositions ?: emptyList()
        if (selectedPosition in targetPositions)
        {
            val affectedPositions = stackableMapItem.getAffectedPositions(selectedPosition)
            
            for (position in affectedPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectOrange)
            }
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
            
            return State.free.stackableMapItemTargetSelectedOnce.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.chosenStackableMapItem = stackableMapItem
                this.targetPositions = targetPositions
                this.selectedPosition.set(selectedPosition)
                this.effectPositions = affectedPositions
            }
        }
    }
    
    return currState
}

fun updateFreeStackableMapItemTargetSelectedOnce(currState : State.free.stackableMapItemTargetSelectedOnce) : State
{
    val stackableMapItem = currState.chosenStackableMapItem
    
    if (stackableMapItem == null)
    {
        return currState
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        Map.clearLayer(MapLayer.select)
        
        if (selectedPosition == currState.selectedPosition)
        {
            val worldCurrentRoom = World.currentRoom
            if (worldCurrentRoom != null)
            {
                stackableMapItem.use(worldCurrentRoom, selectedPosition)
            }
            
            return State.free.stackableMapItemTargetSelectedTwice.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.chosenStackableMapItem = currState.chosenStackableMapItem
            }
        }
        else
        {
            currState.selectedPosition.set(selectedPosition)
            val targetPositions = currState.targetPositions ?: emptyList()
            
            for (position in targetPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
            }
            
            if (selectedPosition in targetPositions)
            {
                val affectedPositions = stackableMapItem.getAffectedPositions(selectedPosition)
                
                for (position in affectedPositions)
                {
                    Map.changeTile(MapLayer.select, position, Tiles.selectOrange)
                }
            }
            else
            {
                return State.free.stackableMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.chosenStackableMapItem = currState.chosenStackableMapItem
                    this.targetPositions = currState.targetPositions
                }
            }
        }
        
        Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
    }
    
    return currState
}

fun updateFreeStackableMapItemTargetSelectedTwice(currState : State.free.stackableMapItemTargetSelectedTwice) : State
{
    if (!Animating.isAnimating())
    {
        // in free state you can destroy corpses
        World.currentRoom?.removeKilledCharacters()
        World.currentRoom?.addToBeAddedEntitiesToRoom()
        World.currentRoom?.updateSpacesEntities()
        GameScreen.updateMapEntityLayer()
        
        val item = currState.chosenStackableMapItem
        item?.lowerAmountByOne()
        if (item != null && item.amount < 1)
        {
            World.hero.inventory.removeItem(item)
            ItemsStructure.fillItemsStructureWithItemsAndSkills()
            
            return State.free.heroSelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
            }
        }
        ItemsStructure.fillItemsStructureWithItemsAndSkills()
        
        val targetPositions = State.free.stackableMapItemChosen.targetPositions ?: emptyList()
        for (position in targetPositions)
        {
            Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
        }
        
        return State.free.stackableMapItemChosen.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
        }
    }
    
    return currState
}

fun updateFreeStackableSelfItemChosen(currState : State.free.stackableSelfItemChosen) : State
{
    return currState
}

fun updateFreeActiveSkillChosen(currState : State.free.activeSkillChosen) : State
{
    val activeSkill = currState.chosenActiveSkill
    
    if (activeSkill == null)
    {
        return currState
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        val targetPositions = currState.targetPositions ?: emptyList()
        if (selectedPosition in targetPositions)
        {
            val affectedPositions = activeSkill.getAffectedPositions(selectedPosition)
            
            for (position in affectedPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectOrange)
            }
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
            
            return State.free.activeSkillTargetSelectedOnce.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.chosenActiveSkill = activeSkill
                this.targetPositions = targetPositions
                this.selectedPosition.set(selectedPosition)
                this.effectPositions = affectedPositions
            }
        }
    }
    
    return currState
}

fun updateFreeActiveSkillTargetSelectedOnce(currState : State.free.activeSkillTargetSelectedOnce) : State
{
    val activeSkill = currState.chosenActiveSkill
    
    if (activeSkill == null)
    {
        return currState
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        Map.clearLayer(MapLayer.select)
        
        if (selectedPosition == currState.selectedPosition)
        {
            val worldCurrentRoom = World.currentRoom
            if (worldCurrentRoom != null)
            {
                activeSkill.use(worldCurrentRoom, selectedPosition)
            }
            
            return State.free.activeSkillTargetSelectedTwice.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.chosenActiveSkill = currState.chosenActiveSkill
            }
        }
        else
        {
            currState.selectedPosition.set(selectedPosition)
            val targetPositions = currState.targetPositions ?: emptyList()
            
            for (position in targetPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
            }
            
            if (selectedPosition in targetPositions)
            {
                val affectedPositions = activeSkill.getAffectedPositions(selectedPosition)
                
                for (position in affectedPositions)
                {
                    Map.changeTile(MapLayer.select, position, Tiles.selectOrange)
                }
            }
            else
            {
                return State.free.activeSkillChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.chosenActiveSkill = currState.chosenActiveSkill
                    this.targetPositions = currState.targetPositions
                }
            }
        }
        
        Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
    }
    
    return currState
}

fun updateFreeActiveSkillTargetSelectedTwice(currState : State.free.activeSkillTargetSelectedTwice) : State
{
    if (!Animating.isAnimating())
    {
        // in free state you can destroy corpses
        World.currentRoom?.removeKilledCharacters()
        World.currentRoom?.addToBeAddedEntitiesToRoom()
        World.currentRoom?.updateSpacesEntities()
        GameScreen.updateMapEntityLayer()
        
        val activeSkill = currState.chosenActiveSkill
        
        ItemsStructure.fillItemsStructureWithItemsAndSkills()
        
        return State.free.heroSelected.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
        }
    }
    
    return currState
}
