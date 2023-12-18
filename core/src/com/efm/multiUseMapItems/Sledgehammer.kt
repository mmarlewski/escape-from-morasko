package com.efm.multiUseMapItems

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.efm.*
import com.efm.assets.*
import com.efm.entity.Character
import com.efm.item.MultiUseMapItem
import com.efm.level.World
import com.efm.room.*
import kotlin.math.sqrt

class Sledgehammer : MultiUseMapItem
{
    override val name : String = "Sledgehammer"
    override var baseAPUseCost : Int = 1
    override var durability : Int = 20
    override var maxDurability : Int = 20
    override val durabilityUseCost : Int = 1
    val damage : Int = 2
    
    override fun getTexture() : Texture
    {
        return Textures.hammer
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
        
        targetPositions.addAll(getSquarePerimeterPositions(source, 1))
        targetPositions.addAll(getSquarePerimeterPositions(source, 2))
        
        return targetPositions.toList()
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        val affectedSpaces = mutableListOf<RoomPosition>()
        
        affectedSpaces.addAll(getSquarePerimeterPositions(targetPosition, 1))
        
        return affectedSpaces
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val targetDirection = getDirection8(World.hero.position, targetPosition)
        val hammerTile = if (targetDirection == null) null else Tiles.getHammerTile(targetDirection)
        val positionsAroundTarget = getSquarePerimeterPositions(targetPosition, 1)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.descendTile(hammerTile, targetPosition.copy(), 0.2f, 0.25f)
        animations += Animation.action { playSoundOnce(Sounds.hammer) }
        val impactAnimations = mutableListOf<Animation>()
        impactAnimations.add(Animation.showTile(Tiles.impact, targetPosition.copy(), 0.5f))
        impactAnimations.add(Animation.cameraShake(3, 1f))
        for (positionAroundTarget in positionsAroundTarget)
        {
            val impactDirection = getDirection8(targetPosition, positionAroundTarget)
            val impactTile = if (impactDirection == null) null else Tiles.getImpactTile(impactDirection)
            impactAnimations.add(Animation.showTile(impactTile, positionAroundTarget.copy(), 0.5f))
        }
        animations += Animation.simultaneous(impactAnimations)
        animations += Animation.action {
            
            val attackedPositions = mutableListOf<RoomPosition>()
            attackedPositions.add(targetPosition)
            attackedPositions.addAll(getSquarePerimeterPositions(targetPosition, 1))
            
            for (attackedPosition in attackedPositions)
            {
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
        }
        
        Animating.executeAnimations(animations)
    }
}
