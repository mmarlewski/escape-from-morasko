package com.efm.skills

import com.efm.*
import com.efm.Map
import com.efm.assets.Textures
import com.efm.assets.Tiles
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.skill.ActiveSkill
import com.efm.skill.BodyPart
import kotlin.math.abs

object Jump : ActiveSkill(
        BodyPart.leftLeg, 1, 3, Textures.jump, "Jump", "Jump over non-traversable fields"
                         )
{
    override fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    {
        val user = World.hero
        val tilesThatCanBeJumpedOver = 3
        return getSquareAreaPositions(source, tilesThatCanBeJumpedOver + 1).filter {
            // do not jump where you can walk in one tile
            checkPositionDistanceFrom(source, it) >= 14 &&
                    // jumps in straight line are the longest
                    checkPositionDistanceFrom(source, it) <= tilesThatCanBeJumpedOver * 10 &&
                    // space is safe to land on
                    (World.currentRoom?.getSpace(it)?.isTraversableFor(user) == true)
        }
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        return listOf(targetPosition)
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        jump(World.hero.position, targetPosition)
    }
    
    private fun jump(startPosition : RoomPosition, endPosition : RoomPosition)
    {
        val animations = mutableListOf<Animation>()
        animations += Animation.action { Map.changeTile(MapLayer.entity, World.hero.position, null) }
        
        val jumpDistance = checkPositionDistanceFrom(startPosition, endPosition) / 10f
        //Gdx.app.log("jump", jumpDistance.toString())
        val jumpDuration = 0.25f * jumpDistance
        val jumpHeight = minOf(0.25f * jumpDistance, 1f)
        
        animations += Animation.moveTileWithArchAndCameraFocus(
                Tiles.heroIdle1, startPosition, endPosition, jumpDuration, jumpHeight
                                                              )
        animations += Animation.action {
            World.currentRoom?.removeEntity(World.hero)
            World.currentRoom?.addEntityAt(World.hero, endPosition)
            adjustCameraAfterMoving()
            adjustMapLayersAfterMoving()
        }
        Animating.executeAnimations(animations)
    }
    
    private fun checkPositionDistanceFrom(pos : RoomPosition, from : RoomPosition) : Int
    {
        val yDistance = abs(pos.y - from.y)
        val xDistance = abs(pos.x - from.x)
        
        return if (yDistance < xDistance) (14 * yDistance + 10 * (xDistance - yDistance)) else (14 * xDistance + 10 * (yDistance - xDistance))
        
    }
}
