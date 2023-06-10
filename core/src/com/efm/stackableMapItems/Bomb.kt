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
    override val name : String = "Smoke Grenade"
    override val maxAmount : Int = 4
    override val baseAPUseCost : Int = 6
    val damage : Int = 10
    
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
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val blastPositions = getSquareAreaPositions(targetPosition.copy(), 2)
        
        val animations = mutableListOf<Animation>()
        animations += Animation.moveTile(Tiles.bomb, World.hero.position, targetPosition.copy(), 1.0f)
        animations += Animation.action { playSoundOnce(Sounds.bomb) }
        
        val showAreaOfFireAnimations = mutableListOf<Animation>()
        for (blastPosition in blastPositions)
        {
            showAreaOfFireAnimations += Animation.showTile(Tiles.fire, blastPosition, 0.5f)
        }
        val showAreaOfFire = Animation.simultaneous(showAreaOfFireAnimations)
        animations += showAreaOfFire
        
        val showAreaOfSmokeAnimations = mutableListOf<Animation>()
        for (blastPosition in blastPositions)
        {
            showAreaOfSmokeAnimations += Animation.showTile(Tiles.smoke, blastPosition, 0.5f)
        }
        val showAreaOfSmoke = Animation.simultaneous(showAreaOfSmokeAnimations)
        animations += showAreaOfSmoke
        
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
    
    override fun getTargetPositions(source : RoomPosition) : List<RoomPosition>
    {
        val positions = mutableListOf<RoomPosition>()
        
        positions.addAll(getSquarePerimeterPositions(World.hero.position, 2))
        positions.addAll(getSquarePerimeterPositions(World.hero.position, 3))
        
        return positions.toList()
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        val positions = mutableListOf<RoomPosition>()
        
        positions.addAll(getSquareAreaPositions(targetPosition, 2))
        positions.add(targetPosition)
        
        return positions.toList()
    }
}
