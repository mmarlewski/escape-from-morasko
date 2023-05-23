package com.efm.state

import com.efm.*
import com.efm.Map
import com.efm.assets.Tiles
import com.efm.entities.Hero
import com.efm.entity.Enemy
import com.efm.level.World
import com.efm.screens.GameScreen

fun updateFreeNoSelection(currState : State.free.noSelection) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (currState.isEnemyInRoom)
    {
        val newState = State.constrained.nothingSelected
        newState.isHeroAlive = true
        newState.isHeroDetected = false
        newState.areAnyActionPointsLeft = true
        return newState
    }
    
    if (GameScreen.isTouched)
    {
        val selectedPosition = GameScreen.roomTouchPosition
        val selectedSpace = World.currentRoom.getSpace(selectedPosition)
        val selectedEntity = selectedSpace?.getEntity()
        Map.clearLayer(MapLayer.outline)
        when (selectedEntity)
        {
            null    ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                return State.free.nothingSelected
            }
            is Hero ->
            {
                GameScreen.xButton.isVisible = true
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectGreen)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
                return State.free.heroSelected
            }
            else    ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
                val newState = State.free.entitySelected
                newState.selectedEntity = selectedEntity
                return newState
            }
        }
    }
    
    return currState
}

fun updateFreeNothingSelected(currState : State.free.nothingSelected) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (currState.isEnemyInRoom)
    {
        val newState = State.constrained.nothingSelected
        newState.isHeroAlive = true
        newState.isHeroDetected = false
        newState.areAnyActionPointsLeft = true
        return newState
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
            Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
            return State.free.heroSelected
        }
        is Enemy ->
        {
            Map.clearLayer(MapLayer.select)
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectRed)
            Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
            val newState = State.free.entitySelected
            newState.selectedEntity = selectedEntity
            return newState
        }
        else     ->
        {
            Map.clearLayer(MapLayer.select)
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
            Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
            val newState = State.free.entitySelected
            newState.selectedEntity = selectedEntity
            return newState
        }
    }
    
    return currState
}

fun updateFreeEntitySelected(currState : State.free.entitySelected) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (currState.isEnemyInRoom)
    {
        val newState = State.constrained.nothingSelected
        newState.isHeroAlive = true
        newState.isHeroDetected = false
        newState.areAnyActionPointsLeft = true
        return newState
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
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
                return State.free.heroSelected
            }
            is Enemy ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectRed)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
                val newState = State.free.entitySelected
                newState.selectedEntity = selectedEntity
                return newState
            }
            else     ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
                val newState = State.free.entitySelected
                newState.selectedEntity = selectedEntity
                return newState
            }
        }
    }
    
    return currState
}

fun updateFreeHeroSelected(currState : State.free.heroSelected) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (currState.isEnemyInRoom)
    {
        val newState = State.constrained.nothingSelected
        newState.isHeroAlive = true
        newState.isHeroDetected = false
        newState.areAnyActionPointsLeft = true
        return newState
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
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
            }
            else    ->
            {
                val pathSpaces = Pathfinding.findPathWithGivenRoom(World.hero.position, selectedPosition, World.currentRoom)
                if (pathSpaces != null)
                {
                    Map.clearLayer(MapLayer.select)
                    Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
                    for (space in pathSpaces)
                    {
                        Map.changeTile(MapLayer.select, space.position, Tiles.selectTeal)
                    }
                    Map.changeTile(MapLayer.select, GameScreen.roomTouchPosition, Tiles.selectYellow)
                    
                    val newState = State.free.moveSelectedOnce
                    newState.selectedPosition.set(selectedPosition)
                    newState.pathSpaces = pathSpaces
                    return newState
                }
            }
        }
    }
    
    return currState
}

fun updateFreeMoveSelectedOnce(currState : State.free.moveSelectedOnce) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (currState.isEnemyInRoom)
    {
        val newState = State.constrained.nothingSelected
        newState.isHeroAlive = true
        newState.isHeroDetected = false
        newState.areAnyActionPointsLeft = true
        return newState
    }
    
    if (GameScreen.isTouched)
    {
        Map.clearLayer(MapLayer.outline)
        val selectedPosition = GameScreen.roomTouchPosition
        val selectedSpace = World.currentRoom.getSpace(selectedPosition)
        val selectedEntity = selectedSpace?.getEntity()
        Map.clearLayer(MapLayer.outline)
        if (selectedPosition == currState.selectedPosition)
        {
            Map.clearLayer(MapLayer.select)
            moveHero(World.hero.position, currState.selectedPosition, currState.pathSpaces)
            val newState = State.free.moveSelectedTwice
            newState.entityOnPosition = selectedEntity
            newState.pathSpaces = currState.pathSpaces
            return newState
        }
        else if (selectedPosition != World.hero.position)
        {
            val pathSpaces = Pathfinding.findPathWithGivenRoom(World.hero.position, selectedPosition, World.currentRoom)
            if (pathSpaces != null)
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
                for (space in pathSpaces)
                {
                    Map.changeTile(MapLayer.select, space.position, Tiles.selectTeal)
                }
                Map.changeTile(MapLayer.select, GameScreen.roomTouchPosition, Tiles.selectYellow)
                
                val newState = State.free.moveSelectedOnce
                newState.selectedPosition.set(selectedPosition)
                newState.pathSpaces = pathSpaces
                return newState
            }
        }
    }
    
    return currState
}

fun updateFreeMoveSelectedTwice(currState : State.free.moveSelectedTwice) : State
{
    if (!Animating.isAnimating())
    {
        GameScreen.roomTouchPosition.set(World.hero.position)
        Map.changeTile(MapLayer.outline, World.hero.position, World.hero.getOutlineTile())
        return State.free.heroSelected
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
            val affectedPositions = multiUseMapItem.getAffectedPositions(selectedPosition) ?: emptyList()
            val newState = State.free.multiUseMapItemTargetSelectedOnce
            newState.chosenMultiUseItem = multiUseMapItem
            newState.targetPositions = targetPositions
            newState.selectedPosition.set(selectedPosition)
            newState.effectPositions = affectedPositions
            
            for (position in affectedPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectOrange)
            }
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
            
            return newState
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
            World.hero.spendAP(multiUseMapItem.baseAPUseCost)
            multiUseMapItem.durability -= multiUseMapItem.durabilityUseCost
            multiUseMapItem.use(World.currentRoom, selectedPosition)
            return State.free.multiUseMapItemTargetSelectedTwice
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
                val affectedPositions = multiUseMapItem.getAffectedPositions(selectedPosition) ?: emptyList()
                
                for (position in affectedPositions)
                {
                    Map.changeTile(MapLayer.select, position, Tiles.selectOrange)
                }
            }
            else
            {
                val newState = State.free.multiUseMapItemChosen
                return newState
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
        return State.free.multiUseMapItemChosen
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
            val affectedPositions = stackableMapItem.getAffectedPositions(selectedPosition) ?: emptyList()
            val newState = State.free.stackableMapItemTargetSelectedOnce
            newState.chosenStackableMapItem = stackableMapItem
            newState.targetPositions = targetPositions
            newState.selectedPosition.set(selectedPosition)
            newState.effectPositions = affectedPositions
            
            for (position in affectedPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectOrange)
            }
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
            
            return newState
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
            World.hero.spendAP(stackableMapItem.baseAPUseCost)
            stackableMapItem.amount -= 1
            stackableMapItem.use(World.currentRoom, selectedPosition)
            return State.free.stackableMapItemTargetSelectedTwice
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
                val affectedPositions = stackableMapItem.getAffectedPositions(selectedPosition) ?: emptyList()
                
                for (position in affectedPositions)
                {
                    Map.changeTile(MapLayer.select, position, Tiles.selectOrange)
                }
            }
            else
            {
                val newState = State.free.stackableMapItemChosen
                return newState
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
        return State.free.stackableMapItemChosen
    }
    
    return currState
}

fun updateStackableSelfItemChosen(currState : State.free.stackableSelfItemChosen) : State
{
    return currState
}
