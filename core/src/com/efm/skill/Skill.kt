package com.efm.skill

import com.badlogic.gdx.graphics.Texture
import com.efm.assets.Textures
import com.efm.getSquareAreaPositions
import com.efm.room.Room
import com.efm.room.RoomPosition

enum class BodyPart
{
    head, torso, leftHand, rightHand, leftLeg, rightLeg
}

abstract class Skill(
        open val bodyPart : BodyPart,
        open val apCost : Int,
        open val coolDown : Int,
        open val texture : Texture
                    )
{
    var isInCoolDown = false
    var currCoolDown = 0
}

abstract class ActiveSkill(
        override val bodyPart : BodyPart,
        override val apCost : Int,
        override val coolDown : Int,
        override val texture : Texture
                          ) : Skill(bodyPart, apCost, coolDown, texture)
{
    abstract fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    abstract fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    abstract fun use(room : Room, targetPosition : RoomPosition)
}
