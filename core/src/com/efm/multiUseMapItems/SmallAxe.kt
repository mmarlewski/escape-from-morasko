package com.efm.multiUseMapItems

import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
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
        for (pos in this.getAffectedPositions(targetPosition))
        {
            animations += Animation.showTile(Tiles.axe, pos.copy(), 0.5f)
        
            animations += Animation.action {
        
                val attackedPosition = pos.copy()
                val attackedSpace = room.getSpace(attackedPosition)
                val attackedEntity = attackedSpace?.getEntity()
                when (attackedEntity)
                {
                    is Character ->
                    {
                        attackedEntity.damageCharacter(this.damage)
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