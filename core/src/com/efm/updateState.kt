package com.efm

import com.efm.assets.Tiles
import com.efm.entities.Hero
import com.efm.level.World
import com.efm.screens.*

fun updateState()
{
    val newState = when (val currState = getState())
    {
        is State.free.noSelection                            -> updateFreeNoSelection(currState)
        is State.free.nothingSelected                        -> updateFreeNothingSelected(currState)
        is State.free.entitySelected                         -> updateFreeEntitySelected(currState)
        is State.free.heroSelected                           -> updateFreeHeroSelected(currState)
        is State.free.moveSelectedOnce                       -> updateFreeMoveSelectedOnce(currState)
        is State.free.moveSelectedTwice                      -> updateFreeMoveSelectedTwice(currState)
        is State.free.moveSelectedTwiceToLevelExit.waiting   -> updateFreeMoveSelectedTwiceToLevelExitWaiting(currState)
        is State.free.moveSelectedTwiceToLevelExit.confirmed -> updateFreeMoveSelectedTwiceToLevelExitConfirmed(currState)
        is State.free.moveSelectedTwiceToLevelExit.cancelled -> updateFreeMoveSelectedTwiceToLevelExitCancelled(currState)
        else                                                 -> State.over
    }
    changeState(newState)
}

// free

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
                return State.free.heroSelected
            }
            else    ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
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
    when (selectedEntity)
    {
        null    ->
        {
            Map.clearLayer(MapLayer.select)
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
        }
        is Hero ->
        {
            GameScreen.xButton.isVisible = true
            Map.clearLayer(MapLayer.select)
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectGreen)
            return State.free.heroSelected
        }
        else    ->
        {
            Map.clearLayer(MapLayer.select)
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
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
        when (selectedEntity)
        {
            null    ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
            }
            is Hero ->
            {
                GameScreen.xButton.isVisible = true
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectGreen)
                return State.free.heroSelected
            }
            else    ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
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
        when (selectedEntity)
        {
            is Hero ->
            {
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
        val selectedPosition = GameScreen.roomTouchPosition
        val selectedSpace = World.currentRoom.getSpace(selectedPosition)
        val selectedEntity = selectedSpace?.getEntity()
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
    if (!Animating.isAnimating)
    {
        GameScreen.roomTouchPosition.set(World.hero.position)
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
