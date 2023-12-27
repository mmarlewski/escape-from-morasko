package com.efm.entities.enemies.ghost

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.PossibleItems
import com.efm.room.RoomPosition
import kotlin.random.Random

class EnemyGhostCorpse(position : RoomPosition = RoomPosition(),
                       loot : PossibleItems? = null,
                       seed : Int = Random.nextInt()) : EnemyCorpse(position, loot, seed)
{
    override fun getTile() : TiledMapTile = Tiles.ghostCorpse
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.ghostCorpseOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile? = null
}