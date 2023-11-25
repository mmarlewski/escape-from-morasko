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
    val translucentThreeQuartersBlack = load("/backgrounds/translucent75percentBlack.png")
    val pauseBackground = load("/backgrounds/pauseBackground.png")
    val pauseBackgroundDarkGrey = load("/backgrounds/pauseBackgroundDarkGrey.png")
    val pauseBackgroundWhite = load("/backgrounds/pauseBackgroundWhite.png")
    val pauseBackgroundBlack = load("/backgrounds/pauseBackgroundBlack.png")
    
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
    val pauseBackgroundDarkGreyNinePatch = NinePatch(pauseBackgroundDarkGrey, 5, 5, 5, 5)
    val pauseBackgroundWhiteNinePatch = NinePatch(pauseBackgroundWhite, 5, 5, 5, 5)
    val pauseBackgroundBlackNinePatch = NinePatch(pauseBackgroundBlack, 5, 5, 5, 5)
    
    //knobs
    val knobBackground = load("/bars/knobBackground.png")
    val knobBefore = load("/bars/knobBefore.png")
    val knobAfter = load("/bars/knobAfter.png")
    val knob = load("/bars/knob.png")
    val knobHealthbarAfter = load("/bars/healthbarForeground.png")
    val knobAbilitybarAfter = load("/bars/abilityBarForeground.png")
    val knobEnemyHealthbar = load("bars/healthbarRed.png")
    val materialKnob = load("bars/materialKnob.png")
    val materialKnobAfter = load("bars/materialKnobAfter.png")
    val materialKnobBeforeBlack = load("bars/materialKnobBeforeBlack.png")
    val materialKnobBeforeBlue = load("bars/materialKnobBeforeBlue.png")
    val knobWhite = load("bars/whiteBar.png")
    
    val knobBackgroundNinePatch = NinePatch(knobBackground, 3, 3, 3, 3)
    val knobBeforeNinePatch = NinePatch(knobBefore, 5, 5, 5, 5)
    val knobAfterNinePatch = NinePatch(knobAfter, 5, 5, 5, 5)
    val knobNinePatch = NinePatch(knob, 5, 5, 5, 5)
    val knobHealthbarAfterNinePatch = NinePatch(knobHealthbarAfter, 5, 5, 5, 5)
    val knobAbilitybarAfterNinePatch = NinePatch(knobAbilitybarAfter, 0, 0, 0, 0)
    val materialKnobNinePatch = NinePatch(materialKnob, 5, 5, 5, 5)
    val materialKnobNinePatchBeforeBlack = NinePatch(materialKnobBeforeBlack, 5, 5, 5, 5)
    val materialKnobNinePatchBeforeBlue = NinePatch(materialKnobBeforeBlue, 5, 5, 5, 5)
    val materialKnobNinePatchAfter = NinePatch(materialKnobAfter, 5, 5, 5, 5)
    val knobWhiteNinePatch = NinePatch(knobWhite, 5, 5, 5, 5)
    val knobEnemyHealthBarNinePatch = NinePatch(knobEnemyHealthbar, 5, 5, 5, 5)
    
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
    val materialCheckboxOn = load("/checkboxes/materialCheckboxOn.png")
    val materialCheckboxOff = load("/checkboxes/materialCheckboxOff.png")
    
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
    val arrowRight = load("/icons/arrowRight.png")
    val check = load("/icons/check.png")
    val backpack = load("/icons/backpack.png")
    val itemHealing = load("/icons/itemHealing.png")
    val itemSkill = load("/icons/itemSkill.png")
    val itemUsable = load("/icons/itemUsable.png")
    val itemWeapon = load("/icons/itemWeapon.png")
    val delete = load("/icons/delete.png")
    val move = load("/icons/move.png")
    
    //images
    val title = load("/images/title.png")
    val heroIcon = load("/images/heroIcon.png")
    val freeToMove = load("/images/freeToMove.png")
    val waitingForPlayerTurn = load("/images/waitingForPlayerTurn.png")
    val translucent1px = load("images/translucent1px.png")
    val settingsTitle = load("images/settings.png")
    val appInfo = load("images/appInfo.png")
    val creditsTitle = load("images/credits.png")
    
    // multiUseMapItems
    val sword = load("items/sword1.png")
    val hammer = load("items/hammer2.png")
    val axe = load("items/axeDoubleBit2.png")
    val staff = load("items/staff1.png")
    val bow = load("items/bow1.png")
    
    // stackableMapItems
    val bomb = load("items/bomb2.png")
    val explosive = load("items/explosive2.png")
    val shuriken = load("items/shuriken2.png")
    
    // stackableSelfItems
    val apple = load("items/apple.png")
    val fish = load("items/fish1.png")
    val mushroom = load("items/mushroom2.png")
    val potionSmall = load("items/potion1.png")
    val potionBig = load("items/potion1.png")
    
    // skills
    
    val barrel = load("abilities/barrel.png")
    val freeze = load("abilities/freeze.png")
    val grassHealing = load("abilities/grassHealing.png")
    val invisibility = load("abilities/invisibility.png")
    val jump = load("abilities/jump.png")
    val lavawalking = load("abilities/lavawalking.png")
    val pockets = load("abilities/pockets.png")
    val pull = load("abilities/pull.png")
    val push = load("abilities/push.png")
    val shield = load("abilities/shield.png")
    val swap = load("abilities/swapPlaces.png")
    val waterwalking = load("abilities/waterwalking.png")
    
    // skill icons
    
    val skillHead = load("icons/skill_head.png")
    val skillArmLeft = load("icons/skill_left_arm.png")
    val skillArmRight = load("icons/skill_right_arm.png")
    val skillLegLeft = load("icons/skill_left_leg.png")
    val skillLegRight = load("icons/skill_right_leg.png")
    val skillTorso = load("icons/skill_torso.png")
    
    private fun load(name : String) : Texture
    {
        val filePath = "textures/$name"
        assetManager.load(filePath, Texture::class.java)
        assetManager.finishLoading()
        val texture = assetManager.get(filePath, Texture::class.java)
        return texture
    }
}
