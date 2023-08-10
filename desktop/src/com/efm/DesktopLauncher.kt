package com.efm

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
object DesktopLauncher
{
    @JvmStatic fun main(arg : Array<String>)
    {
        val config = Lwjgl3ApplicationConfiguration()
        config.setForegroundFPS(foregroundFPS)
        config.setWindowedMode(windowWidth, windowHeight)
        config.setTitle("escape-from-morasko")
        Lwjgl3Application(EscapeFromMorasko, config)
    }
}
