package com.efm.passage

import com.efm.*
import com.efm.entity.Entity
import com.efm.entity.Interactive
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition

/**
 * Interactive object found in a Room used to leave it. Created based on a Passage.
 */
interface Exit : Entity, Interactive
{
    val exitPassage : Passage
    val direction : Direction
    
    override fun interact()
    {
        when (val passage = exitPassage)
        {
            is RoomPassage  -> travelBetweenRooms(passage)
            is LevelPassage -> travelBetweenLevels(passage)
        }
        adjustCameraAfterMoving()
        adjustMapLayersAfterMoving()
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
        
        World.currentRoom.removeEntity(World.hero)
        World.changeCurrentLevel(newLevel)
        World.changeCurrentRoom(newRoom)
        World.currentRoom.addEntityAt(World.hero, newPosition)
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
    
}
