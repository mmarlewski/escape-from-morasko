package com.efm.skills

import com.efm.assets.Textures
import com.efm.skill.BodyPart
import com.efm.skill.Skill

object GrassHealing : Skill(BodyPart.torso, 1, 3, Textures.grassHealing)

object Pockets : Skill(BodyPart.leftLeg, 1, 3, Textures.pockets)

object WaterWalking : Skill(BodyPart.rightLeg, 1, 3, Textures.waterwalking)

object LavaWalking : Skill(BodyPart.rightLeg, 1, 3, Textures.lavawalking)
