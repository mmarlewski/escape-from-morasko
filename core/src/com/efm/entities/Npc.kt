package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.entity.Interactive
import com.efm.room.RoomPosition

class Npc : Entity, Interactive
{
    override val position = RoomPosition()
    
    override fun getTile() : TiledMapTile?
    {
        return Tiles.npc
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile?
    {
        return Tiles.npcOutlineYellow
    }
    
    override fun getOutlineTealTile() : TiledMapTile?
    {
        return Tiles.npcOutlineTeal
    }
    
    override fun interact()
    {
        //
    }
}
