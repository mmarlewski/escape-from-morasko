package com.efm.stackableMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.*
import com.efm.assets.*
import com.efm.entity.Character
import com.efm.item.StackableMapItem
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition

class Bomb(
        override var amount : Int = 1
          ) : StackableMapItem
{
    override val name : String = "Bomb"
    override val maxAmount : Int = 4
    override val baseAPUseCost : Int = 1
    val damage : Int = 11
    
    override fun getTexture() : Texture
    {
        return Textures.bomb
    }
    
    override fun selected()
    {
    }
    
    override fun confirmed()
    {
    }
    
    override fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    {
        val targetPositions = mutableListOf<RoomPosition>()
        
        targetPositions.addAll(getSquarePerimeterPositions(World.hero.position, 2))
        targetPositions.addAll(getSquarePerimeterPositions(World.hero.position, 3))
        
        return targetPositions.toList()
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        val affectedPositions = mutableListOf<RoomPosition>()
        
        affectedPositions.addAll(getSquareAreaPositions(targetPosition, 2))
        
        return affectedPositions.toList()
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val blastPositions = getSquareAreaPositions(targetPosition.copy(), 2)
        
        val showAreaOfFireAnimations = mutableListOf<Animation>()
        for (blastPosition in blastPositions)
        {
            showAreaOfFireAnimations += Animation.showTile(Tiles.fire, blastPosition, 0.5f)
        }
        val showAreaOfFire = Animation.simultaneous(showAreaOfFireAnimations)
        
        val showAreaOfSmokeAnimations = mutableListOf<Animation>()
        for (blastPosition in blastPositions)
        {
            showAreaOfSmokeAnimations += Animation.showTile(Tiles.smoke, blastPosition, 0.5f)
        }
        val showAreaOfSmoke = Animation.simultaneous(showAreaOfSmokeAnimations)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.moveTileWithArch(Tiles.bomb, World.hero.position, targetPosition.copy(), 1.0f, 0.5f)
        animations += Animation.action { playSoundOnce(Sounds.bomb) }
        animations += Animation.simultaneous(
                listOf(
                        Animation.cameraShake(3, 1f),
                        Animation.sequence(
                                listOf(
                                        showAreaOfFire,
                                        showAreaOfSmoke
                                      )
                                          )
                      )
                                            )
        
        animations += Animation.action {
            
            for (blastPosition in blastPositions)
            {
                val blastSpace = room.getSpace(blastPosition)
                val blastEntity = blastSpace?.getEntity()
                when (blastEntity)
                {
                    is Character ->
                    {
                        blastEntity.damageCharacter(damage)
                    }
                }
            }
        }
        
        Animating.executeAnimations(animations)
    }
}
