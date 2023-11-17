package com.efm

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.efm.level.World
import com.efm.screens.MenuScreen
import com.efm.ui.gameScreen.ProgressBars

object EscapeFromMorasko : Game()
{
    lateinit var spriteBatch : SpriteBatch
    
    override fun create()
    {
        spriteBatch = SpriteBatch()
        
//        val loadFromSave = true
        val loadFromSave = false
        
        if (loadFromSave)
        {
            loadWorld()
            World.currentRoom.addEntityAt(World.hero, World.hero.position)
        }
        else
        {
//        World.createWorldPrototypeTwo()
//        World.createWorldBoarTest()
        World.createWorldPrototypeThree()
            
            val startingLevel = World.getLevels().first()
            World.changeCurrentLevel(startingLevel)
            World.changeCurrentRoom(startingLevel.getStartingRoom())
            World.currentRoom.addEntityAt(World.hero, startingLevel.getStartingPosition())
            World.currentRoom.updateSpacesEntities()
        }
        
        changeScreen(MenuScreen)
    }
    
    override fun dispose()
    {
        spriteBatch.dispose()
    }
}
