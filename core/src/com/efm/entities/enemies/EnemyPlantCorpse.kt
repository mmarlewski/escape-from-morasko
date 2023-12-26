package com.efm.entities.enemies

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.PossibleItems
import com.efm.room.RoomPosition

class EnemyPlantCorpse(position : RoomPosition = RoomPosition(), loot : PossibleItems = defaultLoot) : EnemyCorpse(
        position,
        loot
                                                                                                                  )
{
    override fun getTile() : TiledMapTile = Tiles.plantCorpse
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.plantCorpseOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile? = null
    
    companion object
    {
        val defaultLoot = PossibleItems()
    }
}
