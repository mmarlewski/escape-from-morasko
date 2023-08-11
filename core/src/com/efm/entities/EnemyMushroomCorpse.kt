package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.room.RoomPosition

class EnemyMushroomCorpse : Entity
{
    override val position = RoomPosition()
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.mushroomCorpse
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return Tiles.mushroomCorpseOutlineYellow
    }
}
