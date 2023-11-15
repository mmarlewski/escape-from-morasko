package com.efm.skills

import com.efm.assets.Textures
import com.efm.skill.BodyPart
import com.efm.skill.Skill

object GrassHealing : Skill(BodyPart.torso, 1, 3, Textures.grassHealing)
{
    fun getDescription() : String
    {
        return "Heal when stood on grass tile"
    }
}

object Pockets : Skill(BodyPart.leftLeg, 1, 3, Textures.pockets)
{
    fun getDescription() : String
    {
        return "Increases equipment size"
    }
    
}

object WaterWalking : Skill(BodyPart.rightLeg, 1, 3, Textures.waterwalking)
{
    fun getDescription() : String
    {
        return "Allows to walk on water tiles"
    }
    
}

object LavaWalking : Skill(BodyPart.rightLeg, 1, 3, Textures.lavawalking)
{
    fun description() : String
    {
        return "Allows to walk on lava tiles"
    }
}
