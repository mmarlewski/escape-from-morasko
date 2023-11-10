package com.efm.multiUseMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.*
import com.efm.assets.*
import com.efm.entity.Character
import com.efm.item.MultiUseMapItem
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition

class Bow : MultiUseMapItem
{
    override val name : String = "Bow"
    override val baseAPUseCost : Int = 1
    override var durability : Int = 10
    override var maxDurability : Int = 10
    override val durabilityUseCost : Int = 1
    val damage : Int = 2
    
    override fun getTexture() : Texture
    {
        return Textures.bow
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
        
        val squarePositions = getSquareAreaPositions(source, 5)
        for (squarePosition in squarePositions)
        {
            val linePositions =
                    LineFinding.findLineWithGivenRoom(World.hero.position.copy(), squarePosition.copy(), World.currentRoom)
            if (linePositions != null)
            {
                targetPositions.add(squarePosition)
            }
        }
        
        return targetPositions
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        val affectedPositions = mutableListOf<RoomPosition>()
        
        val linePositions =
                LineFinding.findLineWithGivenRoom(World.hero.position.copy(), targetPosition.copy(), World.currentRoom)
        if (linePositions != null)
        {
            affectedPositions.add(World.hero.position.copy())
            affectedPositions.addAll(linePositions)
            affectedPositions.add(targetPosition.copy())
        }
        
        return affectedPositions
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val heroPosition = World.hero.position.copy()
        val linePositions =
                LineFinding.findLineWithGivenRoom(heroPosition.copy(), targetPosition.copy(), World.currentRoom)
        if (linePositions != null)
        {
            val targetDirection = getDirection8(World.hero.position, targetPosition)
            val bowTile = if (targetDirection == null) null else Tiles.getBowTile(targetDirection)
            val arrowTile = if (targetDirection == null) null else Tiles.getArrowTile(targetDirection)
            val impactTile = if (targetDirection == null) null else Tiles.getImpactTile(targetDirection)
            
            val animations = mutableListOf<Animation>()
            
            val animationSeconds = linePositions.size * 0.05f
            animations.add(Animation.showTile(bowTile, heroPosition.copy(), 0.2f))
            animations.add(Animation.action { playSoundOnce(Sounds.bowShot) })
            animations.add(Animation.moveTile(arrowTile, heroPosition.copy(), targetPosition.copy(), animationSeconds))
            animations.add(Animation.action { playSoundOnce(Sounds.bowImpact) })
            animations.add(
                    Animation.simultaneous(
                            listOf(
                                    Animation.showTile(impactTile, targetPosition.copy(), 0.2f),
                                    Animation.cameraShake(1, 0.5f)
                                  )
                                          )
                          )
            animations.add(Animation.action {
                
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
            })
            
            Animating.executeAnimations(animations)
        }
    }
}
