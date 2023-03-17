package com.efm.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music

object Musics
{
    val assetManager = AssetManager()
    
    val medical = load("medical.mp3")
    val executive = load("executive.mp3")
    
    private fun load(name : String) : Music
    {
        val filePath = "music/$name"
        assetManager.load(filePath, Music::class.java)
        assetManager.finishLoading()
        val music = assetManager.get(filePath, Music::class.java)
        return music
    }
}
