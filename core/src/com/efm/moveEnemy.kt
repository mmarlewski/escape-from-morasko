package com.efm
import com.efm.assets.Tiles
import com.efm.entities.Hero
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.room.Space
import com.efm.screens.GameScreen
import com.efm.Map
import com.efm.entity.Enemy

fun moveEnemy(startPosition : RoomPosition, endPosition : RoomPosition, path : List<Space>, enemy : Enemy)
{
    val action = {
        enemy.position.set(endPosition)
        World.currentRoom.updateSpacesEntities()
        GameScreen.updateMapBaseLayer()
        GameScreen.updateMapEntityLayer()
    }
    val animations = mutableListOf<Animation>()
    animations += Animation.action{enemy.hideOwnHealthBar()}
    animations += Animation.action { Map.changeTile(MapLayer.entity, enemy.position, null) }
    val prevMovePosition = startPosition.copy()
    for (space in path)
    {
        animations += Animation.moveTileWithCameraFocus(enemy.getTile(), prevMovePosition.copy(), space.position.copy(), 0.1f)
        animations += Animation.showTileWithCameraFocus(enemy.getTile(), space.position.copy(), 0.01f)
        prevMovePosition.set(space.position)
    }
    animations += Animation.moveTileWithCameraFocus(enemy.getTile(), prevMovePosition, endPosition, 0.1f)
    animations += Animation.action(action)
    animations += Animation.action{enemy.displayOwnHealthBar()}
    Animating.executeAnimations(animations)
}
