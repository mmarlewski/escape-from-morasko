package com.efm

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.efm.level.World
import com.efm.multiUseMapItems.*
import com.efm.screens.MenuScreen
import com.efm.skills.*
import com.efm.stackableMapItems.Bomb
import com.efm.stackableMapItems.Explosive
import com.efm.stackableSelfItems.*
import com.efm.state.State
import com.efm.state.setState

object EscapeFromMorasko : Game()
{
    lateinit var spriteBatch : SpriteBatch
    
    override fun create()
    {
        spriteBatch = SpriteBatch()
        
        changeScreen(MenuScreen)
    }
    
    override fun dispose()
    {
        spriteBatch.dispose()
    }
}
