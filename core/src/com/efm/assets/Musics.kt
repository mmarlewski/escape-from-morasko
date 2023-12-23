package com.efm.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music

/**
 * Contains all Music assets
 */
object Musics
{
    val assetManager = AssetManager()
    
    val ambient_1 = load("ambient_1.mp3")
    val ambient_3 = load("ambient_3.ogg")
    val ambient_4 = load("ambient_4.ogg")
    val ambient_5 = load("ambient_5.mp3")
    
    val combat_1 = load("combat_1.ogg")
    val combat_2 = load("combat_2.ogg")
    val combat_3 = load("combat_3.ogg")
    
    private fun load(name : String) : Music
    {
        val filePath = "music/$name"
        assetManager.load(filePath, Music::class.java)
        assetManager.finishLoading()
        val music = assetManager.get(filePath, Music::class.java)
        return music
    }
}
