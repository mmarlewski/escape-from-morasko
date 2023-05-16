package com.efm

import com.efm.assets.Tiles
import com.efm.entities.Hero
import com.efm.level.World
import com.efm.passage.*
import com.efm.room.RoomPosition
import com.efm.room.Space
import com.efm.screens.GameScreen

fun moveHero(startPosition : RoomPosition, endPosition : RoomPosition, path : List<Space>)
{
    val startSpace = World.currentRoom.getSpace(startPosition)
    val endSpace = World.currentRoom.getSpace(endPosition)
    var animateToEndSpace = true
    
    if (startSpace == endSpace) return
    
    var newPosition = endPosition
    var newRoom = World.currentRoom
    var newLevel = World.currentLevel
    
    val endEntity = endSpace?.getEntity()
    val endBase = endSpace?.getBase()
    
    when (endEntity)
    {
        is Hero ->
        {
        }
        
        null    ->
        {
            if (endBase == null || !endBase.isTreadable)
            {
                val lastSpace = path.lastOrNull()
                newPosition = lastSpace?.position ?: startPosition
                animateToEndSpace = false
            }
        }
        
        is Exit ->
        {
            when (val passage = endEntity.exitPassage)
            {
                is RoomPassage  ->
                {
                    Map.changeTile(
                            MapLayer.select, endPosition.x, endPosition.y, Tiles.selectYellow
                                  )
                    newPosition = when (endEntity.currentRoom)
                    {
                        passage.roomA -> passage.positionB.adjacentPosition(passage.directionB)
                        passage.roomB -> passage.positionA.adjacentPosition(passage.directionA)
                        else          -> newPosition
                    }
                    newRoom = when (endEntity.currentRoom)
                    {
                        passage.roomA -> passage.roomB
                        passage.roomB -> passage.roomA
                        else          -> newRoom
                    }
                }
                
                is LevelPassage ->
                {
                    newPosition = passage.targetLevel.getStartingPosition()
                    newRoom = passage.targetLevel.getStartingRoom()
                    newLevel = passage.targetLevel
                }
            }
        }
        
        else    ->
        {
            val lastSpace = path.lastOrNull()
            newPosition = lastSpace?.position ?: startPosition
            animateToEndSpace = false
        }
    }
    
    val action = {
        World.currentRoom.removeEntity(World.hero)
        
        World.changeCurrentRoom(newRoom)
        World.changeCurrentLevel(newLevel)
        
        World.currentRoom.addEntityAt(World.hero, newPosition)
        World.currentRoom.updateSpacesEntities()
        
        GameScreen.focusCameraOnRoomPosition(World.hero.position)
        GameScreen.updateMapBaseLayer()
        GameScreen.updateMapEntityLayer()
        Map.clearLayer(MapLayer.select)
        Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
    }
    
    val animations = mutableListOf<Animation>()
    animations += Animation.action { Map.changeTile(MapLayer.entity, World.hero.position, null) }
    val prevMovePosition = startPosition.copy()
    for (space in path)
    {
        animations += Animation.moveTileWithCameraFocus(Tiles.hero, prevMovePosition.copy(), space.position.copy(), 0.1f)
        animations += Animation.showTileWithCameraFocus(Tiles.hero, space.position.copy(), 0.01f)
        prevMovePosition.set(space.position)
    }
    if (animateToEndSpace)
        animations += Animation.moveTileWithCameraFocus(Tiles.hero, prevMovePosition, endPosition, 0.1f)
    animations += Animation.action(action)
    Animating.executeAnimations(animations)
}
