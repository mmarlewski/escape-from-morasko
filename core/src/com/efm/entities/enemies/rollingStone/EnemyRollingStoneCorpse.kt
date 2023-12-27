package com.efm.entities.enemies.rollingStone

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.PossibleItems
import com.efm.room.RoomPosition
import kotlin.random.Random

class EnemyRollingStoneCorpse(position : RoomPosition = RoomPosition(),
                              loot : PossibleItems? = null,
                              seed : Int = Random.nextInt()) : EnemyCorpse(position, loot, seed)
{
    override fun getTile() : TiledMapTile = Tiles.rockCorpse
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.rockCorpseOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile? = null
}