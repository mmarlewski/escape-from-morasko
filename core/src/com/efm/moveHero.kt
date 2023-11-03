package com.efm

import com.efm.assets.Tiles
import com.efm.entities.Hero
import com.efm.level.World
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
    
    val endEntity = endSpace?.getEntity()
    val endBase = endSpace?.getBase()
    
    if (World.hero.canMoveNextTurn)
    {
        when (endEntity)
        {
            is Hero ->
            {
            }
        
            null    ->
            {
                if (endBase == null || !endBase.isTreadableFor(World.hero))
                {
                    val lastSpace = path.lastOrNull()
                    newPosition = lastSpace?.position ?: startPosition
                    animateToEndSpace = false
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
            World.currentRoom.addEntityAt(World.hero, newPosition)
            adjustCameraAfterMoving()
            adjustMapLayersAfterMoving()
        }
    
        val animations = mutableListOf<Animation>()
        animations += Animation.action { Map.changeTile(MapLayer.entity, World.hero.position, null) }
        val prevMovePosition = startPosition.copy()
        path.forEachIndexed { index, space ->
        
            val n = (index % IdleAnimation.numberOfMoveAnimations) + 1
            val moveTile = World.hero.getMoveTile(n)
            
            animations += Animation.moveTileWithCameraFocus(moveTile, prevMovePosition.copy(), space.position.copy(), 0.1f)
            animations += Animation.showTileWithCameraFocus(moveTile, space.position.copy(), 0.01f)
            prevMovePosition.set(space.position)
        }
        if (animateToEndSpace) animations += Animation.moveTileWithCameraFocus(
                Tiles.heroIdle1,
                prevMovePosition,
                endPosition,
                0.1f
                                                                              )
        animations += Animation.action(action)
        Animating.executeAnimations(animations)
    }
}

fun adjustMapLayersAfterMoving()
{
    GameScreen.updateMapBaseLayer()
    GameScreen.updateMapEntityLayer()
    Map.clearLayer(MapLayer.select)
    Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
}

fun adjustCameraAfterMoving()
{
    GameScreen.focusCameraOnRoomPosition(World.hero.position)
}
