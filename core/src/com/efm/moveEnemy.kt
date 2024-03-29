package com.efm

import com.efm.entity.Enemy
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.room.Space
import com.efm.screens.GameScreen

/**
 * prepares Enemy move and its Animations
 */
fun moveEnemy(
        startPosition : RoomPosition,
        endPosition : RoomPosition,
        path : List<Space?>,
        enemy : Enemy,
        focusCameraOnHero : Boolean = false
             )
{
    val action = {
        enemy.position.set(endPosition)
        World.currentRoom?.updateSpacesEntities()
        GameScreen.updateMapEntityLayer()
        GameScreen.updateMapBaseLayer()
        GameScreen.updateMapOutlineLayer()
        Map.clearLayer(MapLayer.select)
    }
    val animations = mutableListOf<Animation>()
    animations += Animation.action { enemy.hideOwnHealthBar() }
    animations += Animation.action { Map.changeTile(MapLayer.entity, enemy.position, null) }
    val prevMovePosition = startPosition.copy()
    path.forEachIndexed { index, space ->
        
        val n = (index % IdleAnimation.numberOfMoveAnimations) + 1
        val moveTile = enemy.getMoveTile(n)
        
        if (space != null)
        {
            animations += Animation.moveTileWithCameraFocus(
                    moveTile,
                    prevMovePosition.copy(),
                    space.position.copy(),
                    0.1f
                                                           )
            animations += Animation.showTileWithCameraFocus(moveTile, space.position.copy(), 0.01f)
            animations += Animation.action { GameScreen.updateMapOutlineLayer() }
            prevMovePosition.set(space.position)
        }
    }
    animations += Animation.moveTileWithCameraFocus(enemy.getTile(), prevMovePosition, endPosition, 0.1f)
    animations += Animation.action(action)
    animations += Animation.action { enemy.displayOwnHealthBar() }
    if (focusCameraOnHero)
    {
        animations += Animation.moveCameraSmoothlyWithRoomPositions(null, World.hero.position.copy(), 0.1f)
        animations += Animation.wait(0.3f)
    }
    Animating.executeAnimations(animations)
}

fun getAnimationsUsedInMoveEnemy(
        startPosition : RoomPosition,
        endPosition : RoomPosition,
        path : List<Space?>,
        enemy : Enemy,
        focusCameraOnHero : Boolean = false
                                ) : MutableList<Animation>
{
    val action = {
        enemy.position.set(endPosition)
        World.currentRoom?.updateSpacesEntities()
        GameScreen.updateMapEntityLayer()
        GameScreen.updateMapBaseLayer()
        GameScreen.updateMapOutlineLayer()
        Map.clearLayer(MapLayer.select)
    }
    val animations = mutableListOf<Animation>()
    animations += Animation.action { enemy.hideOwnHealthBar() }
    animations += Animation.action { Map.changeTile(MapLayer.entity, enemy.position, null) }
    val prevMovePosition = startPosition.copy()
    path.forEachIndexed { index, space ->
        
        val n = (index % IdleAnimation.numberOfMoveAnimations) + 1
        val moveTile = enemy.getMoveTile(n)
        
        if (space != null)
        {
            animations += Animation.moveTileWithCameraFocus(
                    moveTile,
                    prevMovePosition.copy(),
                    space.position.copy(),
                    0.1f
                                                           )
            //animations += Animation.showTileWithCameraFocus(moveTile, space.position.copy(), 0.01f)
            //animations += Animation.action { GameScreen.updateMapOutlineLayer() }
            prevMovePosition.set(space.position)
        }
    }
    animations += Animation.moveTileWithCameraFocus(enemy.getTile(), prevMovePosition, endPosition, 0.1f)
    animations += Animation.action(action)
    animations += Animation.action { enemy.displayOwnHealthBar() }
    if (focusCameraOnHero)
    {
        animations += Animation.moveCameraSmoothlyWithRoomPositions(null, World.hero.position.copy(), 0.1f)
        animations += Animation.wait(0.3f)
    }
    return animations
}