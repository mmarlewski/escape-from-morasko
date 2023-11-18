package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.assets.Tiles
import com.efm.entity.Character
import com.efm.item.Container
import com.efm.item.Item
import com.efm.room.RoomPosition
import com.efm.skill.*
import com.efm.state.getState
import com.efm.ui.gameScreen.ProgressBars

/**
 * Hero has its own turn and is controlled by the player.
 */
class Hero(
        override var maxHealthPoints : Int = 100,
        override var healthPoints : Int = 100,
        override var alive : Boolean = true
          ) : Character
{
    override val position = RoomPosition()
    
    var maxAbilityPoints : Int = 14
    var abilityPoints : Int = 14
    
    var apDrainInNextTurn = 0
    var canMoveNextTurn = true
    var isVisible = true
    
    var inventory = HeroInventory()
    
    val bodyPartMap = mutableMapOf<BodyPart, Skill?>().apply { BodyPart.values().forEach { this[it] = null } }
    
    override fun getTile() : TiledMapTile?
    {
        return when
        {
            !canMoveNextTurn && !isVisible -> Tiles.heroVinesInvisible
            !canMoveNextTurn && isVisible  -> Tiles.heroVines
            canMoveNextTurn && !isVisible  -> Tiles.heroIdle1Invisible
            canMoveNextTurn && isVisible   -> Tiles.heroIdle1
            else                           -> null
        }
    }
    
    fun getIdleTile() : TiledMapTile?
    {
        return when
        {
            !canMoveNextTurn && !isVisible -> Tiles.heroVinesInvisible
            !canMoveNextTurn && isVisible  -> Tiles.heroVines
            canMoveNextTurn && !isVisible  -> Tiles.heroIdle1Invisible
            canMoveNextTurn && isVisible   -> Tiles.heroIdle1
            else                           -> null
        }
    }
    
    fun getMoveTile(n : Int) : TiledMapTile?
    {
        if (isVisible)
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
        else
        {
            return when (n)
            {
                1    -> Tiles.heroMove1Invisible
                2    -> Tiles.heroMove2Invisible
                3    -> Tiles.heroMove3Invisible
                4    -> Tiles.heroMove4Invisible
                else -> Tiles.heroMove1Invisible
            }
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
    
    fun updateActiveSkillCoolDown()
    {
        for ((bodyPart, skill) in bodyPartMap.entries)
        {
            if (skill is ActiveSkill && skill.isInCoolDown)
            {
                skill.currCoolDown--
                if (skill.currCoolDown == 0) skill.isInCoolDown = false
            }
        }
    }
    
    fun removeCoolDownFromAllActiveSkills()
    {
        for ((bodyPart, skill) in bodyPartMap.entries)
        {
            if (skill is ActiveSkill)
            {
                skill.isInCoolDown = false
                skill.currCoolDown = 0
            }
        }
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
    
    // for serializing
    
    override fun write(json : Json?)
    {
        super.write(json)
        
        if (json != null)
        {
            json.writeValue("maxAbilityPoints", this.maxAbilityPoints)
            json.writeValue("abilityPoints", this.abilityPoints)
            json.writeValue("apDrainInNextTurn", this.apDrainInNextTurn)
            json.writeValue("canMoveNextTurn", this.canMoveNextTurn)
            json.writeValue("isVisible", this.isVisible)
            json.writeValue("inventory", this.inventory)
            val skillNames = mutableListOf<String>()
            this.bodyPartMap.values.forEach { skillNames.add(it?.name ?: "") }
            json.writeValue("skillNames", skillNames)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super.read(json, jsonData)
        
        if (json != null)
        {
            val jsonMaxAbilityPoints = json.readValue("maxAbilityPoints", Int::class.java, jsonData)
            if (jsonMaxAbilityPoints != null) this.maxAbilityPoints = jsonMaxAbilityPoints
            val jsonAbilityPoints = json.readValue("abilityPoints", Int::class.java, jsonData)
            if (jsonAbilityPoints != null) this.abilityPoints = jsonAbilityPoints
            val jsonApDrainInNextTurn = json.readValue("apDrainInNextTurn", Int::class.java, jsonData)
            if (jsonApDrainInNextTurn != null) this.apDrainInNextTurn = jsonApDrainInNextTurn
            val jsonCanMoveNextTurn = json.readValue("canMoveNextTurn", Boolean::class.java, jsonData)
            if (jsonCanMoveNextTurn != null) this.canMoveNextTurn = jsonCanMoveNextTurn
            val jsonIsVisible = json.readValue("isVisible", Boolean::class.java, jsonData)
            if (jsonIsVisible != null) this.isVisible = jsonIsVisible
            val jsonInventory = json.readValue("inventory", HeroInventory::class.java, jsonData)
            if (jsonInventory != null) this.inventory = jsonInventory
            val jsonSkillNames = json.readValue("skillNames", List::class.java, jsonData)
            if (jsonSkillNames != null)
            {
                for (jsonSkillName in jsonSkillNames)
                {
                    if (jsonSkillName is String)
                    {
                        val skill = getSkillFromName(jsonSkillName)
                        if (skill != null)
                        {
                            this.addSkill(skill)
                        }
                    }
                }
            }
        }
    }
}

class HeroInventory : Container
{
    override val items : MutableList<Item> = mutableListOf<Item>()
    override var maxItems : Int = 10
}
