package com.efm

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Json
import com.efm.entities.Hero
import com.efm.entities.bosses.Boss
import com.efm.entities.bosses.defeatedBosses
import com.efm.level.Level
import com.efm.level.World
import com.efm.skill.BodyPart
import com.efm.state.*

val json = Json()

fun saveGame()
{
    println("saving game...")
    
    val file = Gdx.files.local("save.txt")
    val saveList = listOf<Any>(
            getSoundVolume(),
            getMusicVolume(),
            getState(),
            World.currentLevel?.name ?: "",
            World.currentRoom?.name ?: "",
            World.hero,
            World.levels,
            defeatedBosses,
            getState().tutorialFlags
                              )
    file.writeString(json.prettyPrint(saveList), false)
    
    println("saved game")
}

fun loadGame()
{
    println("loading game...")
    
    val file = Gdx.files.local("save.txt")
    if (file.exists())
    {
        val saveList = json.fromJson(List::class.java, file.readString())
        
        val saveSoundVolume = saveList[0] as Float
        val saveMusicVolume = saveList[1] as Float
        val saveState = saveList[2] as State
        val saveCurrentLevelName = saveList[3] as String
        val saveCurrentRoomName = saveList[4] as String
        val saveHero = saveList[5] as Hero
        val saveLevels = saveList[6] as Array<*>
        val saveDefeatedBosses = saveList[7] as Array<*>
        val saveTutorialFlags = saveList[8] as State.TutorialFlags
        
        setSoundVolume(saveSoundVolume)
        setMusicVolume(saveMusicVolume)
        
        val newState = when (saveState)
        {
        
            is State.free        -> State.free.noSelection.apply {
                this.areEnemiesInRoom = saveState.areEnemiesInRoom
                this.isHeroAlive = saveState.isHeroAlive
            }
        
            is State.constrained -> State.constrained.noSelection.apply {
                this.areEnemiesInRoom = saveState.areEnemiesInRoom
                this.isHeroAlive = saveState.isHeroAlive
                this.isHeroDetected = saveState.isHeroDetected
                this.areAnyActionPointsLeft = saveState.areAnyActionPointsLeft
            }
        
            is State.combat.hero -> State.combat.hero.noSelection.apply {
                this.areEnemiesInRoom = saveState.areEnemiesInRoom
                this.isHeroAlive = saveState.isHeroAlive
                this.areAnyActionPointsLeft = saveState.areAnyActionPointsLeft
            }
        
            else                 -> State.over
        }
        newState.tutorialFlags = saveTutorialFlags
        setState(newState)
    
        World.hero.alive = saveHero.alive
        World.hero.position.set(saveHero.position)
        World.hero.healthPoints = saveHero.healthPoints
        World.hero.healCharacter(0)
        World.hero.abilityPoints = saveHero.abilityPoints
        World.hero.gainAP(0)
        World.hero.apDrainInNextTurn = saveHero.apDrainInNextTurn
        World.hero.canMoveNextTurn = saveHero.canMoveNextTurn
        World.hero.isVisible = saveHero.isVisible
        World.hero.turnsElapsed = saveHero.turnsElapsed
        World.hero.weaponDamageMultiplier = saveHero.weaponDamageMultiplier
        World.hero.inventory.items.clear()
        World.hero.inventory.items.addAll(saveHero.inventory.items)
        BodyPart.values().forEach { World.hero.bodyPartMap[it] = null }
        saveHero.bodyPartMap.forEach { World.hero.bodyPartMap[it.key] = it.value }
        
        defeatedBosses.clear()
        for (boss in saveDefeatedBosses)
        {
            defeatedBosses.add(boss as Boss)
        }
        
        World.levels.clear()
        for (level in saveLevels)
        {
            World.levels.add(level as Level)
        }
        World.currentLevel = World.levels.find { it.name == saveCurrentLevelName }
        World.currentRoom = World.currentLevel?.rooms?.find { it.name == saveCurrentRoomName }
        
        //
        
        println("loaded game")
    }
    else
    {
        println("didn't load game")
    }
}

fun saveExists() : Boolean
{
    val file = Gdx.files.local("save.txt")
    
    return file.exists()
}

fun deleteSave()
{
    println("deleting game...")
    
    Gdx.files.local("save.txt").delete()
    
    println("deleted game")
}
