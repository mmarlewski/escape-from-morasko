package com.efm.entity

/**
 * Character has its own turn. // old idea
 * Now only Enemy has turns.
 * Character has health and can be killed.
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
    
    fun healCharacter(healAmount : Int)
    {
        this.healthPoints += healAmount
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