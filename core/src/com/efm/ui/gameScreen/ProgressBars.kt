package com.efm.ui.gameScreen

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.assets.*
import com.efm.level.World
import com.efm.screens.GameScreen
import com.efm.state.State
import com.efm.state.getState
import kotlin.math.cos

object ProgressBars
{
    lateinit var healthBar : ProgressBar
    lateinit var healthBarLabel : Label
    lateinit var abilityBar : ProgressBar
    lateinit var abilityBarLabel : Label
    lateinit var abilityBarForFlashing : ProgressBar
    lateinit var flashingProgressBars : MutableList<ProgressBar>
    var flashTimer = 0.0
    var timerChange = 0.03
    
    fun setVisibilty(boolean : Boolean)
    {
        abilityBarForFlashing.isVisible = boolean
        abilityBar.isVisible = boolean
        abilityBarLabel.isVisible = boolean
        healthBar.isVisible = boolean
        healthBarLabel.isVisible = boolean
    }
    
    fun createBar(
            height : Float,
            colorTexture : NinePatch,
            currentHealth : Int,
            maxHealth : Int
                 ) : ProgressBar
    {
        var bar = progressBarOf(
                0f,
                maxHealth.toFloat(),
                1f,
                currentHealth.toFloat(),
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
    
    fun createEnemyHealthBarStack(bar : ProgressBar, width : Float, barLabel : Label) : Stack
    {
        val barWidth = width
        val barStack = Stack()
        val barContainer : Container<ProgressBar> = Container(bar)
        
        val table = Table()
        table.add(barLabel)
        
        barContainer.width(barWidth)
        barStack.add(barContainer)
//        barStack.add(table)
        
        barStack.pack()
        
        return barStack
    }
    
    fun createAbilityStack(bar1 : ProgressBar, bar2 : ProgressBar, width : Float, barLabel : Label) : Stack
    {
        val barWidth = width
        val barStack = Stack()
        val barContainer1 : Container<ProgressBar> = Container(bar1)
        val barContainer2 : Container<ProgressBar> = Container(bar2)
        
        val table = Table()
        table.add(barLabel)
        
        barContainer1.width(barWidth)
        barStack.add(barContainer1)
        barContainer2.width(barWidth)
        barStack.add(barContainer2)
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
        abilityBarForFlashing = createBar(
                24f,
                Textures.knobAbilitybarAfterNinePatch,
                World.hero.abilityPoints,
                World.hero.maxAbilityPoints
                                         )
        abilityBarForFlashing.setColor(8f, 196f, 252f, 1f)
        abilityBarLabel = createLabel(World.hero.abilityPoints, World.hero.maxAbilityPoints)
    }
    
    fun updateHeroApHpBars()
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
        abilityBarForFlashing = createBar(
                24f,
                Textures.knobAbilitybarAfterNinePatch,
                World.hero.abilityPoints,
                World.hero.maxAbilityPoints
                                         )
        abilityBarForFlashing.setColor(8f, 196f, 252f, 1f)
        abilityBarLabel = createLabel(World.hero.abilityPoints, World.hero.maxAbilityPoints)
        display()
    }
    
    fun display()
    {
        
        val healthStack = createStack(healthBar, 376f, healthBarLabel)
        val abilityStack = createAbilityStack(abilityBarForFlashing, abilityBar, 180f, abilityBarLabel)
        val progressBars = columnOf(
                rowOf(healthStack, abilityStack)
                                   ).align(Align.top)
        progressBars.pad(40f)
        progressBars.setFillParent(true)
        GameScreen.stage.addActor(progressBars)
    }
    
    fun flashProgressBar()
    {
        if (flashTimer < 0.07)
        {
            timerChange = 0.03
        }
        if (flashTimer > 1.50)
        {
            timerChange = -0.03
        }
        val newState = when (val currState = getState())
        {
            is State.constrained.moveSelectedTwice       ->
            {
                flashTimer = 0.0
                abilityBar.setColor(8f, 196f, 252f, 1f)
            }
            
            is State.constrained.enemySelected           ->
            {
                flashTimer = 0.0
                abilityBar.setColor(8f, 196f, 252f, 1f)
            }
            
            is State.constrained.entitySelected          ->
            {
                flashTimer = 0.0
                abilityBar.setColor(8f, 196f, 252f, 1f)
            }
            
            is State.constrained.noSelection             ->
            {
                flashTimer = 0.0
                abilityBar.setColor(8f, 196f, 252f, 1f)
            }
            
            is State.constrained.nothingSelected         ->
            {
                flashTimer = 0.0
                abilityBar.setColor(8f, 196f, 252f, 1f)
            }
            
            is State.constrained.stackableSelfItemChosen ->
            {
                flashTimer = 0.0
                abilityBar.setColor(8f, 196f, 252f, 1f)
            }
            
            is State.combat.hero.moveSelectedTwice       ->
            {
                flashTimer = 0.0
                abilityBar.setColor(8f, 196f, 252f, 1f)
            }
            
            is State.combat.hero.enemySelected           ->
            {
                flashTimer = 0.0
                abilityBar.setColor(8f, 196f, 252f, 1f)
            }
            
            is State.combat.hero.entitySelected          ->
            {
                flashTimer = 0.0
                abilityBar.setColor(8f, 196f, 252f, 1f)
            }
            
            is State.combat.hero.noSelection             ->
            {
                flashTimer = 0.0
                abilityBar.setColor(8f, 196f, 252f, 1f)
            }
            
            is State.combat.hero.nothingSelected         ->
            {
                flashTimer = 0.0
                abilityBar.setColor(8f, 196f, 252f, 1f)
            }
            
            is State.combat.hero.stackableSelfItemChosen ->
            {
                flashTimer = 0.0
                abilityBar.setColor(8f, 196f, 252f, 1f)
            }
            
            else                                         ->
            {
                abilityBar.setColor(8f, 196f, 252f, cos(flashTimer).toFloat())
                flashTimer += timerChange
            }
        }
        
    }
    
}