package com.efm.entities.enemies

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.PossibleItem
import com.efm.item.PossibleItems
import com.efm.multiUseMapItems.WoodenSword
import com.efm.room.RoomPosition

class EnemyMimicCorpse(position : RoomPosition = RoomPosition(), loot : PossibleItems = defaultLoot) : EnemyCorpse(
        position,
        loot
                                                                                                                  )
{
    override fun getTile() : TiledMapTile = Tiles.mimicCorpse
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.mimicCorpseOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile? = null
    
    companion object
    {
        val defaultLoot = PossibleItems(
                mutableListOf(
                        PossibleItem(WoodenSword(), 0.8f, IntRange(1, 1))
                             )
                                       )
    }
}