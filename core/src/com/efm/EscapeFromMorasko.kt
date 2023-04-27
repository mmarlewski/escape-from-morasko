package com.efm

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.efm.level.World
import com.efm.screens.MenuScreen

object EscapeFromMorasko : Game()
{
    lateinit var spriteBatch : SpriteBatch
    
    override fun create()
    {
        spriteBatch = SpriteBatch()
    
        World.createWorld()
    
        // test big rooms
        World.addTestBigRoomsLevel()
        val test_level = World.getLevels().find { it.name == "test_level" }
        if (test_level != null)
        {
            World.changeCurrentLevel(test_level)
            World.changeCurrentRoom(test_level.getStartingRoom())
            World.currentRoom.addEntityAt(World.hero, test_level.getStartingPosition())
            World.currentRoom.updateSpacesEntities()
        }
    
        changeScreen(MenuScreen)
    }
    
    override fun dispose()
    {
        spriteBatch.dispose()
    }
}
