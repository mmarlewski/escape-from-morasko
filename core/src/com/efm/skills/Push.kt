package com.efm.skills

import com.efm.assets.Textures
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.skill.ActiveSkill
import com.efm.skill.BodyPart

object Push : ActiveSkill(BodyPart.rightHand, 1, 3, Textures.barrel)
{
    override fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    {
        return listOf()
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        return listOf()
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
    }
}
