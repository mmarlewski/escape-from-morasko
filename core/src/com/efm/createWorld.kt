package com.efm

import com.efm.entities.Chest
import com.efm.entities.ExplodingBarrel
import com.efm.entities.bosses.*
import com.efm.entities.bosses.slime.BossSlime
import com.efm.entities.enemies.*
import com.efm.entities.enemies.Boar.EnemyBoar
import com.efm.entities.enemies.Boar.EnemyGhost
import com.efm.entities.exits.StoneExit
import com.efm.entities.exits.StoneExitActiveWhenNoEnemiesAreInRoom
import com.efm.entities.walls.*
import com.efm.level.Level
import com.efm.level.World
import com.efm.passage.LevelPassage
import com.efm.passage.RoomPassage
import com.efm.room.*
import com.efm.stackableMapItems.Bomb
import com.efm.stackableSelfItems.*

/*
fun World.createWorldPrototypeOne()
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
    
    val betweenForge1AndForge2 = RoomPassage(forge1, RoomPosition(2, 3), Direction4.down, forge2, RoomPosition(2, 1))
    val betweenForge1AndForge3 = RoomPassage(forge1, RoomPosition(2, 1), Direction4.up, forge3, RoomPosition(2, 3))
    
    val betweenFurnace1AndFurnace2 = RoomPassage(furnace1, RoomPosition(3, 2), Direction4.left, furnace2, RoomPosition(1, 2))
    val betweenFurnace1AndFurnace3 =
            RoomPassage(furnace1, RoomPosition(1, 2), Direction4.right, furnace3, RoomPosition(3, 2))
    
    val betweenMines1AndMines2 = RoomPassage(mines1, RoomPosition(2, 3), Direction4.up, mines2, RoomPosition(2, 1))
    val betweenMines2AndMines3 = RoomPassage(mines2, RoomPosition(2, 3), Direction4.up, mines3, RoomPosition(2, 1))
    
    //val fromForge3ToFurnace = LevelPassage(forge3, Direction4.up, furnace)
    //val fromFurnace3ToMines = LevelPassage(furnace3, Direction4.right, mines, true)
    
    // forge
    
    forge1.addEntityAt(StoneWall(), 0, 0)
    forge1.addEntityAt(StoneWall(Direction4.down), 1, 0)
    forge1.addEntityAt(StoneExit(Direction4.down, betweenForge1AndForge2), 2, 0)
    forge1.addEntityAt(StoneWall(Direction4.down), 3, 0)
    forge1.addEntityAt(StoneWall(), 4, 0)
    forge1.addEntityAt(StoneWall(Direction4.left), 4, 1)
    forge1.addEntityAt(StoneWall(Direction4.left), 4, 2)
    forge1.addEntityAt(StoneWall(Direction4.left), 4, 3)
    forge1.addEntityAt(StoneWall(), 4, 4)
    forge1.addEntityAt(StoneWall(Direction4.up), 3, 4)
    forge1.addEntityAt(StoneExit(Direction4.up, betweenForge1AndForge3), 2, 4)
    forge1.addEntityAt(StoneWall(Direction4.up), 1, 4)
    forge1.addEntityAt(StoneWall(), 0, 4)
    forge1.addEntityAt(StoneWall(Direction4.right), 0, 3)
    forge1.addEntityAt(StoneWall(Direction4.right), 0, 2)
    forge1.addEntityAt(StoneWall(Direction4.right), 0, 1)
    
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
    forge2.addEntityAt(StoneWall(Direction4.down), 1, 0)
    forge2.addEntityAt(StoneWall(Direction4.down), 2, 0)
    forge2.addEntityAt(StoneWall(Direction4.down), 3, 0)
    forge2.addEntityAt(StoneWall(), 4, 0)
    forge2.addEntityAt(StoneWall(Direction4.left), 4, 1)
    forge2.addEntityAt(StoneWall(Direction4.left), 4, 2)
    forge2.addEntityAt(StoneWall(Direction4.left), 4, 3)
    forge2.addEntityAt(StoneWall(), 4, 4)
    forge2.addEntityAt(StoneWall(Direction4.up), 3, 4)
    forge2.addEntityAt(StoneExit(Direction4.up, betweenForge1AndForge2), 2, 4)
    forge2.addEntityAt(StoneWall(Direction4.up), 1, 4)
    forge2.addEntityAt(StoneWall(), 0, 4)
    forge2.addEntityAt(StoneWall(Direction4.right), 0, 3)
    forge2.addEntityAt(StoneWall(Direction4.right), 0, 2)
    forge2.addEntityAt(StoneWall(Direction4.right), 0, 1)
    
    forge2.addEntityAt(CrossbowEnemy(), 2, 2)
    
    for (i in 1..3)
    {
        for (j in 1..3)
        {
            forge2.changeBaseAt(Base.stone, j, i)
        }
    }
    forge2.changeBaseAt(Base.stone, 2, 4)
    
    forge3.addEntityAt(StoneWall(Direction4.right, Direction4.down), 1, 1)
    forge3.addEntityAt(StoneExit(Direction4.down, betweenForge1AndForge3), 2, 0)
    forge3.addEntityAt(StoneWall(Direction4.down), 3, 0)
    forge3.addEntityAt(StoneWall(), 4, 0)
    forge3.addEntityAt(StoneWall(Direction4.left), 4, 1)
    forge3.addEntityAt(StoneWall(Direction4.left), 4, 2)
    forge3.addEntityAt(StoneWall(Direction4.up, Direction4.left), 3, 3)
    //forge3.addEntityAt(MetalExit(Direction4.up, fromForge3ToFurnace), 2, 4)
    forge3.addEntityAt(StoneWall(Direction4.up), 1, 4)
    forge3.addEntityAt(StoneWall(), 0, 4)
    forge3.addEntityAt(StoneWall(Direction4.right), 0, 3)
    forge3.addEntityAt(StoneWall(Direction4.right), 0, 2)
    
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
    furnace1.addEntityAt(MetalWall(Direction4.down), 1, 0)
    furnace1.addEntityAt(MetalWall(Direction4.down), 2, 0)
    furnace1.addEntityAt(MetalWall(Direction4.down), 3, 0)
    furnace1.addEntityAt(MetalWall(), 4, 0)
    furnace1.addEntityAt(MetalWall(Direction4.left), 4, 1)
    furnace1.addEntityAt(MetalExit(Direction4.left, betweenFurnace1AndFurnace2), 4, 2)
    furnace1.addEntityAt(MetalWall(Direction4.left), 4, 3)
    furnace1.addEntityAt(MetalWall(), 4, 4)
    furnace1.addEntityAt(MetalWall(Direction4.up), 3, 4)
    furnace1.addEntityAt(MetalWall(Direction4.up), 2, 4)
    furnace1.addEntityAt(MetalWall(Direction4.up), 1, 4)
    furnace1.addEntityAt(MetalWall(), 0, 4)
    furnace1.addEntityAt(MetalWall(Direction4.right), 0, 3)
    furnace1.addEntityAt(MetalExit(Direction4.right, betweenFurnace1AndFurnace3), 0, 2)
    furnace1.addEntityAt(MetalWall(Direction4.right), 0, 1)
    
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
    furnace2.addEntityAt(MetalWall(Direction4.down), 1, 0)
    furnace2.addEntityAt(MetalWall(Direction4.down), 2, 0)
    furnace2.addEntityAt(MetalWall(Direction4.down), 3, 0)
    furnace2.addEntityAt(MetalWall(), 4, 0)
    furnace2.addEntityAt(MetalWall(Direction4.left), 4, 1)
    furnace2.addEntityAt(MetalExit(Direction4.left, betweenFurnace1AndFurnace2), 4, 2)
    furnace2.addEntityAt(MetalWall(Direction4.left), 4, 3)
    furnace2.addEntityAt(MetalWall(), 4, 4)
    furnace2.addEntityAt(MetalWall(Direction4.up), 3, 4)
    furnace2.addEntityAt(MetalWall(Direction4.up), 2, 4)
    furnace2.addEntityAt(MetalWall(Direction4.up), 1, 4)
    furnace2.addEntityAt(MetalWall(), 0, 4)
    furnace2.addEntityAt(MetalWall(Direction4.right), 0, 3)
    furnace2.addEntityAt(MetalWall(Direction4.right), 0, 2)
    furnace2.addEntityAt(MetalWall(Direction4.right), 0, 1)
    
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
    furnace3.addEntityAt(MetalWall(Direction4.down), 1, 0)
    furnace3.addEntityAt(MetalWall(Direction4.down), 2, 0)
    furnace3.addEntityAt(MetalWall(Direction4.down), 3, 0)
    furnace3.addEntityAt(MetalWall(), 4, 0)
    furnace3.addEntityAt(MetalWall(Direction4.left), 4, 1)
    furnace3.addEntityAt(MetalWall(Direction4.left), 4, 2)
    furnace3.addEntityAt(MetalWall(Direction4.left), 4, 3)
    furnace3.addEntityAt(MetalWall(), 4, 4)
    furnace3.addEntityAt(MetalWall(Direction4.up), 3, 4)
    furnace3.addEntityAt(RockExit(Direction4.up, betweenFurnace1AndFurnace3), 2, 4)
    furnace3.addEntityAt(MetalWall(Direction4.up), 1, 4)
    furnace3.addEntityAt(MetalWall(), 0, 4)
    furnace3.addEntityAt(MetalWall(Direction4.right), 0, 3)
    //furnace3.addEntityAt(MetalExit(Direction4.right, fromFurnace3ToMines), 0, 2)
    furnace3.addEntityAt(MetalWall(Direction4.right), 0, 1)
    
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
    mines1.addEntityAt(RockWall(Direction4.down), 1, 0)
    mines1.addEntityAt(RockWall(Direction4.down), 2, 0)
    mines1.addEntityAt(RockWall(Direction4.down), 3, 0)
    mines1.addEntityAt(RockWall(), 4, 0)
    mines1.addEntityAt(RockWall(Direction4.left), 4, 1)
    mines1.addEntityAt(RockWall(Direction4.left), 4, 2)
    mines1.addEntityAt(RockWall(Direction4.left), 4, 3)
    mines1.addEntityAt(RockWall(), 4, 4)
    mines1.addEntityAt(RockWall(Direction4.up), 3, 4)
    mines1.addEntityAt(RockExit(Direction4.up, betweenMines1AndMines2), 2, 4)
    mines1.addEntityAt(RockWall(Direction4.up), 1, 4)
    mines1.addEntityAt(RockWall(), 0, 4)
    mines1.addEntityAt(RockWall(Direction4.right), 0, 3)
    mines1.addEntityAt(RockWall(Direction4.right), 0, 2)
    mines1.addEntityAt(RockWall(Direction4.right), 0, 1)
    
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
    mines2.addEntityAt(RockWall(Direction4.down), 1, 0)
    mines2.addEntityAt(RockExit(Direction4.down, betweenMines1AndMines2), 2, 0)
    mines2.addEntityAt(RockWall(Direction4.down), 3, 0)
    mines2.addEntityAt(RockWall(), 4, 0)
    mines2.addEntityAt(RockWall(Direction4.left), 4, 1)
    mines2.addEntityAt(RockWall(Direction4.left), 4, 2)
    mines2.addEntityAt(RockWall(Direction4.left), 4, 3)
    mines2.addEntityAt(RockWall(), 4, 4)
    mines2.addEntityAt(RockWall(Direction4.up), 3, 4)
    mines2.addEntityAt(RockExit(Direction4.up, betweenMines2AndMines3), 2, 4)
    mines2.addEntityAt(RockWall(Direction4.up), 1, 4)
    mines2.addEntityAt(RockWall(), 0, 4)
    mines2.addEntityAt(RockWall(Direction4.right), 0, 3)
    mines2.addEntityAt(RockWall(Direction4.right), 0, 2)
    mines2.addEntityAt(RockWall(Direction4.right), 0, 1)
    
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
    mines3.addEntityAt(RockWall(Direction4.down), 1, 0)
    mines3.addEntityAt(RockExit(Direction4.down, betweenMines2AndMines3), 2, 0)
    mines3.addEntityAt(RockWall(Direction4.down), 3, 0)
    mines3.addEntityAt(RockWall(), 4, 0)
    mines3.addEntityAt(RockWall(Direction4.left), 4, 1)
    mines3.addEntityAt(RockWall(Direction4.left), 4, 2)
    mines3.addEntityAt(RockWall(Direction4.left), 4, 3)
    mines3.addEntityAt(RockWall(), 4, 4)
    mines3.addEntityAt(RockWall(Direction4.up), 3, 4)
    mines3.addEntityAt(RockWall(Direction4.up), 2, 4)
    mines3.addEntityAt(RockWall(Direction4.up), 1, 4)
    mines3.addEntityAt(RockWall(), 0, 4)
    mines3.addEntityAt(RockWall(Direction4.right), 0, 3)
    mines3.addEntityAt(RockWall(Direction4.right), 0, 2)
    mines3.addEntityAt(RockWall(Direction4.right), 0, 1)
    
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
    
}*/

fun World.createWorldPrototypeTwo()
{
    //
    // level 1
    //
    
    // rooms
    
    // l1r1
    val l1r1 = Room("1", 11, 11)
    // change base
    for (y in 1 until l1r1.heightInSpaces) for (x in 1 until l1r1.widthInSpaces) l1r1.changeBaseAt(
            Base.values()[y % 4], x, y
                                                                                                  )
    // add lava
    for (y in 4 until 6) for (x in 4 until 6) l1r1.changeBaseAt(Base.lava, x, y)
    // add walls on left side (facing right)
    for (y in 1 until l1r1.heightInSpaces) l1r1.addEntityAt(StoneWall(Direction4.right), 0, y)
    // add walls on top side (facing down)
    for (x in 1 until l1r1.widthInSpaces) l1r1.addEntityAt(StoneWall(Direction4.down), x, 0)
    
    // l1r1
    val l1r2 = Room("2", 16, 16)
    // change base
    for (y in 1 until l1r2.heightInSpaces) for (x in 1 until l1r2.widthInSpaces) l1r2.changeBaseAt(
            Base.values()[x % 4], x, y
                                                                                                  )
    // add lava
    for (y in 5 until 6) for (x in 2 until 10) l1r2.changeBaseAt(Base.lava, x, y)
    // add walls on left side (facing right)
    for (y in 1 until l1r2.heightInSpaces) l1r2.addEntityAt(StoneWall(Direction4.right), 0, y)
    // add walls on top side (facing down)
    for (x in 1 until l1r2.widthInSpaces) l1r2.addEntityAt(StoneWall(Direction4.down), x, 0)
    
    // l1r3
    val l1r3 = Room("3", 6, 21)
    // change base
    for (y in 1 until l1r3.heightInSpaces) for (x in 1 until l1r3.widthInSpaces)
    {
        if (x % 2 == 0 && y % 2 == 0) l1r3.changeBaseAt(Base.values()[2], x, y)
        else l1r3.changeBaseAt(Base.values()[0], x, y)
    }
    // add lava
    for (y in 1 until 2) for (x in 1 until 20) l1r3.changeBaseAt(Base.lava, x, y)
    // add walls on left side (facing right)
    for (y in 1 until l1r3.heightInSpaces) l1r3.addEntityAt(StoneWall(Direction4.right), 0, y)
    // add walls on top side (facing down)
    for (x in 1 until l1r3.widthInSpaces) l1r3.addEntityAt(StoneWall(Direction4.down), x, 0)
    
    // l1r4
    val l1r4 = Room("4", 21, 6)
    // change base
    for (y in 1 until l1r4.heightInSpaces) for (x in 1 until l1r4.widthInSpaces)
    {
        if (x % 2 == 0 && y % 2 == 0) l1r4.changeBaseAt(Base.values()[2], x, y)
        else l1r4.changeBaseAt(Base.values()[0], x, y)
    }
    // add lava
    for (y in 1 until 20) for (x in 2 until 3) l1r4.changeBaseAt(Base.lava, x, y)
    // add walls on left side (facing right)
    for (y in 1 until l1r4.heightInSpaces) l1r4.addEntityAt(StoneWall(Direction4.right), 0, y)
    // add walls on top side (facing down)
    for (x in 1 until l1r4.widthInSpaces) l1r4.addEntityAt(StoneWall(Direction4.down), x, 0)
    
    // room list
    val l1_rooms = mutableListOf<Room>(l1r1, l1r2, l1r3, l1r4)
    
    // room passages
    val l1_roomPassages = mutableListOf<RoomPassage>(
            RoomPassage(l1r1, RoomPosition(10, 3), Direction4.left, l1r2, RoomPosition(0, 6)),
            RoomPassage(l1r2, RoomPosition(15, 4), Direction4.left, l1r3, RoomPosition(0, 4)),
            RoomPassage(l1r2, RoomPosition(15, 9), Direction4.left, l1r4, RoomPosition(0, 4)),
            RoomPassage(l1r3, RoomPosition(3, 5), Direction4.up, l1r4, RoomPosition(3, 0))  //, isActive = false)
                                                    )
    
    // add room exits
    for (passage in l1_roomPassages)
    {
        // add in roomA
        passage.roomA.replaceEntityAt(StoneExit(passage.directionA, passage), passage.positionA)
        // add in roomB
        passage.roomB.replaceEntityAt(StoneExit(passage.directionB, passage), passage.positionB)
    }
    
    // level with starting point
    val l1 = Level("1", l1_rooms, l1_roomPassages)
    l1.changeStartingRoom(l1r2)
    l1.changeStartingPosition(1, 1)
    
    // entities
    val chest = Chest()
    chest.addItem(Bomb(4))
    chest.addItem(Fish(15))
    chest.addItem(Apple(16))
    chest.addItem(Mushroom(14))
    val octopusHead = BossOctopusHead()
    val octopusTentacle1 = BossOctopusTentacle()
    octopusHead.addTentacle(octopusTentacle1)
    val octopusTentacle2 = BossOctopusTentacle()
    octopusHead.addTentacle(octopusTentacle2)
    l1r2.addEntityAt(BossNatureGolem(), 12, 6)
    l1r2.addEntityAt(EnemyRollingStone(), 7, 7)
    l1r2.addEntityAt(EnemyPlant(), 9, 8)
    l1r2.addEntityAt(EnemyMushroom(), 3, 3)
    l1r2.addEntityAt(chest, 5, 5)
    l1r2.addEntityAt(ExplodingBarrel(), 8, 3)
    l1r2.addEntityAt(EnemySkeleton(), 4, 6)
    l1r2.addEntityAt(EnemyMimic(), 5, 6)
    l1r2.addEntityAt(EnemyMushroomCorpse(), 2, 2)
    l1r2.addEntityAt(EnemySkeletonCorpse(), 10, 3)
    l1r2.addEntityAt(EnemyBat(), 8, 4)
    l1r2.addEntityAt(EnemyWizard(), 8, 8)
    l1r2.addEntityAt(EnemyTurret(), 5, 8)
    l1r2.addEntityAt(BossDragon(), 3, 8)
    l1r2.addEntityAt(BossSlime(), 3, 7)
    l1r2.changeBaseAt(Base.water, 3, 2)
    l1r2.addEntityAt(octopusTentacle1, 3, 2)
    l1r2.changeBaseAt(Base.water,4,2)
    l1r2.changeBaseAt(Base.water,5,2)
    l1r2.changeBaseAt(Base.waterOctopus,5,2)
    l1r2.addEntityAt(octopusHead, 5, 2)
    l1r2.changeBaseAt(Base.water,6,2)
    l1r2.changeBaseAt(Base.water,7,2)
    l1r2.addEntityAt(octopusTentacle2, 7, 2)
    l1r4.addEntityAt(BossWizard(), 3, 3)
    
    // add to World
    addLevel(l1)
    
    //
    // level 2
    //
    
    // rooms
    
    // l2r1
    val l2r1 = Room("1", 5 + 1, 6 + 1)
    // change base
    for (y in 1 until l2r1.heightInSpaces) for (x in 1 until l2r1.widthInSpaces) l2r1.changeBaseAt(
            Base.values()[y % 4], x, y
                                                                                                  )
    // add walls on left side (facing right)
    for (y in 1 until l2r1.heightInSpaces) l2r1.addEntityAt(StoneWall(Direction4.right), 0, y)
    // add walls on top side (facing down)
    for (x in 1 until l2r1.widthInSpaces) l2r1.addEntityAt(StoneWall(Direction4.down), x, 0)
    // add entities
    l2r1.addEntityAt(Chest(), 4, 3)
    
    // l2r2
    val l2r2 = Room("2", 8 + 1, 15 + 1)
    // change base
    for (y in 1 until l2r2.heightInSpaces)
    {
        var base_number = 0
        for (x in 1 until l2r2.widthInSpaces)
        {
            l2r2.changeBaseAt(Base.values()[base_number], x, y)
            if (x % 3 == 0) base_number = (base_number + 1) % 3
        }
    }
    // delete base and space
    for (y in 6 until l2r2.heightInSpaces) for (x in 9 until l2r2.widthInSpaces)
    {
        l2r2.changeBaseAt(null, x, y)
        l2r2.deleteSpaceAt(x, y)
    }
    // add lava
    for (y in 5 until 8) for (x in 3 until 6) l2r2.changeBaseAt(Base.lava, x, y)
    for (y in 3 until 7) for (x in 4 until 7) l2r2.changeBaseAt(Base.lava, x, y)
    // add walls on left side (facing right)
    for (y in 1 until l2r2.heightInSpaces) l2r2.addEntityAt(StoneWall(Direction4.right), 0, y)
    // add walls on top side (facing down)
    for (x in 1 until l2r2.widthInSpaces) l2r2.addEntityAt(StoneWall(Direction4.down), x, 0)
    
    // l2r3
    val l2r3 = Room("3", 10 + 1, 5 + 1)
    // change base
    for (y in 1 until l2r3.heightInSpaces) for (x in 1 until l2r3.widthInSpaces) l2r3.changeBaseAt(Base.metal, x, y)
    
    // add walls on left side (facing right)
    for (y in 1 until l2r3.heightInSpaces) l2r3.addEntityAt(MetalWall(Direction4.right), 0, y)
    // add walls on top side (facing down)
    for (x in 1 until l2r3.widthInSpaces) l2r3.addEntityAt(MetalWall(Direction4.down), x, 0)
    
    // l2r4
    val l2r4 = Room("4", 8 + 1, 16 + 1)
    // change base
    for (y in 1 until l2r4.heightInSpaces)
    {
        var base_number = 0
        for (x in 1 until l2r4.widthInSpaces)
        {
            l2r4.changeBaseAt(Base.values()[base_number], x, y)
            if (y % 3 == 0) base_number = (base_number + 1) % 3
        }
    }
    // add lava
    for (y in 6 until l2r4.heightInSpaces) for (x in 4 until 13) l2r4.changeBaseAt(Base.lava, x, y)
    for (y in 5 until l2r4.heightInSpaces) for (x in 6 until 11) l2r4.changeBaseAt(Base.lava, x, y)
    // delete base and space
    for (y in 0 until 3) for (x in 5 until l2r4.widthInSpaces)
    {
        l2r4.changeBaseAt(null, x, y)
        if (y != 2) l2r4.deleteSpaceAt(x, y)
    }
    // add walls on left side (facing right)
    for (y in 1 until l2r4.heightInSpaces) l2r4.addEntityAt(StoneWall(Direction4.right), 0, y)
    // add walls on top side (facing down)
    for (x in 1 until l2r4.widthInSpaces) l2r4.addEntityAt(StoneWall(Direction4.down), x, 0)
    // add walls on right side (facing left) for l2r5
    for (y in 1 until 3) l2r4.replaceEntityAt(StoneWall(Direction4.left), 4, y)
    // add walls on top side (facing down) for l2r5
    for (x in 4 until l2r4.widthInSpaces) l2r4.replaceEntityAt(StoneWall(Direction4.down), x, 2)
    
    // l2r5
    val l2r5 = Room("5", 6 + 1, 6 + 1)
    // change base
    for (y in 1 until l2r5.heightInSpaces) for (x in 1 until l2r5.widthInSpaces) l2r5.changeBaseAt(Base.grass, x, y)
    // add walls on left side (facing right)
    for (y in 1 until l2r5.heightInSpaces) l2r5.addEntityAt(RockWall(Direction4.right), 0, y)
    // add walls on top side (facing down)
    for (x in 1 until l2r5.widthInSpaces) l2r5.addEntityAt(RockWall(Direction4.down), x, 0)
    // add entities
    l2r5.addEntityAt(Chest(), 4, 2)
    
    // l2r6
    val l2r6 = Room("6", 9 + 1, 10 + 1)
    // change base
    for (y in 1 until l2r6.heightInSpaces)
    {
        var base_number = 0
        for (x in 1 until l2r6.widthInSpaces)
        {
            l2r6.changeBaseAt(Base.values()[0], x, y)
            if (y % 3 == 0) base_number = (base_number + 1) % 3
            if (x % 4 == 0) base_number = (base_number - 1) % 3
        }
    }
    // add walls on left side (facing right)
    for (y in 1 until l2r6.heightInSpaces) l2r6.addEntityAt(RockWall(Direction4.right), 0, y)
    // add walls on top side (facing down)
    for (x in 1 until l2r6.widthInSpaces) l2r6.addEntityAt(RockWall(Direction4.down), x, 0)
    
    // room list
    val l2_rooms = mutableListOf<Room>(l2r1, l2r2, l2r3, l2r4, l2r5, l2r6)
    
    // room passages
    val l2_roomPassages = mutableListOf<RoomPassage>(
            RoomPassage(l2r1, RoomPosition(l2r1.widthInSpaces - 1, 2), Direction4.left, l2r2, RoomPosition(0, 4)),
            RoomPassage(l2r2, RoomPosition(l2r2.widthInSpaces - 1, 3), Direction4.left, l2r3, RoomPosition(0, 3)),
            RoomPassage(l2r3, RoomPosition(3, l2r3.heightInSpaces - 1), Direction4.up, l2r4, RoomPosition(14, 2)),
            RoomPassage(l2r4, RoomPosition(4, 1), Direction4.left, l2r5, RoomPosition(0, 5)),
            RoomPassage(
                    l2r4,
                    RoomPosition(0, l2r4.heightInSpaces - 2),
                    Direction4.right,
                    l2r6,
                    RoomPosition(l2r6.widthInSpaces - 1, l2r6.heightInSpaces - 2)
                       ),
            RoomPassage(l2r6, RoomPosition(3, 0), Direction4.down, l2r1, RoomPosition(3, l2r1.heightInSpaces - 1))
                                                    )
    
    // add room exits
    for (passage in l2_roomPassages)
    {
        // add in roomA
        passage.roomA.replaceEntityAt(StoneExit(passage.directionA, passage), passage.positionA)
        // add in roomB
        passage.roomB.replaceEntityAt(StoneExit(passage.directionB, passage), passage.positionB)
    }
    
    // level with starting point
    val l2 = Level("2", l2_rooms, l2_roomPassages)
    l2.changeStartingRoom(l2r1)
    l2.changeStartingPosition(1, 2)
    
    // add to World
    addLevel(l2)
    
    // level passages
    val levelPassages = mutableListOf<LevelPassage>(
            LevelPassage(l1r4, RoomPosition(l1r4.widthInSpaces - 1, l1r4.heightInSpaces - 1), Direction4.left, l2, true)
                                                   )
    
    // add level exits
    for (passage in levelPassages) passage.originRoom.replaceEntityAt(
            StoneExitActiveWhenNoEnemiesAreInRoom(passage.originDirection, passage), passage.originPosition
                                                                     )
}

fun World.createWorldBoarTest()
{
    //
    // level 1
    //
    
    // rooms
    
    // l1r1
    val l1r1 = Room("1", 11, 11)
    // change base
    for (y in 1 until l1r1.heightInSpaces) for (x in 1 until l1r1.widthInSpaces) l1r1.changeBaseAt(
            Base.values()[y % 4], x, y
                                                                                                  )
    // add lava
    for (y in 4 until 6) for (x in 4 until 6) l1r1.changeBaseAt(Base.lava, x, y)
    // add walls on left side (facing right)
    for (y in 1 until l1r1.heightInSpaces) l1r1.addEntityAt(StoneWall(Direction4.right), 0, y)
    // add walls on top side (facing down)
    for (x in 1 until l1r1.widthInSpaces) l1r1.addEntityAt(StoneWall(Direction4.down), x, 0)
    
    // room list
    val l1_rooms = mutableListOf<Room>(l1r1)
    
    // room passages
    val l1_roomPassages = mutableListOf<RoomPassage>()
    
    // add room exits
    
    // level with starting point
    val l1 = Level("1", l1_rooms, l1_roomPassages)
    l1.changeStartingRoom(l1r1)
    l1.changeStartingPosition(5, 6)
    
    // entities
    val chest = Chest()
    chest.addItem(Bomb(4))
    chest.addItem(Fish(15))
    chest.addItem(Apple(16))
    chest.addItem(Mushroom(14))
    l1r1.addEntityAt(chest, 5, 5)
    val boar = EnemyBoar()
    //l1r1.addEntityAt(boar, 8, 8)
    val ghost = EnemyGhost()
    l1r1.addEntityAt(ghost, 1, 10)
    
    // add to World
    addLevel(l1)
    
    // level passages
    val levelPassages = mutableListOf<LevelPassage>()
    
    // add level exits
    
}