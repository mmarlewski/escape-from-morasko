package com.efm.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound

object Sounds
{
    val assetManager = AssetManager()
    
    val blop = load("blop.wav")
    val coin = load("coin.wav")
    val jump = load("jump.wav")
    
    private fun load(name : String) : Sound
    {
        val filePath = "sounds/$name"
        assetManager.load(filePath, Sound::class.java)
        assetManager.finishLoading()
        val sound = assetManager.get(filePath, Sound::class.java)
        return sound
    }
}
