package com.efm

import com.badlogic.gdx.Gdx

fun changeScreen(screen : BaseScreen)
{
    EscapeFromMorasko.screen = screen
    Gdx.input.inputProcessor = screen.inputProcessor
}

fun BaseScreen.setScreen()
{
    changeScreen(this)
}
