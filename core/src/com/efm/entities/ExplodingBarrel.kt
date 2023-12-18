package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.Character
import com.efm.entity.Entity
import com.efm.level.World
import com.efm.room.RoomPosition

class ExplodingBarrel : Entity, Character
{
    override var maxHealthPoints = 1
    override var healthPoints = 1
    override var alive = true
    override val position = RoomPosition()
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.explodingBarrel
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return Tiles.explodingBarrelOutlineYellow
    }
    
    override fun onDeath()
    {
        super.onDeath()
        
        val animations = mutableListOf<Animation>()
        
        animations.add(Animation.showTile(Tiles.explodingBarrelExplosion, this.position, 0.5f))
        animations.add(Animation.action { playSoundOnce(Sounds.explosive) })
        val damagePositions = getSquareAreaPositions(this.position, 1)
        for (position in damagePositions)
        {
            animations.add(Animation.showTile(Tiles.explodingBarrelExplosion, position, 0.5f))
            animations.add(Animation.action
            {
                val entity = World.currentRoom?.getSpace(position)?.getEntity()
                if (entity is Character) entity.damageCharacter(10)
            })
        }
        
        Animating.executeAnimations(mutableListOf(Animation.simultaneous(animations)))
    }
}
