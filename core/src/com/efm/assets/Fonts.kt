package com.efm.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.BitmapFont

object Fonts
{
    val assetManager = AssetManager()
    
    val inconsolata10 = load("inconsolata/10.fnt")
    val inconsolata20 = load("inconsolata/20.fnt")
    val inconsolata30 = load("inconsolata/30.fnt")
    
    val pixeloid10 = load("pixeloid/10.fnt")
    val pixeloid20 = load("pixeloid/20.fnt")
    val pixeloid30 = load("pixeloid/30.fnt")
    
    val freezing32 = load("freezing/32.fnt")
    
    private fun load(name : String) : BitmapFont
    {
        val filePath = "fonts/$name"
        assetManager.load(filePath, BitmapFont::class.java)
        assetManager.finishLoading()
        val font = assetManager.get(filePath, BitmapFont::class.java)
        return font
    }
}
