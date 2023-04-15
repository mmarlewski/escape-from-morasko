package com.efm

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.efm.level.World
import com.efm.screens.GameScreen
import com.efm.screens.MenuScreen

object EscapeFromMorasko : Game()
{
    lateinit var spriteBatch : SpriteBatch
    
    override fun create()
    {
        spriteBatch = SpriteBatch()
        
        World.createWorld()
        
        changeScreen(MenuScreen)
    }
    
    override fun dispose()
    {
        spriteBatch.dispose()
    }
}
