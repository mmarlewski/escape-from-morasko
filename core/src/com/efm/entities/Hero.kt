package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Character
import com.efm.state.getState
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

/**
 * Hero has its own turn and is controlled by the player.
 */
class Hero(
        override var maxHealthPoints : Int = 100,
        override var healthPoints : Int = 100,
        override var alive : Boolean = true
          ) : Character
{
    override val position = RoomPosition()
    
    var maxAbilityPoints : Int = 14
    var abilityPoints : Int = 14
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.hero
    }
    
    override fun getOutlineYellowTile() : TiledMapTile
    {
        return Tiles.heroOutlineYellow
    }
    
    fun getOutlineGreenTile() : TiledMapTile
    {
        return Tiles.heroOutlineGreen
    }
    
    override fun damageCharacter(dmgAmount : Int)
    {
        super.damageCharacter(dmgAmount)
        GameScreen.healthBar.value = this.healthPoints.toFloat()
        GameScreen.healthBarLabel.setText("${this.healthPoints} / ${this.maxHealthPoints}")
    }
    
    override fun healCharacter(healAmount : Int)
    {
        if (this.healthPoints + healAmount > this.maxHealthPoints)
        {
            this.healthPoints = this.maxHealthPoints
        }
        else
        {
            this.healthPoints += healAmount
        }
        GameScreen.healthBar.value = this.healthPoints.toFloat()
        GameScreen.healthBarLabel.setText("${this.healthPoints} / ${this.maxHealthPoints}")
    }
    
    fun spendAP(apCost : Int)
    {
        if ((this.abilityPoints - apCost) < 0)
        {
            this.abilityPoints = 0
        }
        else
        {
            this.abilityPoints -= apCost
        }
        GameScreen.abilityBar.value -= apCost
        GameScreen.abilityBarLabel.setText("$abilityPoints / $maxAbilityPoints")
    }
    
    fun gainAP(by : Int)
    {
        if (this.abilityPoints + by > this.maxAbilityPoints)
        {
            this.abilityPoints = this.maxAbilityPoints
        }
        else
        {
            this.abilityPoints += by
        }
        GameScreen.abilityBar.value = this.abilityPoints.toFloat()
        GameScreen.abilityBarLabel.setText("${this.abilityPoints} / ${this.maxAbilityPoints}")
    }
    
    fun regainAllAP()
    {
        this.abilityPoints = maxAbilityPoints
        GameScreen.abilityBar.value = maxAbilityPoints.toFloat()
        GameScreen.abilityBarLabel.setText("$abilityPoints / $maxAbilityPoints")
    }
    
    override fun killCharacter()
    {
        super.killCharacter()
        getState().isHeroAlive = false
    }
}
