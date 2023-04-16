package com.efm.room

import com.efm.entity.Character
import com.efm.entity.Entity
import com.efm.passage.Passage

/**
 * Part of a level. Only one is displayed on screen at any given time.
 * @property spaceArray Array of Spaces present at any given position within the Room. Used to check if a fragment of the Room at a given position is accessible and to get its texture.
 * @property spaceList List of all Spaces in the spacesArray.
 * @property characters List of Characters (Entities with a turn) inside the Room.
 */
class Room(val name : String, val heightInSpaces : Int, val widthInSpaces : Int)
{
    fun isPositionWithinBounds(x : Int, y : Int) : Boolean
    {
        return x in 0 until widthInSpaces && y in 0 until heightInSpaces
    }
    
    private var spaceArray = arrayOf<Array<Space?>>()
    private val spaceList = mutableListOf<Space>()
    
    private val entities = mutableListOf<Entity>()
    private val characters = mutableListOf<Character>()
    private val passages = mutableListOf<Passage>()
    
    init
    {
        for (i in 0 until heightInSpaces)
        {
            var spaceRow = arrayOf<Space?>()
            
            for (j in 0 until widthInSpaces)
            {
                spaceRow += Space(j, i)
            }
            
            spaceArray += spaceRow
        }
    }
    
    fun deleteSpaceAt(x : Int, y : Int)
    {
        if (isPositionWithinBounds(x, y))
        {
            spaceArray[y][x] = null
        }
    }
    
    fun updateSpaceList()
    {
        spaceList.clear()
        
        for (spaceRow in spaceArray)
        {
            for (space in spaceRow)
            {
                if (space != null)
                {
                    spaceList.add(space)
                }
            }
        }
    }
    
    fun updateSpacesEntities()
    {
        for (space in spaceList)
        {
            space.clearEntity()
        }
        
        for (entity in entities)
        {
            val space = getSpace(entity.position)
            space?.changeEntity(entity)
        }
    }
    
    fun getSpace(x : Int, y : Int) : Space?
    {
        return if (isPositionWithinBounds(x, y))
        {
            spaceArray[y][x]
        }
        else
        {
            null
        }
    }
    
    fun getSpace(position : RoomPosition) : Space?
    {
        return getSpace(position.x, position.y)
    }
    
    fun changeBaseAt(newBase : Base?, x : Int, y : Int)
    {
        val space = getSpace(x, y)
        space?.changeBase(newBase)
    }
    
    fun changeBaseAt(newBase : Base?, position : RoomPosition)
    {
        val space = getSpace(position)
        space?.changeBase(newBase)
    }
    
    fun getEntities() : List<Entity>
    {
        return entities
    }
    
    fun addEntity(entity : Entity)
    {
        entities.add(entity)
    }
    
    fun addEntityAt(entity : Entity, x : Int, y : Int)
    {
        entity.changePosition(x, y)
        addEntity(entity)
    }
    
    fun addEntityAt(entity : Entity, position : RoomPosition)
    {
        entity.changePosition(position)
        addEntity(entity)
    }
    
    fun removeEntity(entity : Entity)
    {
        entities.remove(entity)
    }
}
