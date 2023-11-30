package com.efm.exit

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.*
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.state.State
import com.efm.state.getState

open class RoomExit(
        override val position : RoomPosition,
        override var direction : Direction4,
        var endRoomName : String,
        var endPosition : RoomPosition,
        override var style : ExitStyle,
        var endDirection : Direction4 = direction.opposite(),
        override var activeWhenNoEnemiesAreInRoom : Boolean = false
                   ) : Exit
{
    override fun getOutlineTealTile() : TiledMapTile? = when (direction)
    {
        Direction4.up    -> style.tiles.exitUpOutlineTeal
        Direction4.right -> style.tiles.exitRightOutlineTeal
        Direction4.down  -> style.tiles.exitDownOutlineTeal
        Direction4.left  -> style.tiles.exitLeftOutlineTeal
    }
    
    override fun getTile() : TiledMapTile? = when (direction)
    {
        Direction4.up    -> style.tiles.exitUp
        Direction4.right -> style.tiles.exitRight
        Direction4.down  -> style.tiles.exitDown
        Direction4.left  -> style.tiles.exitLeft
    }
    
    override fun interact()
    {
        if (!activeWhenNoEnemiesAreInRoom || !World.currentRoom.areEnemiesInRoom())
        {
            val targetRoom : Room? = World.currentLevel.rooms.find { it.name == endRoomName }
            // do not allow travel that would change state combat to state free
            if (!(getState() is State.combat && targetRoom?.areEnemiesInRoom() == false))
            {
                travelBetweenRooms()
                Gdx.app.log("Exit", "adjusting camera")
                adjustCameraAfterMoving()
                adjustMapLayersAfterMoving()
            }
        }
    }
    
    private fun travelBetweenRooms()
    {
        val newRoom : Room? = World.currentLevel.rooms.find { it.name == endRoomName }
        val newPosition : RoomPosition = endPosition.adjacentPosition(endDirection)
        
        println("endRoomName : $endRoomName")
        println("newRoom : $newRoom")
        println("World.currentRoom.name : ${World.currentRoom.name}")
        
        if (newRoom == null) throw Exception("Cannot find the Room that the Exit leads to.")
        
        World.currentRoom.removeEntity(World.hero)
        World.changeCurrentRoom(newRoom)
        World.currentRoom.addEntityAt(World.hero, newPosition)
        
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