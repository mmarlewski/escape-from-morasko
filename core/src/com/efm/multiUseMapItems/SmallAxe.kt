package com.efm.multiUseMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.*
import com.efm.assets.*
import com.efm.entity.Character
import com.efm.item.MultiUseMapItem
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition


class SmallAxe : MultiUseMapItem
{
    override val name : String = "Small Axe"
    override var baseAPUseCost : Int = 4
    override var durability : Int = 15
    override val durabilityUseCost : Int = 1
    val damage : Int = 2
    
    override fun getTexture() : Texture
    {
        return Textures.axe
    }
    
    override fun selected()
    {
        //podświetl zasięg ataku
        //życie przeciwników w zasięgu miga ?
        //pasek ap miga
        //zmien stan
    }
    
    override fun confirmed()
    {
        //use()
    }
    
    override fun use(room: Room, targetPosition : RoomPosition)
    {
        val animations = mutableListOf<Animation>()
        animations += Animation.action { playSoundOnce(Sounds.woodenSword) }
        animations += Animation.showTile(Tiles.axe, targetPosition.copy(), 0.5f)
        val showAreaOfAxeAnimation = mutableListOf<Animation>()
        for (pos in this.getAffectedPositions(targetPosition))
        {
            showAreaOfAxeAnimation += Animation.showTile(Tiles.sledgehammer, pos, 0.5f)
        }
        val showAreaOfAxe = Animation.simultaneous(showAreaOfAxeAnimation)
        animations += showAreaOfAxe
    
        animations += Animation.action {
        
            for (pos in this.getAffectedPositions(targetPosition))
            {
                val hitSpace = room.getSpace(pos)
                val hitEntity = hitSpace?.getEntity()
                when (hitEntity)
                {
                    is Character ->
                    {
                        hitEntity.damageCharacter(damage)
                    }
                }
            }
        }
        Animating.executeAnimations(animations)
    }
    
    override fun getTargetPositions(source:RoomPosition) : List<RoomPosition>
    {
        val possiblePositions = mutableListOf<RoomPosition>()
        possiblePositions.addAll(getSquarePerimeterPositions(World.hero.position, 2))
        possiblePositions.addAll(getSquarePerimeterPositions(World.hero.position, 3))
        return possiblePositions.toList()
    }
    
    override fun getAffectedPositions(targetPosition:RoomPosition) : List<RoomPosition>
    {
        return getSquarePerimeterPositions(World.hero.position, 1)
    }
}