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
        
        animations += Animation.action { playSoundOnce(Sounds.woodenSword) }
        animations += Animation.simultaneous(
                listOf(
                        Animation.sequence(
                                listOf(
                                        Animation.moveTile(axeTile, slashPositions[0], slashPositions[1], 0.2f),
                                        Animation.moveTile(axeTile, slashPositions[1], slashPositions[2], 0.2f)
                                      )
                                          ),
                        Animation.cameraShake(2)
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
                        slashEntity.damageCharacter(damage)
                    }
                }
            }
        }
        
        Animating.executeAnimations(animations)
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
}
