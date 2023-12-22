package com.efm.exit

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.*
import com.efm.assets.Tiles
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.state.State
import com.efm.state.getState
import com.efm.ui.gameScreen.TutorialPopups

open class RoomExit(
        override val position : RoomPosition,
        override var direction : Direction4,
        var endRoomName : String,
        var endPosition : RoomPosition,
        override var style : ExitStyle,
        var endDirection : Direction4 = direction.opposite(),
        override var activeWhenNoEnemiesAreInRoom : Boolean = false,
        override var active : Boolean = true
                   ) : Exit
{
    override fun getTile() : TiledMapTile?
    {
        return if (isOpen())
        {
            when (direction)
            {
                Direction4.up    -> style.tiles.exitUp
                Direction4.right -> style.tiles.exitRight
                Direction4.down  -> style.tiles.exitDown
                Direction4.left  -> style.tiles.exitLeft
            }
        }
        else
        {
            when (direction)
            {
                Direction4.up    -> style.tiles.exitUpClosed
                Direction4.right -> style.tiles.exitRightClosed
                Direction4.down  -> style.tiles.exitDownClosed
                Direction4.left  -> style.tiles.exitLeftClosed
            }
        }
    }
    
    override fun getOutlineTealTile() : TiledMapTile?
    {
        return if (isOpen())
        {
            when (direction)
            {
                Direction4.up    -> Tiles.ExitUpOutlineTeal
                Direction4.right -> Tiles.ExitRightOutlineTeal
                Direction4.down  -> Tiles.ExitDownOutlineTeal
                Direction4.left  -> Tiles.ExitLeftOutlineTeal
            }
        }
        else
        {
            when (direction)
            {
                Direction4.up    -> Tiles.ExitUpOutlineRed
                Direction4.right -> Tiles.ExitRightOutlineRed
                Direction4.down  -> Tiles.ExitDownOutlineRed
                Direction4.left  -> Tiles.ExitLeftOutlineRed
            }
        }
    }
    
    /** do not allow travel that would change state combat to state free **/
    private fun travelWouldChangeStateCombatToStateFree(targetRoom : Room) : Boolean
    {
        val stateIsStateCombat = getState() is State.combat
        val targetRoomIsEmpty = !targetRoom.areEnemiesInRoom()
        return stateIsStateCombat && targetRoomIsEmpty
    }
    
    override fun isOpen() : Boolean
    {
        val worldCurrentLevel = World.currentLevel
        val worldCurrentRoom = World.currentRoom
        
        val isOpen = if (worldCurrentLevel != null && worldCurrentRoom != null)
        {
            val targetRoom = worldCurrentLevel.rooms.find { it.name == endRoomName }
            if (targetRoom != null)
            {
                super.isOpen() && !travelWouldChangeStateCombatToStateFree(targetRoom)
            }
            else false
        }
        else false
        
        return isOpen
    }
    
    override fun interact()
    {
        if (isOpen())
        {
            travelBetweenRooms()
            Gdx.app.log("Exit", "adjusting camera")
            adjustCameraAfterMoving()
            adjustMapLayersAfterMoving()
        }
        else if (getState().tutorialFlags.tutorialActive && !getState().tutorialFlags.closedExitPopupShown)
        {
            TutorialPopups.addPopupToDisplay(TutorialPopups.closedExitPopup)
            TutorialPopups.closedExitPopup.isVisible = true
            getState().tutorialFlags.closedExitPopupShown = true
        }
    }
    
    private fun travelBetweenRooms()
    {
        val worldCurrentLevel = World.currentLevel ?: return
        val worldCurrentRoom = World.currentRoom ?: return
        
        val newRoom : Room? = worldCurrentLevel.rooms.find { it.name == endRoomName }
        val newPosition : RoomPosition = endPosition.adjacentPosition(endDirection)
        
        println("endRoomName : $endRoomName")
        println("newRoom : $newRoom")
        println("World.currentRoom.name : ${World.currentRoom?.name}")
        
        if (newRoom == null) throw Exception("Cannot find the Room that the Exit leads to.")
        
        worldCurrentRoom.removeEntity(World.hero)
        World.changeCurrentRoom(newRoom)
        World.currentRoom?.addEntityAt(World.hero, newPosition)
        
        saveGame()
    }
    
    // for serializing
    
    constructor() : this(RoomPosition(-1, -1), Direction4.up, "", RoomPosition(-1, -1), ExitStyle.stone)
    
    override fun write(json : Json?)
    {
        super.write(json)
        
        if (json != null)
        {
            json.writeValue("endRoomName", this.endRoomName)
            json.writeValue("endPosition", this.endPosition)
            json.writeValue("endDirection", this.endDirection)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super.read(json, jsonData)
        
        if (json != null)
        {
            val jsonEndRoomName = json.readValue("endRoomName", String::class.java, jsonData)
            if (jsonEndRoomName != null) this.endRoomName = jsonEndRoomName
            val jsonEndPosition = json.readValue("endPosition", RoomPosition::class.java, jsonData)
            if (jsonEndPosition != null) this.endPosition.set(jsonEndPosition)
            val jsonEndDirection = json.readValue("endDirection", Direction4::class.java, jsonData)
            if (jsonEndDirection != null) this.endDirection = jsonEndDirection
        }
    }
    
}