// @file:Suppress("unused")

package com.efm.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import com.efm.Direction4
import com.efm.Direction8

object Tiles
{
    val assetManager = AssetManager()
    
    // base
    
    val stoneFloor = load("stoneFloor.png")
    val metalFloor = load("metalFloor.png")
    val rockFloor = load("rockFloor.png")
    val grassFloor = load("grassFloor.png")
    val lavaFloor = load("lavaFloor.png")
    val waterFloor = load("waterFloor.png")
    val waterFloorOctopus = load("waterFloorOctopus.png")
    
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
    
    // hero
    
    val heroIdle1 = load("heroIdle1.png")
    val heroIdle1Invisible = load("heroIdle1Invisible.png")
    val heroVines = load("heroVines.png")
    val heroVinesInvisible = load("heroVinesInvisible.png")
    val heroIdle1OutlineYellow = load("heroIdle1OutlineYellow.png")
    val heroIdle1OutlineGreen = load("heroIdle1OutlineGreen.png")
    val heroMove1 = load("heroMove1.png")
    val heroMove1Invisible = load("heroMove1Invisible.png")
    val heroMove2 = load("heroMove2.png")
    val heroMove2Invisible = load("heroMove2Invisible.png")
    val heroMove3 = load("heroMove3.png")
    val heroMove3Invisible = load("heroMove3Invisible.png")
    val heroMove4 = load("heroMove4.png")
    val heroMove4Invisible = load("heroMove4Invisible.png")
    val heroCorpse = load("heroCorpse.png")
    val heroCorpseOutlineYellow = load("heroCorpseOutlineYellow.png")
    // for `map` tests
    val hero = heroIdle1
    
    // mushroom
    
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
    
    // skeleton
    
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
    
    // bat
    
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
    val batTeeth = load("batTeeth.png")
    
    // boar
    
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
    
    // wizard
    
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
    
    // plant
    
    val plantIdle1 = load("plantIdle1.png")
    val plantIdle1OutlineYellow = load("plantIdle1OutlineYellow.png")
    val plantIdle1OutlineRed = load("plantIdle1OutlineRed.png")
    val plantIdle2 = load("plantIdle2.png")
    val plantIdle2OutlineYellow = load("plantIdle2OutlineYellow.png")
    val plantAttack = load("plantAttack.png")
    val plantCorpse = load("plantCorpse.png")
    val plantCorpseOutlineYellow = load("plantCorpseOutlineYellow.png")
    
    // ghost
    
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
    
    // turret
    
    val turretIdle1 = load("turretIdle1.png")
    val turretIdle1OutlineYellow = load("turretIdle1OutlineYellow.png")
    val turretIdle1OutlineRed = load("turretIdle1OutlineRed.png")
    val turretIdle2 = load("turretIdle2.png")
    val turretIdle2OutlineYellow = load("turretIdle2OutlineYellow.png")
    val turretAttack = load("turretAttack.png")
    val turretProjectile = load("turretProjectile.png")
    val turretCorpse = load("turretCorpse.png")
    val turretCorpseOutlineYellow = load("turretCorpseOutlineYellow.png")
    
    // golem
    
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
    
    // mimic
    
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
    
    // rock
    
    val rockIdle1 = load("rockIdle1.png")
    val rockIdle1OutlineYellow = load("rockIdle1OutlineYellow.png")
    val rockIdle1OutlineRed = load("rockIdle1OutlineRed.png")
    val rockIdle2 = load("rockIdle2.png")
    val rockIdle2OutlineYellow = load("rockIdle2OutlineYellow.png")
    val rockMove1 = load("rockMove1.png")
    val rockMove2 = load("rockMove2.png")
    val rockCorpse = load("rockCorpse.png")
    val rockCorpseOutlineYellow = load("rockCorpseOutlineYellow.png")
    
    // dragon
    
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
    
    val dragonSittingIdle1 = load("dragonSittingIdle1.png")
    val dragonSittingIdle1OutlineYellow = load("dragonSittingIdle1OutlineYellow.png")
    val dragonSittingIdle1OutlineRed = load("dragonSittingIdle1OutlineRed.png")
    val dragonSittingIdle2 = load("dragonSittingIdle2.png")
    val dragonSittingIdle2OutlineYellow = load("dragonSittingIdle2OutlineYellow.png")
    val dragonSittingAttack = load("dragonSittingAttack.png")
    
    // goblin
    
    val goblinEmptyIdle1 = load("goblinEmptyIdle1.png")
    val goblinEmptyIdle1OutlineYellow = load("goblinEmptyIdle1OutlineYellow.png")
    val goblinEmptyIdle1OutlineRed = load("goblinEmptyIdle1OutlineRed.png")
    val goblinEmptyIdle2 = load("goblinEmptyIdle2.png")
    val goblinEmptyIdle2OutlineYellow = load("goblinEmptyIdle2OutlineYellow.png")
    val goblinEmptyMove1 = load("goblinEmptyMove1.png")
    val goblinEmptyMove2 = load("goblinEmptyMove2.png")
    val goblinEmptyAttack = load("goblinEmptyAttack.png")
    val goblinEmptyCorpse = load("goblinEmptyCorpse.png")
    val goblinEmptyCorpseOutlineYellow = load("goblinEmptyCorpseOutlineYellow.png")
    
    val goblinSpearIdle1 = load("goblinSpearIdle1.png")
    val goblinSpearIdle1OutlineYellow = load("goblinSpearIdle1OutlineYellow.png")
    val goblinSpearIdle1OutlineRed = load("goblinSpearIdle1OutlineRed.png")
    val goblinSpearIdle2 = load("goblinSpearIdle2.png")
    val goblinSpearIdle2OutlineYellow = load("goblinSpearIdle2OutlineYellow.png")
    val goblinSpearMove1 = load("goblinSpearMove1.png")
    val goblinSpearMove2 = load("goblinSpearMove2.png")
    val goblinSpearAttack = load("goblinSpearAttack.png")
    val goblinSpearCorpse = load("goblinSpearCorpse.png")
    val goblinSpearCorpseOutlineYellow = load("goblinSpearCorpseOutlineYellow.png")
    
    val goblinBowIdle1 = load("goblinBowIdle1.png")
    val goblinBowIdle1OutlineYellow = load("goblinBowIdle1OutlineYellow.png")
    val goblinBowIdle1OutlineRed = load("goblinBowIdle1OutlineRed.png")
    val goblinBowIdle2 = load("goblinBowIdle2.png")
    val goblinBowIdle2OutlineYellow = load("goblinBowIdle2OutlineYellow.png")
    val goblinBowMove1 = load("goblinBowMove1.png")
    val goblinBowMove2 = load("goblinBowMove2.png")
    val goblinBowAttack = load("goblinBowAttack.png")
    val goblinBowCorpse = load("goblinBowCorpse.png")
    val goblinBowCorpseOutlineYellow = load("goblinBowCorpseOutlineYellow.png")
    
    val goblinBarrelIdle1 = load("goblinBarrelIdle1.png")
    val goblinBarrelIdle1OutlineYellow = load("goblinBarrelIdle1OutlineYellow.png")
    val goblinBarrelIdle1OutlineRed = load("goblinBarrelIdle1OutlineRed.png")
    val goblinBarrelIdle2 = load("goblinBarrelIdle2.png")
    val goblinBarrelIdle2OutlineYellow = load("goblinBarrelIdle2OutlineYellow.png")
    val goblinBarrelMove1 = load("goblinBarrelMove1.png")
    val goblinBarrelMove2 = load("goblinBarrelMove2.png")
    val goblinBarrelAttack = load("goblinBarrelAttack.png")
    
    // chess
    
    val chessPawnWhite = load("chessPawnWhite.png")
    val chessPawnBlack = load("chessPawnBlack.png")
    val chessPawnOutlineYellow = load("chessPawnOutlineYellow.png")
    val chessPawnOutlineRed = load("chessPawnOutlineRed.png")
    
    val chessBishopWhite = load("chessBishopWhite.png")
    val chessBishopBlack = load("chessBishopBlack.png")
    val chessBishopOutlineYellow = load("chessBishopOutlineYellow.png")
    val chessBishopOutlineRed = load("chessBishopOutlineRed.png")
    
    val chessRookWhite = load("chessRookWhite.png")
    val chessRookBlack = load("chessRookBlack.png")
    val chessRookOutlineYellow = load("chessRookOutlineYellow.png")
    val chessRookOutlineRed = load("chessRookOutlineRed.png")
    
    val chessKnightWhite = load("chessKnightWhite.png")
    val chessKnightBlack = load("chessKnightBlack.png")
    val chessKnightOutlineYellow = load("chessKnightOutlineYellow.png")
    val chessKnightOutlineRed = load("chessKnightOutlineRed.png")
    
    val chessQueenWhite = load("chessQueenWhite.png")
    val chessQueenBlack = load("chessQueenBlack.png")
    val chessQueenOutlineYellow = load("chessQueenOutlineYellow.png")
    val chessQueenOutlineRed = load("chessQueenOutlineRed.png")
    
    val chessKingWhite = load("chessKingWhite.png")
    val chessKingBlack = load("chessKingBlack.png")
    val chessKingOutlineYellow = load("chessKingOutlineYellow.png")
    val chessKingOutlineRed = load("chessKingOutlineRed.png")
    
    // octopus
    
    val octopusHeadIdle1 = load("octopusHeadIdle1.png")
    val octopusHeadIdle1OutlineYellow = load("octopusHeadIdle1OutlineYellow.png")
    val octopusHeadIdle1OutlineRed = load("octopusHeadIdle1OutlineRed.png")
    val octopusHeadIdle2 = load("octopusHeadIdle2.png")
    val octopusHeadIdle2OutlineYellow = load("octopusHeadIdle2OutlineYellow.png")
    val octopusHeadCorpse = load("octopusHeadCorpse.png")
    val octopusHeadCorpseOutlineYellow = load("octopusHeadCorpseOutlineYellow.png")
    
    val octopusTentacleIdle1 = load("octopusTentacleIdle1.png")
    val octopusTentacleIdle1OutlineYellow = load("octopusTentacleIdle1OutlineYellow.png")
    val octopusTentacleIdle1OutlineRed = load("octopusTentacleIdle1OutlineRed.png")
    val octopusTentacleIdle2 = load("octopusTentacleIdle2.png")
    val octopusTentacleIdle2OutlineYellow = load("octopusTentacleIdle2OutlineYellow.png")
    val octopusTentacleIdle3 = load("octopusTentacleIdle3.png")
    val octopusTentacleIdle3OutlineYellow = load("octopusTentacleIdle3OutlineYellow.png")
    val octopusTentacleAttack = load("octopusTentacleAttack.png")
    
    // slime
    
    val slimeGreenIdle1 = load("slimeGreenIdle1.png")
    val slimeGreenIdle1OutlineYellow = load("slimeGreenIdle1OutlineYellow.png")
    val slimeGreenIdle1OutlineRed = load("slimeGreenIdle1OutlineRed.png")
    val slimeGreenIdle2 = load("slimeGreenIdle2.png")
    val slimeGreenIdle2OutlineYellow = load("slimeGreenIdle2OutlineYellow.png")
    val slimeGreenAttack = load("slimeGreenAttack.png")
    
    val slimeYellowIdle1 = load("slimeYellowIdle1.png")
    val slimeYellowIdle1OutlineYellow = load("slimeYellowIdle1OutlineYellow.png")
    val slimeYellowIdle1OutlineRed = load("slimeYellowIdle1OutlineRed.png")
    val slimeYellowIdle2 = load("slimeYellowIdle2.png")
    val slimeYellowIdle2OutlineYellow = load("slimeYellowIdle2OutlineYellow.png")
    val slimeYellowAttack = load("slimeYellowAttack.png")
    
    val slimeRedIdle1 = load("slimeRedIdle1.png")
    val slimeRedIdle1OutlineYellow = load("slimeRedIdle1OutlineYellow.png")
    val slimeRedIdle1OutlineRed = load("slimeRedIdle1OutlineRed.png")
    val slimeRedIdle2 = load("slimeRedIdle2.png")
    val slimeRedIdle2OutlineYellow = load("slimeRedIdle2OutlineYellow.png")
    val slimeRedAttack = load("slimeRedAttack.png")
    
    // wizard boss
    
    val wizardEnemy = load("wizardEnemy.png")
    val wizardEnemyOutlineYellow = load("wizardEnemyOutlineYellow.png")
    val wizardEnemyOutlineRed = load("wizardEnemyOutlineRed.png")
    
    // stoneColumn
    
    val stoneColumn = load("stoneColumn.png")
    val stoneColumnOutlineYellow = load("stoneColumnOutlineYellow.png")
    
    // chest
    
    val chest = load("chest.png")
    val chestOutlineYellow = load("chestOutlineYellow.png")
    val chestOutlineTeal = load("chestOutlineTeal.png")
    
    // explodingBarrel
    
    val explodingBarrel = load("explodingBarrel.png")
    val explodingBarrelOutlineYellow = load("explodingBarrelOutlineYellow.png")
    val explodingBarrelExplosion = load("explodingBarrelExplosion.png")
    
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
    
    // fire
    
    val fireUp = load("fireUp.png")
    val fireUpRight = load("fireUpRight.png")
    val fireRight = load("fireRight.png")
    val fireDownRight = load("fireDownRight.png")
    val fireDown = load("fireDown.png")
    val fireDownLeft = load("fireDownLeft.png")
    val fireLeft = load("fireLeft.png")
    val fireUpLeft = load("fireUpLeft.png")
    
    fun getFireTile(direction : Direction8) : StaticTiledMapTile
    {
        return when (direction)
        {
            Direction8.up        -> fireUp
            Direction8.upRight   -> fireUpRight
            Direction8.right     -> fireRight
            Direction8.downRight -> fireDownRight
            Direction8.down      -> fireDown
            Direction8.downLeft  -> fireDownLeft
            Direction8.left      -> fireLeft
            Direction8.upLeft    -> fireUpLeft
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
    val bloodSplatter = load("blood_splatter_32x32.png")
    
    // pull
    
    val tentacleHookUp = load("tentacleHookUp.png")
    val tentacleHookEndUp = load("tentacleHookEndUp.png")
    val tentacleHookRight = load("tentacleHookRight.png")
    val tentacleHookEndRight = load("tentacleHookEndRight.png")
    val tentacleHookDown = load("tentacleHookDown.png")
    val tentacleHookEndDown = load("tentacleHookEndDown.png")
    val tentacleHookLeft = load("tentacleHookLeft.png")
    val tentacleHookEndLeft = load("tentacleHookEndLeft.png")
    
    fun getTentacleHookTile(direction : Direction4) : StaticTiledMapTile
    {
        return when (direction)
        {
            Direction4.up    -> tentacleHookUp
            Direction4.right -> tentacleHookRight
            Direction4.down  -> tentacleHookDown
            Direction4.left  -> tentacleHookLeft
        }
    }
    
    fun getTentacleHookEndTile(direction : Direction4) : StaticTiledMapTile
    {
        return when (direction)
        {
            Direction4.up    -> tentacleHookEndUp
            Direction4.right -> tentacleHookEndRight
            Direction4.down  -> tentacleHookEndDown
            Direction4.left  -> tentacleHookEndLeft
        }
    }
    
    // new walls
    val walls_folder_name = "new_walls/"
    
    // cobblestone
    
    // cobblestoneLightWall
    val cobblestoneLightWallDown = load(walls_folder_name + "cobblestoneLightWallDown.png")
    val cobblestoneLightWallRight = load(walls_folder_name + "cobblestoneLightWallRight.png")
    // cobblestoneDarkWall
    val cobblestoneDarkWallDown = load(walls_folder_name + "cobblestoneDarkWallDown.png")
    val cobblestoneDarkWallRight = load(walls_folder_name + "cobblestoneDarkWallRight.png")
    // cobblestoneLightTallWall
    val cobblestoneLightTallWallDown = load(walls_folder_name + "cobblestoneLightTallWallDown.png")
    val cobblestoneLightTallWallRight = load(walls_folder_name + "cobblestoneLightTallWallRight.png")
    // cobblestoneDarkTallWall
    val cobblestoneDarkTallWallDown = load(walls_folder_name + "cobblestoneDarkTallWallDown.png")
    val cobblestoneDarkTallWallRight = load(walls_folder_name + "cobblestoneDarkTallWallRight.png")
    
    // orange brick
    
    // brickOrangeLightWall
    val brickOrangeLightWallDown = load(walls_folder_name + "brickOrangeLightWallDown.png")
    val brickOrangeLightWallRight = load(walls_folder_name + "brickOrangeLightWallRight.png")
    // brickOrangeDarkWall
    val brickOrangeDarkWallDown = load(walls_folder_name + "brickOrangeDarkWallDown.png")
    val brickOrangeDarkWallRight = load(walls_folder_name + "brickOrangeDarkWallRight.png")
    // brickOrangeLightTallWall
    val brickOrangeLightTallWallDown = load(walls_folder_name + "brickOrangeLightTallWallDown.png")
    val brickOrangeLightTallWallRight = load(walls_folder_name + "brickOrangeLightTallWallRight.png")
    // brickOrangeDarkTallWall
    val brickOrangeDarkTallWallDown = load(walls_folder_name + "brickOrangeDarkTallWallDown.png")
    val brickOrangeDarkTallWallRight = load(walls_folder_name + "brickOrangeDarkTallWallRight.png")
    
    // red brick
    
    // brickRedLightWall
    val brickRedLightWallDown = load(walls_folder_name + "brickRedLightWallDown.png")
    val brickRedLightWallRight = load(walls_folder_name + "brickRedLightWallRight.png")
    // brickRedDarkWall
    val brickRedDarkWallDown = load(walls_folder_name + "brickRedDarkWallDown.png")
    val brickRedDarkWallRight = load(walls_folder_name + "brickRedDarkWallRight.png")
    // brickRedLightTallWall
    val brickRedLightTallWallDown = load(walls_folder_name + "brickRedLightTallWallDown.png")
    val brickRedLightTallWallRight = load(walls_folder_name + "brickRedLightTallWallRight.png")
    // brickRedDarkTallWall
    val brickRedDarkTallWallDown = load(walls_folder_name + "brickRedDarkTallWallDown.png")
    val brickRedDarkTallWallRight = load(walls_folder_name + "brickRedDarkTallWallRight.png")
    
    // grass floor tiles
    val grass_floor_tiles_folder_name = "new_floor_tiles/grass/"
    
    val grassDarkFloor1 = load(grass_floor_tiles_folder_name + "grass_floor_dark_1.png")
    val grassDarkFloor2 = load(grass_floor_tiles_folder_name + "grass_floor_dark_2.png")
    val grassLightFloor1 = load(grass_floor_tiles_folder_name + "grass_floor_light_1.png")
    val grassStoneFloor1 = load(grass_floor_tiles_folder_name + "grass_floor_stone_1.png")
    val grassStoneFloor2 = load(grass_floor_tiles_folder_name + "grass_floor_stone_2.png")
    val grassStoneFloor3 = load(grass_floor_tiles_folder_name + "grass_floor_stone_3.png")
    
    // tiled floor tiles
    val tiled_floor_tiles_folder_name = "new_floor_tiles/tiled/"
    
    val tiledFloor1 = load(tiled_floor_tiles_folder_name + "tiled_floor_1.png")
    val tiledFloor1Blood1 = load(tiled_floor_tiles_folder_name + "tiled_floor_1_blood_1.png")
    val tiledFloor1Blood2 = load(tiled_floor_tiles_folder_name + "tiled_floor_1_blood_2.png")
    val tiledFloor1Blood3 = load(tiled_floor_tiles_folder_name + "tiled_floor_1_blood_3.png")
    val tiledFloor2 = load(tiled_floor_tiles_folder_name + "tiled_floor_2.png")
    val tiledFloor3 = load(tiled_floor_tiles_folder_name + "tiled_floor_3.png")
    
    // wooden floor tiles
    val wooden_floor_tiles_folder_name = "new_floor_tiles/wooden/"
    
    val woodenFloor1 = load(wooden_floor_tiles_folder_name + "wooden_floor_1.png")
    val woodenFloor2 = load(wooden_floor_tiles_folder_name + "wooden_floor_2.png")
    val woodenFloor3 = load(wooden_floor_tiles_folder_name + "wooden_floor_3.png")
    
    // props
    val props_folder_name = "new_props/"
    
    val barrelSmall = load(props_folder_name + "barrel_small.png")
    val barrelSmallDamaged1 = load(props_folder_name + "barrel_small_damaged_1.png")
    val barrelSmallDamaged2 = load(props_folder_name + "barrel_small_damaged_2.png")
    val bookshelfHugeEmpty = load(props_folder_name + "bookshelf_huge_empty.png")
    val cabinetWide = load(props_folder_name + "cabinet_wide.png")
    val chairFront = load(props_folder_name + "chair_front.png")
    val chairSideLeft = load(props_folder_name + "chair_side_left.png")
    val chairSideRight = load(props_folder_name + "chair_side_right.png")
    val crateSmall = load(props_folder_name + "crate_small.png")
    val crateSmallDamaged1 = load(props_folder_name + "crate_small_damaged_1.png")
    val crateSmallDamaged2 = load(props_folder_name + "crate_small_damaged_2.png")
    val potSmall = load(props_folder_name + "pot_small.png")
    val potSmallDamaged1 = load(props_folder_name + "pot_small_damaged_1.png")
    val potSmallFullWater = load(props_folder_name + "pot_small_full_water.png")
    val potSmallFullWine = load(props_folder_name + "pot_small_full_wine.png")
    val stool = load(props_folder_name + "stool.png")
    val tableMedium = load(props_folder_name + "table_medium.png")
    val tableSmall = load(props_folder_name + "table_small.png")
    val vase = load(props_folder_name + "vase.png")
    val vaseDamaged = load(props_folder_name + "vase_damaged.png")
    val vaseFullWater = load(props_folder_name + "vase_full_water.png")
    val vaseFullWine = load(props_folder_name + "vase_full_wine.png")
    
    val barrelSmallOutlineYellow = load(props_folder_name + "barrel_small_outline_yellow.png")
    val bookshelfHugeOutlineYellow = load(props_folder_name + "bookshelf_huge_outline_yellow.png")
    val cabinetWideOutlineYellow = load(props_folder_name + "cabinet_wide_outline_yellow.png")
    val chairFrontOutlineYellow = load(props_folder_name + "chair_front_outline_yellow.png")
    val chairSideLeftOutlineYellow = load(props_folder_name + "chair_side_left_outline_yellow.png")
    val chairSideRightOutlineYellow = load(props_folder_name + "chair_side_right_outline_yellow.png")
    val crateSmallOutlineYellow = load(props_folder_name + "crate_small_outline_yellow.png")
    val potSmallOutlineYellow = load(props_folder_name + "pot_small_outline_yellow.png")
    val stoolOutlineYellow = load(props_folder_name + "stool_outline_yellow.png")
    val tableMediumOutlineYellow = load(props_folder_name + "table_medium_outline_yellow.png")
    val tableSmallOutlineYellow = load(props_folder_name + "table_small_outline_yellow.png")
    val vaseOutlineYellow = load(props_folder_name + "vase_outline_yellow.png")
    
    // composite props
    val props_composite_folder_name = "new_props/new_props_composit/"
    
    val tableMediumWaterWater = load(props_composite_folder_name + "table_medium_water_water.png")
    val tableMediumWaterWine = load(props_composite_folder_name + "table_medium_water_wine.png")
    val tableMediumWineWater = load(props_composite_folder_name + "table_medium_wine_water.png")
    val tableMediumWineWine = load(props_composite_folder_name + "table_medium_wine_wine.png")
    val tableSmallWater = load(props_composite_folder_name + "table_small_water.png")
    val tableSmallWine = load(props_composite_folder_name + "table_small_wine.png")
    
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
