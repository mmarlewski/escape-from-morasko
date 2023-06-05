package com.efm.state

import com.efm.*
import com.efm.Map
import com.efm.assets.Tiles
import com.efm.entities.Hero
import com.efm.entity.Enemy
import com.efm.entity.Interactive
import com.efm.level.World
import com.efm.passage.Exit
import com.efm.screens.GameScreen

fun updateCombatHeroNoSelection(currState : State.combat.hero.noSelection) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (!currState.areEnemiesInRoom)
    {
        GameScreen.abilityBar.isVisible = false
        GameScreen.abilityBarLabel.isVisible = false
        
        return State.free.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
        }
    }
    
    if (World.hero.abilityPoints <= 0)
    {
        Map.clearLayer(MapLayer.select)
        
        val animation = Animation.showTileWithCameraFocus(null, World.hero.position.copy(), 1f)
        Animating.executeAnimations(mutableListOf(animation))
        
        return State.combat.enemies.enemyUnselected.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
            this.enemies = World.currentRoom.getEnemies()
            this.enemyIterator = this.enemies?.iterator()
            this.currEnemy = when (val enemyIterator = this.enemyIterator)
            {
                null -> null
                else -> when (enemyIterator.hasNext())
                {
                    true  -> enemyIterator.next()
                    false -> null
                }
            }
        }
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        val selectedSpace = World.currentRoom.getSpace(selectedPosition)
        val selectedEntity = selectedSpace?.getEntity()
        
        Map.clearLayer(MapLayer.outline)
        
        when (selectedEntity)
        {
            null     ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                
                return State.combat.hero.nothingSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
            is Hero  ->
            {
                GameScreen.xButton.isVisible = true
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectGreen)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineGreenTile())

//                highlighting all possible move positions, looks bad

//                val heroPosition = World.hero.position
//                val heroAbilityPoints = World.hero.abilityPoints
//                val possibleMovePositions = mutableListOf<RoomPosition>()
//                for (i in 0..heroAbilityPoints)
//                {
//                    val squarePerimeterPositions = getSquarePerimeterPositions(heroPosition, i)
//                    for (position in squarePerimeterPositions)
//                    {
//                        val path = Pathfinding.findPathWithGivenRoom(heroPosition, position, World.currentRoom)
//                        if (path != null)
//                        {
//                            possibleMovePositions.add(position)
//                        }
//                    }
//                }
//                for (position in possibleMovePositions)
//                {
//                    Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
//                }
                
                return State.combat.hero.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
            is Enemy ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
                
                return State.combat.hero.enemySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.selectedEnemy = selectedEntity
                }
            }
            else     ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
                
                return State.combat.hero.entitySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.selectedEntity = selectedEntity
                }
            }
        }
    }
    
    return currState
}

fun updateCombatHeroNothingSelected(currState : State.combat.hero.nothingSelected) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (!currState.areEnemiesInRoom)
    {
        GameScreen.abilityBar.isVisible = false
        GameScreen.abilityBarLabel.isVisible = false
        
        return State.free.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
        }
    }
    
    val selectedPosition = GameScreen.roomTouchPosition
    val selectedSpace = World.currentRoom.getSpace(selectedPosition)
    val selectedEntity = selectedSpace?.getEntity()
    
    Map.clearLayer(MapLayer.outline)
    
    when (selectedEntity)
    {
        null     ->
        {
            Map.clearLayer(MapLayer.select)
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
        }
        is Hero  ->
        {
            GameScreen.xButton.isVisible = true
            Map.clearLayer(MapLayer.select)
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectGreen)
            Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineGreenTile())
            
            return State.combat.hero.heroSelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
            }
        }
        is Enemy ->
        {
            Map.clearLayer(MapLayer.select)
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
            Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
            
            return State.combat.hero.enemySelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                this.selectedEnemy = selectedEntity
            }
        }
        else     ->
        {
            Map.clearLayer(MapLayer.select)
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
            Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
            
            return State.combat.hero.entitySelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                this.selectedEntity = selectedEntity
            }
        }
    }
    
    return currState
}

fun updateCombatHeroEntitySelected(currState : State.combat.hero.entitySelected) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (!currState.areEnemiesInRoom)
    {
        GameScreen.abilityBar.isVisible = false
        GameScreen.abilityBarLabel.isVisible = false
        
        return State.free.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
        }
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        val selectedSpace = World.currentRoom.getSpace(selectedPosition)
        val selectedEntity = selectedSpace?.getEntity()
        
        Map.clearLayer(MapLayer.outline)
        
        when (selectedEntity)
        {
            null     ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
            }
            is Hero  ->
            {
                GameScreen.xButton.isVisible = true
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectGreen)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineGreenTile())
                
                return State.combat.hero.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
            is Enemy ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
                
                return State.combat.hero.enemySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.selectedEnemy = selectedEntity
                }
            }
            else     ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
                
                return State.combat.hero.entitySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.selectedEntity = selectedEntity
                }
            }
        }
    }
    
    return currState
}

fun updateCombatHeroEnemySelected(currState : State.combat.hero.enemySelected) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (!currState.areEnemiesInRoom)
    {
        GameScreen.abilityBar.isVisible = false
        GameScreen.abilityBarLabel.isVisible = false
        
        return State.free.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
        }
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        val selectedSpace = World.currentRoom.getSpace(selectedPosition)
        val selectedEntity = selectedSpace?.getEntity()
        
        Map.clearLayer(MapLayer.outline)
        
        when (selectedEntity)
        {
            null     ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
            }
            is Hero  ->
            {
                GameScreen.xButton.isVisible = true
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectGreen)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineGreenTile())
                
                return State.combat.hero.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
            is Enemy ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
                
                return State.combat.hero.enemySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.selectedEnemy = selectedEntity
                }
            }
            else     ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
                
                return State.combat.hero.entitySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.selectedEntity = selectedEntity
                }
            }
        }
    }
    
    return currState
}

fun updateCombatHeroHeroSelected(currState : State.combat.hero.heroSelected) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (!currState.areEnemiesInRoom)
    {
        GameScreen.abilityBar.isVisible = false
        GameScreen.abilityBarLabel.isVisible = false
        
        Map.clearLayer(MapLayer.select)
        Map.clearLayer(MapLayer.outline)
        
        return State.free.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
        }
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        val selectedSpace = World.currentRoom.getSpace(selectedPosition)
        val selectedEntity = selectedSpace?.getEntity()
        
        Map.clearLayer(MapLayer.outline)
        
        when (selectedEntity)
        {
            is Hero ->
            {
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineGreenTile())
            }
            else    ->
            {
                val pathSpaces = Pathfinding.findPathWithGivenRoom(World.hero.position, selectedPosition, World.currentRoom)
                if (pathSpaces != null && pathSpaces.size + 1 <= World.hero.abilityPoints)
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
                        is Exit -> selectedEntity.isPassageToAnotherLevel()
                        else    -> false
                    }
                    
                    return State.combat.hero.moveSelectedOnce.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
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

fun updateCombatHeroMoveSelectedOnce(currState : State.combat.hero.moveSelectedOnce) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (!currState.areEnemiesInRoom)
    {
        GameScreen.abilityBar.isVisible = false
        GameScreen.abilityBarLabel.isVisible = false
        
        return State.free.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
        }
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        val selectedSpace = World.currentRoom.getSpace(selectedPosition)
        val selectedEntity = selectedSpace?.getEntity()
        
        Map.clearLayer(MapLayer.outline)
        
        if (selectedPosition == currState.selectedPosition)
        {
            Map.clearLayer(MapLayer.select)
            moveHero(World.hero.position, currState.selectedPosition, currState.pathSpaces)
            
            return State.combat.hero.moveSelectedTwice.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                this.entityOnPosition = selectedEntity
                this.pathSpaces = currState.pathSpaces
                this.isMoveToAnotherRoom = currState.isMoveToAnotherRoom
                this.isMoveToAnotherLevel = currState.isMoveToAnotherLevel
            }
        }
        else if (selectedPosition != World.hero.position)
        {
            val pathSpaces = Pathfinding.findPathWithGivenRoom(World.hero.position, selectedPosition, World.currentRoom)
            if (pathSpaces != null && pathSpaces.size + 1 <= World.hero.abilityPoints)
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
                    is Exit -> selectedEntity.isPassageToAnotherLevel()
                    else    -> false
                }
                
                return State.combat.hero.moveSelectedOnce.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
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

fun updateCombatHeroMoveSelectedTwice(currState : State.combat.hero.moveSelectedTwice) : State
{
    if (!Animating.isAnimating())
    {
        // interact with Interactive Entity if it was selected in ConstrainedMoveSelectedOnce
        val entityOnPositionHeroWalkedTowards = currState.entityOnPosition
        if (entityOnPositionHeroWalkedTowards is Interactive) entityOnPositionHeroWalkedTowards.interact()
        
        World.hero.spendAP(currState.pathSpaces.size + 1)
        
        val isMoveToAnotherRoom = currState.isMoveToAnotherRoom
        val isMoveToAnotherLevel = currState.isMoveToAnotherLevel
        val areEnemiesInRoom = World.currentRoom.areEnemiesInRoom()
        
        if (isMoveToAnotherRoom)
        {
            when (areEnemiesInRoom)
            {
                true  ->
                {
                    World.hero.regainAP()
                    GameScreen.abilityBar.isVisible = true
                    GameScreen.abilityBarLabel.isVisible = true
                }
                false ->
                {
                    GameScreen.abilityBar.isVisible = false
                    GameScreen.abilityBarLabel.isVisible = false
                }
            }
        }
        
        when (areEnemiesInRoom)
        {
            false ->
            {
                GameScreen.abilityBar.isVisible = false
                GameScreen.abilityBarLabel.isVisible = false
                
                return State.free.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = areEnemiesInRoom
                }
            }
            true  ->
            {
                GameScreen.roomTouchPosition.set(World.hero.position)
                Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
                Map.changeTile(MapLayer.outline, World.hero.position, World.hero.getOutlineGreenTile())
                
                return State.combat.hero.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
        }
    }
    
    return currState
}

fun updateCombatHeroMoveSelectedTwiceToLevelExitWaiting(currState : State.combat.hero.moveSelectedTwiceToLevelExit.waiting) : State
{
    return currState
}

fun updateCombatHeroMoveSelectedTwiceToLevelExitConfirmed(currState : State.combat.hero.moveSelectedTwiceToLevelExit.confirmed) : State
{
    return currState
}

fun updateCombatHeroMoveSelectedTwiceToLevelExitCancelled(currState : State.combat.hero.moveSelectedTwiceToLevelExit.cancelled) : State
{
    return currState
}

fun updateCombatHeroMultiUseMapItemChosen(currState : State.combat.hero.multiUseMapItemChosen) : State
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
            
            return State.combat.hero.multiUseMapItemTargetSelectedOnce.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                this.chosenMultiUseItem = multiUseMapItem
                this.targetPositions = targetPositions
                this.selectedPosition.set(selectedPosition)
                this.effectPositions = affectedPositions
            }
        }
    }
    
    return currState
}

fun updateCombatHeroMultiUseMapItemTargetSelectedOnce(currState : State.combat.hero.multiUseMapItemTargetSelectedOnce) : State
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
            multiUseMapItem.use(World.currentRoom, selectedPosition)
            
            return State.combat.hero.multiUseMapItemTargetSelectedTwice.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
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
                return State.combat.hero.multiUseMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenMultiUseItem = currState.chosenMultiUseItem
                    this.targetPositions = currState.targetPositions
                }
            }
        }
        
        Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
    }
    
    return currState
}

fun updateCombatHeroMultiUseMapItemTargetSelectedTwice(currState : State.combat.hero.multiUseMapItemTargetSelectedTwice) : State
{
    if (!Animating.isAnimating())
    {
        World.hero.spendAP(currState.chosenMultiUseItem?.baseAPUseCost ?: 0)
        currState.chosenMultiUseItem?.lowerDurability()
        
        World.currentRoom.removeKilledCharacters()
        World.currentRoom.updateSpacesEntities()
        GameScreen.updateMapEntityLayer()
        
        currState.areEnemiesInRoom = World.currentRoom.getEnemies().isNotEmpty()
        
        val canBeUsedAgain = when (val chosenItem = currState.chosenMultiUseItem)
        {
            null -> false
            else -> World.hero.abilityPoints >= chosenItem.baseAPUseCost
        }
        
        if (canBeUsedAgain)
        {
            val targetPositions = State.combat.hero.multiUseMapItemChosen.targetPositions ?: emptyList()
            for (position in targetPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
            }
            
            return State.combat.hero.multiUseMapItemChosen.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
            }
        }
        else
        {
            Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
            Map.changeTile(MapLayer.outline, World.hero.position, Tiles.heroOutlineGreen)
            
            return State.combat.hero.heroSelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
            }
        }
    }
    
    return currState
}

fun updateCombatHeroStackableMapItemChosen(currState : State.combat.hero.stackableMapItemChosen) : State
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
            
            return State.combat.hero.stackableMapItemTargetSelectedOnce.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                this.chosenStackableMapItem = stackableMapItem
                this.targetPositions = targetPositions
                this.selectedPosition.set(selectedPosition)
                this.effectPositions = affectedPositions
            }
        }
    }
    
    return currState
}

fun updateCombatHeroStackableMapItemTargetSelectedOnce(currState : State.combat.hero.stackableMapItemTargetSelectedOnce) : State
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
            stackableMapItem.use(World.currentRoom, selectedPosition)
            
            return State.combat.hero.stackableMapItemTargetSelectedTwice.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
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
                return State.combat.hero.stackableMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenStackableMapItem = currState.chosenStackableMapItem
                    this.targetPositions = currState.targetPositions
                }
            }
        }
        
        Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
    }
    
    return currState
}

fun updateCombatHeroStackableMapItemTargetSelectedTwice(currState : State.combat.hero.stackableMapItemTargetSelectedTwice) : State
{
    if (!Animating.isAnimating())
    {
        World.hero.spendAP(currState.chosenStackableMapItem?.baseAPUseCost ?: 0)
        currState.chosenStackableMapItem?.lowerAmountByOne()
        
        World.currentRoom.removeKilledCharacters()
        World.currentRoom.updateSpacesEntities()
        GameScreen.updateMapEntityLayer()
    
        currState.areEnemiesInRoom = World.currentRoom.getEnemies().isNotEmpty()
        
        val canBeUsedAgain = when (val chosenItem = currState.chosenStackableMapItem)
        {
            null -> false
            else -> World.hero.abilityPoints >= chosenItem.baseAPUseCost
        }
        
        if (canBeUsedAgain)
        {
            val targetPositions = State.combat.hero.stackableMapItemChosen.targetPositions ?: emptyList()
            for (position in targetPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
            }
            
            return State.combat.hero.stackableMapItemChosen.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
            }
        }
        else
        {
            Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
            Map.changeTile(MapLayer.outline, World.hero.position, Tiles.heroOutlineGreen)
            
            return State.combat.hero.heroSelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
            }
        }
    }
    
    return currState
}

fun updateCombatHeroStackableSelfItemChosen(currState : State.combat.hero.stackableSelfItemChosen) : State
{
    return currState
}

fun updateCombatHeroTurnEnded(currState : State.combat.hero.turnEnded) : State
{
    return currState
}

fun updateCombatHeroTurnEndedWithActionPointsLeftWaiting(currState : State.combat.hero.turnEndedWithActionPointsLeft.waiting) : State
{
    return currState
}

fun updateCombatHeroTurnEndedWithActionPointsLeftConfirmed(currState : State.combat.hero.turnEndedWithActionPointsLeft.confirmed) : State
{
    return currState
}

fun updateCombatHeroTurnEndedWithActionPointsLeftCancelled(currState : State.combat.hero.turnEndedWithActionPointsLeft.cancelled) : State
{
    return currState
}