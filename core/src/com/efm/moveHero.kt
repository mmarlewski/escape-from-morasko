package com.efm

import com.efm.assets.Textures
import com.efm.assets.Tiles
import com.efm.entities.Hero
import com.efm.level.World
import com.efm.room.*
import com.efm.screens.GameScreen
import com.efm.skills.GrassHealing

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
        if (World.hero.hasSkill(GrassHealing))
        {
            World.currentRoom.getSpace(World.hero.position)?.let { changeBaseIfDrained(it) }
        }
        val prevMovePosition = startPosition.copy()
        path.forEachIndexed { index, space ->
            
            val n = (index % IdleAnimation.numberOfMoveAnimations) + 1
            val moveTile = World.hero.getMoveTile(n)
            if (World.hero.hasSkill(GrassHealing))
            {
                changeBaseIfDrained(space)
            }
            
            animations += Animation.moveTileWithCameraFocus(moveTile, prevMovePosition.copy(), space.position.copy(), 0.1f)
            animations += Animation.showTileWithCameraFocus(moveTile, space.position.copy(), 0.01f)
            prevMovePosition.set(space.position)
        }
        if (animateToEndSpace)
        {
            animations += Animation.moveTileWithCameraFocus(
                    Tiles.heroIdle1,
                    prevMovePosition,
                    endPosition,
                    0.1f
                                                           )
            if (World.hero.hasSkill(GrassHealing))
            {
                World.currentRoom.getSpace(endPosition)?.let { changeBaseIfDrained(it) }
            }
        }
        animations += Animation.action(action)
        Animating.executeAnimations(animations)
    }
}

fun changeBaseIfDrained(space : Space)
{
    if (space.getBase()?.tile == Tiles.grassDarkFloor1)
    {
        space.changeBase(Base.grassDarkDrained1)
        World.hero.healCharacter(3)
    }
    if (space.getBase()?.tile == Tiles.grassDarkFloor2)
    {
        space.changeBase(Base.grassDarkDrained2)
        World.hero.healCharacter(3)
    }
    if (space.getBase()?.tile == Tiles.grassLightFloor1)
    {
        space.changeBase(Base.grassLightDrained1)
        World.hero.healCharacter(3)
    }
    if (space.getBase()?.tile == Tiles.grassStoneFloor1)
    {
        space.changeBase(Base.grassStoneDrained1)
        World.hero.healCharacter(3)
    }
    if (space.getBase()?.tile == Tiles.grassStoneFloor2)
    {
        space.changeBase(Base.grassStoneDrained2)
        World.hero.healCharacter(3)
    }
    if (space.getBase()?.tile == Tiles.grassStoneFloor3)
    {
        space.changeBase(Base.grassStoneDrained3)
        World.hero.healCharacter(3)
    }
}

fun adjustMapLayersAfterMoving()
{
    World.currentRoom.updateSpacesEntities()
    GameScreen.updateMapBaseLayer()
    GameScreen.updateMapEntityLayer()
    Map.clearLayer(MapLayer.select)
    Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
}

fun adjustCameraAfterMoving()
{
    GameScreen.focusCameraOnRoomPosition(World.hero.position)
}
