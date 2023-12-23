package com.efm.level

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Array
import com.efm.entities.Hero
import com.efm.entities.bosses.spawnAllBossesInOneRoom
import com.efm.room.Room
import com.efm.state.State
import com.efm.state.setState

/**
 * Holds information about the game world.
 * Contains all Levels, currentLevel, currentRoom and Hero
 */
object World
{
    val levels = mutableListOf<Level>()
    
    var hero = Hero()
    var currentLevel : Level? = null
    var currentRoom : Room? = null
    
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
        val worldCurrentLevel = currentLevel ?: return
        
        if (newCurrentRoom in worldCurrentLevel.rooms)
        {
            if (newCurrentRoom.name == "finalRoom")
            {
                spawnAllBossesInOneRoom(newCurrentRoom)
            }
            if (newCurrentRoom.areEnemiesInRoom())
            {
                for (enemy in newCurrentRoom.getEnemies())
                {
                    enemy.scaleOwnStats()
                    Gdx.app.log("Scaling", "Scaling HP and DMG of enemy : " + enemy.javaClass.name)
                }
            }
            currentRoom = newCurrentRoom
            
        }
    }
}
