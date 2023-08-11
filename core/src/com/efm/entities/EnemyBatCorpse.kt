package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.room.RoomPosition

class EnemyBatCorpse : Entity
{
    override val position = RoomPosition()
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.batCorpse
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return Tiles.batCorpseOutlineYellow
    }
}
