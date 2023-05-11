package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Character
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

/**
 * Hero has its own turn and is controlled by the player.
 */
class Hero(
        override var maxHealthPoints : Int = 100,
        override var healthPoints : Int = 100,
        override var alive : Boolean = true
          ) : Character
{
    override val position = RoomPosition()
    
    override fun damageCharacter(dmgAmount : Int)
    {
        super.damageCharacter(dmgAmount)
        
        GameScreen.healthBar.value-=dmgAmount
    }
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.hero
    }
    
    override fun getOutlineTile() : TiledMapTile
    {
        return Tiles.heroOutlineYellow
    }
}
