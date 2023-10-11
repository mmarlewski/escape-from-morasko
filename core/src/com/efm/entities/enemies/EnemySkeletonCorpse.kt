package com.efm.entities.enemies

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.*
import com.efm.multiUseMapItems.Bow
import com.efm.room.RoomPosition
import com.efm.stackableSelfItems.PotionSmall

class EnemySkeletonCorpse(position : RoomPosition = RoomPosition()) : EnemyCorpse(position)
{
    override var maxItems : Int = 4
    override var loot = PossibleItems(
            mutableListOf(
                    PossibleItem(Bow(), 0.5f, IntRange(1, 1)),
                    PossibleItem(PotionSmall(), 0.8f, IntRange(0, 3))
                         )
                                     )
    override val items : MutableList<Item> = loot.drawItems()
    
    override fun getTile() : TiledMapTile = Tiles.skeletonCorpse
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.skeletonCorpseOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile? = null
}