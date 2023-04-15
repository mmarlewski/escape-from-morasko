package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Character
import com.efm.room.RoomCoordinates

/**
 * Hero has its own turn and is controlled by the player.
 */
class Hero : Character
{
    override val roomCoordinates = RoomCoordinates(0, 0)
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.hero
    }
    
    override fun getOutlineTile() : TiledMapTile
    {
        return Tiles.heroOutline
    }
}
