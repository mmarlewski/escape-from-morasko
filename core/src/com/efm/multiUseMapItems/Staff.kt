package com.efm.multiUseMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.*
import com.efm.assets.*
import com.efm.entity.Character
import com.efm.item.MultiUseMapItem
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition

class Staff : MultiUseMapItem
{
    override val name : String = "Staff"
    override var baseAPUseCost : Int = 1
    override var durability : Int = 10
    override var maxDurability : Int = 10
    override val durabilityUseCost : Int = 1
    val damage : Int = 2
    
    override fun getTexture() : Texture
    {
        return Textures.staff
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
        
        for (direction in Direction8.values())
        {
            targetPositions.add(source.adjacentPosition(direction))
        }
        
        return targetPositions.toList()
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        val affectedPositions = mutableListOf<RoomPosition>()
        
        val targetDirection = getDirection8(World.hero.position, targetPosition)
        if (targetDirection != null)
        {
            affectedPositions.addAll(getLineFromPositionInDirectionPositions(World.hero.position.copy(), targetDirection, 5))
        }
        
        return affectedPositions
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val targetDirection = getDirection8(World.hero.position, targetPosition)
        val staffTile = if (targetDirection == null) null else Tiles.getStaffTile(targetDirection)
        val beamTile1 = when (targetDirection)
        {
            Direction8.up, Direction8.down          -> Tiles.beamVertical1
            Direction8.right, Direction8.left       -> Tiles.beamHorizontal1
            Direction8.upRight, Direction8.downLeft -> Tiles.beamDiagonalHorizontal1
            Direction8.upLeft, Direction8.downRight -> Tiles.beamDiagonalVertical1
            null                                    -> null
        }
        val beamTile2 = when (targetDirection)
        {
            Direction8.up, Direction8.down          -> Tiles.beamVertical2
            Direction8.right, Direction8.left       -> Tiles.beamHorizontal2
            Direction8.upRight, Direction8.downLeft -> Tiles.beamDiagonalHorizontal2
            Direction8.upLeft, Direction8.downRight -> Tiles.beamDiagonalVertical2
            null                                    -> null
        }
        
        val animations = mutableListOf<Animation>()
        
        val beamShowSeconds = 0.4f
        if (targetDirection != null)
        {
            val linePositions = getLineFromPositionInDirectionPositions(World.hero.position, targetDirection, 5)
            val showBeam1 = mutableListOf<Animation>()
            var showTile1 = true
            for (linePosition in linePositions)
            {
                showBeam1.add(Animation.showTile(if (showTile1) beamTile1 else beamTile2, linePosition, beamShowSeconds))
                showTile1 = !showTile1
            }
            val showBeam2 = mutableListOf<Animation>()
            var showTile2 = true
            for (linePosition in linePositions)
            {
                showBeam2.add(Animation.showTile(if (showTile2) beamTile2 else beamTile1, linePosition, beamShowSeconds))
                showTile2 = !showTile2
            }
            animations.add(
                    Animation.simultaneous(
                            listOf(
                                    Animation.action { playSoundOnce(Sounds.beam) },
                                    Animation.showTile(staffTile, World.hero.position.copy(), beamShowSeconds * 3),
                                    Animation.simultaneous(
                                            listOf(
                                                    Animation.sequence(
                                                            listOf(
                                                                    Animation.simultaneous(showBeam1),
                                                                    Animation.simultaneous(showBeam2),
                                                                    Animation.simultaneous(showBeam1)
                                                                  )
                                                                      ),
                                                    Animation.cameraShake(3, beamShowSeconds * 3)
                                                  )
                                                          )
                                  )
                                          )
                          )
            animations.add(Animation.action {
                
                for (linePosition in linePositions)
                {
                    val lineSpace = room.getSpace(linePosition)
                    if (lineSpace != null)
                    {
                        val lineEntity = lineSpace.getEntity()
                        if (lineEntity is Character)
                        {
                            lineEntity.damageCharacter(damage * World.hero.weaponDamageMultiplier)
                        }
                    }
                }
            })
        }
        
        Animating.executeAnimations(animations)
    }
}
