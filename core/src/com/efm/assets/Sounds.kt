package com.efm.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound

object Sounds
{
    val assetManager = AssetManager()
    
    val blop = load("blop.mp3")
    val coin = load("coin.mp3")
    val jump = load("jump.mp3")
    
    val woodenSword = load("woodenSword.mp3")
    val metalSword = load("metalSword.mp3")
    val bomb = load("bomb.mp3")
    val beam = load("beam.mp3")
    val axe = load("axe.mp3")
    val hammer = load("hammer.mp3")
    val bowShot = load("bowShot.mp3")
    val bowImpact = load("bowImpact.mp3")
    
    private fun load(name : String) : Sound
    {
        val filePath = "sounds/$name"
        assetManager.load(filePath, Sound::class.java)
        assetManager.finishLoading()
        val sound = assetManager.get(filePath, Sound::class.java)
        return sound
    }
}
