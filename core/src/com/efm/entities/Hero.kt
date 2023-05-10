package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Character
import com.efm.room.RoomPosition

/**
 * Hero has its own turn and is controlled by the player.
 */
class Hero : Character
{
    override val position = RoomPosition()
    override var maxHealthPoints = 100
    override var healthPoints = 100
    override var alive = true
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.hero
    }
    
    override fun getOutlineTile() : TiledMapTile
    {
        return Tiles.heroOutlineYellow
    }
}
