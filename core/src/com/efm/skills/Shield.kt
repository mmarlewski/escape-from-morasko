package com.efm.skills

import com.efm.assets.Textures
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.skill.ActiveSkill
import com.efm.skill.BodyPart

object Shield : ActiveSkill(BodyPart.rightHand, 1, 3, Textures.shield)
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
    
    fun getDescription() : String
    {
        return "Invincibility for one turn"
    }
}
