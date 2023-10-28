package com.efm.skill

enum class BodyPart
{
    head, torso, leftHand, rightHand, leftLeg, rightLeg
}

enum class Skill(val isPassive : Boolean, val bodyPart : BodyPart)
{
    swap(true, BodyPart.head),
    freeze(true, BodyPart.head),
    grassHealing(true, BodyPart.torso),
    invisibility(true, BodyPart.torso),
    pull(true, BodyPart.leftHand),
    push(true, BodyPart.leftHand),
    barrelThrowing(true, BodyPart.rightHand),
    shield(true, BodyPart.rightHand),
    jump(true, BodyPart.leftLeg),
    pockets(true, BodyPart.leftLeg),
    waterWalking(true, BodyPart.rightLeg),
    lavaWalking(true, BodyPart.rightLeg)
}
