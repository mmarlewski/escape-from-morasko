package com.efm.multiUseMapItems

import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.Character
import com.efm.item.MultiUseMapItem
import com.efm.level.World
import com.efm.room.*

class WoodenSword : MultiUseMapItem
{
    override val name : String = "Wooden Sword"
    override var baseAPUseCost : Int = 2
    override var durability : Int = 20
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
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val animations = mutableListOf<Animation>()
        animations += Animation.action { playSoundOnce(Sounds.woodenSword) }
        animations += Animation.showTile(Tiles.woodenSword, targetPosition.copy(), 0.5f)
        animations += Animation.action {
            
            val hero = World.hero
            hero.spendAP(baseAPUseCost)
            durability -= durabilityUseCost
            
            val attackedPosition = targetPosition.copy()
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
        Animating.executeAnimations(animations)
    }
    
    override fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    {
        val possiblePositions = mutableListOf<RoomPosition>()
        
        possiblePositions.addAll(getSquarePerimeterPositions(World.hero.position, 1))
        possiblePositions.addAll(getSquarePerimeterPositions(World.hero.position, 2))
        
        return possiblePositions.toList()
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        val affectedPositions = mutableListOf<RoomPosition>()
        
        affectedPositions.add(targetPosition)
        
        return affectedPositions.toList()
    }
}
