package com.efm

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Json
import com.efm.entities.Hero
import com.efm.entities.bosses.defeatedBosses
import com.efm.entity.Enemy
import com.efm.level.Level
import com.efm.level.World
import com.efm.state.*
import kotlin.reflect.KClass

val json = Json()

fun saveGame()
{
    println("saving game...")
    
    val file = Gdx.files.local("save.txt")
    val saveList = listOf<Any>(
            getSoundVolume(),
            getMusicVolume(),
            getState(),
            World.currentLevel.name,
            World.currentRoom.name,
            World.hero,
            World.levels,
            defeatedBosses
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
        
        setSoundVolume(saveSoundVolume)
        setMusicVolume(saveMusicVolume)
        
        when (saveState)
        {
            is State.free        -> setState(State.free.noSelection.apply {
                this.areEnemiesInRoom = saveState.areEnemiesInRoom
                this.isHeroAlive = saveState.isHeroAlive
            })
            
            is State.constrained -> setState(State.constrained.noSelection.apply {
                this.areEnemiesInRoom = saveState.areEnemiesInRoom
                this.isHeroAlive = saveState.isHeroAlive
                this.isHeroDetected = saveState.isHeroDetected
                this.areAnyActionPointsLeft = saveState.areAnyActionPointsLeft
            })
            
            is State.combat.hero -> setState(State.combat.hero.noSelection.apply {
                this.areEnemiesInRoom = saveState.areEnemiesInRoom
                this.isHeroAlive = saveState.isHeroAlive
                this.areAnyActionPointsLeft = saveState.areAnyActionPointsLeft
            })
    
            else                 -> setState(State.over)
        }
    
        World.hero = saveHero
    
        defeatedBosses.clear()
        for (boss in saveDefeatedBosses)
        {
            defeatedBosses.add(boss as KClass<out Enemy>)
        }
    
        World.levels.clear()
        for (level in saveLevels)
        {
            World.levels.add(level as Level)
        }
        World.currentLevel = World.levels.find { it.name == saveCurrentLevelName }!!
        World.currentRoom = World.currentLevel.rooms.find { it.name == saveCurrentRoomName }!!
    
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
