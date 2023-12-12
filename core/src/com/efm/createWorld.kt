@file:Suppress("unused")

package com.efm

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.efm.entities.*
import com.efm.entities.bosses.*
import com.efm.entities.bosses.slime.*
import com.efm.entities.enemies.*
import com.efm.entities.enemies.Boar.EnemyBoar
import com.efm.entities.enemies.Boar.EnemyGhost
import com.efm.entities.walls.*
import com.efm.exit.*
import com.efm.item.PossibleItem
import com.efm.item.PossibleItems
import com.efm.level.Level
import com.efm.level.World
import com.efm.multiUseMapItems.*
import com.efm.room.*
import com.efm.skills.Pockets
import com.efm.stackableMapItems.*
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
/*
fun World.createWorldPrototypeTwo()
{
    //
    // level 1
    //
    
    val l1 = Level("1")
    
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
    spawnChessSet(4, 9, Direction4.up, l1r1)
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
    
    // room passages (exits)
    addRoomPassage(l1, l1r1.name, RoomPosition(10, 3), Direction4.left, l1r2.name, RoomPosition(0, 6))
    addRoomPassage(l1, l1r2.name, RoomPosition(15, 4), Direction4.left, l1r3.name, RoomPosition(0, 4))
    addRoomPassage(l1, l1r2.name, RoomPosition(15, 9), Direction4.left, l1r4.name, RoomPosition(0, 4))
    addRoomPassage(l1, l1r3.name, RoomPosition(3, 5), Direction4.up, l1r4.name, RoomPosition(3, 0))
    
    // level with starting point
    l1.rooms.addAll(l1_rooms)
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
    l1r2.changeBaseAt(Base.water, 4, 2)
    l1r2.changeBaseAt(Base.water, 5, 2)
    l1r2.changeBaseAt(Base.waterOctopus, 5, 2)
    l1r2.addEntityAt(octopusHead, 5, 2)
    l1r2.changeBaseAt(Base.water, 6, 2)
    l1r2.changeBaseAt(Base.water, 7, 2)
    l1r2.addEntityAt(octopusTentacle2, 7, 2)
    l1r4.addEntityAt(BossWizard(), 3, 3)
    
    // add to World
    addLevel(l1)
    
    //
    // level 2
    //
    
    val l2 = Level("2")
    
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
    
    // room passages (exits)
    addRoomPassage(
            l2, l2r1.name, RoomPosition(l2r1.widthInSpaces - 1, 2), Direction4.left, l2r2.name, RoomPosition(0, 4)
                  )
    addRoomPassage(
            l2, l2r2.name, RoomPosition(l2r2.widthInSpaces - 1, 3), Direction4.left, l2r3.name, RoomPosition(0, 3)
                  )
    addRoomPassage(
            l2, l2r3.name, RoomPosition(3, l2r3.heightInSpaces - 1), Direction4.up, l2r4.name, RoomPosition(14, 2)
                  )
    addRoomPassage(l2, l2r4.name, RoomPosition(4, 1), Direction4.left, l2r5.name, RoomPosition(0, 5))
    addRoomPassage(
            l2,
            l2r4.name,
            RoomPosition(0, l2r4.heightInSpaces - 2),
            Direction4.right,
            l2r6.name,
            RoomPosition(l2r6.widthInSpaces - 1, l2r6.heightInSpaces - 2)
                  )
    addRoomPassage(
            l2, l2r6.name, RoomPosition(3, 0), Direction4.down, l2r1.name, RoomPosition(3, l2r1.heightInSpaces - 1)
                  )
    
    // level with starting point
    l2.rooms.addAll(l2_rooms)
    l2.changeStartingRoom(l2r1)
    l2.changeStartingPosition(1, 2)
    
    // add to World
    addLevel(l2)
    
    // level passages
    val l1tol2 = LevelExit(
            RoomPosition(l1r4.widthInSpaces - 1, l1r4.heightInSpaces - 1),
            Direction4.left,
            l2.name,
            ExitStyle.stone,
            activeWhenNoEnemiesAreInRoom = true
                          )
    l1r4.addEntity(l1tol2)
}
*/
fun World.createWorldBoarTest()
{
    //
    // level 1
    //
    
    val l1 = Level("1")
    
    // rooms
    
    // l1r1
    val file = Gdx.files.local("testRoomFile1.txt")
    val string =
            "11 11\n" + "x x x x x x x x x x x\n" + "x x x x x 0 x x x x x\n" + "x x x x x 1 1 x x x x\n" + "x x x x x 2 2 2 x x x\n" + "x x x x 4 4 3 3 3 x x\n" + "x x x 0 4 4 0 0 0 x x\n" + "x x x 1 1 1 1 1 x x x\n" + "x x x 2 2 2 2 2 x x x\n" + "x x x 3 3 3 3 x x x x\n" + "x x 0 0 0 0 0 0 0 x x\n" + "x 1 1 1 1 1 1 1 1 1 1"
    file.writeString(string, false)
    val l1r1 = createRoomFromFile("1", file)
    
    // room list
    val l1Rooms = mutableListOf<Room>(l1r1)
    
    // add room exits
    
    // level with starting point
    l1.rooms.addAll(l1Rooms)
    l1.changeStartingRoom(l1r1)
    l1.changeStartingPosition(6, 2)
    
    // entities
    val chest = Chest()
    chest.addItem(Bomb(4))
    chest.addItem(Fish(15))
    chest.addItem(Apple(16))
    chest.addItem(Mushroom(14))
    l1r1.addEntityAt(chest, 5, 5)
    val boar = EnemyBoar()
    l1r1.addEntityAt(boar, 8, 8)
    val ghost = EnemyGhost()
    l1r1.addEntityAt(ghost, 1, 10)
    val commandBlock = CommandBlock {
        if (!hero.hasSkill(Pockets))
        {
            hero.addSkill(Pockets)
            // fill inventory
            for (i in hero.inventory.items.size until hero.inventory.maxItems) hero.inventory.addItem(Bow())
            Gdx.app.log("CommandBlock", "added Pockets and filled them")
        }
        else
        {
            hero.removeSkill(Pockets)
            Gdx.app.log("CommandBlock", "removed Pockets")
        }
    }
    l1r1.addEntityAt(commandBlock, 6, 6)
    
    // add to World
    addLevel(l1)
}

fun World.createWorldPrototypeThree()
{
    // not sure yet how files will work
    pom()
    //
    // level 1
    //
    val l1 = Level("1").apply {
        // room 1
        //
        val l1r1 = createRoomFromFile("1", Gdx.files.local("l1r1.txt")).apply {
            // walls
            addWalls(WallStyle.metal)
            // entities
            val chest = TutorialChest()
            chest.addItem(WoodenSword())
            chest.addItem(Fish(2))
            addEntityAt(chest, 4, 3)
        }
        // add room to level
        addRoom(l1r1)
        
        // room 2
        //
        val l1r2 = createRoomFromFile("2", Gdx.files.local("l1r2.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneDarkTall)
            // entities
            addEntityAt(EnemyMushroom(), 6, 4)
            val chest = TutorialChest()
            chest.addItem(IronSword())
            chest.addItem(HPPotionSmall(2))
            addEntityAt(chest, 4, 3)
        }
        // add room to level
        addRoom(l1r2)
        
        // room 3
        //
        val l1r3 = createRoomFromFile("3", Gdx.files.local("l1r3.txt")).apply {
            // walls
            addWalls(WallStyle.brickOrangeLight)
            // entities
            addEntityAt(EnemyBat(), 2, 1)
            addEntityAt(EnemyBat(), 5, 6)
            val chest = Chest(
                    PossibleItems(
                            mutableListOf(
                                    PossibleItem(APPotionSmall(), 1f, IntRange(1, 3)),
                                    PossibleItem(Mushroom(), 1f, IntRange(2, 4)),
                                    PossibleItem(Bow(), 1f, IntRange(1, 1))
                                         )
                                 )
                             )
            addEntityAt(chest, 3, 4)
        }
        // add room to level
        addRoom(l1r3)
        
        // room 4
        //
        val l1r4 = createRoomFromFile("4", Gdx.files.local("l1r4.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneLight)
            // entities
            addEntityAt(EnemyGhost(), 4, 8)
            addEntityAt(EnemySkeleton(), 7, 5)
            val chest = Chest(
                    PossibleItems(
                            mutableListOf(
                                    PossibleItem(WoodenSword(), 1f, IntRange(1, 2))
                                         )
                                 )
                             )
            addEntityAt(chest, 6, 5)
            addEntityAt(Npc(), 6, 1)
        }
        // add room to level
        addRoom(l1r4)
        
        // room 5
        //
        val l1r5 = createRoomFromFile("5", Gdx.files.local("l1r5.txt")).apply {
            // walls
            addWalls(WallStyle.brickRedDarkTall)
            // entities
            val bossPosition = RoomPosition(6, 6)
            //spawnRandomUndefeatedBoss(this, bossPosition)
            addEntityAt(TutorialBossSlime(), bossPosition)
        }
        // add room to level
        addRoom(l1r5)
        
        // add room passages
        //
        addRoomPassage(
                this,
                l1r1.name,
                RoomPosition(6, 3),
                Direction4.left,
                l1r2.name,
                RoomPosition(0, 7),
                ExitStyle.metal,
                exitBBase = Base.stone
                      )
        
        // after going through exit focus camera on tutorial enemy
        val tutorialExit =
                TutorialRoomExit(RoomPosition(6, 3), Direction4.left, l1r2.name, RoomPosition(0, 7), ExitStyle.metal)
        l1r1.replaceEntityAt(tutorialExit, 6, 3)
        
        addRoomPassage(
                this,
                l1r2.name,
                RoomPosition(l1r2.widthInSpaces - 1, 3),
                Direction4.left,
                l1r3.name,
                RoomPosition(0, 3),
                ExitStyle.stone
                      )
        addRoomPassage(
                this,
                l1r2.name,
                RoomPosition(l1r2.widthInSpaces - 1, 9),
                Direction4.left,
                l1r4.name,
                RoomPosition(0, 9),
                ExitStyle.stone
                      )
        addRoomPassage(
                this,
                l1r3.name,
                RoomPosition(l1r3.widthInSpaces - 1, 3),
                Direction4.left,
                l1r4.name,
                RoomPosition(l1r3.widthInSpaces - 1, 3),
                ExitStyle.stone
                      )
        // one end of this one should be locked
        addRoomPassage(
                this,
                l1r4.name,
                RoomPosition(l1r4.widthInSpaces - 1, 7),
                Direction4.left,
                l1r5.name,
                RoomPosition(0, 7),
                ExitStyle.stone
                      )
        (l1r5.getSpace(0, 7)?.getEntity() as Exit).activeWhenNoEnemiesAreInRoom = true
        // starting position
        //
        changeStartingRoom(l1r1)
        changeStartingPosition(1, 3)
        // level exit
        l1r5.replaceEntityAt(
                LevelExit(
                        RoomPosition(l1r5.widthInSpaces - 1, 0),
                        Direction4.down,
                        "2",
                        ExitStyle.stone,
                        activeWhenNoEnemiesAreInRoom = true
                         ), RoomPosition(l1r5.widthInSpaces - 1, 0)
                            )
    }
    // add level to World
    this.addLevel(l1)
    //
    // level 2
    //
    val l2 = Level("2").apply {
        // room 1
        //
        val l2r1 = createRoomFromFile("1", Gdx.files.local("l2r1.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneDark)
            // chests
            val chest1 = Chest(
                    PossibleItems(
                            mutableListOf(
                                    PossibleItem(WoodenSword(), 0.5f, 1..1),
                                    PossibleItem(IronSword(), 0.2f, 1..1),
                                    PossibleItem(Sledgehammer(), 0.2f, 1..1),
                                    PossibleItem(SmallAxe(), 0.3f, 1..1),
                                    PossibleItem(Shuriken(), 1f, 0..10),
                                    PossibleItem(Mushroom(), 0.5f, 0..6),
                                    PossibleItem(APPotionSmall(), 0.5f, 1..3)
                                         )
                                 )
                              )
            addEntityAt(chest1, 13, 3)
            val chest2 = Chest(
                    PossibleItems(
                            mutableListOf(
                                    PossibleItem(IronSword(), 1f, 1..1),
                                    PossibleItem(HPPotionSmall(), 0.5f, 1..4)
                                         )
                                 )
                              )
            addEntityAt(chest2, 7, this.heightInSpaces - 1)
            // entities
            addEntityAt(EnemyMushroom(), 7, 10)
            addEntityAt(EnemySkeleton(), 4, 7)
            addEntityAt(EnemyBat(), 10, 6)
            addEntityAt(EnemySkeleton(), 11, 9)
            addEntityAt(EnemyMushroom(), 14, 5)
        }
        // add room to level
        addRoom(l2r1)
        
        // room 2
        //
        val l2r2 = createRoomFromFile("2", Gdx.files.local("l2r2.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneDarkTall)
            // entities
            addEntityAt(EnemyBoar(), 4, 2)
        }
        // add room to level
        addRoom(l2r2)
        
        // room 3
        //
        val l2r3 = createRoomFromFile("3", Gdx.files.local("l2r3.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneDarkTall)
            // entities
            addEntityAt(EnemyMushroom(), 2, 5)
            addEntityAt(EnemyMushroom(), 4, 3)
        }
        // add room to level
        addRoom(l2r3)
        
        // room 4
        //
        val l2r4 = createRoomFromFile("4", Gdx.files.local("l2r4.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneLight)
            // chests
            val chest = Chest(
                    PossibleItems(
                            mutableListOf(
                                    PossibleItem(IronSword(), 0.3f, 1..1),
                                    PossibleItem(Sledgehammer(), 0.3f, 1..1),
                                    PossibleItem(Shuriken(), 1f, 0..6),
                                    PossibleItem(Mushroom(), 1f, 0..4),
                                    PossibleItem(APPotionSmall(), 0.3f, 1..3),
                                    PossibleItem(HPPotionSmall(), 0.3f, 1..3)
                                         )
                                 )
                             )
            addEntityAt(chest, this.widthInSpaces - 1, 1)
            // entities
            addEntityAt(EnemySkeleton(), 8, 2)
            addEntityAt(EnemyMushroom(), 13, 3)
        }
        // add room to level
        addRoom(l2r4)
        
        // room 5
        //
        val l2r5 = createRoomFromFile("5", Gdx.files.local("l2r5.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneLight)
            // entities
        }
        // add room to level
        addRoom(l2r5)
        
        // room 6
        //
        val l2r6 = createRoomFromFile("6", Gdx.files.local("l2r6.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneLightTall)
            // chests
            val chest = Chest(
                    PossibleItems(
                            mutableListOf(
                                    PossibleItem(SmallAxe(), 0.3f, 1..1),
                                    PossibleItem(IronSword(), 0.3f, 1..1),
                                    PossibleItem(Shuriken(), 1f, 0..6),
                                    PossibleItem(Mushroom(), 1f, 0..4),
                                    PossibleItem(APPotionSmall(), 0.3f, 1..3),
                                    PossibleItem(HPPotionSmall(), 0.3f, 1..3)
                                         )
                                 )
                             )
            addEntityAt(chest, 4, 5)
            // entities
            addEntityAt(EnemyBoar(), 5, 3)
        }
        // add room to level
        addRoom(l2r6)
        
        // room 7
        //
        val l2r7 = createRoomFromFile("7", Gdx.files.local("l2r7.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneLightTall)
            // entities
            addEntityAt(EnemySkeleton(), 3, 5)
        }
        // add room to level
        addRoom(l2r7)
        
        // room 8
        //
        val l2r8 = createRoomFromFile("8", Gdx.files.local("l2r8.txt")).apply {
            // walls
            addWalls(WallStyle.stone)
            // entities
            addEntityAt(EnemyPlant(), 7, 2)
            addEntityAt(EnemyBat(), 3, 3)
        }
        // add room to level
        addRoom(l2r8)
        
        // room 9
        //
        val l2r9 = createRoomFromFile("9", Gdx.files.local("l2r9.txt")).apply {
            // walls
            addWalls(WallStyle.stone)
            // entities
            addEntityAt(BossSlime(), RoomPosition(9, 1))
            // level exit
            replaceEntityAt(
                    LevelExit(
                            RoomPosition(9, 0),
                            Direction4.down,
                            "3",
                            ExitStyle.stone,
                            activeWhenNoEnemiesAreInRoom = true
                             ), RoomPosition(9, 0)
                           )
        }
        // add room to level
        addRoom(l2r9)
        
        // add room passages
        //
        addRoomPassage(
                this,
                l2r1.name,
                RoomPosition(l2r1.widthInSpaces - 1, 14),
                Direction4.left,
                l2r2.name,
                RoomPosition(0, 3),
                ExitStyle.metal
                      )
        addRoomPassage(
                this,
                l2r1.name,
                RoomPosition(14, 0),
                Direction4.down,
                l2r4.name,
                RoomPosition(5, l2r4.heightInSpaces - 3),
                ExitStyle.metal
                      )
        (l2r4.getSpace(5, l2r4.heightInSpaces - 3)?.getEntity() as Exit).activeWhenNoEnemiesAreInRoom = true
        addRoomPassage(
                this,
                l2r1.name,
                RoomPosition(6, 3),
                Direction4.down,
                l2r7.name,
                RoomPosition(2, l2r7.heightInSpaces - 1),
                ExitStyle.metal
                      )
        (l2r7.getSpace(2, l2r7.heightInSpaces - 1)?.getEntity() as Exit).activeWhenNoEnemiesAreInRoom = true
        addRoomPassage(
                this,
                l2r1.name,
                RoomPosition(0, 10),
                Direction4.right,
                l2r5.name,
                RoomPosition(5, 4),
                ExitStyle.metal
                      )
        addRoomPassage(
                this,
                l2r5.name,
                RoomPosition(4, 0),
                Direction4.down,
                l2r6.name,
                RoomPosition(4, l2r6.heightInSpaces - 1),
                ExitStyle.rock
                      )
        addRoomPassage(
                this,
                l2r6.name,
                RoomPosition(l2r6.widthInSpaces - 1, 2),
                Direction4.left,
                l2r7.name,
                RoomPosition(0, 1),
                ExitStyle.rock
                      )
        addRoomPassage(
                this,
                l2r7.name,
                RoomPosition(4, 0),
                Direction4.down,
                l2r8.name,
                RoomPosition(4, l2r8.heightInSpaces - 1),
                ExitStyle.rock
                      )
        addRoomPassage(
                this,
                l2r2.name,
                RoomPosition(6, 0),
                Direction4.down,
                l2r3.name,
                RoomPosition(3, l2r3.heightInSpaces - 1),
                ExitStyle.stone
                      )
        addRoomPassage(
                this,
                l2r3.name,
                RoomPosition(3, 0),
                Direction4.down,
                l2r4.name,
                RoomPosition(13, l2r4.heightInSpaces - 1),
                ExitStyle.stone
                      )
        addRoomPassage(
                this,
                l2r4.name,
                RoomPosition(3, 0),
                Direction4.down,
                l2r8.name,
                RoomPosition(8, l2r8.heightInSpaces - 2),
                ExitStyle.stone
                      )
        addRoomPassage(
                this,
                l2r8.name,
                RoomPosition(5, 0),
                Direction4.down,
                l2r9.name,
                RoomPosition(9, l2r9.heightInSpaces - 3),
                ExitStyle.metal
                      )
        (l2r8.getSpace(5, 0)?.getEntity() as Exit).activeWhenNoEnemiesAreInRoom = true
        // starting position
        //
        changeStartingRoom(l2r1)
        changeStartingPosition(8, l2r1.heightInSpaces - 1)
        // level exit
    }
    // add level to World
    this.addLevel(l2)
    //
    // level 3
    //
    val l3 = Level("3").apply {
        // room 1
        //
        val l3r1 = createRoomFromFile("1", Gdx.files.local("l3r1.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneLight)
            //chests
            val chest = Chest(
                    PossibleItems(
                            mutableListOf(
                                    PossibleItem(WoodenSword(), 1f, 1..2)
                                         )
                                 )
                             )
            addEntityAt(chest, 1, 6)
            // entities
            addEntityAt(EnemyBoar(), 5, 1)
        }
        // add room to level
        addRoom(l3r1)
        
        // room 2
        //
        val l3r2 = createRoomFromFile("2", Gdx.files.local("l3r2.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneDark)
            //chests
            val chest = Chest(
                    PossibleItems(
                            mutableListOf(
                                    PossibleItem(WoodenSword(), 0.9f, 1..2),
                                    PossibleItem(Sledgehammer(), 0.3f, 1..1),
                                    PossibleItem(Shuriken(), 1f, 0..6),
                                    PossibleItem(Mushroom(), 0.5f, 1..2),
                                    PossibleItem(Bomb(), 0.3f, 1..2),
                                    PossibleItem(HPPotionBig(), 0.4f, 1..3)
                                         )
                                 )
                             )
            addEntityAt(chest, 1, 5)
            // entities
            addEntityAt(EnemyMushroom(), 4, 4)
            addEntityAt(EnemyMushroom(), 6, 4)
        }
        // add room to level
        addRoom(l3r2)
        
        // room 3
        //
        val l3r3 = createRoomFromFile("3", Gdx.files.local("l3r3.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneDarkTall)
            //chests
            val chest = Chest(
                    PossibleItems(
                            mutableListOf(
                                    PossibleItem(TurquoiseSword(), 0.3f, 1..1),
                                    PossibleItem(Shuriken(), 1f, 0..6),
                                    PossibleItem(APPotionBig(), 0.3f, 1..2),
                                    PossibleItem(APPotionBig(), 0.7f, 1..4)
                                         )
                                 )
                             )
            addEntityAt(chest, 7, 3)
            // entities
            addEntityAt(EnemyBat(), 3, 5)
            addEntityAt(EnemySkeleton(), 5, 4)
        }
        // add room to level
        addRoom(l3r3)
        
        // room 4
        //
        val l3r4 = createRoomFromFile("4", Gdx.files.local("l3r4.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneLightTall)
            // chests
            val chest = Chest(
                    PossibleItems(
                            mutableListOf(
                                    PossibleItem(WoodenSword(), 0.3f, 1..1),
                                    PossibleItem(SmallAxe(), 0.3f, 1..1),
                                    PossibleItem(Shuriken(), 1f, 2..6),
                                    PossibleItem(APPotionSmall(), 0.3f, 1..3),
                                    PossibleItem(HPPotionSmall(), 0.3f, 1..3)
                                         )
                                 )
                             )
            addEntityAt(chest, 5, 3)
        }
        // add room to level
        addRoom(l3r4)
        
        // room 5
        //
        val l3r5 = createRoomFromFile("5", Gdx.files.local("l3r5.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneLightTall)
            // chests
            val chest = Chest(
                    PossibleItems(
                            mutableListOf(
                                    PossibleItem(AmberSword(), 0.3f, 1..1),
                                    PossibleItem(SmallAxe(), 0.7f, 1..1),
                                    PossibleItem(Shuriken(), 1f, 2..6),
                                    PossibleItem(APPotionSmall(), 0.3f, 1..3)
                                         )
                                 )
                             )
            addEntityAt(chest, 1, 3)
            // entities
            addEntityAt(EnemyMushroom(), 3, 2)
            addEntityAt(EnemySlimeQuarter(), 3, 3)
        }
        // add room to level
        addRoom(l3r5)
        
        // room 6
        //
        val l3r6 = createRoomFromFile("6", Gdx.files.local("l3r6.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneDarkTall)
            // chests
            val chest = Chest(
                    PossibleItems(
                            mutableListOf(
                                    PossibleItem(SmallAxe(), 0.6f, 1..1),
                                    PossibleItem(Staff(), 0.2f, 1..1),
                                    PossibleItem(Shuriken(), 0.5f, 0..6),
                                    PossibleItem(APPotionBig(), 0.2f, 0..2),
                                    PossibleItem(APPotionSmall(), 0.5f, 1..4)
                                         )
                                 )
                             )
            addEntityAt(chest, 3, 1)
            // entities
            addEntityAt(EnemySlimeQuarter(), 2, 2)
            addEntityAt(EnemyPlant(), 4, 2)
            addEntityAt(EnemyRollingStone(), 7, 4)
        }
        // add room to level
        addRoom(l3r6)
        
        // room 7
        //
        val l3r7 = createRoomFromFile("7", Gdx.files.local("l3r7.txt")).apply {
            // walls
            addWalls(WallStyle.brickRedDarkTall)
            // chests
            val chest = Chest(
                    PossibleItems(
                            mutableListOf(
                                    PossibleItem(Sledgehammer(), 0.3f, 1..1),
                                    PossibleItem(Staff(), 0.2f, 1..1),
                                    PossibleItem(IronSword(), 0.7f, 1..1),
                                    PossibleItem(Explosive(), 0.3f, 0..3),
                                    PossibleItem(APPotionBig(), 0.2f, 0..2),
                                    PossibleItem(Bomb(), 0.3f, 1..3)
                                         )
                                 )
                             )
            addEntityAt(chest, 7, 1)
            // entities
            addEntityAt(EnemyGhost(), 5, 6)
            addEntityAt(EnemyBat(), 6, 8)
            addEntityAt(EnemySlimeQuarter(), 10, 7)
            addEntityAt(EnemySlimeQuarter(), 8, 3)
        }
        // add room to level
        addRoom(l3r7)
        
        // room 8
        //
        val l3r8 = createRoomFromFile("8", Gdx.files.local("l3r8.txt")).apply {
            // walls
            addWalls(WallStyle.stone)
            // entities
            addEntityAt(EnemyRollingStone(), 2, 3)
            addEntityAt(EnemyRollingStone(), 9, 3)
            addEntityAt(BossNatureGolem(), 6, 2)
            // level exit
            replaceEntityAt(
                    LevelExit(
                            RoomPosition(7, 0),
                            Direction4.down,
                            "4",
                            ExitStyle.stone,
                            activeWhenNoEnemiesAreInRoom = true
                             ), RoomPosition(7, 0)
                           )
        }
        // add room to level
        addRoom(l3r8)
        
        // add room passages
        //
        addRoomPassage(
                this,
                l3r1.name,
                RoomPosition(6, 1),
                Direction4.left,
                l3r4.name,
                RoomPosition(3, 6),
                ExitStyle.rock
                      )
        (l3r1.getSpace(6, 1)?.getEntity() as Exit).activeWhenNoEnemiesAreInRoom = true
        addRoomPassage(
                this,
                l3r1.name,
                RoomPosition(6, 4),
                Direction4.left,
                l3r2.name,
                RoomPosition(2, 2),
                ExitStyle.rock
                      )
        addRoomPassage(
                this,
                l3r2.name,
                RoomPosition(7, 1),
                Direction4.left,
                l3r3.name,
                RoomPosition(0, 5),
                ExitStyle.rock
                      )
        addRoomPassage(
                this,
                l3r3.name,
                RoomPosition(3, 1),
                Direction4.right,
                l3r5.name,
                RoomPosition(5, 5),
                ExitStyle.rock
                      )
        addRoomPassage(
                this,
                l3r5.name,
                RoomPosition(2, 0),
                Direction4.down,
                l3r7.name,
                RoomPosition(9, 10),
                ExitStyle.rock
                      )
        addRoomPassage(
                this,
                l3r5.name,
                RoomPosition(5, 1),
                Direction4.left,
                l3r6.name,
                RoomPosition(3, 5),
                ExitStyle.rock
                      )
        addRoomPassage(
                this,
                l3r4.name,
                RoomPosition(3, 0),
                Direction4.down,
                l3r7.name,
                RoomPosition(5, 10),
                ExitStyle.rock
                      )
        addRoomPassage(
                this,
                l3r6.name,
                RoomPosition(6, 0),
                Direction4.down,
                l3r8.name,
                RoomPosition(7, 10),
                ExitStyle.rock
                      )
        (l3r6.getSpace(6, 0)?.getEntity() as Exit).activeWhenNoEnemiesAreInRoom = true
        (l3r8.getSpace(7, 10)?.getEntity() as Exit).activeWhenNoEnemiesAreInRoom = true
        // starting position
        //
        changeStartingRoom(l3r1)
        changeStartingPosition(2, 6)
        // level exit
    }
    // add level to World
    this.addLevel(l3)
}

fun pom()
{
    // l1
    val l1r1 =
            "7 7\n" + "x x x x x x x\n" + "x 22 13 23 26 13 15\n" + "x 15 23 25 15 23 14\n" + "x 14 16 17 13 15 23\n" + "x 13 17 18 27 14 25\n" + "x 14 24 27 15 23 13\n" + "x 22 14 13 25 26 24"
    Gdx.files.local("l1r1.txt").writeString(l1r1, false)
    val l1r2 =
            "11 11\n" + "x x x x x x x x x x x\n" + "x 12 10 12 12 11 11 12 10 10 11\n" + "x 10 11 10 12 11 12 10 10 12 11\n" + "x 12 11 10 12 12 10 12 11 10 10\n" + "x 12 12 12 10 11 12 10 12 11 12\n" + "x 10 11 10 11 12 12 10 12 12 10\n" + "x 12 10 11 11 10 12 11 10 11 11\n" + "x 11 10 11 11 10 12 12 11 12 10\n" + "x 12 10 10 10 11 11 12 10 11 12\n" + "x 11 11 12 12 10 11 10 12 10 11\n" + "x 10 11 10 12 11 12 12 10 12 12"
    Gdx.files.local("l1r2.txt").writeString(l1r2, false)
    val l1r3 =
            "7 6\n" + "x x x x x x\n" + "x 20 20 20 21 20\n" + "x 20 19 20 20 21\n" + "x 19 21 20 19 19\n" + "x 20 20 20 21 20\n" + "x 21 20 19 20 21\n" + "x 19 20 21 20 19"
    Gdx.files.local("l1r3.txt").writeString(l1r3, false)
    val l1r4 =
            "11 11\n" + "x x x x x x x x x x x\n" + "x x x x x x 10 11 10 12 7\n" + "x x x x x x 11 8 10 10 11\n" + "x x x x x x 10 11 7 7 10\n" + "x x x x x x 11 8 12 8 8\n" + "x x x x x x 10 8 7 8 7\n" + "x x x x x x 8 7 11 7 7\n" + "x 10 10 7 8 10 12 9 9 9 9\n" + "x 11 8 7 10 9 9 9 5 5 5\n" + "x 10 8 11 9 9 5 5 5 5 5\n" + "x 7 8 9 9 5 5 5 5 5 5"
    Gdx.files.local("l1r4.txt").writeString(l1r4, false)
    val l1r5 =
            "11 14\n" + "x x x x x x x x x x x x x x\n" + "x 2 2 2 4 4 4 4 4 2 2 2 2 2\n" + "x 2 2 2 2 2 2 2 4 4 2 2 2 2\n" + "x 2 2 2 2 2 2 2 2 2 2 2 2 2\n" + "x 2 2 2 2 2 2 2 2 2 2 2 2 2\n" + "x 2 2 2 2 2 2 2 2 2 4 4 2 2\n" + "x 2 2 2 2 2 2 2 2 2 4 4 2 2\n" + "x 2 2 4 4 2 2 2 2 4 4 4 2 2\n" + "x 2 2 4 4 2 2 4 4 4 4 4 2 2\n" + "x 2 2 2 2 2 2 2 2 2 2 2 2 2\n" + "x x x x x x x x x x x x x x\n"
    Gdx.files.local("l1r5.txt").writeString(l1r5, false)
    // l2
    val l2r1 = """15 17
x x x x x x x x x x x x x x x x x
x x x x x x x x x x 2 2 2 2 2 2 2
x x x x x x x x x x 2 2 2 2 2 2 2
x x x x x x x x x x 2 2 2 2 2 2 2
x x x 2 2 2 2 2 2 2 2 2 2 2 2 2 2
x x x 2 2 2 2 2 2 2 2 2 2 2 2 2 2
x x x 2 2 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2"""
    Gdx.files.local("l2r1.txt").writeString(l2r1, false)
    val l2r2 = """5 10
x x x x x x x x x x
x 7 8 12 11 7 12 11 12 7
x 10 12 12 8 10 11 8 8 12
x 11 8 7 12 7 11 7 8 11
x 12 7 12 10 12 8 11 12 7"""
    Gdx.files.local("l2r2.txt").writeString(l2r2, false)
    val l2r3 = """9 7
x x x x x x x
x 9 2 2 2 2 9
x 9 2 2 9 9 9
x 9 9 2 9 9 9
x 9 9 2 2 9 9
x 9 9 2 2 9 9
x 9 9 2 2 9 9
x 9 9 2 2 2 9
x 9 2 2 2 2 9"""
    Gdx.files.local("l2r3.txt").writeString(l2r3, false)
    val l2r4 = """7 17
x x x x x x x x x x x x x x x x x
x x 7 7 7 7 8 8 8 7 7 7 8 7 8 8 8
x 7 8 8 8 10 12 10 11 11 10 12 11 12 8 8 7
x 8 8 7 7 12 11 10 10 12 10 11 12 7 10 7 7
x 7 7 7 8 8 7 8 8 7 7 8 8 12 11 7 8
x x x x x x x x x x x 7 8 7 7 8 8
x x x x x x x x x x x 7 7 7 8 7 8"""
    Gdx.files.local("l2r4.txt").writeString(l2r4, false)
    val l2r5 = """7 6
x x x x x x
x 2 2 8 7 2
x 2 2 7 2 2
x 2 8 7 7 2
x 2 7 8 8 7
x 2 2 2 7 2
x 2 2 2 2 2"""
    Gdx.files.local("l2r5.txt").writeString(l2r5, false)
    val l2r6 = """11 10
x x x x x x x x x x
x x x x x x x 2 2 2
x x x x x x x 2 11 10
x x x x x 2 2 11 10 11
x x x x x 2 2 11 10 2
x x x 2 2 11 10 2 2 2
x x x 2 11 11 2 x x x
x 2 2 2 2 10 2 x x x
x 2 2 2 11 2 2 x x x
x 2 2 11 10 2 2 x x x
x 2 2 2 10 2 2 x x x"""
    Gdx.files.local("l2r6.txt").writeString(l2r6, false)
    val l2r7 = """8 6
x x x x x x
x 2 2 2 2 2
x 2 2 2 2 2
x 2 2 2 2 2
x 2 2 2 2 2
x 2 2 2 2 2
x 2 2 2 2 2
x 2 2 2 2 2"""
    Gdx.files.local("l2r7.txt").writeString(l2r7, false)
    val l2r8 = """8 11
x x x x x x x x x x x
x 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 x x x x"""
    Gdx.files.local("l2r8.txt").writeString(l2r8, false)
    val l2r9 = """10 20
x x x x x x x x x x x x x x x x x x x x
x 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 x 2 2 2 2 2 2 2 2 2 x 2 2 2 2
x 2 2 2 2 2 x 2 2 2 2 2 2 2 x 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 x x x x x 2 2 2 2 2 2 2
x 2 2 2 2 x x x x x x x x x x x 2 2 2 2
x 2 2 2 2 x x x x x x x x x x x 2 2 2 2"""
    Gdx.files.local("l2r9.txt").writeString(l2r9, false)
    // l3
    val l3r1 = """7 7
x x x x x x x
x x x 2 2 2 2
x x x 2 2 2 2
x x x 2 2 2 2
x 2 2 2 2 2 2
x 2 2 2 2 x x
x 2 2 2 2 x x"""
    Gdx.files.local("l3r1.txt").writeString(l3r1, false)
    val l3r2 = """7 8
x x x x x x x x
x x x 2 2 2 2 2
x x x 2 2 2 2 2
x x x 2 2 2 2 2
x 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2"""
    Gdx.files.local("l3r2.txt").writeString(l3r2, false)
    val l3r3 = """9 8
x x x x x x x x
x x x x 2 2 2 2
x x x x 2 2 2 2
x 2 2 2 2 2 2 2
x 2 2 2 2 2 x x
x 2 2 2 2 2 x x
x 2 2 2 x x x x
x 2 2 2 x x x x"""
    Gdx.files.local("l3r3.txt").writeString(l3r3, false)
    val l3r4 = """7 6
x x x x x x
x 2 2 2 2 2
x 2 2 2 2 2
x 2 2 2 2 2
x 2 2 2 2 2
x 2 2 2 2 2
x x x x 2 2"""
    Gdx.files.local("l3r4.txt").writeString(l3r4, false)
    val l3r5 = """7 6
x x x x x x
x 2 2 2 2 2
x 2 2 2 2 2
x 2 2 2 2 2
x 2 2 2 2 2
x 2 2 2 2 2
x 2 2 2 2 2"""
    Gdx.files.local("l3r5.txt").writeString(l3r5, false)
    val l3r6 = """8 10
x x x x x x x x x x
x 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 x x
x x x x 2 2 2 2 x x
x x x x 2 2 2 2 2 2
x x x x 2 2 2 2 2 2"""
    Gdx.files.local("l3r6.txt").writeString(l3r6, false)
    val l3r7 = """11 12
x x x x x x x x x x x x
x x x x x x x 2 2 2 2 2
x x x x x x x 2 2 2 2 2
x x x x x x x 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2"""
    Gdx.files.local("l3r7.txt").writeString(l3r7, false)
    val l3r8 = """11 13
x x x x x x x x x x x x x
x 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2
x 2 2 2 2 2 2 2 2 2 2 2 2"""
    Gdx.files.local("l3r8.txt").writeString(l3r8, false)
}

private fun createRoomFromFile(name : String, fileHandle : FileHandle) : Room
{
    val reader = fileHandle.reader().buffered()
    // first line contains room dimensions
    val (height : Int, width : Int) = reader.buffered().readLine().split(" ").map { it.toInt() }
    // room bases
    val roomBasesArray : Array<Array<Int?>> = Array(height) { Array(width) { null } }
    reader.run {
        var y = 0
        this.forEachLine { line ->
            val bases = line.split(" ").map { it.toIntOrNull() }
            for (x in bases.indices) roomBasesArray[y][x] = bases[x]
            y++
        }
    }
    // create Room
    val room = Room(name, height, width)
    // change bases
    for (y in roomBasesArray.indices)
    {
        for (x in roomBasesArray[y].indices)
        {
            val baseNumber = roomBasesArray[y][x]
            if (baseNumber != null)
            {
                val base = Base.getBase(baseNumber)
                room.changeBaseAt(base, x, y)
            }
            else
            {
                // delete base and space
                room.changeBaseAt(null, x, y)
                room.deleteSpaceAt(x, y)
            }
        }
    }
    return room
}

private fun Room.addWalls(wallStyle : WallStyle = WallStyle.stone)
{
    // edges
    val upEdge = 0
    val downEdge = this.heightInSpaces
    val leftEdge = 0
    val rightEdge = this.widthInSpaces
    // find wall positions
    val leftSideWallPositions = mutableListOf<RoomPosition>()
    for (y in upEdge until downEdge)
    {
        for (x in leftEdge until rightEdge)
        {
            if (this.getSpace(x, y) != null)
            {
                leftSideWallPositions.add(RoomPosition(x - 1, y))
                break
            }
        }
    }
    val upSideWallPositions = mutableListOf<RoomPosition>()
    for (x in leftEdge until rightEdge)
    {
        for (y in upEdge until downEdge)
        {
            if (this.getSpace(x, y) != null)
            {
                upSideWallPositions.add(RoomPosition(x, y - 1))
                break
            }
        }
    }
    // add walls
    for (pos in leftSideWallPositions)
    {
        this.addSpaceAt(pos.x, pos.y)
        val wall = Wall(wallStyle, Direction4.right)
        this.addEntityAt(wall, pos)
    }
    for (pos in upSideWallPositions)
    {
        this.addSpaceAt(pos.x, pos.y)
        if (pos in leftSideWallPositions)
        {
            val wall = Wall(wallStyle, Direction4.down, Direction4.right)
            this.replaceEntityAt(wall, pos)
        }
        else
        {
            val wall = Wall(wallStyle, Direction4.down)
            this.addEntityAt(wall, pos)
        }
    }
}

fun addRoomPassage(
        level : Level,
        roomAName : String,
        positionA : RoomPosition,
        directionA : Direction4,
        roomBName : String,
        positionB : RoomPosition,
        exitStyle : ExitStyle = ExitStyle.stone,
        exitABase : Base? = null,
        exitBBase : Base? = null
                  )
{
    val directionB = directionA.opposite()
    
    val exitA = RoomExit(positionA, directionA, roomBName, positionB, exitStyle)
    if (exitABase != null) level.rooms.find { it.name == roomAName }?.changeBaseAt(exitABase, positionA)
    level.rooms.find { it.name == roomAName }?.replaceEntityAt(exitA, positionA)
    
    val exitB = RoomExit(positionB, directionB, roomAName, positionA, exitStyle)
    if (exitBBase != null) level.rooms.find { it.name == roomBName }?.changeBaseAt(exitBBase, positionB)
    level.rooms.find { it.name == roomBName }?.replaceEntityAt(exitB, positionB)
}