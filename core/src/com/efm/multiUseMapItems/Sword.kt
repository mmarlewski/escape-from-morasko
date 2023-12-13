package com.efm.multiUseMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.*
import com.efm.assets.*
import com.efm.entity.Character
import com.efm.item.MultiUseMapItem
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition

abstract class Sword : MultiUseMapItem
{
    override var baseAPUseCost : Int = 1
    override var durability : Int = 10
    override var maxDurability : Int = 10
    override val durabilityUseCost : Int = 1
    open val damage : Int = 2
    
    override fun getTexture() : Texture
    {
        return Textures.woodenSword
    }
    
    override fun selected()
    {
    }
    
    override fun confirmed()
    {
    }
    
    override fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    {
        val targetPositions = mutableListOf<RoomPosition>()
        
        targetPositions.addAll(getSquarePerimeterPositions(World.hero.position, 1))
        targetPositions.addAll(getSquarePerimeterPositions(World.hero.position, 2))
        
        return targetPositions.toList()
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        val affectedPositions = mutableListOf<RoomPosition>()
        
        affectedPositions.add(targetPosition)
        
        return affectedPositions
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val swordDirection = getDirection8(World.hero.position, targetPosition)
        val swordTile = if (swordDirection == null) null else Tiles.getSwordTile(swordDirection)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.descendTile(swordTile, targetPosition.copy(), 0.2f, 0.25f)
        animations += Animation.action { playSoundOnce(Sounds.woodenSword) }
        animations += Animation.simultaneous(
                listOf(
                        Animation.showTile(Tiles.impact, targetPosition.copy(), 0.2f),
                        Animation.cameraShake(1, 0.5f)
                      )
                                            )
        animations += Animation.action {
            
            val attackedPosition = targetPosition.copy()
            val attackedSpace = room.getSpace(attackedPosition)
            val attackedEntity = attackedSpace?.getEntity()
            when (attackedEntity)
            {
                is Character ->
                {
                    attackedEntity.damageCharacter(this.damage * World.hero.weaponDamageMultiplier)
                }
            }
        }
        
        Animating.executeAnimations(animations)
    }
}
