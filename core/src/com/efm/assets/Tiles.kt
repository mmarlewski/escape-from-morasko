package com.efm.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile

object Tiles
{
    val assetManager = AssetManager()
    
    val baseBlue = load("baseBlue.png")
    val baseRed = load("baseRed.png")
    val topTriangle = load("topTriangle.png")
    val topRectangle = load("topRectangle.png")
    
    fun load(name : String) : StaticTiledMapTile
    {
        val filePath = "tiles/$name"
        assetManager.load(filePath, Texture::class.java)
        assetManager.finishLoading()
        val texture = assetManager.get(filePath, Texture::class.java)
        val tile = StaticTiledMapTile(TextureRegion(texture))
        return tile
    }
}
