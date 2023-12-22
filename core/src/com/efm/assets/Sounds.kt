package com.efm.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound

object Sounds
{
    val assetManager = AssetManager()
    
    // ui
    
    val ui_1 = load("ui_1.mp3")
    val ui_2 = load("ui_2.mp3")
    val ui_3 = load("ui_3.mp3")
    val ui_4 = load("ui_4.mp3")
    val ui_5 = load("ui_5.mp3")
    
    // multi use map items
    
    val punch = load("punch.mp3")
    val woodenSword = load("woodenSword.mp3")
    val metalSword = load("metalSword.mp3")
    val beam = load("beam.mp3")
    val axe = load("axe.mp3")
    val hammer = load("hammer.mp3")
    
    // stackable map items
    
    val bomb = load("bomb.mp3")
    val explosive = load("explosive.mp3")
    val bowShot = load("bowShot.mp3")
    val bowImpact = load("bowImpact.mp3")
    val shurikenShot = load("shurikenShot.mp3")
    val shurikenImpact = load("shurikenImpact.mp3")
    
    // enemies
    
    val mushroomMove = load("mushroomMove.mp3")
    val mushroomAttack = load("mushroomAttack.mp3")
    
    val skeletonMove = load("skeletonMove.mp3")
    
    val batMove = load("batMove.mp3")
    val batAttack = load("batAttack.mp3")
    
    val boarMove = load("boarMove.mp3")
    val boarAttack = load("boarAttack.mp3")
    
    val wizardMove = load("wizardMove.mp3")
    val wizardAttack = load("wizardAttack.mp3")
    
//    val plantAttack = load("plantAttack.mp3")
    
    val ghostAppear = load("ghostAppear.mp3")
    val ghostDisappear = load("ghostDisappear.mp3")
    val ghostTimer = load("ghostTimer.mp3")
    val ghostDie = load("ghostDie.mp3")
    
    val turretAttack= load("turretAttack.mp3")
    
    val golemMove = load("golemMove.mp3")
    val golemAttack = load("golemAttack.mp3")
    
    val mimicMove = load("mimicMove.mp3")
    val mimicAttack = load("mimicAttack.mp3")
    val mimicAppear = load("mimicAppear.mp3")
    
    val rockMove = load("rockMove.mp3")
    
    val dragonMove = load("dragonMove.mp3")
    val dragonAttack = load("dragonAttack.mp3")
    val dragonSittingAttack = load("dragonSittingAttack.mp3")
    
    val barrelDrop = load("barrelDrop.mp3")
    val goblinAttack = load("goblinAttack.mp3")
    val goblinMove = load("goblinMove.mp3")
    
    val chessMove = load("chessMove.mp3")
    val chessAttack = load("chessAttack.mp3")
    
    val octopusTentacleAttack = load("octopusTentacleAttack.mp3")
    val octopusHeadEmerge = load("octopusHeadEmerge.mp3")
    val octopusHeadSubmerge = load("octopusHeadSubmerge.mp3")
    
    val slimeMove = load("slimeMove.mp3")
    val slimeAttack = load("slimeAttack.mp3")
    
    
    private fun load(name : String) : Sound
    {
        val filePath = "sounds/$name"
        assetManager.load(filePath, Sound::class.java)
        assetManager.finishLoading()
        val sound = assetManager.get(filePath, Sound::class.java)
        return sound
    }
}
