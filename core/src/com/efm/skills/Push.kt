package com.efm.skills

import com.efm.*
import com.efm.assets.Textures
import com.efm.assets.Tiles
import com.efm.entity.Character
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen
import com.efm.skill.ActiveSkill
import com.efm.skill.BodyPart

object Push : ActiveSkill(BodyPart.leftHand, 1, 3, Textures.push, "Push", "Pushes an enemy away from you")
{
    override fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    {
        val positions = mutableListOf<RoomPosition>()
        
        val upPosition = source.adjacentPosition(Direction4.up)
        val upSpace = World.currentRoom.getSpace(upPosition)
        if (upSpace?.getEntity() != null) positions.add(upPosition)
        
        val rightPosition = source.adjacentPosition(Direction4.right)
        val rightSpace = World.currentRoom.getSpace(rightPosition)
        if (rightSpace?.getEntity() != null) positions.add(rightPosition)
        
        val downPosition = source.adjacentPosition(Direction4.down)
        val downSpace = World.currentRoom.getSpace(downPosition)
        if (downSpace?.getEntity() != null) positions.add(downPosition)
        
        val leftPosition = source.adjacentPosition(Direction4.left)
        val leftSpace = World.currentRoom.getSpace(leftPosition)
        if (leftSpace?.getEntity() != null) positions.add(leftPosition)
        
        return positions
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        val positions = mutableListOf<RoomPosition>()
        
        val direction = getDirection4(World.hero.position, targetPosition)
        if (direction != null) positions.add(targetPosition.adjacentPosition(direction))
        
        return positions
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val animations = mutableListOf<Animation>()
        
        val targetSpace = World.currentRoom.getSpace(targetPosition)
        val targetEntity = targetSpace?.getEntity()
        val direction = getDirection8(World.hero.position, targetPosition)
        if (targetEntity != null && direction != null)
        {
            val impactTile = Tiles.getImpactTile(direction)
            animations.add(Animation.showTile(impactTile, targetPosition, 0.5f))
            
            val directionPosition = targetPosition.adjacentPosition(direction)
            val directionSpace = World.currentRoom.getSpace(directionPosition)
            if (directionSpace != null)
            {
                // disappear entity
                animations.add(Animation.action {
                    targetEntity.position.set(-1, -1)
                    World.currentRoom.updateSpacesEntities()
                    GameScreen.updateMapEntityLayer()
                })
                
                val directionEntity = directionSpace.getEntity()
                val directionBase = directionSpace.getBase()
                if (directionEntity == null)
                {
                    // move
                    animations.add(Animation.moveTile(targetEntity.getTile(), targetPosition, directionPosition, 0.2f))
                    
                    // change entity position ot new
                    animations.add(Animation.action {
                        targetEntity.position.set(directionPosition)
                        World.currentRoom.updateSpacesEntities()
                        GameScreen.updateMapEntityLayer()
                    })
                    
                    // kill entity
                    if (directionBase != null && !directionBase.isTreadableFor(targetEntity))
                    {
                        if (targetEntity is Character)
                        {
                            animations.add(Animation.action { targetEntity.alive = false })
                        }
                    }
                }
                else
                {
                    // bump
                    animations.add(Animation.moveTile(targetEntity.getTile(), targetPosition, directionPosition, 0.2f))
                    animations.add(Animation.moveTile(targetEntity.getTile(), directionPosition, targetPosition, 0.2f))
                    
                    // change entity position ot normal
                    animations.add(Animation.action { targetEntity.position.set(targetPosition) })
                }
            }
        }
        
        Animating.executeAnimations(animations)
    }
    
}
