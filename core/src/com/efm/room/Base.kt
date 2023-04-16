package com.efm.room

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles

/**
 * Translates names of surfaces to textures representing them.
 */
enum class Base(val tile : TiledMapTile)
{
    stone(Tiles.stoneFloor),
    metal(Tiles.metalFloor),
    rock(Tiles.rockFloor),
    grass(Tiles.grassFloor),
    lava(Tiles.lavaPool)
}
