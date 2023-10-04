package com.efm.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound

object Sounds
{
    val assetManager = AssetManager()
    
    // ui
    val blop = load("blop.mp3")
    val coin = load("coin.mp3")
    val jump = load("jump.mp3")
    
    // multi use map items
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
    
    // wizard
    val wizardMove = load("wizardMove.mp3")
    val wizardAttack = load("wizardAttack.mp3")
    
    // turret
    val turretAttack = load("turretAttack.mp3")

    val dragonMove = load("dragonMove.mp3")
    val dragonAttack = load("dragonAttack.mp3")
    val dragonSittingAttack = load("dragonSittingAttack.mp3")
    
//    val Move = load("Move.mp3")
//    val Attack = load("Attack.mp3")
    
    
    private fun load(name : String) : Sound
    {
        val filePath = "sounds/$name"
        assetManager.load(filePath, Sound::class.java)
        assetManager.finishLoading()
        val sound = assetManager.get(filePath, Sound::class.java)
        return sound
    }
}
