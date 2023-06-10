package com.efm.multiUseMapItems

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.efm.*
import com.efm.assets.*
import com.efm.entity.Character
import com.efm.item.MultiUseMapItem
import com.efm.level.World
import com.efm.room.*
import kotlin.math.abs
import kotlin.math.sqrt

class Sledgehammer : MultiUseMapItem
{
    override val name : String = "Sledgehammer"
    override var baseAPUseCost : Int = 6
    override var durability : Int = 50
    override val durabilityUseCost : Int = 1
    val damage : Int = 2
    
    override fun getTexture() : Texture
    {
        return Textures.hammer
    }
    
    override fun selected()
    {
        //podświetl zasięg ataku
        //życie przeciwników w zasięgu miga ?
        //pasek ap miga
        //zmien stan
    }
    
    override fun confirmed()
    {
        //use()
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val animations = mutableListOf<Animation>()
        animations += Animation.action { playSoundOnce(Sounds.woodenSword) }
        animations += Animation.showTile(Tiles.sledgehammer, targetPosition.copy(), 0.5f)
        
        val showAreaOfHammerAnimation = mutableListOf<Animation>()
        for (pos in this.getAffectedPositions(targetPosition))
        {
            showAreaOfHammerAnimation += Animation.showTile(Tiles.sledgehammer, pos, 0.5f)
        }
        val showAreaOfHammer = Animation.simultaneous(showAreaOfHammerAnimation)
        animations += showAreaOfHammer
        
        animations += Animation.action {
            
            for (pos in this.getAffectedPositions(targetPosition))
            {
                val hitSpace = room.getSpace(pos)
                val hitEntity = hitSpace?.getEntity()
                when (hitEntity)
                {
                    is Character ->
                    {
                        hitEntity.damageCharacter(damage)
                    }
                }
            }
        }
        Animating.executeAnimations(animations)
    }
    
    override fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    {
        val possiblePositions = mutableListOf<RoomPosition>()
        possiblePositions.add(World.hero.position.positionOffsetBy(1, Direction.up))
        possiblePositions.add(World.hero.position.positionOffsetBy(1, Direction.down))
        possiblePositions.add(World.hero.position.positionOffsetBy(1, Direction.left))
        possiblePositions.add(World.hero.position.positionOffsetBy(1, Direction.right))
        
        return possiblePositions.toList()
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        val affectedSpaces = mutableListOf<RoomPosition>()
        for (posX in 0..World.currentRoom.heightInSpaces)
        {
            for (posY in 0..World.currentRoom.widthInSpaces)
            {
                val distance =
                        sqrt(((targetPosition.x - posX) * (targetPosition.x - posX) + (targetPosition.y - posY) * (targetPosition.y - posY)).toDouble())
                if (distance in 1.5..2.5)
                {
                    affectedSpaces.add(Vector2(posX.toFloat(), posY.toFloat()).toRoomPosition())
                }
            }
        }
        print(affectedSpaces)
        return affectedSpaces
    }
}