package com.efm.stackableMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.*
import com.efm.assets.*
import com.efm.entity.Character
import com.efm.item.StackableMapItem
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.state.State
import com.efm.state.getState
import com.efm.ui.gameScreen.*

class Explosive(
        override var amount : Int = 1
               ) : StackableMapItem
{
    override val name : String = "Explosive"
    override val maxAmount : Int = 8
    override val baseAPUseCost : Int = 1
    val damage = 5
    override val statsDescription : String
        get() = "Damage: " + damage + "\n" +
                "AP cost: " + baseAPUseCost
    
    override fun getTexture() : Texture
    {
        return Textures.explosive
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
        
        affectedPositions.addAll(getSquareAreaPositions(targetPosition, 1))
        
        return affectedPositions.toList()
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val blastPerimeterPositions = getSquarePerimeterPositions(targetPosition.copy(), 1)
        val showBlast = mutableListOf<Animation>()
        showBlast.add(Animation.showTile(Tiles.fire, targetPosition.copy(), 0.5f))
        for (blastPosition in blastPerimeterPositions)
        {
            val blastDirection = getDirection8(targetPosition, blastPosition)
            val impactTile = if (blastDirection == null) null else Tiles.getImpactTile(blastDirection)
            showBlast.add(Animation.showTile(impactTile, blastPosition.copy(), 0.5f))
        }
        
        val animations = mutableListOf<Animation>()
        animations += Animation.action{PopUps.setBackgroundVisibility(false)}
        animations += Animation.action{LeftStructure.menuButton.isVisible = false}
        val currentlyOpenedTab = findOpenedTab()
        animations.add(Animation.moveTile(Tiles.explosive, World.hero.position, targetPosition.copy(), 0.5f))
        animations.add(Animation.action { playSoundOnce(Sounds.explosive) })
        animations.add(
                Animation.simultaneous(
                        listOf(
                                Animation.cameraShake(2, 0.5f),
                                Animation.simultaneous(showBlast)
                              )
                                      )
                      )
        animations.add(Animation.action {
            for (blastPosition in blastPerimeterPositions + targetPosition)
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
        })
        animations += Animation.action{ interfaceVisibilityWithTutorial()}
        if (currentlyOpenedTab != null && currentlyOpenedTab != ItemsStructure.weaponDisplay)
        {
            animations += Animation.action{ hideWeaponsAndShowOtherTab(currentlyOpenedTab) }
        }
        animations += Animation.action{LeftStructure.menuButton.isVisible = true}
        if (getState() is State.free)
        {
            animations += Animation.action{
                ProgressBars.abilityBar.isVisible = false
                ProgressBars.abilityBarForFlashing.isVisible = false
                ProgressBars.abilityBarLabel.isVisible = false}
        }
        Animating.executeAnimations(animations)
    }
}
