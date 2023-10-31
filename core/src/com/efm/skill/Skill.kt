package com.efm.skill

import com.badlogic.gdx.graphics.Texture
import com.efm.assets.Textures

enum class BodyPart
{
    head, torso, leftHand, rightHand, leftLeg, rightLeg
}

enum class Skill(val isPassive : Boolean, val bodyPart : BodyPart, val texture:Texture)
{
    swap(false, BodyPart.head, Textures.swap),
    freeze(false, BodyPart.head, Textures.freeze),
    grassHealing(true, BodyPart.torso, Textures.grassHealing),
    invisibility(false, BodyPart.torso, Textures.invisibility),
    pull(false, BodyPart.leftHand, Textures.pull),
    push(false, BodyPart.leftHand, Textures.push ),
    barrelThrowing(false, BodyPart.rightHand, Textures.barrel),
    shield(false, BodyPart.rightHand, Textures.shield),
    jump(false, BodyPart.leftLeg, Textures.jump),
    pockets(true, BodyPart.leftLeg, Textures.pockets),
    waterWalking(true, BodyPart.rightLeg, Textures.waterwalking),
    lavaWalking(true, BodyPart.rightLeg, Textures.lavawalking)
}
