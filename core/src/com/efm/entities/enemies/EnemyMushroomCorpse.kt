package com.efm.entities.enemies

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.*
import com.efm.room.RoomPosition
import com.efm.stackableSelfItems.Mushroom

class EnemyMushroomCorpse(position : RoomPosition = RoomPosition()) : EnemyCorpse(position)
{
    override var maxItems : Int = 4
    override var loot = PossibleItems(
            mutableListOf(
                    PossibleItem(Mushroom(), 0.8f, IntRange(0, 4))
                         )
                                     )
    override val items : MutableList<Item> = loot.drawItems()
    
    override fun getTile() : TiledMapTile = Tiles.mushroomCorpse
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.mushroomCorpseOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile? = null
}
