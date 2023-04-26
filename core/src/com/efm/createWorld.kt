package com.efm

import com.efm.entities.*
import com.efm.level.Level
import com.efm.level.World
import com.efm.passage.LevelPassage
import com.efm.passage.RoomPassage
import com.efm.room.*

fun World.createWorld()
{
    // rooms
    
    val forge = Level("forge")
    val forge1 = Room("forge1", 5, 5)
    val forge2 = Room("forge1", 5, 5)
    val forge3 = Room("forge1", 5, 5)
    forge.changeStartingPosition(1, 3)
    forge.changeStartingRoom(forge1)
    
    val furnace = Level("furnace")
    val furnace1 = Room("furnace1", 5, 5)
    val furnace2 = Room("furnace2", 5, 5)
    val furnace3 = Room("furnace3", 5, 5)
    furnace.changeStartingPosition(1, 3)
    furnace.changeStartingRoom(furnace1)
    
    val mines = Level("mines")
    val mines1 = Room("mines1", 5, 5)
    val mines2 = Room("mines2", 5, 5)
    val mines3 = Room("mines3", 5, 5)
    mines.changeStartingPosition(1, 3)
    mines.changeStartingRoom(mines1)
    
    // passages
    
    val betweenForge1AndForge2 = RoomPassage(forge1, RoomPosition(2, 3), forge2, RoomPosition(2, 1), true)
    val betweenForge1AndForge3 = RoomPassage(forge1, RoomPosition(2, 1), forge3, RoomPosition(2, 3), true)
    
    val betweenFurnace1AndFurnace2 = RoomPassage(furnace1, RoomPosition(3, 2), furnace2, RoomPosition(1, 2), true)
    val betweenFurnace1AndFurnace3 = RoomPassage(furnace1, RoomPosition(1, 2), furnace3, RoomPosition(3, 2), true)
    
    val betweenMines1AndMines2 = RoomPassage(mines1, RoomPosition(2, 3), mines2, RoomPosition(2, 1), true)
    val betweenMines2AndMines3 = RoomPassage(mines2, RoomPosition(2, 3), mines3, RoomPosition(2, 1), true)
    
    val fromForge3ToFurnace = LevelPassage(forge3, furnace, true)
    val fromFurnace3ToMines = LevelPassage(furnace3, mines, true)
    
    // forge
    
    forge1.addEntityAt(StoneWall(), 0, 0)
    forge1.addEntityAt(StoneWall(Direction.down), 1, 0)
    forge1.addEntityAt(StoneExit(Direction.down, betweenForge1AndForge2, forge1), 2, 0)
    forge1.addEntityAt(StoneWall(Direction.down), 3, 0)
    forge1.addEntityAt(StoneWall(), 4, 0)
    forge1.addEntityAt(StoneWall(Direction.left), 4, 1)
    forge1.addEntityAt(StoneWall(Direction.left), 4, 2)
    forge1.addEntityAt(StoneWall(Direction.left), 4, 3)
    forge1.addEntityAt(StoneWall(), 4, 4)
    forge1.addEntityAt(StoneWall(Direction.up), 3, 4)
    forge1.addEntityAt(StoneExit(Direction.up, betweenForge1AndForge3, forge1), 2, 4)
    forge1.addEntityAt(StoneWall(Direction.up), 1, 4)
    forge1.addEntityAt(StoneWall(), 0, 4)
    forge1.addEntityAt(StoneWall(Direction.right), 0, 3)
    forge1.addEntityAt(StoneWall(Direction.right), 0, 2)
    forge1.addEntityAt(StoneWall(Direction.right), 0, 1)
    
    forge1.addEntityAt(BladeEnemy(), 2, 2)
    
    for (i in 1..3)
    {
        for (j in 1..3)
        {
            forge1.changeBaseAt(Base.stone, j, i)
        }
    }
    forge1.changeBaseAt(Base.stone, 2, 0)
    forge1.changeBaseAt(Base.stone, 2, 4)
    
    forge2.addEntityAt(StoneWall(), 0, 0)
    forge2.addEntityAt(StoneWall(Direction.down), 1, 0)
    forge2.addEntityAt(StoneWall(Direction.down), 2, 0)
    forge2.addEntityAt(StoneWall(Direction.down), 3, 0)
    forge2.addEntityAt(StoneWall(), 4, 0)
    forge2.addEntityAt(StoneWall(Direction.left), 4, 1)
    forge2.addEntityAt(StoneWall(Direction.left), 4, 2)
    forge2.addEntityAt(StoneWall(Direction.left), 4, 3)
    forge2.addEntityAt(StoneWall(), 4, 4)
    forge2.addEntityAt(StoneWall(Direction.up), 3, 4)
    forge2.addEntityAt(StoneExit(Direction.up, betweenForge1AndForge2, forge2), 2, 4)
    forge2.addEntityAt(StoneWall(Direction.up), 1, 4)
    forge2.addEntityAt(StoneWall(), 0, 4)
    forge2.addEntityAt(StoneWall(Direction.right), 0, 3)
    forge2.addEntityAt(StoneWall(Direction.right), 0, 2)
    forge2.addEntityAt(StoneWall(Direction.right), 0, 1)
    
    forge2.addEntityAt(CrossbowEnemy(), 2, 2)
    
    for (i in 1..3)
    {
        for (j in 1..3)
        {
            forge2.changeBaseAt(Base.stone, j, i)
        }
    }
    forge2.changeBaseAt(Base.stone, 2, 4)
    
    forge3.addEntityAt(StoneWall(Direction.right, Direction.down), 1, 1)
    forge3.addEntityAt(StoneExit(Direction.down, betweenForge1AndForge3, forge3), 2, 0)
    forge3.addEntityAt(StoneWall(Direction.down), 3, 0)
    forge3.addEntityAt(StoneWall(), 4, 0)
    forge3.addEntityAt(StoneWall(Direction.left), 4, 1)
    forge3.addEntityAt(StoneWall(Direction.left), 4, 2)
    forge3.addEntityAt(StoneWall(Direction.up, Direction.left), 3, 3)
    forge3.addEntityAt(MetalExit(Direction.up, fromForge3ToFurnace, forge3), 2, 4)
    forge3.addEntityAt(StoneWall(Direction.up), 1, 4)
    forge3.addEntityAt(StoneWall(), 0, 4)
    forge3.addEntityAt(StoneWall(Direction.right), 0, 3)
    forge3.addEntityAt(StoneWall(Direction.right), 0, 2)
    
    forge3.addEntityAt(BladeEnemyCorpse(), 2, 2)
    
    for (i in 1..3)
    {
        for (j in 1..3)
        {
            forge3.changeBaseAt(Base.stone, j, i)
        }
    }
    forge3.changeBaseAt(Base.stone, 2, 0)
    forge3.changeBaseAt(Base.stone, 2, 4)
    forge3.changeBaseAt(null, 1, 1)
    forge3.changeBaseAt(null, 3, 3)
    
    forge1.updateSpaceList()
    forge1.updateSpacesEntities()
    forge.addRoom(forge1)
    
    forge2.updateSpaceList()
    forge2.updateSpacesEntities()
    forge.addRoom(forge2)
    
    forge3.updateSpaceList()
    forge3.updateSpacesEntities()
    forge.addRoom(forge3)
    
    // furnace
    
    furnace1.addEntityAt(MetalWall(), 0, 0)
    furnace1.addEntityAt(MetalWall(Direction.down), 1, 0)
    furnace1.addEntityAt(MetalWall(Direction.down), 2, 0)
    furnace1.addEntityAt(MetalWall(Direction.down), 3, 0)
    furnace1.addEntityAt(MetalWall(), 4, 0)
    furnace1.addEntityAt(MetalWall(Direction.left), 4, 1)
    furnace1.addEntityAt(MetalExit(Direction.left, betweenFurnace1AndFurnace2, furnace1), 4, 2)
    furnace1.addEntityAt(MetalWall(Direction.left), 4, 3)
    furnace1.addEntityAt(MetalWall(), 4, 4)
    furnace1.addEntityAt(MetalWall(Direction.up), 3, 4)
    furnace1.addEntityAt(MetalWall(Direction.up), 2, 4)
    furnace1.addEntityAt(MetalWall(Direction.up), 1, 4)
    furnace1.addEntityAt(MetalWall(), 0, 4)
    furnace1.addEntityAt(MetalWall(Direction.right), 0, 3)
    furnace1.addEntityAt(MetalExit(Direction.right, betweenFurnace1AndFurnace3, furnace1), 0, 2)
    furnace1.addEntityAt(MetalWall(Direction.right), 0, 1)
    
    furnace1.addEntityAt(CrossbowEnemyCorpse(), 2, 2)
    
    for (i in 1..3)
    {
        for (j in 1..3)
        {
            furnace1.changeBaseAt(Base.metal, j, i)
        }
    }
    furnace1.changeBaseAt(Base.metal, 4, 2)
    furnace1.changeBaseAt(Base.metal, 0, 2)
    
    furnace2.addEntityAt(MetalWall(), 0, 0)
    furnace2.addEntityAt(MetalWall(Direction.down), 1, 0)
    furnace2.addEntityAt(MetalWall(Direction.down), 2, 0)
    furnace2.addEntityAt(MetalWall(Direction.down), 3, 0)
    furnace2.addEntityAt(MetalWall(), 4, 0)
    furnace2.addEntityAt(MetalWall(Direction.left), 4, 1)
    furnace2.addEntityAt(MetalExit(Direction.left, betweenFurnace1AndFurnace2, furnace2), 4, 2)
    furnace2.addEntityAt(MetalWall(Direction.left), 4, 3)
    furnace2.addEntityAt(MetalWall(), 4, 4)
    furnace2.addEntityAt(MetalWall(Direction.up), 3, 4)
    furnace2.addEntityAt(MetalWall(Direction.up), 2, 4)
    furnace2.addEntityAt(MetalWall(Direction.up), 1, 4)
    furnace2.addEntityAt(MetalWall(), 0, 4)
    furnace2.addEntityAt(MetalWall(Direction.right), 0, 3)
    furnace2.addEntityAt(MetalWall(Direction.right), 0, 2)
    furnace2.addEntityAt(MetalWall(Direction.right), 0, 1)
    
    furnace2.addEntityAt(Chest(), 2, 2)
    
    for (i in 1..3)
    {
        for (j in 1..3)
        {
            furnace2.changeBaseAt(Base.metal, j, i)
        }
    }
    furnace2.changeBaseAt(Base.metal, 4, 2)
    
    furnace3.addEntityAt(MetalWall(), 0, 0)
    furnace3.addEntityAt(MetalWall(Direction.down), 1, 0)
    furnace3.addEntityAt(MetalWall(Direction.down), 2, 0)
    furnace3.addEntityAt(MetalWall(Direction.down), 3, 0)
    furnace3.addEntityAt(MetalWall(), 4, 0)
    furnace3.addEntityAt(MetalWall(Direction.left), 4, 1)
    furnace3.addEntityAt(MetalWall(Direction.left), 4, 2)
    furnace3.addEntityAt(MetalWall(Direction.left), 4, 3)
    furnace3.addEntityAt(MetalWall(), 4, 4)
    furnace3.addEntityAt(MetalWall(Direction.up), 3, 4)
    furnace3.addEntityAt(RockExit(Direction.up, betweenFurnace1AndFurnace3, furnace3), 2, 4)
    furnace3.addEntityAt(MetalWall(Direction.up), 1, 4)
    furnace3.addEntityAt(MetalWall(), 0, 4)
    furnace3.addEntityAt(MetalWall(Direction.right), 0, 3)
    furnace3.addEntityAt(MetalExit(Direction.right, fromFurnace3ToMines, furnace3), 0, 2)
    furnace3.addEntityAt(MetalWall(Direction.right), 0, 1)
    
    furnace3.addEntityAt(StoneColumn(), 2, 2)
    
    for (i in 1..3)
    {
        for (j in 1..3)
        {
            furnace3.changeBaseAt(Base.metal, j, i)
        }
    }
    furnace3.changeBaseAt(Base.metal, 0, 2)
    furnace3.changeBaseAt(Base.metal, 2, 4)
    
    furnace1.updateSpaceList()
    furnace1.updateSpacesEntities()
    furnace.addRoom(furnace1)
    
    furnace2.updateSpaceList()
    furnace2.updateSpacesEntities()
    furnace.addRoom(furnace2)
    
    furnace3.updateSpaceList()
    furnace3.updateSpacesEntities()
    furnace.addRoom(furnace3)
    
    // mines
    
    mines1.addEntityAt(RockWall(), 0, 0)
    mines1.addEntityAt(RockWall(Direction.down), 1, 0)
    mines1.addEntityAt(RockWall(Direction.down), 2, 0)
    mines1.addEntityAt(RockWall(Direction.down), 3, 0)
    mines1.addEntityAt(RockWall(), 4, 0)
    mines1.addEntityAt(RockWall(Direction.left), 4, 1)
    mines1.addEntityAt(RockWall(Direction.left), 4, 2)
    mines1.addEntityAt(RockWall(Direction.left), 4, 3)
    mines1.addEntityAt(RockWall(), 4, 4)
    mines1.addEntityAt(RockWall(Direction.up), 3, 4)
    mines1.addEntityAt(RockExit(Direction.up, betweenMines1AndMines2, mines1), 2, 4)
    mines1.addEntityAt(RockWall(Direction.up), 1, 4)
    mines1.addEntityAt(RockWall(), 0, 4)
    mines1.addEntityAt(RockWall(Direction.right), 0, 3)
    mines1.addEntityAt(RockWall(Direction.right), 0, 2)
    mines1.addEntityAt(RockWall(Direction.right), 0, 1)
    
    mines1.addEntityAt(ExplodingBarrel(), 2, 2)
    
    for (i in 1..3)
    {
        for (j in 1..3)
        {
            mines1.changeBaseAt(Base.rock, j, i)
        }
    }
    mines1.changeBaseAt(Base.rock, 2, 4)
    
    mines2.addEntityAt(RockWall(), 0, 0)
    mines2.addEntityAt(RockWall(Direction.down), 1, 0)
    mines2.addEntityAt(RockExit(Direction.down, betweenMines1AndMines2, mines2), 2, 0)
    mines2.addEntityAt(RockWall(Direction.down), 3, 0)
    mines2.addEntityAt(RockWall(), 4, 0)
    mines2.addEntityAt(RockWall(Direction.left), 4, 1)
    mines2.addEntityAt(RockWall(Direction.left), 4, 2)
    mines2.addEntityAt(RockWall(Direction.left), 4, 3)
    mines2.addEntityAt(RockWall(), 4, 4)
    mines2.addEntityAt(RockWall(Direction.up), 3, 4)
    mines2.addEntityAt(RockExit(Direction.up, betweenMines2AndMines3, mines2), 2, 4)
    mines2.addEntityAt(RockWall(Direction.up), 1, 4)
    mines2.addEntityAt(RockWall(), 0, 4)
    mines2.addEntityAt(RockWall(Direction.right), 0, 3)
    mines2.addEntityAt(RockWall(Direction.right), 0, 2)
    mines2.addEntityAt(RockWall(Direction.right), 0, 1)
    
    for (i in 1..3)
    {
        for (j in 1..3)
        {
            mines2.changeBaseAt(Base.rock, j, i)
        }
    }
    mines2.changeBaseAt(Base.rock, 2, 0)
    mines2.changeBaseAt(Base.rock, 2, 4)
    
    mines3.addEntityAt(RockWall(), 0, 0)
    mines3.addEntityAt(RockWall(Direction.down), 1, 0)
    mines3.addEntityAt(RockExit(Direction.down, betweenMines2AndMines3, mines3), 2, 0)
    mines3.addEntityAt(RockWall(Direction.down), 3, 0)
    mines3.addEntityAt(RockWall(), 4, 0)
    mines3.addEntityAt(RockWall(Direction.left), 4, 1)
    mines3.addEntityAt(RockWall(Direction.left), 4, 2)
    mines3.addEntityAt(RockWall(Direction.left), 4, 3)
    mines3.addEntityAt(RockWall(), 4, 4)
    mines3.addEntityAt(RockWall(Direction.up), 3, 4)
    mines3.addEntityAt(RockWall(Direction.up), 2, 4)
    mines3.addEntityAt(RockWall(Direction.up), 1, 4)
    mines3.addEntityAt(RockWall(), 0, 4)
    mines3.addEntityAt(RockWall(Direction.right), 0, 3)
    mines3.addEntityAt(RockWall(Direction.right), 0, 2)
    mines3.addEntityAt(RockWall(Direction.right), 0, 1)
    
    for (i in 1..3)
    {
        for (j in 1..3)
        {
            mines3.changeBaseAt(Base.rock, j, i)
        }
    }
    mines3.changeBaseAt(Base.rock, 2, 0)
    
    mines1.updateSpaceList()
    mines1.updateSpacesEntities()
    mines.addRoom(mines1)
    
    mines2.updateSpaceList()
    mines2.updateSpacesEntities()
    mines.addRoom(mines2)
    
    mines3.updateSpaceList()
    mines3.updateSpacesEntities()
    mines.addRoom(mines3)
    
    // world
    
    addLevel(forge)
    addLevel(furnace)
    addLevel(mines)
    
    changeCurrentLevel(forge)
    changeCurrentRoom(forge1)
    
    forge1.addEntityAt(hero, forge.getStartingPosition())
    forge1.updateSpacesEntities()
}
