package com.efm.level

import com.badlogic.gdx.utils.Array
import com.efm.entities.Hero
import com.efm.room.Room
import com.efm.state.State
import com.efm.state.setState

object World
{
    val levels = mutableListOf<Level>()
    
    var hero = Hero()
    lateinit var currentLevel : Level
    lateinit var currentRoom : Room
    
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
}
