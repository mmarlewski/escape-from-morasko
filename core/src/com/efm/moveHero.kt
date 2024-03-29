package com.efm

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.efm.assets.Tiles
import com.efm.entities.Hero
import com.efm.level.World
import com.efm.room.*
import com.efm.screens.GameScreen
import com.efm.skills.GrassHealing
import com.efm.state.State
import com.efm.state.getState
import com.efm.ui.gameScreen.*

/**
 * prepares Hero move and its Animations
 */
fun moveHero(startPosition : RoomPosition, endPosition : RoomPosition, path : List<Space>)
{
    val worldCurrentRoom = World.currentRoom ?: return
    
    val startSpace = worldCurrentRoom.getSpace(startPosition)
    val endSpace = worldCurrentRoom.getSpace(endPosition)
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
            worldCurrentRoom.removeEntity(World.hero)
            worldCurrentRoom.addEntityAt(World.hero, newPosition)
            adjustCameraAfterMoving()
            adjustMapLayersAfterMoving()
        }
        
        val animations = mutableListOf<Animation>()
        animations += Animation.action{PopUps.setBackgroundVisibility(false)}
        animations += Animation.action{LeftStructure.menuButton.isVisible = false}
        val currentlyOpenedTab = findOpenedTab()
        animations += Animation.action { Map.changeTile(MapLayer.entity, World.hero.position, null) }
        if (World.hero.hasSkill(GrassHealing))
        {
            val space = worldCurrentRoom.getSpace(World.hero.position)
            if (space != null && space.getBase() in Base.grassHealingTiles)
            {
                changeBaseIfDrained(space)
            }
        }
        val prevMovePosition = startPosition.copy()
        path.forEachIndexed { index, space ->
            
            val n = (index % IdleAnimation.numberOfMoveAnimations) + 1
            val moveTile = World.hero.getMoveTile(n)
            
            animations += Animation.moveTileWithCameraFocus(moveTile, prevMovePosition.copy(), space.position.copy(), 0.1f)
            animations += Animation.showTileWithCameraFocus(moveTile, space.position.copy(), 0.01f)
            prevMovePosition.set(space.position)
            
            if (World.hero.hasSkill(GrassHealing) && space.getBase() in Base.grassHealingTiles)
            {
                changeBaseIfDrained(space)
                val showAndHeal = mutableListOf(
                        Animation.showTile(moveTile, space.position.copy(), 0.1f),
                        Animation.ascendTile(Tiles.hpPlus, space.position.copy(), 0.1f, 0.1f)
                                               )
                animations += Animation.simultaneous(showAndHeal)
            }
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
                val space = worldCurrentRoom.getSpace(endPosition)
                if (space != null && space.getBase() in Base.grassHealingTiles)
                {
                    changeBaseIfDrained(space)
                    val showAndHeal = mutableListOf(
                            Animation.showTile(Tiles.heroIdle1, space.position.copy(), 0.2f),
                            Animation.ascendTile(Tiles.hpPlus, space.position.copy(), 0.2f, 0.25f)
                                                   )
                    animations += Animation.simultaneous(showAndHeal)
                }
            }
        }
        animations += Animation.action(action)
        animations += Animation.action{ interfaceVisibilityWithTutorial()}
        if (currentlyOpenedTab != null && currentlyOpenedTab != ItemsStructure.weaponDisplay)
        {
            animations += Animation.action{ hideWeaponsAndShowOtherTab(currentlyOpenedTab) }
        }
        animations += Animation.action{LeftStructure.menuButton.isVisible = true}
        if (getState() is State.free)
        {
            animations += Animation.action{
            ProgressBars.abilityBar.isVisible = false
            ProgressBars.abilityBarForFlashing.isVisible = false
            ProgressBars.abilityBarLabel.isVisible = false}
        }
        Animating.executeAnimations(animations)
    }
}

fun hideWeaponsAndShowOtherTab(currentlyOpenedTab : ScrollPane)
{
    ItemsStructure.weaponDisplay.isVisible = false
    currentlyOpenedTab.isVisible = true
}

fun findOpenedTab() : ScrollPane?
{
    return when
    {
        ItemsStructure.potionDisplay.isVisible -> ItemsStructure.potionDisplay
        ItemsStructure.skillDisplay.isVisible -> ItemsStructure.skillDisplay
        ItemsStructure.usableDisplay.isVisible -> ItemsStructure.usableDisplay
        ItemsStructure.weaponDisplay.isVisible -> ItemsStructure.weaponDisplay
        else -> null
    }
    
}

fun changeBaseIfDrained(space : Space)
{
    /*
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
    */
    // only light grass heals
    if (space.getBase()?.tile == Tiles.grassLightFloor1)
    {
        space.changeBase(Base.grassLightDrained1)
        World.hero.healCharacter(3)
    }
    /*
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
    */
}

fun adjustMapLayersAfterMoving()
{
    World.currentRoom?.updateSpacesEntities()
    GameScreen.updateMapBaseLayer()
    GameScreen.updateMapEntityLayer()
    Map.clearLayer(MapLayer.select)
    Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
}

fun adjustCameraAfterMoving()
{
    GameScreen.focusCameraOnRoomPosition(World.hero.position)
}
