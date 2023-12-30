package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.entity.Interactive
import com.efm.item.MultiUseMapItem
import com.efm.level.World
import com.efm.multiUseMapItems.Fist
import com.efm.room.Base
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen
import com.efm.skill.Skill
import com.efm.skill.allSkills
import com.efm.ui.gameScreen.*
import kotlin.math.max

enum class Modifier(val function : () -> Unit, val popupSubtitle : String, val popupDescription : String)
{
    GainHpLoseAp(
            {
                World.hero.maxHealthPoints += 20
                World.hero.maxAbilityPoints -= 2
                if (World.hero.abilityPoints > World.hero.maxAbilityPoints)
                {
                    World.hero.abilityPoints = World.hero.maxAbilityPoints
                }
                ProgressBars.updateHeroApHpBars()
            },
            "Life Infusion",
            "Sacrifice some of your skill to gain resilience. Max Health increases, " +
                    "but say goodbye to a portion of your precious Ability Points. " +
                    "Do you accept this offer?"
                ),
    GainApLoseHp(
            {
                World.hero.maxHealthPoints -= 20
                if (World.hero.healthPoints > World.hero.maxHealthPoints)
                {
                    World.hero.healthPoints = World.hero.maxHealthPoints
                }
                World.hero.maxAbilityPoints += 2
                ProgressBars.updateHeroApHpBars()
            },
            "Mind Over Matter",
            "Opt for increased mental prowess. Max Ability Points rise, but " +
                    "at the cost of sacrificing a portion of your Max Health. " +
                    "Do you accept this offer?"
                ),
    SwapWaterAndLava(
            {
                for (room in World.currentLevel?.rooms ?: listOf())
                {
                    for (space in room.getSpaces())
                    {
                        val currBase = space.getBase()
                        val newBase = when (currBase)
                        {
                            Base.water -> Base.lava
                            Base.lava  -> Base.water
                            else       -> currBase
                        }
                        space.changeBase(newBase)
                    }
                }
                GameScreen.updateMapBaseLayer()
            },
            "Elemental Swap",
            "Have control over the environment. Transform lava tiles into water and vice versa. " +
                    "A strategic move that can alter the course of your journey. " +
                    "Do you accept this offer?"
                    ),
    AddRandomSkill(
            {
                val newSkills = mutableListOf<Skill>()
                newSkills.addAll(allSkills.toList())
                for ((bodyPart, skill) in World.hero.bodyPartMap)
                {
                    newSkills.remove(skill)
                }
                if (newSkills.isNotEmpty())
                {
                    World.hero.addSkill(newSkills.random())
                }
                
                ItemsStructure.fillItemsStructureWithItemsAndSkills()
            },
            "Body and Soul Exchange",
            "Exchange your skills on a body part. A gamble with the unknown," +
                    " as you let go of familiarity for the potential of newfound strength. " +
                    "Do you accept this offer?"
                  ),
    
    StrongerWeaponsLoseHp(
            {
                World.hero.weaponDamageMultiplier += 0.5
            
                World.hero.maxHealthPoints -= 20
                if (World.hero.healthPoints > World.hero.maxHealthPoints)
                {
                    World.hero.healthPoints = World.hero.maxHealthPoints
                }
                ProgressBars.updateHeroApHpBars()
            },
            "Magic Strength Training",
            "I know a secret way to achieve superhuman strength, but it comes with a cost... " +
                    "A chunk of your Max Health in exchange for the destructive might! " +
                    "Will you take this offer?"
                         ),
    
    StrongerWeaponsLoseDurability(
            {
                World.hero.weaponDamageMultiplier += 0.5
                for (w in World.hero.inventory.items.filter { it is MultiUseMapItem && it !is Fist })
                {
                    val weapon = w as MultiUseMapItem
                    val durabilityDrain = IntRange(0, weapon.durability).random()
                    weapon.durability = max(1, weapon.durability - durabilityDrain)
                }
            },
            "Weapon Training",
            "I can show you how to fight like a real warrior! " +
                    "Some of your stuff can break, mind you - this training is no joke. " +
                    "Are you ready?"
                                 )
}

class Npc(var modifier : Modifier = Modifier.StrongerWeaponsLoseHp) : Entity, Interactive
{
    override val position = RoomPosition()
    var wasUsed = false
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.npc
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return Tiles.npcOutlineYellow
    }
    
    override fun getOutlineTealTile() : TiledMapTile?
    {
        return if (!wasUsed) Tiles.npcOutlineTeal else null
    }
    
    override fun interact()
    {
        if (!wasUsed)
        {
            val popup = SpecialEventsPopups.createPopup(
                    modifier.popupSubtitle,
                    modifier.popupDescription,
                    {
                        wasUsed = true
                        modifier.function()
                    },
                    {
                        //
                    }
                                                       )
            SpecialEventsPopups.addPopupToDisplay(popup)
            popup.isVisible = true
            PopUps.setBackgroundVisibility(false)
            LeftStructure.menuButton.isVisible = false
        }
    }
}
