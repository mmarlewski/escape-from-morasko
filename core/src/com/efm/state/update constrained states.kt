package com.efm.state

import com.efm.*
import com.efm.Map
import com.efm.assets.Tiles
import com.efm.entities.Hero
import com.efm.entity.Enemy
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

fun updateConstrainedNoSelection(currState : State.constrained.noSelection) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (!currState.areEnemiesInRoom)
    {
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
                
                return State.constrained.nothingSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
            is Hero  ->
            {
                GameScreen.xButton.isVisible = true
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectGreen)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
                
                return State.constrained.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
            is Enemy ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
                
                val detectionPositions = selectedEntity.getDetectionPositions()
                for (position in detectionPositions)
                {
                    Map.changeTile(MapLayer.select, position, Tiles.selectPurple)
                }
                
                return State.constrained.enemySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.selectedEnemy = selectedEntity
                }
            }
            else     ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
                
                return State.constrained.entitySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.selectedEntity = selectedEntity
                }
            }
        }
    }
    
    return currState
}

fun updateConstrainedNothingSelected(currState : State.constrained.nothingSelected) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (!currState.areEnemiesInRoom)
    {
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
            Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
            
            return State.constrained.heroSelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.isHeroDetected = currState.isHeroDetected
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
            }
        }
        is Enemy ->
        {
            Map.clearLayer(MapLayer.select)
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectRed)
            Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
            
            val detectionPositions = selectedEntity.getDetectionPositions()
            for (position in detectionPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectPurple)
            }
            
            return State.constrained.enemySelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.isHeroDetected = currState.isHeroDetected
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                this.selectedEnemy = selectedEntity
            }
        }
        else     ->
        {
            Map.clearLayer(MapLayer.select)
            Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
            Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
            
            return State.constrained.entitySelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.isHeroDetected = currState.isHeroDetected
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                this.selectedEntity = selectedEntity
            }
        }
    }
    
    return currState
}

fun updateConstrainedEntitySelected(currState : State.constrained.entitySelected) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (!currState.areEnemiesInRoom)
    {
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
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
                
                return State.constrained.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
            is Enemy ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectRed)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
                
                val detectionPositions = selectedEntity.getDetectionPositions()
                for (position in detectionPositions)
                {
                    Map.changeTile(MapLayer.select, position, Tiles.selectPurple)
                }
                
                return State.constrained.enemySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.selectedEnemy = selectedEntity
                }
            }
            else     ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
                
                return State.constrained.entitySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.selectedEntity = selectedEntity
                }
            }
        }
    }
    
    return currState
}

fun updateConstrainedEnemySelected(currState : State.constrained.enemySelected) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (!currState.areEnemiesInRoom)
    {
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
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
                
                return State.constrained.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
            is Enemy ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectRed)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
                
                val detectionPositions = selectedEntity.getDetectionPositions()
                for (position in detectionPositions)
                {
                    Map.changeTile(MapLayer.select, position, Tiles.selectPurple)
                }
                
                return State.constrained.enemySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.selectedEnemy = selectedEntity
                }
            }
            else     ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
                
                return State.constrained.entitySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.selectedEntity = selectedEntity
                }
            }
        }
    }
    
    return currState
}

fun updateConstrainedHeroSelected(currState : State.constrained.heroSelected) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (!currState.areEnemiesInRoom)
    {
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
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTile())
            }
            else    ->
            {
                val pathSpaces = Pathfinding.findPathWithGivenRoom(World.hero.position, selectedPosition, World.currentRoom)
                if (pathSpaces != null)
                {
                    val detectionPathPositions = mutableListOf<RoomPosition>()
                    for (enemy in World.currentRoom.getEnemies())
                    {
                        for (detectionPosition in enemy.getDetectionPositions())
                        {
                            for (pathSpace in pathSpaces)
                            {
                                if (detectionPosition == pathSpace.position)
                                {
                                    detectionPathPositions.add(detectionPosition)
                                }
                            }
                        }
                    }
                    
                    Map.clearLayer(MapLayer.select)
                    Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
                    for (space in pathSpaces)
                    {
                        Map.changeTile(MapLayer.select, space.position, Tiles.selectTeal)
                    }
                    for (position in detectionPathPositions)
                    {
                        Map.changeTile(MapLayer.select, position, Tiles.selectPurple)
                    }
                    Map.changeTile(MapLayer.select, GameScreen.roomTouchPosition, Tiles.selectYellow)
                    
                    return State.constrained.moveSelectedOnce.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.isHeroDetected = currState.isHeroDetected
                        this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                        this.selectedPosition.set(selectedPosition)
                        this.pathSpaces = pathSpaces
                    }
                }
            }
        }
    }
    
    return currState
}

fun updateConstrainedMoveSelectedOnce(currState : State.constrained.moveSelectedOnce) : State
{
    if (!currState.isHeroAlive)
    {
        return State.over
    }
    
    if (!currState.areEnemiesInRoom)
    {
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
            
            var isHeroMovingThroughDetectionPosition = false
            enemyFor@ for (enemy in World.currentRoom.getEnemies())
            {
                for (detectionPosition in enemy.getDetectionPositions())
                {
                    for (pathSpace in currState.pathSpaces)
                    {
                        if (detectionPosition == pathSpace.position)
                        {
                            isHeroMovingThroughDetectionPosition = true
                            break@enemyFor
                        }
                    }
                }
            }
            
            return State.constrained.moveSelectedTwice.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.isHeroDetected = currState.isHeroDetected
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                this.entityOnPosition = selectedEntity
                this.pathSpaces = currState.pathSpaces
                this.isHeroMovingThroughDetectionPosition = isHeroMovingThroughDetectionPosition
            }
        }
        else if (selectedPosition != World.hero.position)
        {
            val pathSpaces = Pathfinding.findPathWithGivenRoom(World.hero.position, selectedPosition, World.currentRoom)
            if (pathSpaces != null)
            {
                val detectionPathPositions = mutableListOf<RoomPosition>()
                for (enemy in World.currentRoom.getEnemies())
                {
                    for (detectionPosition in enemy.getDetectionPositions())
                    {
                        for (pathSpace in pathSpaces)
                        {
                            if (detectionPosition == pathSpace.position)
                            {
                                detectionPathPositions.add(detectionPosition)
                            }
                        }
                    }
                }
                
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
                for (space in pathSpaces)
                {
                    Map.changeTile(MapLayer.select, space.position, Tiles.selectTeal)
                }
                for (position in detectionPathPositions)
                {
                    Map.changeTile(MapLayer.select, position, Tiles.selectPurple)
                }
                Map.changeTile(MapLayer.select, GameScreen.roomTouchPosition, Tiles.selectYellow)
                
                return State.constrained.moveSelectedOnce.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.selectedPosition.set(selectedPosition)
                    this.pathSpaces = pathSpaces
                }
            }
        }
    }
    
    return currState
}

fun updateConstrainedMoveSelectedTwice(currState : State.constrained.moveSelectedTwice) : State
{
    if (!Animating.isAnimating())
    {
        val areEnemiesInRoom = World.currentRoom.areEnemiesInRoom()
        return when (areEnemiesInRoom)
        {
            false -> State.free.heroSelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = areEnemiesInRoom
            }
            true  ->
            {
                if (currState.isHeroMovingThroughDetectionPosition)
                {
                    return State.combat.hero.heroSelected.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.areAnyActionPointsLeft = true
                    }
                }
                else
                {
                    GameScreen.roomTouchPosition.set(World.hero.position)
                    Map.changeTile(MapLayer.outline, World.hero.position, World.hero.getOutlineTile())
                    
                    return State.constrained.heroSelected.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.isHeroDetected = currState.isHeroDetected
                        this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    }
                }
            }
        }
    }
    
    return currState
}

fun updateConstrainedMoveSelectedTwiceToLevelExitWaiting(currState : State.constrained.moveSelectedTwiceToLevelExit.waiting) : State
{
    return currState
}

fun updateConstrainedMoveSelectedTwiceToLevelExitConfirmed(currState : State.constrained.moveSelectedTwiceToLevelExit.confirmed) : State
{
    return currState
}

fun updateConstrainedMoveSelectedTwiceToLevelExitCancelled(currState : State.constrained.moveSelectedTwiceToLevelExit.cancelled) : State
{
    return currState
}

fun updateConstrainedMultiUseMapItemChosen(currState : State.constrained.multiUseMapItemChosen) : State
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
            
            return State.constrained.multiUseMapItemTargetSelectedOnce.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.isHeroDetected = currState.isHeroDetected
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

fun updateConstrainedMultiUseMapItemTargetSelectedOnce(currState : State.constrained.multiUseMapItemTargetSelectedOnce) : State
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
            
            return State.constrained.multiUseMapItemTargetSelectedTwice.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.isHeroDetected = currState.isHeroDetected
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
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
                return State.constrained.multiUseMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
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

fun updateConstrainedMultiUseMapItemTargetSelectedTwice(currState : State.constrained.multiUseMapItemTargetSelectedTwice) : State
{
    if (!Animating.isAnimating())
    {
        val targetPositions = State.constrained.multiUseMapItemChosen.targetPositions ?: emptyList()
        for (position in targetPositions)
        {
            Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
        }
        
        return State.constrained.multiUseMapItemChosen.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
            this.isHeroDetected = currState.isHeroDetected
            this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
        }
    }
    
    return currState
}

fun updateConstrainedStackableMapItemChosen(currState : State.constrained.stackableMapItemChosen) : State
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
            
            return State.constrained.stackableMapItemTargetSelectedOnce.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.isHeroDetected = currState.isHeroDetected
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

fun updateConstrainedStackableMapItemTargetSelectedOnce(currState : State.constrained.stackableMapItemTargetSelectedOnce) : State
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
            
            return State.constrained.stackableMapItemTargetSelectedTwice.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.isHeroDetected = currState.isHeroDetected
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
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
                return State.constrained.stackableMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
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

fun updateConstrainedStackableMapItemTargetSelectedTwice(currState : State.constrained.stackableMapItemTargetSelectedTwice) : State
{
    if (!Animating.isAnimating())
    {
        val targetPositions = State.constrained.stackableMapItemChosen.targetPositions ?: emptyList()
        for (position in targetPositions)
        {
            Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
        }
        
        return State.constrained.stackableMapItemChosen.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
            this.isHeroDetected = currState.isHeroDetected
            this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
        }
    }
    
    return currState
}

fun updateConstrainedStackableSelfItemChosen(currState : State.constrained.stackableSelfItemChosen) : State
{
    return currState
}

fun updateConstrainedTurnEnded(currState : State.constrained.turnEnded) : State
{
    return currState
}

fun updateConstrainedTurnEndedWithActionPointsLeftWaiting(currState : State.constrained.turnEndedWithActionPointsLeft.waiting) : State
{
    return currState
}

fun updateConstrainedTurnEndedWithActionPointsLeftConfirmed(currState : State.constrained.turnEndedWithActionPointsLeft.confirmed) : State
{
    return currState
}

fun updateConstrainedTurnEndedWithActionPointsLeftCancelled(currState : State.constrained.turnEndedWithActionPointsLeft.cancelled) : State
{
    return currState
}
