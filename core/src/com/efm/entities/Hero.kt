package com.efm.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.*
import com.efm.assets.Tiles
import com.efm.entity.Character
import com.efm.item.*
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen
import com.efm.skill.*
import com.efm.skills.Pockets
import com.efm.stackableMapItems.TestingBomb
import com.efm.stackableSelfItems.Apple
import com.efm.stackableSelfItems.Fish
import com.efm.state.getState
import com.efm.ui.gameScreen.ProgressBars

/**
 * Hero has its own turn and is controlled by the player.
 */
class Hero(
        override var maxHealthPoints : Int = defaultHeroMaxHp,
        override var healthPoints : Int = defaultHeroMaxHp,
        override var alive : Boolean = true
          ) : Character
{
    override val position = RoomPosition()
    
    var maxAbilityPoints : Int = defaultHeroMaxAp
    var abilityPoints : Int = defaultHeroMaxAp
    
    var apDrainInNextTurn = 0
    var canMoveNextTurn = true
    var isVisible = true
    
    var inventory = HeroInventory()
    
    val bodyPartMap = mutableMapOf<BodyPart, Skill?>().apply { BodyPart.values().forEach { this[it] = null } }
    
    var turnsElapsed : Int = 0
    
    var isInvincible = false
        set(value)
        {
            field = value
            GameScreen.updateMapEntityLayer()
        }
    
    var weaponDamageMultiplier = 1
    
    override fun getTile() : TiledMapTile
    {
        return when
        {
            canMoveNextTurn && isVisible && !isInvincible   -> Tiles.heroIdle1
            
            !canMoveNextTurn && isVisible && !isInvincible  -> Tiles.heroVines
            !canMoveNextTurn && !isVisible && !isInvincible -> Tiles.heroVinesInvisible
            !canMoveNextTurn && isVisible && isInvincible   -> Tiles.heroVinesInvincible
            !canMoveNextTurn && !isVisible && isInvincible  -> Tiles.heroVinesInvisibleInvincible
            
            canMoveNextTurn && !isVisible && !isInvincible  -> Tiles.heroIdle1Invisible
            canMoveNextTurn && !isVisible && isInvincible   -> Tiles.heroIdle1InvisibleInvincible
            
            //canMoveNextTurn && isVisible && isInvincible
            else                                            -> Tiles.heroIdle1Invincible
        }
    }
    
    fun getIdleTile() : TiledMapTile
    {
        return when
        {
            canMoveNextTurn && isVisible && !isInvincible   -> Tiles.heroIdle1
            
            !canMoveNextTurn && isVisible && !isInvincible  -> Tiles.heroVines
            !canMoveNextTurn && !isVisible && !isInvincible -> Tiles.heroVinesInvisible
            !canMoveNextTurn && isVisible && isInvincible   -> Tiles.heroVinesInvincible
            !canMoveNextTurn && !isVisible && isInvincible  -> Tiles.heroVinesInvisibleInvincible
            
            canMoveNextTurn && !isVisible && !isInvincible  -> Tiles.heroIdle1Invisible
            canMoveNextTurn && !isVisible && isInvincible   -> Tiles.heroIdle1InvisibleInvincible
            
            //canMoveNextTurn && isVisible && isInvincible
            else                                            -> Tiles.heroIdle1Invincible
        }
    }
    
    fun getMoveTile(n : Int) : TiledMapTile
    {
        if (isVisible && !isInvincible)
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
        else if (!isVisible && !isInvincible)
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
        else if (isVisible && isInvincible)
        {
            return when (n)
            {
                1    -> Tiles.heroMove1Invincible
                2    -> Tiles.heroMove2Invincible
                3    -> Tiles.heroMove3Invincible
                4    -> Tiles.heroMove4Invincible
                else -> Tiles.heroMove1Invincible
            }
        }
        else
        {
            return when (n)
            {
                1    -> Tiles.heroMove1InvisibleInvincible
                2    -> Tiles.heroMove2InvisibleInvincible
                3    -> Tiles.heroMove3InvisibleInvincible
                4    -> Tiles.heroMove4InvisibleInvincible
                else -> Tiles.heroMove1InvisibleInvincible
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
        if (!isInvincible)
        {
            super.damageCharacter(dmgAmount)
            ProgressBars.healthBar.value = this.healthPoints.toFloat()
            ProgressBars.healthBarLabel.setText("${this.healthPoints} / ${this.maxHealthPoints}")
        }
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
        for ((_, skill) in bodyPartMap.entries)
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
        for ((_, skill) in bodyPartMap.entries)
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
        if (skill is Pockets)
        {
            this.inventory.maxItems += Pockets.additionalInventorySlotsAmount
        }
    }
    
    fun removeSkill(skill : Skill)
    {
        bodyPartMap[skill.bodyPart] = null
        if (skill is Pockets)
        {
            this.inventory.maxItems -= Pockets.additionalInventorySlotsAmount
        }
    }
    
    fun hasSkill(skill : Skill) : Boolean
    {
        for ((_, s) in bodyPartMap.iterator())
        {
            if (s == skill) return true
        }
        return false
    }
    
    fun incrementTurnsElapsed()
    {
        turnsElapsed += 1
    }
    
    fun getAmountOfTurnsElapsed() : Int
    {
        return turnsElapsed
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
            json.writeValue("weaponDamageMultiplier", this.weaponDamageMultiplier)
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
            val jsonWeaponDamageMultiplier = json.readValue("weaponDamageMultiplier", Int::class.java, jsonData)
            if (jsonWeaponDamageMultiplier != null) this.weaponDamageMultiplier = jsonWeaponDamageMultiplier
        }
    }
    
    fun setStartingInventory()
    {
        this.inventory.items.clear()
        this.inventory.addItem(Apple(2))
        this.inventory.addItem(Fish(1))
    }
    
    fun godMode()
    {
        this.inventory.addItem(TestingBomb())
        this.maxAbilityPoints = Int.MAX_VALUE / 4
        this.abilityPoints = this.maxAbilityPoints
        this.maxHealthPoints = Int.MAX_VALUE / 4
        this.healthPoints = this.maxHealthPoints
    }
}

class HeroInventory : Container
{
    override val items : MutableList<Item> = mutableListOf()
    override var maxItems : Int = 10
        set(value)
        {
            if (items.size > value)
            {
                val droppedPockets = DroppedPockets()
                droppedPockets.maxItems = 2 * (this.items.size - value)
                while (items.size > value)
                {
                    val item = items.last()
                    moveItem(item, this, droppedPockets)
                }
                for (pos in getSquareAreaPositions(World.hero.position, 1))
                {
                    val space = World.currentRoom?.getSpace(pos)
                    if (space != null && space.isTraversableFor(World.hero))
                    {
                        World.currentRoom?.addEntityAt(droppedPockets, pos)
                        World.currentRoom?.updateSpacesEntities()
                        GameScreen.updateMapBaseLayer()
                        GameScreen.updateMapEntityLayer()
                        break
                    }
                }
            }
            field = value
            Gdx.app.log("HeroInventory", "set maxItems = $value")
        }
    /*
    // var maxItems done with Delegates.observable
    override var maxItems : Int by kotlin.properties.Delegates.observable(10) { _, _, newValue ->
        if (items.size > newValue)
        {
            val droppedPockets = DroppedPockets()
            droppedPockets.maxItems = 2 * (this.items.size - newValue)
            while (items.size > newValue)
            {
                val item = items.last()
                moveItem(item, this, droppedPockets)
            }
            for (pos in getSquareAreaPositions(World.hero.position, 1))
            {
                val space = World.currentRoom.getSpace(pos)
                if (space != null && space.isTraversableFor(World.hero))
                {
                    World.currentRoom.addEntityAt(droppedPockets, pos)
                    World.currentRoom.updateSpacesEntities()
                    GameScreen.updateMapBaseLayer()
                    GameScreen.updateMapEntityLayer()
                    break
                }
            }
        }
        Gdx.app.log("HeroInventory", "set maxItems = $newValue")
    }
    */
}
