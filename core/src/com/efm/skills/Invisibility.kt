package com.efm.skills

import com.efm.assets.Textures
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.skill.ActiveSkill
import com.efm.skill.BodyPart
import com.efm.state.*

object Invisibility : ActiveSkill(BodyPart.torso, Textures.invisibility)
{
    override fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    {
        return listOf(World.hero.position)
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        return listOf(World.hero.position)
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        World.hero.isVisible = false
    }
}
