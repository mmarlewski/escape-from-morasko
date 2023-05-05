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
    
    if (startSpace == endSpace) return
    
    val action =
            {
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
                            newPosition = if (lastSpace != null)
                            {
                                lastSpace.position
                            }
                            else
                            {
                                startPosition
                            }
                        }
                    }
                    
                    is Exit ->
                    {
                        when (val passage = endEntity.exitPassage)
                        {
                            is RoomPassage  ->
                            {
                                Map.changeTile(
                                        MapLayer.select,
                                        endPosition.x,
                                        endPosition.y,
                                        Tiles.selectYellow
                                              )
                                newPosition = when (endEntity.currentRoom)
                                {
                                    passage.roomA -> passage.positionB
                                    passage.roomB -> passage.positionA
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
                        newPosition = if (lastSpace != null)
                        {
                            lastSpace.position
                        }
                        else
                        {
                            startPosition
                        }
                    }
                }
                
                World.currentRoom.removeEntity(World.hero)
                
                World.changeCurrentRoom(newRoom)
                World.changeCurrentLevel(newLevel)
                
                World.currentRoom.addEntityAt(World.hero, newPosition)
                World.currentRoom.updateSpacesEntities()
                
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
                
                GameScreen.focusCameraOnRoomPosition(World.hero.position)
                
                GameScreen.updateMapBaseLayer()
                GameScreen.updateMapEntityLayer()
                
            }
    
    val animations = mutableListOf<Animation>()
    animations += changeMapTile(MapLayer.entity, startPosition.x, startPosition.y, null)
    for (space in path)
    {
        animations += changeMapTile(MapLayer.select, space.position.x, space.position.y, null)
        animations += focusGameScreenCamera(space.position)
        animations += changeMapTile(MapLayer.entity, space.position.x, space.position.y, Tiles.hero)
        animations += wait(0.1f)
        animations += changeMapTile(MapLayer.entity, space.position.x, space.position.y, null)
    }
    animations += action(action)
    Animating.executeAnimations(animations)
}
