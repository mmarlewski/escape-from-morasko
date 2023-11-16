package com.efm.level

import com.badlogic.gdx.utils.Array
import com.efm.entities.Hero
import com.efm.room.Room

object World
{
    private val levels = mutableListOf<Level>()
    
    var hero = Hero()
    lateinit var currentLevel : Level
    lateinit var currentRoom : Room
    
    fun getLevels() : List<Level>
    {
        return levels
    }
    
    fun addLevel(level : Level)
    {
        levels.add(level)
    }
    
    fun clearLevels()
    {
        levels.clear()
    }
    
    fun changeCurrentLevel(newCurrentLevel : Level)
    {
        if (newCurrentLevel in levels)
        {
            currentLevel = newCurrentLevel
        }
    }
    
    fun changeCurrentRoom(newCurrentRoom : Room)
    {
        if (newCurrentRoom in currentLevel.rooms)
        {
            currentRoom = newCurrentRoom
        }
    }
    
    fun set(saveList : List<*>)
    {
        val saveCurrentLevelName = saveList[0] as String
        val saveCurrentRoomName = saveList[1] as String
        val saveHero = saveList[2] as Hero
        val saveLevels = saveList[3] as Array<*>
        this.hero = saveHero
        this.levels.clear()
        for (level in saveLevels)
        {
            this.levels.add(level as Level)
        }
        this.currentLevel = this.levels.find { it.name == saveCurrentLevelName }!!
        this.currentRoom = this.currentLevel.rooms.find { it.name == saveCurrentRoomName }!!
    }
}
