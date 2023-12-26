@file:Suppress("unused")

package com.efm

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.efm.entities.*
import com.efm.entities.bosses.*
import com.efm.entities.bosses.slime.*
import com.efm.entities.enemies.*
import com.efm.entities.enemies.bat.EnemyBat
import com.efm.entities.enemies.dzik.EnemyBoar
import com.efm.entities.enemies.ghost.EnemyGhost
import com.efm.entities.enemies.mushroom.EnemyMushroom
import com.efm.entities.enemies.plant.EnemyPlant
import com.efm.entities.enemies.rollingStone.EnemyRollingStone
import com.efm.entities.enemies.skeleton.EnemySkeleton
import com.efm.entities.walls.*
import com.efm.entity.Chest
import com.efm.exit.*
import com.efm.item.PossibleItem
import com.efm.item.PossibleItems
import com.efm.level.Level
import com.efm.level.World
import com.efm.multiUseMapItems.*
import com.efm.room.*
import com.efm.stackableMapItems.*
import com.efm.stackableSelfItems.*

fun World.createWorldPrototype()
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
            val chest = Chest()
            chest.addItem(WoodenSword())
            chest.addItem(Fish(2))
            addEntityAt(chest, 4, 3)
            /*//test
            val ch = Chest(PossibleItems(mutableListOf(PossibleItem(Fish(), 1f, 1..2), PossibleItem(Apple(), 1f, 1..2))))
            val crp = EnemyMimicCorpse(
                    RoomPosition(),
                    PossibleItems(mutableListOf(PossibleItem(Fish(), 1f, 1..2), PossibleItem(Apple(), 1f, 1..2)))
                                      )
            addEntityAt(ch, 4, 4)
            addEntityAt(crp, 4, 5)*/
        }
        // add room to level
        addRoom(l1r1)
        
        // room 2
        //
        val l1r2 = createRoomFromFile("2", Gdx.files.local("l1r2.txt")).apply {
            // walls
            addWalls(WallStyle.cobblestoneDarkTall)
            // entities
            addEntityAt(EnemyMushroom(), 5, 4)
            val chest = Chest()
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
            addEntityAt(EnemyMushroom(), 4, 8)
            addEntityAt(EnemySkeleton(), 7, 5)
            val chest = Chest(
                    PossibleItems(
                            mutableListOf(
                                    PossibleItem(WoodenSword(), 1f, IntRange(1, 2))
                                         )
                                 )
                             )
            addEntityAt(chest, 6, 5)
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
                RoomPosition(0, 6),
                ExitStyle.metal,
                exitBBase = Base.stone
                      )
        
        // after going through exit focus camera on tutorial enemy
        val tutorialExit =
                TutorialRoomExit(RoomPosition(6, 3), Direction4.left, l1r2.name, RoomPosition(0, 6), ExitStyle.metal)
        l1r1.replaceEntityAt(tutorialExit, 6, 3)
        
        addRoomPassage(
                this,
                l1r2.name,
                RoomPosition(l1r2.widthInSpaces - 1, 2),
                Direction4.left,
                l1r3.name,
                RoomPosition(0, 3),
                ExitStyle.stone
                      )
        addRoomPassage(
                this,
                l1r2.name,
                RoomPosition(l1r2.widthInSpaces - 1, 7),
                Direction4.left,
                l1r4.name,
                RoomPosition(0, 8),
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
        // boss
        addBossRoomPassage(
                this,
                l1r4.name,
                RoomPosition(l1r4.widthInSpaces - 1, 7),
                Direction4.left,
                l1r5.name,
                RoomPosition(0, 7),
                ExitStyle.stone
                          )
        // starting position
        //
        startingRoom = l1r1
        startingPosition.set(1, 3)
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
                                    PossibleItem(Bow(), 0.5f, 1..1),
                                    PossibleItem(Explosive(), 0.4f, 0..3),
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
            addEntityAt(Npc().apply { modifier = Modifier.StrongerWeaponsLoseHp }, 1, 1)
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
                                    PossibleItem(IronSword(), 0.4f, 1..1),
                                    PossibleItem(Sledgehammer(), 0.4f, 1..1),
                                    PossibleItem(Shuriken(), 1f, 0..6),
                                    PossibleItem(Mushroom(), 1f, 0..4),
                                    PossibleItem(APPotionSmall(), 0.4f, 1..3),
                                    PossibleItem(HPPotionSmall(), 0.4f, 1..3)
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
                                    PossibleItem(SmallAxe(), 0.5f, 1..1),
                                    PossibleItem(IronSword(), 0.5f, 1..1),
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
        startingRoom = l2r1
        startingPosition.set(8, l2r1.heightInSpaces - 1)
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
                                    PossibleItem(AmberSword(), 1f, 1..2),
                                    PossibleItem(Explosive(), 0.5f, 1..3),
                                    PossibleItem(Apple(), 0.5f, 1..4)
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
                                    PossibleItem(WoodenSword(), 1f, 1..2),
                                    PossibleItem(Sledgehammer(), 0.4f, 1..1),
                                    PossibleItem(Shuriken(), 1f, 0..6),
                                    PossibleItem(Mushroom(), 0.6f, 1..2),
                                    PossibleItem(Bomb(), 0.5f, 1..2),
                                    PossibleItem(HPPotionBig(), 0.5f, 1..3)
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
                                    PossibleItem(TurquoiseSword(), 0.4f, 1..1),
                                    PossibleItem(WoodenSword(), 0.2f, 1..1),
                                    PossibleItem(Shuriken(), 1f, 0..6),
                                    PossibleItem(Explosive(), 0.4f, 0..3),
                                    PossibleItem(APPotionBig(), 0.4f, 1..2),
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
                                    PossibleItem(WoodenSword(), 0.6f, 1..1),
                                    PossibleItem(SmallAxe(), 0.6f, 1..1),
                                    PossibleItem(Shuriken(), 1f, 2..6),
                                    PossibleItem(Explosive(), 0.4f, 0..3),
                                    PossibleItem(APPotionSmall(), 0.3f, 1..3),
                                    PossibleItem(HPPotionSmall(), 0.3f, 1..3)
                                         )
                                 )
                             )
            addEntityAt(chest, 5, 3)
            // entities
            addEntityAt(Npc().apply { modifier = Modifier.AddRandomSkill }, 5, 2)
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
                                    PossibleItem(AmberSword(), 0.4f, 1..1),
                                    PossibleItem(SmallAxe(), 0.8f, 1..1),
                                    PossibleItem(Bow(), 0.5f, 1..1),
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
                                    PossibleItem(SmallAxe(), 0.7f, 1..1),
                                    PossibleItem(Staff(), 0.3f, 1..1),
                                    PossibleItem(Shuriken(), 0.7f, 0..6),
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
                                    PossibleItem(Sledgehammer(), 0.5f, 1..1),
                                    PossibleItem(Staff(), 0.3f, 1..1),
                                    PossibleItem(IronSword(), 0.9f, 1..1),
                                    PossibleItem(Explosive(), 0.4f, 0..3),
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
        startingRoom = l3r1
        startingPosition.set(2, 6)
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
    val l1r2 = """9 9
x x x x x x x x x
x 12 10 12 12 11 11 12 10
x 10 11 10 12 11 12 10 10
x 12 11 10 12 12 10 12 11
x 12 12 12 10 11 12 10 12
x 12 10 11 11 10 12 11 10
x 11 10 11 11 10 12 12 11
x 12 10 10 10 11 11 12 10
x 11 11 12 12 10 11 10 12"""
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

fun addBossRoomPassage(
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
    
    val exitA = BossExit(positionA, directionA, roomBName, positionB, exitStyle)
    if (exitABase != null) level.rooms.find { it.name == roomAName }?.changeBaseAt(exitABase, positionA)
    level.rooms.find { it.name == roomAName }?.replaceEntityAt(exitA, positionA)
    
    val exitB = BossExit(positionB, directionB, roomAName, positionA, exitStyle)
    if (exitBBase != null) level.rooms.find { it.name == roomBName }?.changeBaseAt(exitBBase, positionB)
    level.rooms.find { it.name == roomBName }?.replaceEntityAt(exitB, positionB)
}