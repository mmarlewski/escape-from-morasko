package com.efm.room

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entities.Hero
import com.efm.entities.enemies.EnemyBat
import com.efm.entity.Entity
import com.efm.skills.LavaWalking
import com.efm.skills.WaterWalking

/**
 * Base is a part of Space on witch Entities walk
 */
enum class Base(val tile : TiledMapTile, val isTreadableFor : (Entity) -> Boolean = { true })
{
    stone(Tiles.stoneFloor, { true }),
    metal(Tiles.metalFloor, { true }),
    rock(Tiles.rockFloor, { true }),
    grass(Tiles.grassFloor, { true }),
    lava(Tiles.lavaFloor, { entity -> entity is EnemyBat || (entity is Hero && entity.hasSkill(LavaWalking)) }),
    water(Tiles.waterFloor, { entity -> entity is EnemyBat || (entity is Hero && entity.hasSkill(WaterWalking)) }),
    waterOctopus(Tiles.waterFloorOctopus, { false }),
    grassDark1(Tiles.grassDarkFloor1),
    grassDark2(Tiles.grassDarkFloor2),
    grassLight1(Tiles.grassLightFloor1),
    grassStone1(Tiles.grassStoneFloor1),
    grassStone2(Tiles.grassStoneFloor2),
    grassStone3(Tiles.grassStoneFloor3),
    tiled1(Tiles.tiledFloor1),
    tiled2(Tiles.tiledFloor2),
    tiled3(Tiles.tiledFloor3),
    tiled1blood1(Tiles.tiledFloor1Blood1),
    tiled1blood2(Tiles.tiledFloor1Blood2),
    tiled1blood3(Tiles.tiledFloor1Blood3),
    wooden1(Tiles.woodenFloor1),
    wooden2(Tiles.woodenFloor2),
    wooden3(Tiles.woodenFloor3),
    // "TO DO" find a better way
    tiled2x(Tiles.tiledFloor2x),
    tiled2y(Tiles.tiledFloor2y),
    tiled2xy(Tiles.tiledFloor2xy),
    tiled3x(Tiles.tiledFloor3x),
    tiled3y(Tiles.tiledFloor3y),
    tiled3xy(Tiles.tiledFloor3xy),
    grassDarkDrained1(Tiles.grassDarkFloorDrained1),
    grassDarkDrained2(Tiles.grassDarkFloorDrained2),
    grassLightDrained1(Tiles.grassLightFloorDrained1),
    grassStoneDrained1(Tiles.grassStoneFloorDrained1),
    grassStoneDrained2(Tiles.grassStoneFloorDrained2),
    grassStoneDrained3(Tiles.grassStoneFloorDrained3);
    
    companion object
    {
        fun getOrdinal(base : Base) : Int = base.ordinal
        fun getBase(baseNumber : Int) = values()[baseNumber]
        
        val grassTiles = listOf(
                grassDark1,
                grassDark2,
                grassLight1,
                grassStone1,
                grassStone2,
                grassStone3
                               )
        
        val woodTiles = listOf(
                wooden1,
                wooden2,
                wooden3
                              )
        val tiledTiles = listOf(
                tiled1,
                tiled2,
                tiled3,
                tiled2x,
                tiled2y,
                tiled2xy,
                tiled3x,
                tiled3y,
                tiled3xy
                               )
        val tiledTilesWithBlood = listOf(
                tiled1,
                tiled2,
                tiled3,
                tiled1blood1,
                tiled1blood2,
                tiled1blood3,
                tiled2x,
                tiled2y,
                tiled2xy,
                tiled3x,
                tiled3y,
                tiled3xy
                               )
    }
}
