package com.efm

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen

abstract class BaseScreen : Screen
{
    lateinit var inputProcessor : InputProcessor
    
    override fun show()
    {
        //
    }
    
    override fun pause()
    {
        //
    }
    
    override fun resume()
    {
        //
    }
    
    override fun hide()
    {
        //
    }
}
