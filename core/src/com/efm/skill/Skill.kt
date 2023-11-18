package com.efm.skill

import com.badlogic.gdx.graphics.Texture
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.skills.*

enum class BodyPart
{
    head, torso, leftHand, rightHand, leftLeg, rightLeg
}

abstract class Skill(
        open val bodyPart : BodyPart,
        open val apCost : Int,
        open val coolDown : Int,
        open val texture : Texture,
        open val name : String,
        open val description : String
                    )
{
    var isInCoolDown = false
    var currCoolDown = 0
}

abstract class ActiveSkill(
        override val bodyPart : BodyPart,
        override val apCost : Int,
        override val coolDown : Int,
        override val texture : Texture,
        override val name : String,
        override val description : String
                          ) : Skill(bodyPart, apCost, coolDown, texture, name, description)
{
    abstract fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    abstract fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    abstract fun use(room : Room, targetPosition : RoomPosition)
}

fun getSkillFromName(name : String) : Skill?
{
    return when (name)
    {
        "Barrel Throwing" -> BarrelThrowing
        "Freeze"          -> Freeze
        "Invisibility"    -> Invisibility
        "Jump"            -> Jump
        "Pull"            -> Pull
        "Push"            -> Push
        "Shield"          -> Shield
        "Swap"            -> Swap
        "Grass Healing"   -> GrassHealing
        "Pockets"         -> Pockets
        "Water Walking"   -> WaterWalking
        "Lava Walking"    -> LavaWalking
        else              -> null
    }
}
