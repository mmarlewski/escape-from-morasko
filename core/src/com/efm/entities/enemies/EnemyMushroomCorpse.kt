package com.efm.entities.enemies

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.PossibleItem
import com.efm.item.PossibleItems
import com.efm.multiUseMapItems.WoodenSword
import com.efm.room.RoomPosition
import com.efm.stackableSelfItems.Mushroom

class EnemyMushroomCorpse(position : RoomPosition = RoomPosition(), loot : PossibleItems = defaultLoot) : EnemyCorpse(
        position,
        loot
                                                                                                                     )
{
    override fun getTile() : TiledMapTile = Tiles.mushroomCorpse
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.mushroomCorpseOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile? = null
    
    companion object
    {
        val defaultLoot = PossibleItems(
                mutableListOf(
                        PossibleItem(Mushroom(), 0.8f, IntRange(0, 4)),
                        PossibleItem(WoodenSword(), 0.1f, 1..1)
                             )
                                       )
    }
}
