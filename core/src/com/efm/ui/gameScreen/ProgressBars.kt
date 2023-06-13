package com.efm.ui.gameScreen

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.assets.*
import com.efm.level.World
import com.efm.screens.GameScreen

object ProgressBars
{
    lateinit var healthBar : ProgressBar
    lateinit var healthBarLabel : Label
    lateinit var abilityBar : ProgressBar
    lateinit var abilityBarLabel : Label
    
    fun createBar(
            height : Float,
            colorTexture : NinePatch,
            currentHealth : Int,
            maxHealth : Int
                 ) : ProgressBar
    {
        var bar = progressBarOf(
                0f,
                currentHealth.toFloat(),
                1f,
                maxHealth.toFloat(),
                Textures.knobBackgroundNinePatch,
                colorTexture,
                Textures.translucentNinePatch,
                height
                               )
        return bar
        
    }
    
    fun createLabel(currentHealth : Int, maxHealth : Int) : Label
    {
        var barLabel = labelOf("$currentHealth / $maxHealth", Fonts.pixeloid20, Colors.black, Textures.translucentNinePatch)
        return barLabel
    }
    
    fun createStack(bar : ProgressBar, width : Float, barLabel : Label) : Stack
    {
        val barWidth = width
        val barStack = Stack()
        val barContainer : Container<ProgressBar> = Container(bar)
    
        val table = Table()
        table.add(barLabel)
    
        barContainer.width(barWidth)
        barStack.add(barContainer)
        barStack.add(table)
    
        return barStack
    }
    
    init
    {
        healthBar = createBar(
                24f,
                Textures.knobHealthbarAfterNinePatch,
                World.hero.healthPoints,
                World.hero.maxHealthPoints
                             )
        healthBarLabel = createLabel(World.hero.healthPoints, World.hero.maxHealthPoints)
        
        abilityBar = createBar(
                24f,
                Textures.knobAbilitybarAfterNinePatch,
                World.hero.abilityPoints,
                World.hero.maxAbilityPoints
                              )
        abilityBarLabel = createLabel(World.hero.abilityPoints, World.hero.maxAbilityPoints)
    }
    
    fun display()
    {
        
        val healthStack = createStack(healthBar, 376f, healthBarLabel)
        val abilityStack = createStack(abilityBar, 180f, abilityBarLabel)
        
        val progressBars = columnOf(
                rowOf(healthStack, abilityStack)
                                   ).align(Align.top)
        progressBars.pad(40f)
        progressBars.setFillParent(true)
        GameScreen.stage.addActor(progressBars)
    }
}