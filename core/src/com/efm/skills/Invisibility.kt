package com.efm.skills

import com.efm.assets.Textures
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.skill.ActiveSkill
import com.efm.skill.BodyPart

object Invisibility : ActiveSkill(BodyPart.torso, 1, 3, Textures.invisibility)
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
    
    fun getDescription() : String
    {
        return "Allows to be undetectable for enemy units"
    }
}
