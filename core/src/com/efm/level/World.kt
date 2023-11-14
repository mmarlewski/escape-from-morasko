package com.efm.level

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
    
    fun set(level :Level)
    {
        this.levels.clear()
        this.levels.add(level)
//        for(level in array)
//        {
//            this.levels.add(level)
//        }
        
//        this.passages.clear()
//        this.passages.addAll(newWorld.passages)
//
//        this.currentLevel = newWorld.currentLevel
//        this.currentRoom = newWorld.currentRoom
    }
}
