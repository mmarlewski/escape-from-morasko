package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.IdleAnimation
import com.efm.assets.Tiles
import com.efm.entity.Character
import com.efm.item.Container
import com.efm.item.Item
import com.efm.room.RoomPosition
import com.efm.skill.BodyPart
import com.efm.skill.Skill
import com.efm.state.getState
import com.efm.ui.gameScreen.ProgressBars
import kotlin.random.Random

/**
 * Hero has its own turn and is controlled by the player.
 */
class Hero(
        override var maxHealthPoints : Int = 100, override var healthPoints : Int = 100, override var alive : Boolean = true
          ) : Character
{
    override val position = RoomPosition()
    
    var maxAbilityPoints : Int = 14
    var abilityPoints : Int = 14
    
    var apDrainInNextTurn = 0
    var canMoveNextTurn = true
    
    val inventory = HeroInventory()
    
    val bodyPartMap = mutableMapOf<BodyPart, Skill?>().apply { BodyPart.values().forEach { this[it] = null } }
    
    override fun getTile() : TiledMapTile
    {
        return if(canMoveNextTurn) Tiles.heroIdle1 else Tiles.heroVines
    }
    
    fun getIdleTile() : TiledMapTile?
    {
        return if(canMoveNextTurn) Tiles.heroIdle1 else Tiles.heroVines
    }
    
    fun getMoveTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.heroMove1
            2    -> Tiles.heroMove2
            3    -> Tiles.heroMove3
            4    -> Tiles.heroMove4
            else -> Tiles.heroMove1
        }
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return Tiles.heroIdle1OutlineYellow
    }
    
    fun getOutlineGreenTile() : TiledMapTile
    {
        return Tiles.heroIdle1OutlineGreen
    }
    
    override fun damageCharacter(dmgAmount : Int)
    {
        super.damageCharacter(dmgAmount)
        ProgressBars.healthBar.value = this.healthPoints.toFloat()
        ProgressBars.healthBarLabel.setText("${this.healthPoints} / ${this.maxHealthPoints}")
    }
    
    override fun healCharacter(healAmount : Int)
    {
        if (this.healthPoints + healAmount > this.maxHealthPoints)
        {
            this.healthPoints = this.maxHealthPoints
        }
        else
        {
            this.healthPoints += healAmount
        }
        ProgressBars.healthBar.value = this.healthPoints.toFloat()
        ProgressBars.healthBarLabel.setText("${this.healthPoints} / ${this.maxHealthPoints}")
    }
    
    fun spendAP(apCost : Int)
    {
        if ((this.abilityPoints - apCost) < 0)
        {
            this.abilityPoints = 0
        }
        else
        {
            this.abilityPoints -= apCost
        }
        ProgressBars.abilityBar.value -= apCost
        ProgressBars.abilityBarForFlashing.value -= apCost
        ProgressBars.abilityBarLabel.setText("$abilityPoints / $maxAbilityPoints")
    }
    
    fun gainAP(by : Int)
    {
        if (this.abilityPoints + by > this.maxAbilityPoints)
        {
            this.abilityPoints = this.maxAbilityPoints
        }
        else
        {
            this.abilityPoints += by
        }
        ProgressBars.abilityBar.value = this.abilityPoints.toFloat()
        ProgressBars.abilityBarForFlashing.value = this.abilityPoints.toFloat()
        ProgressBars.abilityBarLabel.setText("${this.abilityPoints} / ${this.maxAbilityPoints}")
    }
    
    fun regainAllAP()
    {
        this.abilityPoints = maxAbilityPoints - apDrainInNextTurn
        apDrainInNextTurn = 0
        ProgressBars.abilityBar.value = this.abilityPoints.toFloat()
        ProgressBars.abilityBarLabel.setText("$abilityPoints / $maxAbilityPoints")
    }
    
    override fun onDeath()
    {
        getState().isHeroAlive = false
    }
    
    fun setCanMoveToTrue()
    {
        this.canMoveNextTurn = true
    }
    
    fun getSkillOnBodyPart(bodyPart : BodyPart) : Skill?
    {
        return bodyPartMap[bodyPart]
    }
    
    fun setSkillOnBodyPart(bodyPart : BodyPart, skill : Skill)
    {
        bodyPartMap[bodyPart] = skill
    }
    
    fun addSkill(skill : Skill)
    {
        bodyPartMap[skill.bodyPart] = skill
    }
    
    fun removeSkill(skill : Skill)
    {
        bodyPartMap[skill.bodyPart] = null
    }
    
    fun hasSkill(skill : Skill) : Boolean
    {
        for ((bp, s) in bodyPartMap.iterator())
        {
            if (s == skill) return true
        }
        return false
    }
}

class HeroInventory : Container
{
    override val items : MutableList<Item> = mutableListOf<Item>()
    override var maxItems : Int = 10
}