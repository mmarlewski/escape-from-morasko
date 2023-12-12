package com.efm

import com.efm.level.World
import com.efm.ui.gameScreen.ProgressBars

fun increaseHeroStats(hpIncrease : Int, apIncrease : Int)
{
    World.hero.maxHealthPoints += hpIncrease
    World.hero.maxAbilityPoints += apIncrease
    ProgressBars.updateHeroApHpBars()
}