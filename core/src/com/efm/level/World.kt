package com.efm.level

import com.efm.entities.Hero
import com.efm.passage.Passage
import com.efm.room.Room

object World
{
    private val levels = mutableListOf<Level>()
    private val passages = mutableListOf<Passage>()
    
    val hero = Hero()
    lateinit var currentLevel : Level
    lateinit var currentRoom : Room
    
    fun addLevel(level : Level)
    {
        levels.add(level)
    }
    
    fun changeCurrentLevel(newCurrentLevel : Level)
    {
        currentLevel = newCurrentLevel
    }
    
    fun changeCurrentRoom(newCurrentRoom : Room)
    {
        currentRoom = newCurrentRoom
    }
}
