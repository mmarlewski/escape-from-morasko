package com.efm.entities.enemies.mimic

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.PossibleItem
import com.efm.item.PossibleItems
import com.efm.multiUseMapItems.WoodenSword
import com.efm.room.RoomPosition
import kotlin.random.Random

class EnemyMimicCorpse(position : RoomPosition = RoomPosition(),
                       loot : PossibleItems? = null,
                       seed : Int = Random.nextInt()) : EnemyCorpse(position,loot, seed)
{
    override fun getTile() : TiledMapTile = Tiles.mimicCorpse
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.mimicCorpseOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile? = null
}

val defaultLoot = PossibleItems(
        mutableListOf(
                PossibleItem(WoodenSword(), 0.8f, IntRange(1, 1))
                     )
                               )