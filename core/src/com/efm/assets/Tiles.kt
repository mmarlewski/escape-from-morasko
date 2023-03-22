package com.efm.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile

object Tiles
{
    val assetManager = AssetManager()
    
    val groundStone = load("groundStone.png")
    
    val doorUp = load("doorUp.png")
    val doorRight = load("doorRight.png")
    val doorDown = load("doorDown.png")
    val doorLeft = load("doorLeft.png")
    val column = load("column.png")
    val chest = load("chest.png")
    
    val select = load("select.png")
    
    val hero = load("hero.png")
    val demon = load("demon.png")
    
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
