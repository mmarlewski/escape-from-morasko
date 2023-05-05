package com.efm

import com.efm.assets.Tiles
import com.efm.level.World
import com.efm.screens.*

fun updateState()
{
    when (currState)
    {
        noPositionSelected        -> updateNoPositionSelected()
        movePositionSelectedOnce  -> updateMovePositionSelectedOnce()
        movePositionSelectedTwice -> updateMovePositionSelectedTwice()
    }
}

fun updateNoPositionSelected()
{
    if (noPositionSelected.isMovingMode && GameScreen.isTouched)
    {
        val startSpace = World.currentRoom.getSpace(World.hero.position)
        val endSpace = World.currentRoom.getSpace(GameScreen.roomTouchPosition)
        val isMovePossible = (startSpace != null && endSpace != null && startSpace != endSpace)
        
        if (isMovePossible)
        {
            val newPathSpaces = Pathfinding.findPathWithGivenRoom(
                    World.hero.position,
                    GameScreen.roomTouchPosition,
                    World.currentRoom
                                                                 )
            if (newPathSpaces != null)
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
                for (space in newPathSpaces)
                {
                    Map.changeTile(MapLayer.select, space.position, Tiles.selectTeal)
                }
                Map.changeTile(MapLayer.select, GameScreen.roomTouchPosition, Tiles.selectYellow)
            }
            
            movePositionSelectedOnce.selectedPosition.set(GameScreen.roomTouchPosition)
            movePositionSelectedOnce.pathSpaces = newPathSpaces
            currState = movePositionSelectedOnce
        }
    }
}

fun updateMovePositionSelectedOnce()
{
    if (GameScreen.isTouched)
    {
        val startSpace = World.currentRoom.getSpace(World.hero.position)
        val endSpace = World.currentRoom.getSpace(GameScreen.roomTouchPosition)
        val isMovePossible = (startSpace != null && endSpace != null && startSpace != endSpace)
        
        if (isMovePossible)
        {
            val isMovePositionSelectedTwice =
                    movePositionSelectedOnce.selectedPosition == GameScreen.roomTouchPosition
            
            if (isMovePositionSelectedTwice)
            {
                Map.clearLayer(MapLayer.select)
                movePositionSelectedOnce.pathSpaces?.let {
                    moveHero(
                            World.hero.position.copy(),
                            GameScreen.roomTouchPosition.copy(),
                            it
                            )
                }
                
                currState = movePositionSelectedTwice
            }
            else
            {
                val newPathSpaces = Pathfinding.findPathWithGivenRoom(
                        World.hero.position,
                        GameScreen.roomTouchPosition,
                        World.currentRoom
                                                                     )
                if (newPathSpaces != null)
                {
                    Map.clearLayer(MapLayer.select)
                    Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
                    for (space in newPathSpaces)
                    {
                        Map.changeTile(MapLayer.select, space.position, Tiles.selectTeal)
                    }
                    Map.changeTile(MapLayer.select, GameScreen.roomTouchPosition, Tiles.selectYellow)
                }
                
                movePositionSelectedOnce.selectedPosition.set(GameScreen.roomTouchPosition)
                movePositionSelectedOnce.pathSpaces = newPathSpaces
            }
        }
    }
}

fun updateMovePositionSelectedTwice()
{
    if (!Animating.isAnimating)
    {
        currState = noPositionSelected
    }
}
