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

object Freeze : ActiveSkill(BodyPart.head, 1, 3, Textures.freeze)
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
        val space = room.getSpace(targetPosition)
        if (space != null) {
            val entity = space.getEntity()
            if (entity != null)
            {
                if (entity is Enemy)
                {
                    entity.setIsFrozen(true)
                }
            }
        }
    }
    
    fun getDescription() : String
    {
        return "Freezes an opponent, preventing them from performing a turn"
    }
    
    fun getName() : String
    {
        return "Freeze"
    }
}
