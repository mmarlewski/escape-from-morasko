package com.efm.exit

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.*
import com.efm.*
import com.efm.assets.*
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

open class LevelExit(
        override val position : RoomPosition,
        override var direction : Direction4,
        var endLevelName : String,
        override var style : ExitStyle,
        override var activeWhenNoEnemiesAreInRoom : Boolean = true,
        override var active : Boolean = true
                    ) : Exit
{
    override fun getTile() : TiledMapTile?
    {
        return if (isOpen())
        {
            when (direction)
            {
                Direction4.up    -> style.tiles.exitLevelUp
                Direction4.right -> style.tiles.exitLevelRight
                Direction4.down  -> style.tiles.exitLevelDown
                Direction4.left  -> style.tiles.exitLevelLeft
            }
        }
        else
        {
            when (direction)
            {
                Direction4.up    -> style.tiles.exitLevelUpClosed
                Direction4.right -> style.tiles.exitLevelRightClosed
                Direction4.down  -> style.tiles.exitLevelDownClosed
                Direction4.left  -> style.tiles.exitLevelLeftClosed
            }
        }
    }
    
    override fun getOutlineTealTile() : TiledMapTile?
    {
        return if (isOpen())
        {
            when (direction)
            {
                Direction4.up    -> Tiles.ExitLevelUpOutlineTeal
                Direction4.right -> Tiles.ExitLevelRightOutlineTeal
                Direction4.down  -> Tiles.ExitLevelDownOutlineTeal
                Direction4.left  -> Tiles.ExitLevelLeftOutlineTeal
            }
        }
        else
        {
            when (direction)
            {
                Direction4.up    -> Tiles.ExitLevelUpOutlineRed
                Direction4.right -> Tiles.ExitLevelRightOutlineRed
                Direction4.down  -> Tiles.ExitLevelDownOutlineRed
                Direction4.left  -> Tiles.ExitLevelLeftOutlineRed
            }
        }
    }
    
    override fun interact()
    {
        if (isOpen())
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
    
    fun nextLevelPopup() : Window
    {
        val nextLevelPopUp = windowAreaOf(
                "You are about to leave this level\n\nAre you sure?",
                Fonts.pixeloid20,
                Colors.black,
                Textures.pauseBackgroundNinePatch,
                { travelBetweenLevels() },
                {}
                                         )
        nextLevelPopUp.isVisible = false
        return nextLevelPopUp
    }
    
    private fun travelBetweenLevels()
    {
        val worldCurrentRoom = World.currentRoom ?: return
        
        val newLevel = World.levels.find { it.name == endLevelName }
        val newPosition = newLevel?.startingPosition
        val newRoom = newLevel?.startingRoom
        Gdx.app.log("Exit", "travelling...")
        worldCurrentRoom.removeEntity(World.hero)
        if (newLevel != null) World.changeCurrentLevel(newLevel)
        if (newRoom != null) World.changeCurrentRoom(newRoom)
        if (newPosition != null) World.currentRoom?.addEntityAt(World.hero, newPosition)
        Gdx.app.log("Exit", "travelled")
        Gdx.app.log("Exit", "adjusting camera again")
        adjustCameraAfterMoving()
        adjustMapLayersAfterMoving()
        
        saveGame()
    }
    
    // for serializing
    
    constructor() : this(RoomPosition(-1, -1), Direction4.up, "", ExitStyle.stone)
    
    override fun write(json : Json?)
    {
        super.write(json)
        
        if (json != null)
        {
            json.writeValue("endLevelName", this.endLevelName)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super.read(json, jsonData)
        
        if (json != null)
        {
            val jsonEndLevelName = json.readValue("endLevelName", String::class.java, jsonData)
            if (jsonEndLevelName != null) this.endLevelName = jsonEndLevelName
        }
    }
}