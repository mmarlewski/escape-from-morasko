package com.efm.entities.enemies.plant

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.PossibleItems
import com.efm.room.RoomPosition
import kotlin.random.Random

class EnemyPlantCorpse(position : RoomPosition = RoomPosition(),
                       loot : PossibleItems? = null,
                       seed : Int = Random.nextInt()) : EnemyCorpse(position, loot, seed)
{
    override fun getTile() : TiledMapTile = Tiles.plantCorpse
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.plantCorpseOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile? = null
}