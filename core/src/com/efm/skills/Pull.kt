package com.efm.skills

import com.efm.*
import com.efm.assets.Textures
import com.efm.assets.Tiles
import com.efm.entity.Character
import com.efm.entity.Entity
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen
import com.efm.skill.ActiveSkill
import com.efm.skill.BodyPart

object Pull : ActiveSkill(
        BodyPart.leftHand,
        1,
        3,
        Textures.pull,
        "Pull",
        "Pulls an enemy towards you"
                         )
{
    override fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    {
        val targetPositions = mutableListOf<RoomPosition>()
        
        for (direction in Direction4.values())
        {
            direction@ for (i in 1..5)
            {
                val position = source.positionOffsetBy(i, direction)
                val space = World.currentRoom.getSpace(position)
                val entity = space?.getEntity()
                if (entity != null)
                {
                    if (i > 1) targetPositions.add(position.copy())
                    break@direction
                }
            }
        }
        
        return targetPositions
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        val affectedPosition = mutableListOf<RoomPosition>()
        
        val direction = getDirection4(targetPosition, World.hero.position)
        if (direction != null)
        {
            for (i in 1..5)
            {
                val position = targetPosition.positionOffsetBy(i, direction)
                if (position == World.hero.position) break
                else affectedPosition.add(position.copy())
            }
        }
        
        return affectedPosition
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val showSeconds = 0.2f
        
        val animations = mutableListOf<Animation>()
        
        val heroPosition = World.hero.position
        val direction = getDirection4(heroPosition, targetPosition)
        if (direction != null)
        {
            val hook = Tiles.getTentacleHookTile(direction)
            val hookEnd = Tiles.getTentacleHookEndTile(direction)
            
            lateinit var pullEntity : Entity
            var pullEntityIndex = 0
            var entityKilled = false
            
            // tentacle to target
            for (i in 1..5)
            {
                val position = heroPosition.positionOffsetBy(i, direction)
                val space = World.currentRoom.getSpace(position)
                val entity = space?.getEntity()
                val showAnimations = mutableListOf<Animation>()
                for (j in 1 until i)
                {
                    showAnimations.add(
                            Animation.showTile(
                                    hook,
                                    heroPosition.positionOffsetBy(j, direction),
                                    showSeconds
                                              )
                                      )
                }
                showAnimations.add(Animation.showTile(hookEnd, position, showSeconds))
                animations.add(Animation.simultaneous(showAnimations))
                if (entity != null)
                {
                    pullEntity = entity
                    pullEntityIndex = i
                    break
                }
            }
            
            // disappear entity
            animations.add(Animation.action {
                pullEntity.position.set(-1, -1)
                World.currentRoom.updateSpacesEntities()
                GameScreen.updateMapEntityLayer()
            })
            
            // tentacle from target
            for (i in pullEntityIndex - 1 downTo 1)
            {
                val position = heroPosition.positionOffsetBy(i, direction)
                val space = World.currentRoom.getSpace(position)
                val base = space?.getBase()
                val showAnimations = mutableListOf<Animation>()
                for (j in 1 until i)
                {
                    showAnimations.add(
                            Animation.showTile(
                                    hook,
                                    heroPosition.positionOffsetBy(j, direction),
                                    showSeconds
                                              )
                                      )
                }
                showAnimations.add(Animation.showTile(pullEntity.getTile(), position, showSeconds))
                showAnimations.add(Animation.showTile(hookEnd, position, showSeconds))
                animations.add(Animation.simultaneous(showAnimations))
                
                // kill entity
                if (base != null && !base.isTreadableFor(pullEntity))
                {
                    if (pullEntity is Character)
                    {
                        animations.add(Animation.action { pullEntity.alive = false })
                        animations.add(Animation.action { pullEntity.position.set(position) })
                        entityKilled = true
                        break
                    }
                }
            }
            
            // place entity next to hero
            if (!entityKilled)
            {
                animations.add(Animation.action { pullEntity.position.set(heroPosition.positionOffsetBy(1, direction)) })
            }
        }
        
        Animating.executeAnimations(animations)
    }
}
