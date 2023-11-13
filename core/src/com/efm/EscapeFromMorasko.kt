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
        
//        World.createWorldPrototypeTwo()
//        World.createWorldBoarTest()
        World.createWorldPrototypeThree()
//        loadWorld()
        val startingLevel = World.getLevels().first()
        
        World.changeCurrentLevel(startingLevel)
        World.changeCurrentRoom(startingLevel.getStartingRoom())
        World.currentRoom.addEntityAt(World.hero, startingLevel.getStartingPosition())
        World.currentRoom.updateSpacesEntities()
        
        changeScreen(MenuScreen)
    }
    
    override fun dispose()
    {
        spriteBatch.dispose()
    }
}
