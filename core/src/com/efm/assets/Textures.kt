package com.efm.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch

object Textures
{
    val assetManager = AssetManager()
    
    val up = load("up.png")
    val upLong = load("upLong.png")
    val down = load("down.png")
    val over = load("over.png")
    val disabled = load("disabled.png")
    val focused = load("focused.png")
    val upNinePatch = NinePatch(up, 5, 5, 5, 5)
    val upLongNinePatch = NinePatch(upLong, 5,5,5,5)
    val downNinePatch = NinePatch(down, 5, 5, 5, 5)
    val overNinePatch = NinePatch(over, 5, 5, 5, 5)
    val disabledNinePatch = NinePatch(disabled, 5, 5, 5, 5)
    val focusedNinePatch = NinePatch(focused, 5, 5, 5, 5)
    
    val knobBackground = load("knobBackground.png")
    val knobBefore = load("knobBefore.png")
    val knobAfter = load("knobAfter.png")
    val knob = load("knob.png")
    val knobHealthbarAfter = load("healthbarForeground.png")
    val knobAbilitybarAfter = load("abilityBarForeground.png")
    val knobBackgroundNinePatch = NinePatch(knobBackground, 3, 3, 3, 3)
    val knobBeforeNinePatch = NinePatch(knobBefore, 5, 5, 5, 5)
    val knobAfterNinePatch = NinePatch(knobAfter, 5, 5, 5, 5)
    val knobNinePatch = NinePatch(knob, 5, 5, 5, 5)
    val knobHealthbarAfterNinePatch = NinePatch(knobHealthbarAfter, 5, 5, 5, 5)
    val knobAbilitybarAfterNinePatch = NinePatch(knobAbilitybarAfter, 5, 5, 5, 5)
    
    
    val cursor = load("cursor.png")
    val selection = load("selection.png")
    val cursorNinePatch = NinePatch(cursor, 1, 1, 1, 1)
    val selectionNinePatch = NinePatch(selection, 1, 1, 1, 1)
    
    val checkBoxOn = load("checkBoxOn.png")
    val checkBoxOff = load("checkBoxOff.png")
    val checkBoxOnOver = load("checkBoxOnOver.png")
    val checkBoxOffOver = load("checkBoxOffOver.png")
    val checkBoxOnDisabled = load("checkBoxOnDisabled.png")
    val checkBoxOffDisabled = load("checkBoxOffDisabled.png")
    
    val radioButtonOn = load("radioButtonOn.png")
    val radioButtonOff = load("radioButtonOff.png")
    val radioButtonOnOver = load("radioButtonOnOver.png")
    val radioButtonOffOver = load("radioButtonOffOver.png")
    val radioButtonOnDisabled = load("radioButtonOnDisabled.png")
    val radioButtonOffDisabled = load("radioButtonOffDisabled.png")
    
    val coin = load("coin.png")
    
    val title = load("title.png")
    val settings = load("settings.png")
    val exit = load("exit.png")
    val info = load("info.png")
    val menuList = load("menuList.png")
    val freeToMove = load("freeToMove.png")
    val waitingForPlayerTurn = load("waitingForPlayerTurn.png")
    val nextTurn = load("nextTurn.png")
    val close = load("close.png")
    val back = load("goBack.png")
    val arrowLeft = load("arrowLeft.png")
    val heroIcon = load("heroIcon.png")
    val check = load("check.png")
    
    private fun load(name : String) : Texture
    {
        val filePath = "textures/$name"
        assetManager.load(filePath, Texture::class.java)
        assetManager.finishLoading()
        val texture = assetManager.get(filePath, Texture::class.java)
        return texture
    }
}
