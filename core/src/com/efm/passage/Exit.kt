package com.efm.passage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.assets.*
import com.efm.entity.Entity
import com.efm.entity.Interactive
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

/**
 * Interactive object found in a Room used to leave it. Created based on a Passage.
 */
open class Exit(var direction : Direction4, var passage : Passage?) : Entity, Interactive
{
    override val position : RoomPosition = RoomPosition()
    
    override fun getOutlineTealTile() : TiledMapTile?
    {
        return null
    }
    
    override fun interact()
    {
        when (val passage = passage)
        {
            is RoomPassage  -> travelBetweenRooms(passage)
            is LevelPassage -> showNextLevelPopup(passage)
        }
        Gdx.app.log("Exit", "adjusting camera")
        adjustCameraAfterMoving()
        adjustMapLayersAfterMoving()
    }
    
    override fun getTile() : TiledMapTile?
    {
        return null
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile?
    {
        return null
    }
    
    fun showNextLevelPopup(passage : LevelPassage)
    {
        val popup = nextLevelPopup(passage)
        popup.isVisible = true
        val nextLevelPopupWindow = columnOf(rowOf(popup)).align(Align.center)
        nextLevelPopupWindow.setFillParent(true)
        GameScreen.stage.addActor(nextLevelPopupWindow)
    }
    
    fun nextLevelPopup(passage : LevelPassage) : Window
    {
        val nextLevelPopUp = windowAreaOf(
                "You are about to leave this level\n\nAre you sure?",
                Fonts.pixeloid20,
                Colors.black,
                Textures.pauseBackgroundNinePatch
                                         ) { travelBetweenLevels(passage) }
        nextLevelPopUp.isVisible = false
        return nextLevelPopUp
    }
    
    fun travelBetweenRooms(passage : RoomPassage)
    {
        val newRoom : Room? = World.currentLevel.rooms.find { it.name == getRoomOnOtherSideOfPassage(passage) }
        val newPosition : RoomPosition? = getPositionOnOtherSideOfPassage(passage)
        
        println("getRoomOnOtherSideOfPassage(passage) : ${getRoomOnOtherSideOfPassage(passage)}")
        println("newRoom : $newRoom")
        println("World.currentRoom.name : ${World.currentRoom.name}")
        
        if (newPosition == null || newRoom == null) throw java.lang.Exception("Exit's Passage is not connected to Room where Hero is in.")
        
        World.currentRoom.removeEntity(World.hero)
        World.changeCurrentRoom(newRoom)
        World.currentRoom.addEntityAt(World.hero, newPosition)
        //World.currentRoom.updateSpacesEntities()  // obsolete unless I'm wrong
    }
    
    fun travelBetweenLevels(passage : LevelPassage)
    {
        val newLevel = World.getLevels().find { it.name == passage.targetLevelName }
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
        //World.currentRoom.updateSpacesEntities()  // obsolete unless I'm wrong
    }
    
    fun getRoomOnOtherSideOfPassage(passage : RoomPassage) : String
    {
        return when (World.currentRoom.name)
        {
            passage.roomAName -> passage.roomBName
            passage.roomBName -> passage.roomAName
            else              -> "else"
        }
    }
    
    fun getPositionOnOtherSideOfPassage(passage : RoomPassage) : RoomPosition?
    {
        return when (World.currentRoom.name)
        {
            passage.roomAName -> passage.positionB.adjacentPosition(passage.directionB)
            passage.roomBName -> passage.positionA.adjacentPosition(passage.directionA)
            else              -> null
        }
    }
    
    fun isPassageToAnotherLevel() : Boolean
    {
        return passage is LevelPassage
    }
    
    // for serialization
    constructor() : this(Direction4.up, null)
}
