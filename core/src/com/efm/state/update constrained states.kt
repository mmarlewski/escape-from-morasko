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
import com.efm.room.RoomPosition
import com.efm.room.Space
import com.efm.screens.GameScreen
import com.efm.ui.gameScreen.*

fun updateConstrainedNoSelection(currState : State.constrained.noSelection) : State
{
    if (!currState.areEnemiesInRoom)
    {
        ProgressBars.abilityBar.isVisible = false
        ProgressBars.abilityBarForFlashing.isVisible = false
        ProgressBars.abilityBarLabel.isVisible = false
        
        World.hero.removeCoolDownFromAllActiveSkills()
        ItemsStructure.fillItemsStructureWithItemsAndSkills()
        
        return State.free.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
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
            
            is Hero       ->
            {
                RightStructure.moveButtonVisibility(true)
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
    
                return State.constrained.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
    
            is EnemyMimic ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(
                        MapLayer.outline,
                        selectedPosition,
                        selectedEntity.getOutlineTealTile()
                              )
        
                return State.constrained.entitySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.selectedEntity = selectedEntity
                }
            }
    
            is Enemy      ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
        
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
                if (selectedEntity is Interactive) Map.changeTile(
                        MapLayer.outline,
                        selectedPosition,
                        selectedEntity.getOutlineTealTile()
                                                                 )
                else Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
    
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
    if (!currState.areEnemiesInRoom)
    {
        ProgressBars.abilityBar.isVisible = false
        ProgressBars.abilityBarForFlashing.isVisible = false
        ProgressBars.abilityBarLabel.isVisible = false
    
        World.hero.removeCoolDownFromAllActiveSkills()
        ItemsStructure.fillItemsStructureWithItemsAndSkills()
    
        return State.free.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
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
            null          ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
            }
            
            is Hero       ->
            {
                RightStructure.moveButtonVisibility(true)
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectGreen)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineGreenTile())
    
                return State.constrained.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
    
            is EnemyMimic ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(
                        MapLayer.outline,
                        selectedPosition,
                        selectedEntity.getOutlineTealTile()
                              )
        
                return State.constrained.entitySelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.selectedEntity = selectedEntity
                }
            }
    
            is Enemy      ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectRed)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
        
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
                if (selectedEntity is Interactive) Map.changeTile(
                        MapLayer.outline,
                        selectedPosition,
                        selectedEntity.getOutlineTealTile()
                                                                 )
                else Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
                
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

fun updateConstrainedEntitySelected(currState : State.constrained.entitySelected) : State
{
    if (!currState.areEnemiesInRoom)
    {
        ProgressBars.abilityBar.isVisible = false
        ProgressBars.abilityBarForFlashing.isVisible = false
        ProgressBars.abilityBarLabel.isVisible = false
        
        World.hero.removeCoolDownFromAllActiveSkills()
        ItemsStructure.fillItemsStructureWithItemsAndSkills()
        
        return State.free.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
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
            null          ->
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
            
            is Hero       ->
            {
                RightStructure.moveButtonVisibility(true)
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectGreen)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineGreenTile())
    
                return State.constrained.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
    
            is EnemyMimic ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineTealTile())
        
                if (selectedEntity == currState.selectedEntity && World.hero.position.isAdjacentTo(selectedEntity.position))
                {
                    selectedEntity.interact()
                    Map.clearLayer(MapLayer.select)
                    Map.clearLayer(MapLayer.outline)
                    return State.combat.hero.heroSelected.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = true
                        this.areAnyActionPointsLeft = true
                    }
                }
                Map.clearLayer(MapLayer.select)
                Map.clearLayer(MapLayer.outline)
                return State.constrained.nothingSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
    
            is Enemy      ->
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectRed)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
        
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
                    return State.constrained.nothingSelected.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.isHeroDetected = currState.isHeroDetected
                        this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    }
                }
                else Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
        
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
    if (!currState.areEnemiesInRoom)
    {
        ProgressBars.abilityBar.isVisible = false
        ProgressBars.abilityBarForFlashing.isVisible = false
        ProgressBars.abilityBarLabel.isVisible = false
        
        World.hero.removeCoolDownFromAllActiveSkills()
        ItemsStructure.fillItemsStructureWithItemsAndSkills()
        
        return State.free.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
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
                RightStructure.moveButtonVisibility(true)
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectGreen)
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineGreenTile())
                
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
                Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
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
                if (selectedEntity is Interactive) Map.changeTile(
                        MapLayer.outline,
                        selectedPosition,
                        selectedEntity.getOutlineTealTile()
                                                                 )
                else Map.changeTile(MapLayer.outline, selectedPosition, selectedEntity.getOutlineYellowTile())
    
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
    if (!currState.areEnemiesInRoom)
    {
        ProgressBars.abilityBar.isVisible = false
        ProgressBars.abilityBarForFlashing.isVisible = false
        ProgressBars.abilityBarLabel.isVisible = false
        
        World.hero.removeCoolDownFromAllActiveSkills()
        ItemsStructure.fillItemsStructureWithItemsAndSkills()
        
        return State.free.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
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
    
                return State.constrained.nothingSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
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
                if (pathSpaces != null && World.hero.abilityPoints > 0)
                {
                    val canReach = (pathSpaces.size + 1 <= World.hero.abilityPoints)
                    val possiblePathSpaces = pathSpaces.take(World.hero.abilityPoints)
                    
                    val detectionPathPositions = mutableListOf<RoomPosition>()
                    ProgressBars.abilityBarForFlashing.value = ProgressBars.abilityBar.value - (pathSpaces.size + 1)
                    for (enemy in World.currentRoom?.getEnemies() ?: listOf())
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
                    if (selectedEntity is Enemy)
                    {
                        val detectionPositions = selectedEntity.getDetectionPositions()
                        for (position in detectionPositions)
                        {
                            Map.changeTile(MapLayer.select, position, Tiles.selectPurple)
                        }
                    }
                    for (space in pathSpaces)
                    {
                        Map.changeTile(MapLayer.select, space.position, Tiles.selectRed)
                    }
                    for (space in possiblePathSpaces)
                    {
                        Map.changeTile(MapLayer.select, space.position, Tiles.selectTeal)
                    }
                    for (position in detectionPathPositions)
                    {
                        Map.changeTile(MapLayer.select, position, Tiles.selectPurple)
                    }
                    Map.changeTile(MapLayer.select, GameScreen.roomTouchPosition, Tiles.selectYellow)
                    
                    if (canReach)
                    {
                        if (selectedEntity is Interactive)
                        {
                            Map.changeTile(
                                    MapLayer.outline,
                                    GameScreen.roomTouchPosition,
                                    selectedEntity.getOutlineTealTile()
                                          )
                        }
                        val isMoveToAnotherRoom = (selectedEntity is Exit)
                        val isMoveToAnotherLevel = when (selectedEntity)
                        {
                            is Exit -> selectedEntity is LevelExit
                            else    -> false
                        }
                        
                        return State.constrained.moveSelectedOnce.apply {
                            this.isHeroAlive = currState.isHeroAlive
                            this.areEnemiesInRoom = currState.areEnemiesInRoom
                            this.isHeroDetected = currState.isHeroDetected
                            this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                            this.selectedPosition.set(selectedPosition)
                            this.pathSpaces = pathSpaces
                            this.isMoveToAnotherRoom = isMoveToAnotherRoom
                            this.isMoveToAnotherLevel = isMoveToAnotherLevel
                        }
                    }
                    else
                    {
                        return State.constrained.moveSelectedOnce.apply {
                            this.isHeroAlive = currState.isHeroAlive
                            this.areEnemiesInRoom = currState.areEnemiesInRoom
                            this.isHeroDetected = currState.isHeroDetected
                            this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                            this.selectedPosition.set(selectedPosition)
                            this.pathSpaces = possiblePathSpaces
                            this.isMoveToAnotherRoom = false
                            this.isMoveToAnotherLevel = false
                        }
                    }
                }
            }
        }
    }
    
    return currState
}

fun updateConstrainedMoveSelectedOnce(currState : State.constrained.moveSelectedOnce) : State
{
    if (!currState.areEnemiesInRoom)
    {
        ProgressBars.abilityBar.isVisible = false
        ProgressBars.abilityBarForFlashing.isVisible = false
        ProgressBars.abilityBarLabel.isVisible = false
        
        World.hero.removeCoolDownFromAllActiveSkills()
        ItemsStructure.fillItemsStructureWithItemsAndSkills()
        
        return State.free.noSelection.apply {
            this.isHeroAlive = currState.isHeroAlive
            this.areEnemiesInRoom = currState.areEnemiesInRoom
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
            
            val canReach = (currState.pathSpaces.size + 1 <= World.hero.abilityPoints)
            val movePosition = if (canReach) selectedPosition else currState.pathSpaces.last().position
            
            moveHero(World.hero.position.copy(), movePosition.copy(), currState.pathSpaces)
            for (enemy in World.currentRoom?.getEnemies() ?: listOf())
            {
                enemy.healthStack.isVisible = false
            }
            var isHeroMovingThroughDetectionPosition = false
            val pathsSpacesPlusSelectedSpace = mutableListOf<Space>()
            pathsSpacesPlusSelectedSpace.addAll(currState.pathSpaces)
            if (selectedSpace != null) pathsSpacesPlusSelectedSpace.add(selectedSpace)
            enemyFor@ for (enemy in World.currentRoom?.getEnemies() ?: listOf())
            {
                for (detectionPosition in enemy.getDetectionPositions())
                {
                    for (pathSpace in pathsSpacesPlusSelectedSpace)
                    {
                        if (detectionPosition == pathSpace.position)
                        {
                            isHeroMovingThroughDetectionPosition = true
                            break@enemyFor
                        }
                    }
                }
            }
            if (!World.hero.isVisible)
            {
                isHeroMovingThroughDetectionPosition = false
            }
            
            return State.constrained.moveSelectedTwice.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.isHeroDetected = currState.isHeroDetected
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                this.entityOnPosition = if (canReach) selectedEntity else null
                this.pathSpaces = currState.pathSpaces
                this.isHeroMovingThroughDetectionPosition = isHeroMovingThroughDetectionPosition
                this.isMoveToAnotherRoom = currState.isMoveToAnotherRoom
                this.isMoveToAnotherLevel = currState.isMoveToAnotherLevel
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
            if (pathSpaces != null && World.hero.abilityPoints > 0)
            {
                val canReach = (pathSpaces.size + 1 <= World.hero.abilityPoints)
                val possiblePathSpaces = pathSpaces.take(World.hero.abilityPoints)
                
                val detectionPathPositions = mutableListOf<RoomPosition>()
                ProgressBars.abilityBarForFlashing.value = ProgressBars.abilityBar.value - (pathSpaces.size + 1)
                for (enemy in World.currentRoom?.getEnemies() ?: listOf())
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
                if (selectedEntity is Enemy)
                {
                    val detectionPositions = selectedEntity.getDetectionPositions()
                    for (position in detectionPositions)
                    {
                        Map.changeTile(MapLayer.select, position, Tiles.selectPurple)
                    }
                }
                for (space in pathSpaces)
                {
                    Map.changeTile(MapLayer.select, space.position, Tiles.selectRed)
                }
                for (space in possiblePathSpaces)
                {
                    Map.changeTile(MapLayer.select, space.position, Tiles.selectTeal)
                }
                for (position in detectionPathPositions)
                {
                    Map.changeTile(MapLayer.select, position, Tiles.selectPurple)
                }
                Map.changeTile(MapLayer.select, GameScreen.roomTouchPosition, Tiles.selectYellow)
                
                
                if (canReach)
                {
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
                    
                    return State.constrained.moveSelectedOnce.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.isHeroDetected = currState.isHeroDetected
                        this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                        this.selectedPosition.set(selectedPosition)
                        this.pathSpaces = pathSpaces
                        this.isMoveToAnotherRoom = isMoveToAnotherRoom
                        this.isMoveToAnotherLevel = isMoveToAnotherLevel
                    }
                }
                else
                {
                    return State.constrained.moveSelectedOnce.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = currState.areEnemiesInRoom
                        this.isHeroDetected = currState.isHeroDetected
                        this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                        this.selectedPosition.set(selectedPosition)
                        this.pathSpaces = possiblePathSpaces
                        this.isMoveToAnotherRoom = false
                        this.isMoveToAnotherLevel = false
                    }
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
        val entityOnPositionHeroWalkedTowards = currState.entityOnPosition
        if (entityOnPositionHeroWalkedTowards == null)  // Hero stops at selectedPosition
        {
            World.hero.spendAP(currState.pathSpaces.size + 1)
        
        }
        else    // Hero stops next to the Entity that is at selectedPosition
        {
            World.hero.spendAP(currState.pathSpaces.size)
            // interact with Interactive Entity if it was selected in ConstrainedMoveSelectedOnce
            if (entityOnPositionHeroWalkedTowards is Interactive)
            {
                entityOnPositionHeroWalkedTowards.interact()
                if (entityOnPositionHeroWalkedTowards is EnemyMimic)
                {
                    return State.combat.hero.heroSelected.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = true
                        this.areAnyActionPointsLeft = true
                    }
                }
            }
        }
    
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
        
        
        when (areEnemiesInRoom)
        {
            true  ->
            {
                ProgressBars.abilityBar.isVisible = true
                ProgressBars.abilityBarForFlashing.isVisible = true
                ProgressBars.abilityBarLabel.isVisible = true
            }
            
            false ->
            {
                ProgressBars.abilityBar.isVisible = false
                ProgressBars.abilityBarForFlashing.isVisible = false
                ProgressBars.abilityBarLabel.isVisible = false
                
                World.hero.removeCoolDownFromAllActiveSkills()
                ItemsStructure.fillItemsStructureWithItemsAndSkills()
            }
        }
        
        
        return when (areEnemiesInRoom)
        {
            false -> State.free.heroSelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = false
            }
            
            true  ->
            {
                if (currState.isHeroMovingThroughDetectionPosition)
                {
                    return State.combat.hero.heroSelected.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = true
                        this.areAnyActionPointsLeft = true
                    }
                }
                else
                {
                    GameScreen.roomTouchPosition.set(World.hero.position)
                    Map.changeTile(MapLayer.outline, World.hero.position, World.hero.getOutlineGreenTile())
                    
                    return State.constrained.heroSelected.apply {
                        this.isHeroAlive = currState.isHeroAlive
                        this.areEnemiesInRoom = true
                        this.isHeroDetected = false
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
        if ((currState.chosenMultiUseItem?.baseAPUseCost ?: 0) <= World.hero.abilityPoints)
        {
            ProgressBars.abilityBarForFlashing.value = ProgressBars.abilityBar.value - multiUseMapItem.baseAPUseCost
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
        ProgressBars.abilityBarForFlashing.value = ProgressBars.abilityBar.value - multiUseMapItem.baseAPUseCost
        if (selectedPosition == currState.selectedPosition)
        {
            val worldCurrentRoom = World.currentRoom
            if (worldCurrentRoom != null)
            {
                multiUseMapItem.use(worldCurrentRoom, selectedPosition)
            }
            
            return State.constrained.multiUseMapItemTargetSelectedTwice.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.isHeroDetected = currState.isHeroDetected
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
        World.hero.spendAP(currState.chosenMultiUseItem?.baseAPUseCost ?: 0)
        
        World.currentRoom?.removeKilledCharacters()
        World.currentRoom?.addToBeAddedEntitiesToRoom()
        World.currentRoom?.updateSpacesEntities()
        GameScreen.updateMapEntityLayer()
        
        currState.areEnemiesInRoom = World.currentRoom?.getEnemies()?.isNotEmpty() ?: false
        
        val item = currState.chosenMultiUseItem
        item?.lowerDurability()
        if (item != null && item.durability < 1)
        {
            World.hero.inventory.removeItem(item)
            ItemsStructure.fillItemsStructureWithItemsAndSkills()
    
            return State.constrained.heroSelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
            }
        }
        ItemsStructure.fillItemsStructureWithItemsAndSkills()
    
        var wasEnemyAttacked = false
        if (!currState.areEnemiesInRoom)
        {
            ProgressBars.abilityBar.isVisible = false
            ProgressBars.abilityBarForFlashing.isVisible = false
            ProgressBars.abilityBarLabel.isVisible = false
        
            World.hero.removeCoolDownFromAllActiveSkills()
            ItemsStructure.fillItemsStructureWithItemsAndSkills()
        
            return State.free.noSelection.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
            }
        }
        else
        {
            enemyFor@ for (enemy in World.currentRoom?.getEnemies() ?: listOf())
            {
                val affectedPositions = State.constrained.multiUseMapItemTargetSelectedOnce.effectPositions
            
                if (affectedPositions != null)
                {
                    for (position in affectedPositions)
                    {
                        if (enemy.position == position)
                        {
                            wasEnemyAttacked = true
                            break@enemyFor
                        }
                    }
                }
            }
        }
        
        val canBeUsedAgain = when (val chosenItem = currState.chosenMultiUseItem)
        {
            null -> false
            else -> World.hero.abilityPoints >= chosenItem.baseAPUseCost
        }
        
        if (canBeUsedAgain)
        {
            val targetPositions = State.constrained.multiUseMapItemChosen.targetPositions ?: emptyList()
            ProgressBars.abilityBarForFlashing.value =
                    ProgressBars.abilityBar.value - (currState.chosenMultiUseItem?.baseAPUseCost
                            ?: 0)
            
            for (position in targetPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
            }
            
            return if (wasEnemyAttacked)
            {
                State.combat.hero.multiUseMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenMultiUseItem = currState.chosenMultiUseItem
                    this.targetPositions = targetPositions
                }
            }
            else
            {
                State.constrained.multiUseMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenMultiUseItem = currState.chosenMultiUseItem
                    this.targetPositions = targetPositions
                }
            }
        }
        else
        {
            Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
            Map.changeTile(MapLayer.outline, World.hero.position, Tiles.heroIdle1OutlineGreen)
            
            return if (wasEnemyAttacked)
            {
                State.combat.hero.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
            else
            {
                State.constrained.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
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
        if (stackableMapItem.baseAPUseCost <= World.hero.abilityPoints)
        {
            ProgressBars.abilityBarForFlashing.value = ProgressBars.abilityBar.value - stackableMapItem.baseAPUseCost
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
        ProgressBars.abilityBarForFlashing.value = ProgressBars.abilityBar.value - stackableMapItem.baseAPUseCost
        if (selectedPosition == currState.selectedPosition)
        {
            val worldCurrentRoom = World.currentRoom
            if (worldCurrentRoom != null)
            {
                stackableMapItem.use(worldCurrentRoom, selectedPosition)
            }
            
            return State.constrained.stackableMapItemTargetSelectedTwice.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.isHeroDetected = currState.isHeroDetected
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
        World.hero.spendAP(currState.chosenStackableMapItem?.baseAPUseCost ?: 0)
        
        World.currentRoom?.removeKilledCharacters()
        World.currentRoom?.addToBeAddedEntitiesToRoom()
        World.currentRoom?.updateSpacesEntities()
        GameScreen.updateMapEntityLayer()
        
        currState.areEnemiesInRoom = World.currentRoom?.getEnemies()?.isNotEmpty() ?: false
        
        val item = currState.chosenStackableMapItem
        item?.lowerAmountByOne()
        if (item != null && item.amount < 1)
        {
            World.hero.inventory.removeItem(item)
            ItemsStructure.fillItemsStructureWithItemsAndSkills()
    
            return State.constrained.heroSelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
            }
        }
        ItemsStructure.fillItemsStructureWithItemsAndSkills()
    
        var wasEnemyAttacked = false
        if (!currState.areEnemiesInRoom)
        {
            ProgressBars.abilityBar.isVisible = false
            ProgressBars.abilityBarForFlashing.isVisible = false
            ProgressBars.abilityBarLabel.isVisible = false
        
            World.hero.removeCoolDownFromAllActiveSkills()
            ItemsStructure.fillItemsStructureWithItemsAndSkills()
        
            return State.free.noSelection.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
            }
        }
        else
        {
            enemyFor@ for (enemy in World.currentRoom?.getEnemies() ?: listOf())
            {
                val affectedPositions = State.constrained.stackableMapItemTargetSelectedOnce.effectPositions
            
                if (affectedPositions != null)
                {
                    for (position in affectedPositions)
                    {
                        if (enemy.position == position)
                        {
                            wasEnemyAttacked = true
                            break@enemyFor
                        }
                    }
                }
            }
        }
        
        val canBeUsedAgain = when (val chosenItem = currState.chosenStackableMapItem)
        {
            null -> false
            else -> World.hero.abilityPoints >= chosenItem.baseAPUseCost
        }
        
        if (canBeUsedAgain)
        {
            ProgressBars.abilityBarForFlashing.value =
                    ProgressBars.abilityBar.value - (currState.chosenStackableMapItem?.baseAPUseCost
                            ?: 0)
            val targetPositions = State.constrained.stackableMapItemChosen.targetPositions ?: emptyList()
            for (position in targetPositions)
            {
                Map.changeTile(MapLayer.select, position, Tiles.selectTeal)
            }
            
            return if (wasEnemyAttacked)
            {
                State.combat.hero.stackableMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenStackableMapItem = currState.chosenStackableMapItem
                    this.targetPositions = targetPositions
                }
            }
            else
            {
                State.constrained.stackableMapItemChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenStackableMapItem = currState.chosenStackableMapItem
                    this.targetPositions = targetPositions
                }
            }
        }
        else
        {
            Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
            Map.changeTile(MapLayer.outline, World.hero.position, Tiles.heroIdle1OutlineGreen)
            
            return if (wasEnemyAttacked)
            {
                State.combat.hero.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
            else
            {
                State.constrained.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
            }
        }
    }
    
    return currState
}

fun updateConstrainedStackableSelfItemChosen(currState : State.constrained.stackableSelfItemChosen) : State
{
    return currState
}

fun updateConstrainedActiveSkillChosen(currState : State.constrained.activeSkillChosen) : State
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
        if ((currState.chosenActiveSkill?.apCost ?: 0) <= World.hero.abilityPoints)
        {
            ProgressBars.abilityBarForFlashing.value =
                    ProgressBars.abilityBar.value - (currState.chosenActiveSkill?.apCost ?: 0)
            if (selectedPosition in targetPositions)
            {
                val affectedPositions = activeSkill.getAffectedPositions(selectedPosition)
                
                for (position in affectedPositions)
                {
                    Map.changeTile(MapLayer.select, position, Tiles.selectOrange)
                }
                Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
                
                return State.constrained.activeSkillTargetSelectedOnce.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenActiveSkill = activeSkill
                    this.targetPositions = targetPositions
                    this.selectedPosition.set(selectedPosition)
                    this.effectPositions = affectedPositions
                }
            }
        }
    }
    
    return currState
}

fun updateConstrainedActiveSkillTargetSelectedOnce(currState : State.constrained.activeSkillTargetSelectedOnce) : State
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
        ProgressBars.abilityBarForFlashing.value =
                ProgressBars.abilityBar.value - (currState.chosenActiveSkill?.apCost ?: 0)
        if (selectedPosition == currState.selectedPosition)
        {
            val worldCurrentRoom = World.currentRoom
            if (worldCurrentRoom != null)
            {
                activeSkill.use(worldCurrentRoom, selectedPosition)
            }
            
            return State.constrained.activeSkillTargetSelectedTwice.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.isHeroDetected = currState.isHeroDetected
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
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
                return State.constrained.activeSkillChosen.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                    this.chosenActiveSkill = currState.chosenActiveSkill
                    this.targetPositions = currState.targetPositions
                }
            }
        }
        
        Map.changeTile(MapLayer.select, selectedPosition, Tiles.selectYellow)
    }
    
    return currState
}

fun updateConstrainedActiveSkillTargetSelectedTwice(currState : State.constrained.activeSkillTargetSelectedTwice) : State
{
    if (!Animating.isAnimating())
    {
        World.hero.spendAP(currState.chosenActiveSkill?.apCost ?: 0)
        
        World.currentRoom?.removeKilledCharacters()
        World.currentRoom?.addToBeAddedEntitiesToRoom()
        World.currentRoom?.updateSpacesEntities()
        GameScreen.updateMapEntityLayer()
        
        val activeSkill = currState.chosenActiveSkill
        if (activeSkill != null)
        {
            activeSkill.isInCoolDown = true
            activeSkill.currCoolDown = activeSkill.coolDown
        }
        ItemsStructure.fillItemsStructureWithItemsAndSkills()
        
        val areEnemiesInRoom = World.currentRoom?.getEnemies()?.isNotEmpty() ?: false
        
        var wasEnemyAttacked = false
        
        if (areEnemiesInRoom)
        {
            enemyFor@ for (enemy in World.currentRoom?.getEnemies() ?: listOf())
            {
                val affectedPositions = State.constrained.activeSkillTargetSelectedOnce.effectPositions
                
                if (affectedPositions != null)
                {
                    for (position in affectedPositions)
                    {
                        if (enemy.position == position)
                        {
                            wasEnemyAttacked = true
                            break@enemyFor
                        }
                    }
                }
            }
        }
        
        Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
        Map.changeTile(MapLayer.outline, World.hero.position, Tiles.heroIdle1OutlineGreen)
        
        return if (wasEnemyAttacked)
        {
            State.combat.hero.heroSelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
            }
        }
        else
        {
            State.constrained.heroSelected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.isHeroDetected = currState.isHeroDetected
                this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
            }
        }
    }
    
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
