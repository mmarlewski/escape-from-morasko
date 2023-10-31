package com.efm.skills

import com.efm.assets.Textures
import com.efm.entity.Character
import com.efm.getSquareAreaPositions
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.skill.ActiveSkill
import com.efm.skill.BodyPart

object Pull : ActiveSkill(BodyPart.leftHand, Textures.pull)
{
    override fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    {
        return getSquareAreaPositions(source, 3)
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        return getSquareAreaPositions(targetPosition, 1)
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val squarePositions = getSquareAreaPositions(targetPosition, 1)
        
        for (position in squarePositions)
        {
            val space = World.currentRoom.getSpace(position)
            val entity = space?.getEntity()
            if (entity is Character)
            {
                entity.damageCharacter(50)
            }
        }
    }
}
