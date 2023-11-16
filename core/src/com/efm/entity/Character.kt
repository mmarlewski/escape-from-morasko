package com.efm.entity

import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue

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
            this.alive = false
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
    
    fun onDeath()
    {
    }
    
    // for serializing
    
    override fun write(json : Json?)
    {
        super.write(json)
        
        if (json != null)
        {
            json.writeValue("maxHealthPoints", this.maxHealthPoints)
            json.writeValue("healthPoints", this.healthPoints)
            json.writeValue("alive", this.alive)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super.read(json, jsonData)
        
        if (json != null)
        {
            val jsonMaxHealthPoints = json.readValue("maxHealthPoints", Int::class.java, jsonData)
            if (jsonMaxHealthPoints != null) this.maxHealthPoints = jsonMaxHealthPoints
            val jsonHealthPoints = json.readValue("healthPoints", Int::class.java, jsonData)
            if (jsonHealthPoints != null) this.healthPoints = jsonHealthPoints
            val jsonAlive = json.readValue("alive", Boolean::class.java, jsonData)
            if (jsonAlive != null) this.alive = jsonAlive
        }
    }
}