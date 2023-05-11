package com.efm.entity

/**
 * Character has its own turn.
 */
interface Character : Entity
{
    var maxHealthPoints : Int
    var healthPoints : Int
    var alive : Boolean
    
    fun damageCharacter(dmgAmount : Int)
    {
        this.healthPoints -= dmgAmount
        if (this.healthPoints <= 0)
        {
            killCharacter()
        }
    }
    
    fun healCharacter(dmgAmount : Int)
    {
        this.healthPoints += dmgAmount
        if (this.healthPoints > this.maxHealthPoints)
        {
            this.healthPoints = this.maxHealthPoints
        }
    }
    
    fun killCharacter()
    {
        this.alive = false
    }
}