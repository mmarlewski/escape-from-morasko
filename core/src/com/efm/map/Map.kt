package com.efm.map

import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile

class Map(var numberOfRows : Int, var numberOfColumns : Int)
{
    val canBeSteppedOn : Array<BooleanArray> = Array(numberOfRows) { BooleanArray(numberOfColumns) }
    val groundTiles : Array<Array<StaticTiledMapTile?>> =
            Array(numberOfRows) { Array<StaticTiledMapTile?>(numberOfColumns) { i -> null } }
    val entities : Array<Array<Any?>> = Array(numberOfRows) { Array<Any?>(numberOfColumns) { i -> null } }
}