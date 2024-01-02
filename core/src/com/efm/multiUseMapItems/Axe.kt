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

abstract class Axe : MultiUseMapItem
{
    override val name : String = "Standard Axe"
    override var baseAPUseCost : Int = 1
    override var durability : Int = 10
    override var maxDurability : Int = 10
    override val durabilityUseCost : Int = 1
    open val damage : Int = 2
    override val statsDescription : String
        get() = "Damage: " + damage + "\n" +
                "Uses left: " + durability + "\n" +
                "AP cost: " + baseAPUseCost
    
    override fun getTexture() : Texture
    {
        return Textures.doubleBitAxe
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
        
        for (direction in Direction4.values())
        {
            targetPositions.add(source.adjacentPosition(direction))
        }
        
        return targetPositions.toList()
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        val affectedPositions = mutableListOf<RoomPosition>()
        
        val targetDirection = getDirection4(World.hero.position, targetPosition)
        
        when (targetDirection)
        {
            Direction4.up, Direction4.down    ->
            {
                affectedPositions.add(targetPosition.adjacentPosition(Direction4.left))
                affectedPositions.add(targetPosition.adjacentPosition(Direction4.right))
            }
            Direction4.right, Direction4.left ->
            {
                affectedPositions.add(targetPosition.adjacentPosition(Direction4.up))
                affectedPositions.add(targetPosition.adjacentPosition(Direction4.down))
            }
            null                              ->
            {
            }
        }
        
        return affectedPositions
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val targetDirection = getDirection4(World.hero.position, targetPosition)
        val axeTile = if (targetDirection == null) null else Tiles.getAxeTile(targetDirection.toDirection8())
        val slashPositions = mutableListOf<RoomPosition>()
        
        when (targetDirection)
        {
            Direction4.up    ->
            {
                slashPositions.add(targetPosition.adjacentPosition(Direction4.left))
                slashPositions.add(targetPosition.copy())
                slashPositions.add(targetPosition.adjacentPosition(Direction4.right))
            }
            Direction4.right ->
            {
                slashPositions.add(targetPosition.adjacentPosition(Direction4.up))
                slashPositions.add(targetPosition.copy())
                slashPositions.add(targetPosition.adjacentPosition(Direction4.down))
            }
            Direction4.down  ->
            {
                slashPositions.add(targetPosition.adjacentPosition(Direction4.right))
                slashPositions.add(targetPosition.copy())
                slashPositions.add(targetPosition.adjacentPosition(Direction4.left))
            }
            Direction4.left  ->
            {
                slashPositions.add(targetPosition.adjacentPosition(Direction4.down))
                slashPositions.add(targetPosition.copy())
                slashPositions.add(targetPosition.adjacentPosition(Direction4.up))
            }
            null             ->
            {
            }
        }
        
        val animations = mutableListOf<Animation>()
        
        val slashShowSeconds = 0.2f
        animations += Animation.action { playSoundOnce(Sounds.axe) }
        animations += Animation.simultaneous(
                listOf(
                        Animation.sequence(
                                listOf(
                                        Animation.moveTile(axeTile, slashPositions[0], slashPositions[1], slashShowSeconds),
                                        Animation.moveTile(axeTile, slashPositions[1], slashPositions[2], slashShowSeconds)
                                      )
                                          ),
                        Animation.cameraShake(2, slashShowSeconds * 4)
                      )
                                            )
        animations += Animation.action {
            
            for (slashPosition in slashPositions)
            {
                val slashSpace = room.getSpace(slashPosition)
                val slashEntity = slashSpace?.getEntity()
                when (slashEntity)
                {
                    is Character ->
                    {
                        slashEntity.damageCharacter((this.damage * World.hero.weaponDamageMultiplier).roundToInt())
                    }
                }
            }
        }
        
        Animating.executeAnimations(animations)
    }
}