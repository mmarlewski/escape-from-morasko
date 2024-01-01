package com.efm.multiUseMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.*
import com.efm.assets.*
import com.efm.entity.Character
import com.efm.item.MultiUseMapItem
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import kotlin.math.roundToInt

object Fist : MultiUseMapItem
{
    override val name : String = "Fist"
    override var baseAPUseCost : Int = 1
    override var durability : Int = Int.MAX_VALUE
    override var maxDurability : Int = Int.MAX_VALUE
    override val durabilityUseCost : Int = 1
    val damage : Int = 1
    override val statsDescription : String
        get() = "Damage: " + damage + "\n" +
                "Uses left: Infinite" + "\n" +
                "AP cost: " + baseAPUseCost
    
    override fun getTexture() : Texture
    {
        return Textures.fist
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
        
        return targetPositions.toList()
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        val affectedSpaces = mutableListOf<RoomPosition>()
        
        affectedSpaces.add(targetPosition.copy())
        
        return affectedSpaces
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val targetDirection = getDirection8(World.hero.position, targetPosition)
        val impactTile = if (targetDirection == null) null else Tiles.getImpactTile(targetDirection)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.action { playSoundOnce(Sounds.punch) }
        animations += Animation.simultaneous(
                listOf(
                        Animation.showTile(impactTile, targetPosition.copy(), 0.2f),
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
                    attackedEntity.damageCharacter((this.damage * World.hero.weaponDamageMultiplier).roundToInt())
                }
            }
        }
        
        Animating.executeAnimations(animations)
    }
    
    override fun lowerDurability()
    {
        //
    }
}
