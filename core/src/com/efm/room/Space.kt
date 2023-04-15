package com.efm.room

import com.efm.entity.Entity

/**
 * Fragment of a Room's area, corresponds to Map's tile
 */
class Space(x : Int, y : Int)
{
    private val position = RoomPosition(x, y)
    private var entity : Entity? = null
    private var base : Base? = null
    
    fun getEntity() : Entity?
    {
        return entity
    }
    
    fun changeEntity(newEntity : Entity)
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
    
    fun changeBase(newBase : Base)
    {
        base = newBase
    }
}
