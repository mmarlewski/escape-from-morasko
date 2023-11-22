package com.efm.skills

import com.efm.assets.Textures
import com.efm.skill.BodyPart
import com.efm.skill.Skill

object GrassHealing : Skill(
        BodyPart.torso,
        1,
        3,
        Textures.grassHealing,
        "Grass Healing",
        "Heal when standing on grass tile"
                           )

object Pockets : Skill(
        BodyPart.leftLeg,
        1,
        3,
        Textures.pockets,
        "Pockets",
        "Increases equipment size"
                      )
{
    const val additionalInventorySlotsAmount = 5
}

object WaterWalking : Skill(
        BodyPart.rightLeg,
        1,
        3, Textures.waterwalking,
        "Water Walking",
        "Allows to walk on water tiles"
                           )

object LavaWalking : Skill(
        BodyPart.rightLeg,
        1,
        3,
        Textures.lavawalking,
        "Lava Walking",
        "Allows to walk on lava tiles"
                          )
