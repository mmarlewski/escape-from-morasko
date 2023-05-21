package com.efm.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch

object Textures
{
    val assetManager = AssetManager()
    
    val mainMenuBackground = load("/backgrounds/mainMenuBackground.png")
    
    //buttons and backgrounds
    val up = load("/backgrounds/up.png")
    val upLong = load("/backgrounds/upLong.png")
    val down = load("/backgrounds/down.png")
    val downLong = load("/backgrounds/downLong.png")
    val over = load("/backgrounds/over.png")
    val disabled = load("/backgrounds/disabled.png")
    val focused = load("/backgrounds/focused.png")
    val translucent = load("/backgrounds/translucent.png")
    val translucentQuarter = load("/backgrounds/translucent25percent.png")
    val translucentHalf = load("/backgrounds/translucent50percent.png")
    val translucentThreeQuarters = load("/backgrounds/translucent75percent.png")
    val pauseBackground = load("/backgrounds/pauseBackground.png")
    val upNinePatch = NinePatch(up, 5, 5, 5, 5)
    val upLongNinePatch = NinePatch(upLong, 5, 5, 5, 5)
    val downNinePatch = NinePatch(down, 5, 5, 5, 5)
    val downLongNinePatch = NinePatch(downLong, 5, 5, 5, 5)
    val overNinePatch = NinePatch(over, 5, 5, 5, 5)
    val disabledNinePatch = NinePatch(disabled, 5, 5, 5, 5)
    val focusedNinePatch = NinePatch(focused, 5, 5, 5, 5)
    val translucentNinePatch = NinePatch(translucent, 5, 5, 5, 5)
    val translucentQuarterNinePatch = NinePatch(translucentQuarter, 5, 5, 5, 5)
    val translucentHalfNinePatch = NinePatch(translucentHalf, 5, 5, 5, 5)
    val translucentThreeQuartersNinePatch = NinePatch(translucentThreeQuarters, 5, 5, 5, 5)
    val pauseBackgroundNinePatch = NinePatch(pauseBackground, 5, 5, 5, 5)
    
    //knobs
    val knobBackground = load("/bars/knobBackground.png")
    val knobBefore = load("/bars/knobBefore.png")
    val knobAfter = load("/bars/knobAfter.png")
    val knob = load("/bars/knob.png")
    val knobHealthbarAfter = load("/bars/healthbarForegroundRounded.png")
    val knobAbilitybarAfter = load("/bars/abilityBarForegroundRounded.png")
    val knobBackgroundNinePatch = NinePatch(knobBackground, 3, 3, 3, 3)
    val knobBeforeNinePatch = NinePatch(knobBefore, 5, 5, 5, 5)
    val knobAfterNinePatch = NinePatch(knobAfter, 5, 5, 5, 5)
    val knobNinePatch = NinePatch(knob, 5, 5, 5, 5)
    val knobHealthbarAfterNinePatch = NinePatch(knobHealthbarAfter, 5, 5, 5, 5)
    val knobAbilitybarAfterNinePatch = NinePatch(knobAbilitybarAfter, 5, 5, 5, 5)
    
    //other
    val cursor = load("/other/cursor.png")
    val selection = load("/other/selection.png")
    val cursorNinePatch = NinePatch(cursor, 1, 1, 1, 1)
    val selectionNinePatch = NinePatch(selection, 1, 1, 1, 1)
    
    //checkboxes
    val checkBoxOn = load("/checkboxes/checkBoxOn.png")
    val checkBoxOff = load("/checkboxes/checkBoxOff.png")
    val checkBoxOnOver = load("/checkboxes/checkBoxOnOver.png")
    val checkBoxOffOver = load("/checkboxes/checkBoxOffOver.png")
    val checkBoxOnDisabled = load("/checkboxes/checkBoxOnDisabled.png")
    val checkBoxOffDisabled = load("/checkboxes/checkBoxOffDisabled.png")
    
    //radio buttons
    val radioButtonOn = load("/radioButtons/radioButtonOn.png")
    val radioButtonOff = load("/radioButtons/radioButtonOff.png")
    val radioButtonOnOver = load("/radioButtons/radioButtonOnOver.png")
    val radioButtonOffOver = load("/radioButtons/radioButtonOffOver.png")
    val radioButtonOnDisabled = load("/radioButtons/radioButtonOnDisabled.png")
    val radioButtonOffDisabled = load("/radioButtons/radioButtonOffDisabled.png")
    
    //icons
    val coin = load("/icons/coin.png")
    val settings = load("/icons/settings.png")
    val exit = load("/icons/exit.png")
    val info = load("/icons/info.png")
    val menuList = load("/icons/menuList.png")
    val nextTurn = load("/icons/nextTurn.png")
    val close = load("/icons/close.png")
    val back = load("/icons/goBack.png")
    val arrowLeft = load("/icons/arrowLeft.png")
    val check = load("/icons/check.png")
    val backpack = load("/icons/backpack.png")
    val itemHealing = load("/icons/itemHealing.png")
    val itemSkill = load("/icons/itemSkill.png")
    val itemUsable = load("/icons/itemUsable.png")
    val itemWeapon = load("/icons/itemWeapon.png")
    
    //images
    val title = load("/images/title.png")
    val heroIcon = load("/images/heroIcon.png")
    val freeToMove = load("/images/freeToMove.png")
    val waitingForPlayerTurn = load("/images/waitingForPlayerTurn.png")
    
    //items
    val potion = load("items/potion1.png")
    val sword = load("items/sword1.png")
    val bomb = load("items/bomb1.png")
    
    private fun load(name : String) : Texture
    {
        val filePath = "textures/$name"
        assetManager.load(filePath, Texture::class.java)
        assetManager.finishLoading()
        val texture = assetManager.get(filePath, Texture::class.java)
        return texture
    }
}
