package com.efm.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile

object Tiles
{
    val assetManager = AssetManager()
    
    // base
    
    val stoneFloor = load("stoneFloor.png")
    val grassFloor = load("grassFloor.png")
    val lavaPool = load("lavaPool.png")
    
    // select
    
    val selectYellow = load("selectYellow.png")
    val selectGreen = load("selectGreen.png")
    val selectRed = load("selectRed.png")
    
    // stoneWall
    
    val stoneWallUp = load("stoneWallUp.png")
    val stoneWallRight = load("stoneWallRight.png")
    val stoneWallDown = load("stoneWallDown.png")
    val stoneWallLeft = load("stoneWallLeft.png")
    val stoneWallUpRight = load("stoneWallUpRight.png")
    val stoneWallRightDown = load("stoneWallRightDown.png")
    val stoneWallDownLeft = load("stoneWallDownLeft.png")
    val stoneWallLeftUp = load("stoneWallLeftUp.png")
    
    // stoneExit
    
    val stoneExitUp = load("stoneExitUp.png")
    val stoneExitRight = load("stoneExitRight.png")
    val stoneExitDown = load("stoneExitDown.png")
    val stoneExitLeft = load("stoneExitLeft.png")
    
    // other entities
    
    val hero = load("hero.png")
    val heroOutline = load("heroOutline.png")
    val heroCorpse = load("heroCorpse.png")
    val heroCorpseOutline = load("heroCorpseOutline.png")
    
    val bladeEnemy = load("bladeEnemy.png")
    val bladeEnemyOutline = load("bladeEnemyOutline.png")
    val bladeEnemyCorpse = load("bladeEnemyCorpse.png")
    val bladeEnemyCorpseOutline = load("bladeEnemyCorpseOutline.png")
    
    val crossbowEnemy = load("crossbowEnemy.png")
    val crossbowEnemyOutline = load("crossbowEnemyOutline.png")
    val crossbowEnemyCorpse = load("crossbowEnemyCorpse.png")
    val crossbowEnemyCorpseOutline = load("crossbowEnemyCorpseOutline.png")
    
    val stoneColumn = load("stoneColumn.png")
    val stoneColumnOutline = load("stoneColumnOutline.png")
    
    val chest = load("chest.png")
    val chestOutline = load("chestOutline.png")
    
    val explodingBarrel = load("explodingBarrel.png")
    val explodingBarrelOutline = load("explodingBarrelOutline.png")
    
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
