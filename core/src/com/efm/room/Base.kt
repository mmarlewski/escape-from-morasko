package com.efm.room

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entities.Hero
import com.efm.entities.enemies.EnemyBat
import com.efm.entity.Entity
import com.efm.skill.Skill

/**
 * Translates names of surfaces to textures representing them.
 */
enum class Base(val tile : TiledMapTile, val isTreadableFor : (Entity) -> Boolean)
{
    stone(Tiles.stoneFloor, { true }),
    metal(Tiles.metalFloor, { true }),
    rock(Tiles.rockFloor, { true }),
    grass(Tiles.grassFloor, { true }),
    lava(Tiles.lavaFloor, { entity -> entity is EnemyBat || (entity is Hero && entity.hasSkill(Skill.lavaWalking)) }),
    water(Tiles.waterFloor, { entity -> entity is EnemyBat || (entity is Hero && entity.hasSkill(Skill.waterWalking)) }),
    waterOctopus(Tiles.waterFloorOctopus, { false })
}
