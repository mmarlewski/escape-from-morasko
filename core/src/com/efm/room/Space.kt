package com.efm.room

import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.entity.Entity

/**
 * Space is a part of a Room, corresponds to one tile on game map
 */
class Space(x : Int, y : Int, private var base : Base? = null) : Json.Serializable
{
    val position = RoomPosition(x, y)
    private var entity : Entity? = null
    
    fun getEntity() : Entity?
    {
        return entity
    }
    
    fun setEntity(newEntity : Entity)
    {
        entity = newEntity
    }
    
    fun clearEntity()
    {
        entity = null
    }
    
    fun getBase() : Base?
    {
        return base
    }
    
    fun changeBase(newBase : Base?)
    {
        base = newBase
    }
    
    /**
     * can Entity go through and occupy
     */
    fun isTraversableFor(e : Entity) : Boolean
    {
        return base?.isTreadableFor?.invoke(e) == true && entity == null
    }
    
    /**
     * can Entity go through
     */
    fun isTreadableFor(e : Entity) : Boolean
    {
        return base?.isTreadableFor?.invoke(e) == true
    }
    
    // for serializing
    
    constructor() : this(0, 0, null)
    
    override fun write(json : Json?)
    {
        if (json != null)
        {
            json.writeValue("position", this.position)
            json.writeValue("base", this.base?.name)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        if (json != null)
        {
            val jsonPosition = json.readValue("position", RoomPosition::class.java, jsonData)
            if (jsonPosition != null) this.position.set(jsonPosition)
            
            val jsonBase = json.readValue("base", String::class.java, jsonData)
            if (jsonBase != null)
            {
                try
                {
                    val base = Base.valueOf(jsonBase)
                    this.base = base
                }
                catch (_ :Exception)
                {
                }
            }
        }
    }
}
