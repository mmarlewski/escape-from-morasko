package com.efm.entities.enemies

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.PossibleItem
import com.efm.item.PossibleItems
import com.efm.multiUseMapItems.Bow
import com.efm.room.RoomPosition
import com.efm.stackableSelfItems.APPotionSmall
import com.efm.stackableSelfItems.HPPotionSmall

class EnemySkeletonCorpse(position : RoomPosition = RoomPosition(), loot : PossibleItems = defaultLoot) : EnemyCorpse(
        position,
        loot
                                                                                                                     )
{
    override fun getTile() : TiledMapTile = Tiles.skeletonCorpse
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.skeletonCorpseOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile? = null
    
    companion object
    {
        val defaultLoot = PossibleItems(
                mutableListOf(
                        PossibleItem(Bow(), 0.5f, IntRange(1, 1)),
                        PossibleItem(APPotionSmall(), 0.8f, IntRange(0, 1)),
                        PossibleItem(HPPotionSmall(), 0.8f, IntRange(0, 3))
                             )
                                       )
    }
}
