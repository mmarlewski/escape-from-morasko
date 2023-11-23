package com.efm.skills

import com.efm.assets.Textures
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.skill.ActiveSkill
import com.efm.skill.BodyPart

object Shield : ActiveSkill(
        BodyPart.rightHand,
        10,
        5,
        Textures.shield,
        "Shield",
        "Grants invincibility for one turn"
                           )
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
        World.hero.isInvincible = true
    }
}
