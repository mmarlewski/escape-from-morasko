package com.efm

import com.efm.entities.BladeEnemy
import com.efm.entities.StoneWall
import com.efm.level.Level
import com.efm.level.World
import com.efm.room.Base
import com.efm.room.Room

fun World.createWorld()
{
    // forge
    
    val forge = Level("forge")
    
    val forge1 = Room("forge1", 5, 5)
    
    forge1.addEntityAt(StoneWall(false, false, false, false), 0, 0)
    forge1.addEntityAt(StoneWall(false, false, true, false), 1, 0)
    forge1.addEntityAt(StoneWall(false, false, true, false), 2, 0)
    forge1.addEntityAt(StoneWall(false, false, true, false), 3, 0)
    forge1.addEntityAt(StoneWall(false, false, false, false), 4, 0)
    forge1.addEntityAt(StoneWall(false, false, false, true), 4, 1)
    forge1.addEntityAt(StoneWall(false, false, false, true), 4, 2)
    forge1.addEntityAt(StoneWall(false, false, false, true), 4, 3)
    forge1.addEntityAt(StoneWall(false, false, false, false), 4, 4)
    forge1.addEntityAt(StoneWall(true, false, false, false), 3, 4)
    forge1.addEntityAt(StoneWall(true, false, false, false), 2, 4)
    forge1.addEntityAt(StoneWall(true, false, false, false), 1, 4)
    forge1.addEntityAt(StoneWall(false, false, false, false), 0, 4)
    forge1.addEntityAt(StoneWall(false, true, false, false), 0, 3)
    forge1.addEntityAt(StoneWall(false, true, false, false), 0, 2)
    forge1.addEntityAt(StoneWall(false, true, false, false), 0, 1)
    
    forge1.addEntityAt(BladeEnemy(), 1, 1)
    
    for (i in 1..4)
    {
        for (j in 1..4)
        {
            forge1.changeBaseAt(Base.stone, j, i)
        }
    }
    
    val forge2 = Room("forge1", 5, 5)
    
    forge2.addEntityAt(StoneWall(false, false, false, false), 0, 0)
    forge2.addEntityAt(StoneWall(false, false, true, false), 1, 0)
    forge2.addEntityAt(StoneWall(false, false, true, false), 2, 0)
    forge2.addEntityAt(StoneWall(false, false, true, false), 3, 0)
    forge2.addEntityAt(StoneWall(false, false, false, false), 4, 0)
    forge2.addEntityAt(StoneWall(false, false, false, true), 4, 1)
    forge2.addEntityAt(StoneWall(false, false, false, true), 4, 2)
    forge2.addEntityAt(StoneWall(false, false, false, true), 4, 3)
    forge2.addEntityAt(StoneWall(false, false, false, false), 4, 4)
    forge2.addEntityAt(StoneWall(true, false, false, false), 3, 4)
    forge2.addEntityAt(StoneWall(true, false, false, false), 2, 4)
    forge2.addEntityAt(StoneWall(true, false, false, false), 1, 4)
    forge2.addEntityAt(StoneWall(false, false, false, false), 0, 4)
    forge2.addEntityAt(StoneWall(false, true, false, false), 0, 3)
    forge2.addEntityAt(StoneWall(false, true, false, false), 0, 2)
    forge2.addEntityAt(StoneWall(false, true, false, false), 0, 1)
    
    forge2.addEntityAt(BladeEnemy(), 1, 1)
    
    for (i in 1..4)
    {
        for (j in 1..4)
        {
            forge2.changeBaseAt(Base.stone, j, i)
        }
    }
    
    val forge3 = Room("forge1", 5, 5)
    
    forge3.addEntityAt(StoneWall(false, false, false, false), 0, 0)
    forge3.addEntityAt(StoneWall(false, false, true, false), 1, 0)
    forge3.addEntityAt(StoneWall(false, false, true, false), 2, 0)
    forge3.addEntityAt(StoneWall(false, false, true, false), 3, 0)
    forge3.addEntityAt(StoneWall(false, false, false, false), 4, 0)
    forge3.addEntityAt(StoneWall(false, false, false, true), 4, 1)
    forge3.addEntityAt(StoneWall(false, false, false, true), 4, 2)
    forge3.addEntityAt(StoneWall(false, false, false, true), 4, 3)
    forge3.addEntityAt(StoneWall(false, false, false, false), 4, 4)
    forge3.addEntityAt(StoneWall(true, false, false, false), 3, 4)
    forge3.addEntityAt(StoneWall(true, false, false, false), 2, 4)
    forge3.addEntityAt(StoneWall(true, false, false, false), 1, 4)
    forge3.addEntityAt(StoneWall(false, false, false, false), 0, 4)
    forge3.addEntityAt(StoneWall(false, true, false, false), 0, 3)
    forge3.addEntityAt(StoneWall(false, true, false, false), 0, 2)
    forge3.addEntityAt(StoneWall(false, true, false, false), 0, 1)
    
    forge3.addEntityAt(BladeEnemy(), 1, 1)
    
    for (i in 1..4)
    {
        for (j in 1..4)
        {
            forge3.changeBaseAt(Base.stone, j, i)
        }
    }
    
    forge1.updateSpaceList()
    forge1.updateSpacesEntities()
    forge.addRoom(forge1)
    
    forge2.updateSpaceList()
    forge2.updateSpacesEntities()
    forge.addRoom(forge2)
    
    forge3.updateSpaceList()
    forge3.updateSpacesEntities()
    forge.addRoom(forge3)
    
    forge.changeStartingRoom(forge1)
    forge.changeStartingPosition(2, 2)
    
    // furnace
    
    val furnace = Level("furnace")
    
    val furnace1 = Room("furnace1", 5, 5)
    
    furnace1.addEntityAt(StoneWall(false, false, false, false), 0, 0)
    furnace1.addEntityAt(StoneWall(false, false, true, false), 1, 0)
    furnace1.addEntityAt(StoneWall(false, false, true, false), 2, 0)
    furnace1.addEntityAt(StoneWall(false, false, true, false), 3, 0)
    furnace1.addEntityAt(StoneWall(false, false, false, false), 4, 0)
    furnace1.addEntityAt(StoneWall(false, false, false, true), 4, 1)
    furnace1.addEntityAt(StoneWall(false, false, false, true), 4, 2)
    furnace1.addEntityAt(StoneWall(false, false, false, true), 4, 3)
    furnace1.addEntityAt(StoneWall(false, false, false, false), 4, 4)
    furnace1.addEntityAt(StoneWall(true, false, false, false), 3, 4)
    furnace1.addEntityAt(StoneWall(true, false, false, false), 2, 4)
    furnace1.addEntityAt(StoneWall(true, false, false, false), 1, 4)
    furnace1.addEntityAt(StoneWall(false, false, false, false), 0, 4)
    furnace1.addEntityAt(StoneWall(false, true, false, false), 0, 3)
    furnace1.addEntityAt(StoneWall(false, true, false, false), 0, 2)
    furnace1.addEntityAt(StoneWall(false, true, false, false), 0, 1)
    
    furnace1.addEntityAt(BladeEnemy(), 1, 1)
    
    for (i in 1..4)
    {
        for (j in 1..4)
        {
            furnace1.changeBaseAt(Base.stone, j, i)
        }
    }
    
    val furnace2 = Room("furnace2", 5, 5)
    
    furnace2.addEntityAt(StoneWall(false, false, false, false), 0, 0)
    furnace2.addEntityAt(StoneWall(false, false, true, false), 1, 0)
    furnace2.addEntityAt(StoneWall(false, false, true, false), 2, 0)
    furnace2.addEntityAt(StoneWall(false, false, true, false), 3, 0)
    furnace2.addEntityAt(StoneWall(false, false, false, false), 4, 0)
    furnace2.addEntityAt(StoneWall(false, false, false, true), 4, 1)
    furnace2.addEntityAt(StoneWall(false, false, false, true), 4, 2)
    furnace2.addEntityAt(StoneWall(false, false, false, true), 4, 3)
    furnace2.addEntityAt(StoneWall(false, false, false, false), 4, 4)
    furnace2.addEntityAt(StoneWall(true, false, false, false), 3, 4)
    furnace2.addEntityAt(StoneWall(true, false, false, false), 2, 4)
    furnace2.addEntityAt(StoneWall(true, false, false, false), 1, 4)
    furnace2.addEntityAt(StoneWall(false, false, false, false), 0, 4)
    furnace2.addEntityAt(StoneWall(false, true, false, false), 0, 3)
    furnace2.addEntityAt(StoneWall(false, true, false, false), 0, 2)
    furnace2.addEntityAt(StoneWall(false, true, false, false), 0, 1)
    
    furnace2.addEntityAt(BladeEnemy(), 1, 1)
    
    for (i in 1..4)
    {
        for (j in 1..4)
        {
            furnace2.changeBaseAt(Base.stone, j, i)
        }
    }
    
    val furnace3 = Room("furnace3", 5, 5)
    
    furnace3.addEntityAt(StoneWall(false, false, false, false), 0, 0)
    furnace3.addEntityAt(StoneWall(false, false, true, false), 1, 0)
    furnace3.addEntityAt(StoneWall(false, false, true, false), 2, 0)
    furnace3.addEntityAt(StoneWall(false, false, true, false), 3, 0)
    furnace3.addEntityAt(StoneWall(false, false, false, false), 4, 0)
    furnace3.addEntityAt(StoneWall(false, false, false, true), 4, 1)
    furnace3.addEntityAt(StoneWall(false, false, false, true), 4, 2)
    furnace3.addEntityAt(StoneWall(false, false, false, true), 4, 3)
    furnace3.addEntityAt(StoneWall(false, false, false, false), 4, 4)
    furnace3.addEntityAt(StoneWall(true, false, false, false), 3, 4)
    furnace3.addEntityAt(StoneWall(true, false, false, false), 2, 4)
    furnace3.addEntityAt(StoneWall(true, false, false, false), 1, 4)
    furnace3.addEntityAt(StoneWall(false, false, false, false), 0, 4)
    furnace3.addEntityAt(StoneWall(false, true, false, false), 0, 3)
    furnace3.addEntityAt(StoneWall(false, true, false, false), 0, 2)
    furnace3.addEntityAt(StoneWall(false, true, false, false), 0, 1)
    
    furnace3.addEntityAt(BladeEnemy(), 1, 1)
    
    for (i in 1..4)
    {
        for (j in 1..4)
        {
            furnace3.changeBaseAt(Base.stone, j, i)
        }
    }
    
    furnace1.updateSpaceList()
    furnace1.updateSpacesEntities()
    furnace.addRoom(furnace1)
    
    furnace2.updateSpaceList()
    furnace2.updateSpacesEntities()
    furnace.addRoom(furnace2)
    
    furnace3.updateSpaceList()
    furnace3.updateSpacesEntities()
    furnace.addRoom(furnace3)
    
    furnace.changeStartingRoom(furnace1)
    furnace.changeStartingPosition(2, 2)
    
    // mines
    
    val mines = Level("mines")
    
    val mines1 = Room("mines1", 5, 5)
    
    mines1.addEntityAt(StoneWall(false, false, false, false), 0, 0)
    mines1.addEntityAt(StoneWall(false, false, true, false), 1, 0)
    mines1.addEntityAt(StoneWall(false, false, true, false), 2, 0)
    mines1.addEntityAt(StoneWall(false, false, true, false), 3, 0)
    mines1.addEntityAt(StoneWall(false, false, false, false), 4, 0)
    mines1.addEntityAt(StoneWall(false, false, false, true), 4, 1)
    mines1.addEntityAt(StoneWall(false, false, false, true), 4, 2)
    mines1.addEntityAt(StoneWall(false, false, false, true), 4, 3)
    mines1.addEntityAt(StoneWall(false, false, false, false), 4, 4)
    mines1.addEntityAt(StoneWall(true, false, false, false), 3, 4)
    mines1.addEntityAt(StoneWall(true, false, false, false), 2, 4)
    mines1.addEntityAt(StoneWall(true, false, false, false), 1, 4)
    mines1.addEntityAt(StoneWall(false, false, false, false), 0, 4)
    mines1.addEntityAt(StoneWall(false, true, false, false), 0, 3)
    mines1.addEntityAt(StoneWall(false, true, false, false), 0, 2)
    mines1.addEntityAt(StoneWall(false, true, false, false), 0, 1)
    
    mines1.addEntityAt(BladeEnemy(), 1, 1)
    
    for (i in 1..4)
    {
        for (j in 1..4)
        {
            mines1.changeBaseAt(Base.stone, j, i)
        }
    }
    
    val mines2 = Room("mines2", 5, 5)
    
    mines2.addEntityAt(StoneWall(false, false, false, false), 0, 0)
    mines2.addEntityAt(StoneWall(false, false, true, false), 1, 0)
    mines2.addEntityAt(StoneWall(false, false, true, false), 2, 0)
    mines2.addEntityAt(StoneWall(false, false, true, false), 3, 0)
    mines2.addEntityAt(StoneWall(false, false, false, false), 4, 0)
    mines2.addEntityAt(StoneWall(false, false, false, true), 4, 1)
    mines2.addEntityAt(StoneWall(false, false, false, true), 4, 2)
    mines2.addEntityAt(StoneWall(false, false, false, true), 4, 3)
    mines2.addEntityAt(StoneWall(false, false, false, false), 4, 4)
    mines2.addEntityAt(StoneWall(true, false, false, false), 3, 4)
    mines2.addEntityAt(StoneWall(true, false, false, false), 2, 4)
    mines2.addEntityAt(StoneWall(true, false, false, false), 1, 4)
    mines2.addEntityAt(StoneWall(false, false, false, false), 0, 4)
    mines2.addEntityAt(StoneWall(false, true, false, false), 0, 3)
    mines2.addEntityAt(StoneWall(false, true, false, false), 0, 2)
    mines2.addEntityAt(StoneWall(false, true, false, false), 0, 1)
    
    mines2.addEntityAt(BladeEnemy(), 1, 1)
    
    for (i in 1..4)
    {
        for (j in 1..4)
        {
            mines2.changeBaseAt(Base.stone, j, i)
        }
    }
    
    val mines3 = Room("mines3", 5, 5)
    
    mines3.addEntityAt(StoneWall(false, false, false, false), 0, 0)
    mines3.addEntityAt(StoneWall(false, false, true, false), 1, 0)
    mines3.addEntityAt(StoneWall(false, false, true, false), 2, 0)
    mines3.addEntityAt(StoneWall(false, false, true, false), 3, 0)
    mines3.addEntityAt(StoneWall(false, false, false, false), 4, 0)
    mines3.addEntityAt(StoneWall(false, false, false, true), 4, 1)
    mines3.addEntityAt(StoneWall(false, false, false, true), 4, 2)
    mines3.addEntityAt(StoneWall(false, false, false, true), 4, 3)
    mines3.addEntityAt(StoneWall(false, false, false, false), 4, 4)
    mines3.addEntityAt(StoneWall(true, false, false, false), 3, 4)
    mines3.addEntityAt(StoneWall(true, false, false, false), 2, 4)
    mines3.addEntityAt(StoneWall(true, false, false, false), 1, 4)
    mines3.addEntityAt(StoneWall(false, false, false, false), 0, 4)
    mines3.addEntityAt(StoneWall(false, true, false, false), 0, 3)
    mines3.addEntityAt(StoneWall(false, true, false, false), 0, 2)
    mines3.addEntityAt(StoneWall(false, true, false, false), 0, 1)
    
    mines3.addEntityAt(BladeEnemy(), 1, 1)
    
    for (i in 1..4)
    {
        for (j in 1..4)
        {
            mines3.changeBaseAt(Base.stone, j, i)
        }
    }
    
    mines1.updateSpaceList()
    mines1.updateSpacesEntities()
    mines.addRoom(mines1)
    
    mines2.updateSpaceList()
    mines2.updateSpacesEntities()
    mines.addRoom(mines2)
    
    mines3.updateSpaceList()
    mines3.updateSpacesEntities()
    mines.addRoom(mines3)
    
    mines.changeStartingRoom(mines1)
    mines.changeStartingPosition(2, 2)
    
    // world
    
    addLevel(forge)
    addLevel(furnace)
    addLevel(mines)
    
    changeCurrentLevel(forge)
    changeCurrentRoom(forge1)
    forge1.addEntityAt(hero, forge.getStartingPosition())
}
