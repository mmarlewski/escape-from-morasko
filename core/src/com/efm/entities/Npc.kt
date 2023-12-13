package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Entity
import com.efm.entity.Interactive
import com.efm.level.World
import com.efm.room.Base
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen
import com.efm.skill.*
import com.efm.ui.gameScreen.ItemsStructure
import com.efm.ui.gameScreen.ProgressBars

enum class Modifier(val function : () -> Unit)
{
    GainHpLoseAp({
                     World.hero.maxHealthPoints += 20
                     World.hero.maxAbilityPoints -= 2
                     if (World.hero.abilityPoints > World.hero.maxAbilityPoints)
                     {
                         World.hero.abilityPoints = World.hero.maxAbilityPoints
                     }
                     ProgressBars.updateHeroApHpBars()
                 }),
    GainApLoseHp({
                     World.hero.maxHealthPoints -= 20
                     if (World.hero.healthPoints > World.hero.maxHealthPoints)
                     {
                         World.hero.healthPoints = World.hero.maxHealthPoints
                     }
                     World.hero.maxAbilityPoints += 2
                     ProgressBars.updateHeroApHpBars()
                 }),
    SwapWaterAndLava({
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
                     }),
    AddRandomSkill({
                       val freeBodyParts = mutableListOf<BodyPart>()
                       for ((bodyPart, skill) in World.hero.bodyPartMap)
                       {
                           if (skill == null)
                           {
                               freeBodyParts.add(bodyPart)
                           }
                       }
                       
                       if (freeBodyParts.isNotEmpty())
                       {
                           val randomFreeBodyPart = freeBodyParts.random()
                           
                           val bodyPartSkills = mutableListOf<Skill>()
                           for (skill in allSkills)
                           {
                               if (skill.bodyPart == randomFreeBodyPart)
                               {
                                   bodyPartSkills.add(skill)
                               }
                           }
                           
                           if (bodyPartSkills.isNotEmpty())
                           {
                               World.hero.addSkill(bodyPartSkills.random())
                           }
                       }
                       
                       ItemsStructure.fillItemsStructureWithItemsAndSkills()
                   }),
    
    StrongerWeaponsLoseHp({
                              //
                          })
}

class Npc : Entity, Interactive
{
    override val position = RoomPosition()
    var modifier = Modifier.SwapWaterAndLava
    var wasUsed = false
    
    override fun getTile() : TiledMapTile?
    {
        return Tiles.npc
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile?
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
            wasUsed = true
            modifier.function()
        }
    }
}
