package com.efm.passage

import com.badlogic.gdx.Gdx
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
interface Exit : Entity, Interactive
{
    val exitPassage : Passage
    val direction : Direction4
    
    override fun interact()
    {
        when (val passage = exitPassage)
        {
            is RoomPassage  -> travelBetweenRooms(passage)
            is LevelPassage -> showNextLevelPopup(passage)
        }
        Gdx.app.log("Exit", "adjusting camera")
        adjustCameraAfterMoving()
        adjustMapLayersAfterMoving()
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
        val newRoom : Room? = getRoomOnOtherSideOfPassage(passage)
        val newPosition : RoomPosition? = getPositionOnOtherSideOfPassage(passage)
        
        if (newPosition == null || newRoom == null) throw java.lang.Exception("Exit's Passage is not connected to Room where Hero is in.")
        
        World.currentRoom.removeEntity(World.hero)
        World.changeCurrentRoom(newRoom)
        World.currentRoom.addEntityAt(World.hero, newPosition)
        //World.currentRoom.updateSpacesEntities()  // obsolete unless I'm wrong
    }
    
    fun travelBetweenLevels(passage : LevelPassage)
    {
        val newLevel = passage.targetLevel
        val newPosition = newLevel.getStartingPosition()
        val newRoom = newLevel.getStartingRoom()
        Gdx.app.log("Exit", "travelling...")
        World.currentRoom.removeEntity(World.hero)
        World.changeCurrentLevel(newLevel)
        World.changeCurrentRoom(newRoom)
        World.currentRoom.addEntityAt(World.hero, newPosition)
        Gdx.app.log("Exit", "travelled")
        Gdx.app.log("Exit", "adjusting camera again")
        adjustCameraAfterMoving()
        adjustMapLayersAfterMoving()
        //World.currentRoom.updateSpacesEntities()  // obsolete unless I'm wrong
    }
    
    fun getRoomOnOtherSideOfPassage(passage : RoomPassage) : Room?
    {
        return when (World.currentRoom)
        {
            passage.roomA -> passage.roomB
            passage.roomB -> passage.roomA
            else          -> null
        }
    }
    
    fun getPositionOnOtherSideOfPassage(passage : RoomPassage) : RoomPosition?
    {
        return when (World.currentRoom)
        {
            passage.roomA -> passage.positionB.adjacentPosition(passage.directionB)
            passage.roomB -> passage.positionA.adjacentPosition(passage.directionA)
            else          -> null
        }
    }
    
    fun isPassageToAnotherLevel() : Boolean
    {
        return exitPassage is LevelPassage
    }
}
