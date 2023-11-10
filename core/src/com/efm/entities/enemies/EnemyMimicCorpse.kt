package com.efm.entities.enemies

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.*
import com.efm.multiUseMapItems.WoodenSword
import com.efm.room.RoomPosition

class EnemyMimicCorpse(position : RoomPosition = RoomPosition()) : EnemyCorpse(position)
{
    override var maxItems : Int = 4
    override var loot = PossibleItems(
            mutableListOf(
                    PossibleItem(WoodenSword(), 0.8f, IntRange(1, 1))
                         )
                                     )
    override val items : MutableList<Item> = loot.drawItems()
    
    override fun getTile() : TiledMapTile = Tiles.mimicCorpse
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.mimicCorpseOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile? = null
}