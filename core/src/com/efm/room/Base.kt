package com.efm.room

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles

/**
 * Translates names of surfaces to textures representing them.
 */
enum class Base(val tile : TiledMapTile, val isTreadable : Boolean)
{
    stone(Tiles.stoneFloor, true),
    metal(Tiles.metalFloor, true),
    rock(Tiles.rockFloor, true),
    grass(Tiles.grassFloor, true),
    lava(Tiles.lavaPool, false)
}
