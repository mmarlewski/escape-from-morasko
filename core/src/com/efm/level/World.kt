package com.efm.level

import com.efm.entities.Hero
import com.efm.entity.Enemy
import com.efm.passage.Passage
import com.efm.room.Room

object World
{
    private val levels = mutableListOf<Level>()
    private val passages = mutableListOf<Passage>()
    
    val hero = Hero()
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
        if (newCurrentRoom in currentLevel.getRooms())
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
        
        for(lvl in this.levels)
        {
            for(room in lvl.getRooms())
            {
                for(entity in room.getEntities())
                {
                    when(entity)
                    {
                        is Enemy->
                        {
                            entity.createOwnHealthBar()
                        }
                    }
                }
                
                room.updateSpacesEntities()
            }
        }
        
//        this.passages.clear()
//        this.passages.addAll(newWorld.passages)
//
//        this.currentLevel = newWorld.currentLevel
//        this.currentRoom = newWorld.currentRoom
    }
}
