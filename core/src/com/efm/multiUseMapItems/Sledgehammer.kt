package com.efm.multiUseMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.*
import com.efm.assets.*
import com.efm.entity.Character
import com.efm.item.MultiUseMapItem
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.state.State
import com.efm.state.getState
import com.efm.ui.gameScreen.*
import kotlin.math.roundToInt

class Sledgehammer : MultiUseMapItem
{
    override val name : String = "Sledgehammer"
    override var baseAPUseCost : Int = 1
    override var durability : Int = 10
    override var maxDurability : Int = 10
    override val durabilityUseCost : Int = 1
    val damage : Int = 5
    override val statsDescription : String
        get() = "Damage: " + damage + "\n" +
                "Uses left: " + durability + "\n" +
                "AP cost: " + baseAPUseCost
    
    override fun getTexture() : Texture
    {
        return Textures.hammer
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
        
        targetPositions.addAll(getSquarePerimeterPositions(source, 1))
        targetPositions.addAll(getSquarePerimeterPositions(source, 2))
        
        return targetPositions.toList()
    }
    
    override fun getAffectedPositions(targetPosition : RoomPosition) : List<RoomPosition>
    {
        val affectedSpaces = mutableListOf<RoomPosition>()
        
        affectedSpaces.addAll(getSquarePerimeterPositions(targetPosition, 1))
        
        return affectedSpaces
    }
    
    override fun use(room : Room, targetPosition : RoomPosition)
    {
        val targetDirection = getDirection8(World.hero.position, targetPosition)
        val hammerTile = if (targetDirection == null) null else Tiles.getHammerTile(targetDirection)
        val positionsAroundTarget = getSquarePerimeterPositions(targetPosition, 1)
        
        val animations = mutableListOf<Animation>()
        animations += Animation.action{ PopUps.setBackgroundVisibility(false)}
        animations += Animation.action{ LeftStructure.menuButton.isVisible = false}
        val currentlyOpenedTab = findOpenedTab()
        animations += Animation.descendTile(hammerTile, targetPosition.copy(), 0.2f, 0.25f)
        animations += Animation.action { playSoundOnce(Sounds.hammer) }
        val impactAnimations = mutableListOf<Animation>()
        impactAnimations.add(Animation.showTile(Tiles.impact, targetPosition.copy(), 0.5f))
        impactAnimations.add(Animation.cameraShake(3, 1f))
        for (positionAroundTarget in positionsAroundTarget)
        {
            val impactDirection = getDirection8(targetPosition, positionAroundTarget)
            val impactTile = if (impactDirection == null) null else Tiles.getImpactTile(impactDirection)
            impactAnimations.add(Animation.showTile(impactTile, positionAroundTarget.copy(), 0.5f))
        }
        animations += Animation.simultaneous(impactAnimations)
        animations += Animation.action {
            
            val attackedPositions = mutableListOf<RoomPosition>()
            attackedPositions.add(targetPosition)
            attackedPositions.addAll(getSquarePerimeterPositions(targetPosition, 1))
            
            for (attackedPosition in attackedPositions)
            {
                val attackedSpace = room.getSpace(attackedPosition)
                val attackedEntity = attackedSpace?.getEntity()
                when (attackedEntity)
                {
                    is Character ->
                    {
                        attackedEntity.damageCharacter((this.damage * World.hero.weaponDamageMultiplier).roundToInt())
                    }
                }
            }
        }
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
