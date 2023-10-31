package com.efm.room

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entities.Hero
import com.efm.entities.enemies.EnemyBat
import com.efm.entity.Entity
import com.efm.skills.LavaWalking
import com.efm.skills.WaterWalking

enum class Base(val tile : TiledMapTile, val isTreadableFor : (Entity) -> Boolean)
{
    stone(Tiles.stoneFloor, { true }),
    metal(Tiles.metalFloor, { true }),
    rock(Tiles.rockFloor, { true }),
    grass(Tiles.grassFloor, { true }),
    lava(Tiles.lavaFloor, { entity -> entity is EnemyBat || (entity is Hero && entity.hasSkill(LavaWalking)) }),
    water(Tiles.waterFloor, { entity -> entity is EnemyBat || (entity is Hero && entity.hasSkill(WaterWalking)) }),
    waterOctopus(Tiles.waterFloorOctopus, { false })
}
