package com.efm.skills

import com.efm.*
import com.efm.assets.*
import com.efm.entities.ExplodingBarrel
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.skill.ActiveSkill
import com.efm.skill.BodyPart

object BarrelThrowing : ActiveSkill(
        BodyPart.rightHand,
        1,
        3,
        Textures.barrel,
        "Barrel Throwing",
        "Throw an explosive barrel that will explode next round"
                                   )
{
    override fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    {
        val targetPositions = mutableListOf<RoomPosition>()
        
        val squareAreaPositions = getSquareAreaPositions(source, 2)
        for (position in squareAreaPositions)
        {
            val space = World.currentRoom?.getSpace(position)
            val entity = space?.getEntity()
            if (space != null && entity == null)
            {
                targetPositions.add(position)
            }
        }
        
        return targetPositions
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        return listOf(targetPosition.copy())
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val animations = mutableListOf<Animation>()
        
        animations.add(
                Animation.moveTileWithArch(
                        Tiles.explodingBarrel,
                        World.hero.position.copy(),
                        targetPosition.copy(),
                        1.0f,
                        1.0f
                                          )
                      )
        animations.add(
                Animation.simultaneous(
                        listOf(
                                Animation.action { playSoundOnce(Sounds.barrelDrop) },
                                Animation.showTile(Tiles.explodingBarrel, targetPosition.copy(), 0.5f),
                                Animation.showTile(Tiles.impact, targetPosition.copy(), 0.5f)
                              )
                                      )
                      )
        animations.add(Animation.action
        {
            World.currentRoom?.addEntityAt(ExplodingBarrel(), targetPosition.copy())
        })
        
        Animating.executeAnimations(animations)
    }
}
