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

abstract class Skill(open val bodyPart : BodyPart, open val texture : Texture)

abstract class ActiveSkill(override val bodyPart : BodyPart, override val texture : Texture) : Skill(bodyPart, texture)
{
    abstract fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    abstract fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    abstract fun use(room : Room, targetPosition : RoomPosition)
}
