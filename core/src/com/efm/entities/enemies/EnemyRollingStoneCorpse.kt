package com.efm.entities.enemies

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.Item
import com.efm.item.PossibleItems
import com.efm.room.RoomPosition

class EnemyRollingStoneCorpse(position : RoomPosition = RoomPosition()) : EnemyCorpse(position)
{
    override var maxItems : Int = 2
    override val loot : PossibleItems = PossibleItems()
    override val items : MutableList<Item> = loot.drawItems()
    
    override fun getTile() : TiledMapTile = Tiles.rockCorpse
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.rockCorpseOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile? = null
}