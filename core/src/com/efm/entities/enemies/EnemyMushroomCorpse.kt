package com.efm.entities.enemies

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.*
import com.efm.multiUseMapItems.WoodenSword
import com.efm.room.RoomPosition
import com.efm.stackableSelfItems.Mushroom

class EnemyMushroomCorpse(position : RoomPosition = RoomPosition()) : EnemyCorpse(position)
{
    override var maxItems : Int = 4
    
    init
    {
        loot = PossibleItems(
                mutableListOf(
                        PossibleItem(Mushroom(), 0.8f, IntRange(0, 4)),
                        PossibleItem(WoodenSword(), 0.1f, 1..1)
                             )
                            )
    }
    
    override val items : MutableList<Item> = loot?.drawItems() ?: mutableListOf()
    
    override fun getTile() : TiledMapTile = Tiles.mushroomCorpse
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.mushroomCorpseOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile? = null
}
