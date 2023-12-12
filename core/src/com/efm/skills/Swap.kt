package com.efm.skills

import com.efm.LineFinding
import com.efm.assets.Textures
import com.efm.entity.Enemy
import com.efm.getSquareAreaPositions
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.skill.ActiveSkill
import com.efm.skill.BodyPart

object Swap : ActiveSkill(
        BodyPart.head,
        1,
        3,
        Textures.swap,
        "Swap",
        "Swap places with enemy unit"
                         )
{
    override fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    {
        val targetPositions = mutableListOf<RoomPosition>()
        
        val squarePositions = getSquareAreaPositions(source, 10)
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
        return listOf(targetPosition)
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val heroSpace = World.currentRoom.getSpace(World.hero.position)
        val targetSpace = World.currentRoom.getSpace(targetPosition)
        val targetEntity = targetSpace?.getEntity()
        if (targetEntity is Enemy)
        {
            World.currentRoom.removeEntity(targetEntity)
            val heroPos = World.hero.position
            World.currentRoom.removeEntity(World.hero)
            World.currentRoom.addEntityAt(targetEntity, heroPos)
            World.currentRoom.addEntityAt(World.hero, targetPosition)
            
            val heroBase = heroSpace?.getBase()
            if (heroBase != null && !heroBase.isTreadableFor(targetEntity))
            {
                targetEntity.alive = false
            }
        }
    }
}
