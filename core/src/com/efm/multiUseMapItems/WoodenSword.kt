package com.efm.multiUseMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.*
import com.efm.assets.*
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
    
    override fun getTexture() : Texture
    {
        return Textures.sword
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
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val animations = mutableListOf<Animation>()
        animations += Animation.action { playSoundOnce(Sounds.woodenSword) }
        animations += Animation.descendTile(Tiles.woodenSword, targetPosition.copy(), 0.2f,0.25f)
        animations += Animation.cameraShake()
        animations += Animation.action {
            
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
