package com.efm.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import com.efm.Direction8

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
    
    val selectRed = load("selectRed.png")
    val selectOrange = load("selectOrange.png")
    val selectYellow = load("selectYellow.png")
    val selectGreen = load("selectGreen.png")
    val selectTeal = load("selectTeal.png")
    val selectBlue = load("selectBlue.png")
    val selectPurple = load("selectPurple.png")
    
    // stoneWall
    
    val stoneWallUpRightDownLeft = load("stoneWallUpRightDownLeft.png")
    val stoneWallUpRightDown = load("stoneWallUpRightDown.png")
    val stoneWallUpRightLeft = load("stoneWallUpRightLeft.png")
    val stoneWallUpRight = load("stoneWallUpRight.png")
    val stoneWallUpDownLeft = load("stoneWallUpDownLeft.png")
    val stoneWallUpDown = load("stoneWallUpDown.png")
    val stoneWallUpLeft = load("stoneWallUpLeft.png")
    val stoneWallUp = load("stoneWallUp.png")
    val stoneWallRightDownLeft = load("stoneWallRightDownLeft.png")
    val stoneWallRightDown = load("stoneWallRightDown.png")
    val stoneWallRightLeft = load("stoneWallRightLeft.png")
    val stoneWallRight = load("stoneWallRight.png")
    val stoneWallDownLeft = load("stoneWallDownLeft.png")
    val stoneWallDown = load("stoneWallDown.png")
    val stoneWallLeft = load("stoneWallLeft.png")
    val stoneWall = load("stoneWall.png")
    
    // stoneExit
    
    val stoneExitUp = load("stoneExitUp.png")
    val stoneExitUpOutlineTeal = load("stoneExitUpOutlineTeal.png")
    val stoneExitLevelUp = load("stoneExitLevelUp.png")
    val stoneExitLevelUpOutlineTeal = load("stoneExitLevelUpOutlineTeal.png")
    val stoneExitRight = load("stoneExitRight.png")
    val stoneExitRightOutlineTeal = load("stoneExitRightOutlineTeal.png")
    val stoneExitLevelRight = load("stoneExitLevelRight.png")
    val stoneExitLevelRightOutlineTeal = load("stoneExitLevelRightOutlineTeal.png")
    val stoneExitDown = load("stoneExitDown.png")
    val stoneExitDownOutlineTeal = load("stoneExitDownOutlineTeal.png")
    val stoneExitLevelDown = load("stoneExitLevelDown.png")
    val stoneExitLevelDownOutlineTeal = load("stoneExitLevelDownOutlineTeal.png")
    val stoneExitLeft = load("stoneExitLeft.png")
    val stoneExitLeftOutlineTeal = load("stoneExitLeftOutlineTeal.png")
    val stoneExitLevelLeft = load("stoneExitLevelLeft.png")
    val stoneExitLevelLeftOutlineTeal = load("stoneExitLevelLeftOutlineTeal.png")
    
    // metalWall
    
    val metalWallUpRightDownLeft = load("metalWallUpRightDownLeft.png")
    val metalWallUpRightDown = load("metalWallUpRightDown.png")
    val metalWallUpRightLeft = load("metalWallUpRightLeft.png")
    val metalWallUpRight = load("metalWallUpRight.png")
    val metalWallUpDownLeft = load("metalWallUpDownLeft.png")
    val metalWallUpDown = load("metalWallUpDown.png")
    val metalWallUpLeft = load("metalWallUpLeft.png")
    val metalWallUp = load("metalWallUp.png")
    val metalWallRightDownLeft = load("metalWallRightDownLeft.png")
    val metalWallRightDown = load("metalWallRightDown.png")
    val metalWallRightLeft = load("metalWallRightLeft.png")
    val metalWallRight = load("metalWallRight.png")
    val metalWallDownLeft = load("metalWallDownLeft.png")
    val metalWallDown = load("metalWallDown.png")
    val metalWallLeft = load("metalWallLeft.png")
    val metalWall = load("metalWall.png")
    
    // metalExit
    
    val metalExitUp = load("metalExitUp.png")
    val metalExitUpOutlineTeal = load("metalExitUpOutlineTeal.png")
    val metalExitLevelUp = load("metalExitLevelUp.png")
    val metalExitLevelUpOutlineTeal = load("metalExitLevelUpOutlineTeal.png")
    val metalExitRight = load("metalExitRight.png")
    val metalExitRightOutlineTeal = load("metalExitRightOutlineTeal.png")
    val metalExitLevelRight = load("metalExitLevelRight.png")
    val metalExitLevelRightOutlineTeal = load("metalExitLevelRightOutlineTeal.png")
    val metalExitDown = load("metalExitDown.png")
    val metalExitDownOutlineTeal = load("metalExitDownOutlineTeal.png")
    val metalExitLevelDown = load("metalExitLevelDown.png")
    val metalExitLevelDownOutlineTeal = load("metalExitLevelDownOutlineTeal.png")
    val metalExitLeft = load("metalExitLeft.png")
    val metalExitLeftOutlineTeal = load("metalExitLeftOutlineTeal.png")
    val metalExitLevelLeft = load("metalExitLevelLeft.png")
    val metalExitLevelLeftOutlineTeal = load("metalExitLevelLeftOutlineTeal.png")
    
    // rockWall
    
    val rockWallUpRightDownLeft = load("rockWallUpRightDownLeft.png")
    val rockWallUpRightDown = load("rockWallUpRightDown.png")
    val rockWallUpRightLeft = load("rockWallUpRightLeft.png")
    val rockWallUpRight = load("rockWallUpRight.png")
    val rockWallUpDownLeft = load("rockWallUpDownLeft.png")
    val rockWallUpDown = load("rockWallUpDown.png")
    val rockWallUpLeft = load("rockWallUpLeft.png")
    val rockWallUp = load("rockWallUp.png")
    val rockWallRightDownLeft = load("rockWallRightDownLeft.png")
    val rockWallRightDown = load("rockWallRightDown.png")
    val rockWallRightLeft = load("rockWallRightLeft.png")
    val rockWallRight = load("rockWallRight.png")
    val rockWallDownLeft = load("rockWallDownLeft.png")
    val rockWallDown = load("rockWallDown.png")
    val rockWallLeft = load("rockWallLeft.png")
    val rockWall = load("rockWall.png")
    
    // rockExit
    
    val rockExitUp = load("rockExitUp.png")
    val rockExitUpOutlineTeal = load("rockExitUpOutlineTeal.png")
    val rockExitLevelUp = load("rockExitLevelUp.png")
    val rockExitLevelUpOutlineTeal = load("rockExitLevelUpOutlineTeal.png")
    val rockExitRight = load("rockExitRight.png")
    val rockExitRightOutlineTeal = load("rockExitRightOutlineTeal.png")
    val rockExitLevelRight = load("rockExitLevelRight.png")
    val rockExitLevelRightOutlineTeal = load("rockExitLevelRightOutlineTeal.png")
    val rockExitDown = load("rockExitDown.png")
    val rockExitDownOutlineTeal = load("rockExitDownOutlineTeal.png")
    val rockExitLevelDown = load("rockExitLevelDown.png")
    val rockExitLevelDownOutlineTeal = load("rockExitLevelDownOutlineTeal.png")
    val rockExitLeft = load("rockExitLeft.png")
    val rockExitLeftOutlineTeal = load("rockExitLeftOutlineTeal.png")
    val rockExitLevelLeft = load("rockExitLevelLeft.png")
    val rockExitLevelLeftOutlineTeal = load("rockExitLevelLeftOutlineTeal.png")
    
    // other entities
    
    val hero = load("hero.png")
    val heroOutlineYellow = load("heroOutlineYellow.png")
    val heroOutlineGreen = load("heroOutlineGreen.png")
    val heroCorpse = load("heroCorpse.png")
    val heroCorpseOutlineYellow = load("heroCorpseOutlineYellow.png")
    
    val mushroomIdle1 = load("mushroomIdle1.png")
    val mushroomIdle1OutlineYellow = load("mushroomIdle1OutlineYellow.png")
    val mushroomIdle1OutlineRed = load("mushroomIdle1OutlineRed.png")
    val mushroomIdle2 = load("mushroomIdle2.png")
    val mushroomIdle2OutlineYellow = load("mushroomIdle2OutlineYellow.png")
    val mushroomMove1 = load("mushroomMove1.png")
    val mushroomMove2 = load("mushroomMove2.png")
    val mushroomAttack = load("mushroomAttack.png")
    val mushroomCorpse = load("mushroomCorpse.png")
    val mushroomCorpseOutlineYellow = load("mushroomCorpseOutlineYellow.png")
    
    val skeletonIdle1 = load("skeletonIdle1.png")
    val skeletonIdle1OutlineYellow = load("skeletonIdle1OutlineYellow.png")
    val skeletonIdle1OutlineRed = load("skeletonIdle1OutlineRed.png")
    val skeletonIdle2 = load("skeletonIdle2.png")
    val skeletonIdle2OutlineYellow = load("skeletonIdle2OutlineYellow.png")
    val skeletonIdle3 = load("skeletonIdle3.png")
    val skeletonIdle3OutlineYellow = load("skeletonIdle3OutlineYellow.png")
    val skeletonMove1 = load("skeletonMove1.png")
    val skeletonMove2 = load("skeletonMove2.png")
    val skeletonMove3 = load("skeletonMove3.png")
    val skeletonAttack = load("skeletonAttack.png")
    val skeletonCorpse = load("skeletonCorpse.png")
    val skeletonCorpseOutlineYellow = load("skeletonCorpseOutlineYellow.png")
    
    val batIdle1 = load("batIdle1.png")
    val batIdle1OutlineYellow = load("batIdle1OutlineYellow.png")
    val batIdle1OutlineRed = load("batIdle1OutlineRed.png")
    val batIdle2 = load("batIdle2.png")
    val batIdle2OutlineYellow = load("batIdle2OutlineYellow.png")
    val batIdle3 = load("batIdle3.png")
    val batIdle3OutlineYellow = load("batIdle3OutlineYellow.png")
    val batMove1 = load("batIdle1.png")
    val batMove2 = load("batIdle2.png")
    val batMove3 = load("batIdle3.png")
    val batAttack = load("batAttack.png")
    val batCorpse = load("batCorpse.png")
    val batCorpseOutlineYellow = load("batCorpseOutlineYellow.png")
    
    val boarIdle1 = load("boarIdle1.png")
    val boarIdle1OutlineYellow = load("boarIdle1OutlineYellow.png")
    val boarIdle1OutlineRed = load("boarIdle1OutlineRed.png")
    val boarIdle2 = load("boarIdle2.png")
    val boarIdle2OutlineYellow = load("boarIdle2OutlineYellow.png")
    val boarMove1 = load("boarMove1.png")
    val boarMove2 = load("boarMove2.png")
    val boarAttack = load("boarAttack.png")
    val boarCorpse = load("boarCorpse.png")
    val boarCorpseOutlineYellow = load("boarCorpseOutlineYellow.png")
    
    val wizardIdle1 = load("wizardIdle1.png")
    val wizardIdle1OutlineYellow = load("wizardIdle1OutlineYellow.png")
    val wizardIdle1OutlineRed = load("wizardIdle1OutlineRed.png")
    val wizardIdle2 = load("wizardIdle2.png")
    val wizardIdle2OutlineYellow = load("wizardIdle2OutlineYellow.png")
    val wizardIdle3 = load("wizardIdle3.png")
    val wizardIdle3OutlineYellow = load("wizardIdle3OutlineYellow.png")
    val wizardIdle4 = load("wizardIdle4.png")
    val wizardIdle4OutlineYellow = load("wizardIdle4OutlineYellow.png")
    val wizardMove1 = load("wizardMove1.png")
    val wizardMove2 = load("wizardMove2.png")
    val wizardMove3 = load("wizardMove3.png")
    val wizardMove4 = load("wizardMove4.png")
    val wizardAttack = load("wizardAttack.png")
    val wizardProjectile = load("wizardProjectile.png")
    val wizardCorpse = load("wizardCorpse.png")
    val wizardCorpseOutlineYellow = load("wizardCorpseOutlineYellow.png")
    
    val plantIdle1 = load("plantIdle1.png")
    val plantIdle1OutlineYellow = load("plantIdle1OutlineYellow.png")
    val plantIdle1OutlineRed = load("plantIdle1OutlineRed.png")
    val plantIdle2 = load("plantIdle2.png")
    val plantIdle2OutlineYellow = load("plantIdle2OutlineYellow.png")
    val plantAttack = load("plantAttack.png")
    val plantCorpse = load("plantCorpse.png")
    val plantCorpseOutlineYellow = load("plantCorpseOutlineYellow.png")
    
    val ghost1Idle1 = load("ghost1Idle1.png")
    val ghost2Idle1 = load("ghost2Idle1.png")
    val ghost3Idle1 = load("ghost3Idle1.png")
    val ghostIdle1OutlineYellow = load("ghostIdle1OutlineYellow.png")
    val ghostIdle1OutlineRed = load("ghostIdle1OutlineRed.png")
    val ghost1Idle2 = load("ghost1Idle2.png")
    val ghost2Idle2 = load("ghost2Idle2.png")
    val ghost3Idle2 = load("ghost3Idle2.png")
    val ghostIdle2OutlineYellow = load("ghostIdle2OutlineYellow.png")
    val ghost1Idle3 = load("ghost1Idle3.png")
    val ghost2Idle3 = load("ghost2Idle3.png")
    val ghost3Idle3 = load("ghost3Idle3.png")
    val ghostIdle3OutlineYellow = load("ghostIdle3OutlineYellow.png")
    val ghost1Move1 = load("ghost1Move1.png")
    val ghost2Move1 = load("ghost2Move1.png")
    val ghost3Move1 = load("ghost3Move1.png")
    val ghost1Move2 = load("ghost1Move2.png")
    val ghost2Move2 = load("ghost2Move2.png")
    val ghost3Move2 = load("ghost3Move2.png")
    val ghost1Move3 = load("ghost1Move3.png")
    val ghost2Move3 = load("ghost2Move3.png")
    val ghost3Move3 = load("ghost3Move3.png")
    val ghostCorpse = load("ghostCorpse.png")
    val ghostCorpseOutlineYellow = load("ghostCorpseOutlineYellow.png")
    
    val turretIdle1 = load("turretIdle1.png")
    val turretIdle1OutlineYellow = load("turretIdle1OutlineYellow.png")
    val turretIdle1OutlineRed = load("turretIdle1OutlineRed.png")
    val turretIdle2 = load("turretIdle2.png")
    val turretIdle2OutlineYellow = load("turretIdle2OutlineYellow.png")
    val turretAttack = load("turretAttack.png")
    val turretProjectile = load("turretProjectile.png")
    val turretCorpse = load("turretCorpse.png")
    val turretCorpseOutlineYellow = load("turretCorpseOutlineYellow.png")
    
    val golemIdle1 = load("golemIdle1.png")
    val golemIdle1OutlineYellow = load("golemIdle1OutlineYellow.png")
    val golemIdle1OutlineRed = load("golemIdle1OutlineRed.png")
    val golemIdle2 = load("golemIdle2.png")
    val golemIdle2OutlineYellow = load("golemIdle2OutlineYellow.png")
    val golemMove1 = load("golemMove1.png")
    val golemMove2 = load("golemMove2.png")
    val golemAttack = load("golemAttack.png")
    val golemProjectile = load("golemProjectile.png")
    val golemCorpse = load("golemCorpse.png")
    val golemCorpseOutlineYellow = load("golemCorpseOutlineYellow.png")
    
    val mimicIdle1 = load("mimicIdle1.png")
    val mimicIdle1OutlineYellow = load("mimicIdle1OutlineYellow.png")
    val mimicIdle1OutlineRed = load("mimicIdle1OutlineRed.png")
    val mimicIdle2 = load("mimicIdle2.png")
    val mimicIdle2OutlineYellow = load("mimicIdle2OutlineYellow.png")
    val mimicMove1 = load("mimicMove1.png")
    val mimicMove2 = load("mimicMove2.png")
    val mimicAttack = load("mimicAttack.png")
    val mimicCorpse = load("mimicCorpse.png")
    val mimicCorpseOutlineYellow = load("mimicCorpseOutlineYellow.png")
    
    val rockIdle1 = load("rockIdle1.png")
    val rockIdle1OutlineYellow = load("rockIdle1OutlineYellow.png")
    val rockIdle1OutlineRed = load("rockIdle1OutlineRed.png")
    val rockIdle2 = load("rockIdle2.png")
    val rockIdle2OutlineYellow = load("rockIdle2OutlineYellow.png")
    val rockMove1 = load("rockMove1.png")
    val rockMove2 = load("rockMove2.png")
    val rockCorpse = load("rockCorpse.png")
    val rockCorpseOutlineYellow = load("rockCorpseOutlineYellow.png")
    
    val dragonIdle1 = load("dragonIdle1.png")
    val dragonIdle1OutlineYellow = load("dragonIdle1OutlineYellow.png")
    val dragonIdle1OutlineRed = load("dragonIdle1OutlineRed.png")
    val dragonIdle2 = load("dragonIdle2.png")
    val dragonIdle2OutlineYellow = load("dragonIdle2OutlineYellow.png")
    val dragonMove1 = load("dragonMove1.png")
    val dragonMove2 = load("dragonMove2.png")
    val dragonAttack = load("dragonAttack.png")
    val dragonProjectile = load("dragonProjectile.png")
    val dragonCorpse = load("dragonCorpse.png")
    val dragonCorpseOutlineYellow = load("dragonCorpseOutlineYellow.png")
    
    val wizardEnemy = load("wizardEnemy.png")
    val wizardEnemyOutlineYellow = load("wizardEnemyOutlineYellow.png")
    val wizardEnemyOutlineRed = load("wizardEnemyOutlineRed.png")
    
    val stoneColumn = load("stoneColumn.png")
    val stoneColumnOutlineYellow = load("stoneColumnOutlineYellow.png")
    
    val chest = load("chest.png")
    val chestOutlineYellow = load("chestOutlineYellow.png")
    val chestOutlineTeal = load("chestOutlineTeal.png")
    
    val explodingBarrel = load("explodingBarrel.png")
    val explodingBarrelOutlineYellow = load("explodingBarrelOutlineYellow.png")
    
    // sword
    
    val swordUp = load("swordUp.png")
    val swordUpRight = load("swordUpRight.png")
    val swordRight = load("swordRight.png")
    val swordDownRight = load("swordDownRight.png")
    val swordDown = load("swordDown.png")
    val swordDownLeft = load("swordDownLeft.png")
    val swordLeft = load("swordLeft.png")
    val swordUpLeft = load("swordUpLeft.png")
    
    fun getSwordTile(direction : Direction8) : StaticTiledMapTile
    {
        return when (direction)
        {
            Direction8.up        -> swordUp
            Direction8.upRight   -> swordUpRight
            Direction8.right     -> swordRight
            Direction8.downRight -> swordDownRight
            Direction8.down      -> swordDown
            Direction8.downLeft  -> swordDownLeft
            Direction8.left      -> swordLeft
            Direction8.upLeft    -> swordUpLeft
        }
    }
    
    // axe
    
    val axeUp = load("axeUp.png")
    val axeUpRight = load("axeUpRight.png")
    val axeRight = load("axeRight.png")
    val axeDownRight = load("axeDownRight.png")
    val axeDown = load("axeDown.png")
    val axeDownLeft = load("axeDownLeft.png")
    val axeLeft = load("axeLeft.png")
    val axeUpLeft = load("axeUpLeft.png")
    
    fun getAxeTile(direction : Direction8) : StaticTiledMapTile
    {
        return when (direction)
        {
            Direction8.up        -> axeUp
            Direction8.upRight   -> axeUpRight
            Direction8.right     -> axeRight
            Direction8.downRight -> axeDownRight
            Direction8.down      -> axeDown
            Direction8.downLeft  -> axeDownLeft
            Direction8.left      -> axeLeft
            Direction8.upLeft    -> axeUpLeft
        }
    }
    
    // hammer
    
    val hammerUp = load("hammerUp.png")
    val hammerUpRight = load("hammerUpRight.png")
    val hammerRight = load("hammerRight.png")
    val hammerDownRight = load("hammerDownRight.png")
    val hammerDown = load("hammerDown.png")
    val hammerDownLeft = load("hammerDownLeft.png")
    val hammerLeft = load("hammerLeft.png")
    val hammerUpLeft = load("hammerUpLeft.png")
    
    fun getHammerTile(direction : Direction8) : StaticTiledMapTile
    {
        return when (direction)
        {
            Direction8.up        -> hammerUp
            Direction8.upRight   -> hammerUpRight
            Direction8.right     -> hammerRight
            Direction8.downRight -> hammerDownRight
            Direction8.down      -> hammerDown
            Direction8.downLeft  -> hammerDownLeft
            Direction8.left      -> hammerLeft
            Direction8.upLeft    -> hammerUpLeft
        }
    }
    
    // bow
    
    val bowUp = load("bowUp.png")
    val bowUpRight = load("bowUpRight.png")
    val bowRight = load("bowRight.png")
    val bowDownRight = load("bowDownRight.png")
    val bowDown = load("bowDown.png")
    val bowDownLeft = load("bowDownLeft.png")
    val bowLeft = load("bowLeft.png")
    val bowUpLeft = load("bowUpLeft.png")
    
    fun getBowTile(direction : Direction8) : StaticTiledMapTile
    {
        return when (direction)
        {
            Direction8.up        -> bowUp
            Direction8.upRight   -> bowUpRight
            Direction8.right     -> bowRight
            Direction8.downRight -> bowDownRight
            Direction8.down      -> bowDown
            Direction8.downLeft  -> bowDownLeft
            Direction8.left      -> bowLeft
            Direction8.upLeft    -> bowUpLeft
        }
    }
    
    // arrow
    
    val arrowUp = load("arrowUp.png")
    val arrowUpRight = load("arrowUpRight.png")
    val arrowRight = load("arrowRight.png")
    val arrowDownRight = load("arrowDownRight.png")
    val arrowDown = load("arrowDown.png")
    val arrowDownLeft = load("arrowDownLeft.png")
    val arrowLeft = load("arrowLeft.png")
    val arrowUpLeft = load("arrowUpLeft.png")
    
    fun getArrowTile(direction : Direction8) : StaticTiledMapTile
    {
        return when (direction)
        {
            Direction8.up        -> arrowUp
            Direction8.upRight   -> arrowUpRight
            Direction8.right     -> arrowRight
            Direction8.downRight -> arrowDownRight
            Direction8.down      -> arrowDown
            Direction8.downLeft  -> arrowDownLeft
            Direction8.left      -> arrowLeft
            Direction8.upLeft    -> arrowUpLeft
        }
    }
    
    // impact
    
    val impact = load("impact.png")
    val impactUp = load("impactUp.png")
    val impactUpRight = load("impactUpRight.png")
    val impactRight = load("impactRight.png")
    val impactDownRight = load("impactDownRight.png")
    val impactDown = load("impactDown.png")
    val impactDownLeft = load("impactDownLeft.png")
    val impactLeft = load("impactLeft.png")
    val impactUpLeft = load("impactUpLeft.png")
    
    fun getImpactTile(direction : Direction8) : StaticTiledMapTile
    {
        return when (direction)
        {
            Direction8.up        -> impactUp
            Direction8.upRight   -> impactUpRight
            Direction8.right     -> impactRight
            Direction8.downRight -> impactDownRight
            Direction8.down      -> impactDown
            Direction8.downLeft  -> impactDownLeft
            Direction8.left      -> impactLeft
            Direction8.upLeft    -> impactUpLeft
        }
    }
    
    // staff
    
    val staffUp = load("staffUp.png")
    val staffUpRight = load("staffUpRight.png")
    val staffRight = load("staffRight.png")
    val staffDownRight = load("staffDownRight.png")
    val staffDown = load("staffDown.png")
    val staffDownLeft = load("staffDownLeft.png")
    val staffLeft = load("staffLeft.png")
    val staffUpLeft = load("staffUpLeft.png")
    
    fun getStaffTile(direction : Direction8) : StaticTiledMapTile
    {
        return when (direction)
        {
            Direction8.up        -> staffUp
            Direction8.upRight   -> staffUpRight
            Direction8.right     -> staffRight
            Direction8.downRight -> staffDownRight
            Direction8.down      -> staffDown
            Direction8.downLeft  -> staffDownLeft
            Direction8.left      -> staffLeft
            Direction8.upLeft    -> staffUpLeft
        }
    }
    
    // beam
    
    val beamVertical1 = load("beamVertical1.png")
    val beamVertical2 = load("beamVertical2.png")
    val beamHorizontal1 = load("beamHorizontal1.png")
    val beamHorizontal2 = load("beamHorizontal2.png")
    val beamDiagonalVertical1 = load("beamDiagonalVertical1.png")
    val beamDiagonalVertical2 = load("beamDiagonalVertical2.png")
    val beamDiagonalHorizontal1 = load("beamDiagonalHorizontal1.png")
    val beamDiagonalHorizontal2 = load("beamDiagonalHorizontal2.png")
    
    // other
    
    val bomb = load("bomb.png")
    val explosive = load("explosive.png")
    val fire = load("fire.png")
    val smoke = load("smoke.png")
    val shuriken = load("shuriken.png")
    
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
