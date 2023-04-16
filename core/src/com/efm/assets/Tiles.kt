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
    val metalFloor = load("metalFloor.png")
    val rockFloor = load("rockFloor.png")
    val grassFloor = load("grassFloor.png")
    val lavaPool = load("lavaPool.png")
    
    // select
    
    val selectYellow = load("selectYellow.png")
    val selectGreen = load("selectGreen.png")
    val selectRed = load("selectRed.png")
    
    // stoneWall
    
    val stoneWallUpRightDownLeft = load("stoneWallUpRightDownLeft.png")
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
    
    // metalWall
    
    val metalWallUpRightDownLeft = load("metalWallUpRightDownLeft.png")
    val metalWallUp = load("metalWallUp.png")
    val metalWallRight = load("metalWallRight.png")
    val metalWallDown = load("metalWallDown.png")
    val metalWallLeft = load("metalWallLeft.png")
//    val metalWallUpRight = load("metalWallUpRight.png")
//    val metalWallRightDown = load("metalWallRightDown.png")
//    val metalWallDownLeft = load("metalWallDownLeft.png")
//    val metalWallLeftUp = load("metalWallLeftUp.png")
    
    // metalExit
    
    val metalExitUp = load("metalExitUp.png")
    val metalExitRight = load("metalExitRight.png")
    val metalExitDown = load("metalExitDown.png")
    val metalExitLeft = load("metalExitLeft.png")
    
    // rockWall
    
    val rockWallUpRightDownLeft = load("rockWallUpRightDownLeft.png")
    val rockWallUp = load("rockWallUp.png")
    val rockWallRight = load("rockWallRight.png")
    val rockWallDown = load("rockWallDown.png")
    val rockWallLeft = load("rockWallLeft.png")
//    val rockWallUpRight = load("rockWallUpRight.png")
//    val rockWallRightDown = load("rockWallRightDown.png")
//    val rockWallDownLeft = load("rockWallDownLeft.png")
//    val rockWallLeftUp = load("rockWallLeftUp.png")
    
    // rockExit
    
    val rockExitUp = load("rockExitUp.png")
    val rockExitRight = load("rockExitRight.png")
    val rockExitDown = load("rockExitDown.png")
    val rockExitLeft = load("rockExitLeft.png")
    
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
