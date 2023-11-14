package com.efm.exit

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction4
import com.efm.adjustCameraAfterMoving
import com.efm.adjustMapLayersAfterMoving
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition

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
        Direction4.up -> style.tiles.exitUpOutlineTeal
        Direction4.right -> style.tiles.exitRightOutlineTeal
        Direction4.down -> style.tiles.exitDownOutlineTeal
        Direction4.left -> style.tiles.exitLeftOutlineTeal
    }
    
    override fun getTile() : TiledMapTile? = when (direction)
    {
        Direction4.up -> style.tiles.exitUp
        Direction4.right -> style.tiles.exitRight
        Direction4.down -> style.tiles.exitDown
        Direction4.left -> style.tiles.exitLeft
    }
    
    override fun interact()
    {
        if (!activeWhenNoEnemiesAreInRoom || !World.currentRoom.areEnemiesInRoom())
        {
            travelBetweenRooms()
            Gdx.app.log("Exit", "adjusting camera")
            adjustCameraAfterMoving()
            adjustMapLayersAfterMoving()
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
    }
    
}