package com.efm.exit

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.assets.Colors
import com.efm.assets.Fonts
import com.efm.assets.Textures
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

open class LevelExit(
        override val position : RoomPosition,
        override var direction : Direction4,
        var endLevelName : String,
        override var style : ExitStyle, override var activeWhenNoEnemiesAreInRoom : Boolean = false
                    ) : Exit
{
    override fun getOutlineTealTile() : TiledMapTile? = when (direction)
    {
        Direction4.up -> style.tiles.exitLevelUpOutlineTeal
        Direction4.right -> style.tiles.exitLevelRightOutlineTeal
        Direction4.down -> style.tiles.exitLevelDownOutlineTeal
        Direction4.left -> style.tiles.exitLevelLeftOutlineTeal
    }
    
    override fun getTile() : TiledMapTile? = when (direction)
    {
        Direction4.up -> style.tiles.exitLevelUp
        Direction4.right -> style.tiles.exitLevelRight
        Direction4.down -> style.tiles.exitLevelDown
        Direction4.left -> style.tiles.exitLevelLeft
    }
    
    override fun interact()
    {
        if (!activeWhenNoEnemiesAreInRoom || !World.currentRoom.areEnemiesInRoom())
        {
            showNextLevelPopup()
            Gdx.app.log("Exit", "adjusting camera")
            adjustCameraAfterMoving()
            adjustMapLayersAfterMoving()
        }
    }
    
    private fun showNextLevelPopup()
    {
        val popup = nextLevelPopup()
        popup.isVisible = true
        val nextLevelPopupWindow = columnOf(rowOf(popup)).align(Align.center)
        nextLevelPopupWindow.setFillParent(true)
        GameScreen.stage.addActor(nextLevelPopupWindow)
    }
    
    private fun nextLevelPopup() : Window
    {
        val nextLevelPopUp = windowAreaOf(
                "You are about to leave this level\n\nAre you sure?",
                Fonts.pixeloid20,
                Colors.black,
                Textures.pauseBackgroundNinePatch
        ) { travelBetweenLevels() }
        nextLevelPopUp.isVisible = false
        return nextLevelPopUp
    }
    
    private fun travelBetweenLevels()
    {
        val newLevel = World.getLevels().find { it.name == endLevelName }
        val newPosition = newLevel?.getStartingPosition()
        val newRoom = newLevel?.getStartingRoom()
        Gdx.app.log("Exit", "travelling...")
        World.currentRoom.removeEntity(World.hero)
        if (newLevel != null) World.changeCurrentLevel(newLevel)
        if (newRoom != null) World.changeCurrentRoom(newRoom)
        if (newPosition != null) World.currentRoom.addEntityAt(World.hero, newPosition)
        Gdx.app.log("Exit", "travelled")
        Gdx.app.log("Exit", "adjusting camera again")
        adjustCameraAfterMoving()
        adjustMapLayersAfterMoving()
    }
}