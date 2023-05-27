package com.efm.room

import com.badlogic.gdx.Gdx
import com.efm.entity.*
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
    private val enemies = mutableListOf<Enemy>()
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
        updateSpaceList()
        updateSpacesEntities()
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
            space?.setEntity(entity)
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
    
    fun getCharacters() : List<Character>
    {
        return characters
    }
    
    fun getEnemies() : List<Enemy>
    {
        return enemies
    }
    
    fun addEntity(entity : Entity)
    {
        // add to list
        entities.add(entity)
        if (entity is Character) characters.add(entity)
        if (entity is Enemy) enemies.add(entity)
        // add to space if space exists
        val space = getSpace(entity.position)
        if (space != null)
        {
            if (space.getEntity() == null) space.setEntity(entity)
            else //throw Exception("Tried to add Entity to Space already occupied by another Entity")
                Gdx.app.log("warning", "Added Entity to Space already occupied by another Entity")
        }
        else Gdx.app.log("warning", "No Space exists in the position of added Entity.")
    }
    
    fun addEntityAt(entity : Entity, x : Int, y : Int)
    {
        entity.setPosition(x, y)
        addEntity(entity)
    }
    
    fun addEntityAt(entity : Entity, position : RoomPosition)
    {
        entity.setPosition(position)
        addEntity(entity)
    }
    
    fun replaceEntityAt(entity : Entity, x : Int, y : Int)
    {
        replaceEntityAt(entity, RoomPosition(x, y))
    }
    
    fun replaceEntityAt(entity : Entity, position : RoomPosition)
    {
        val space = getSpace(position)
        if (space != null)
        {
            val currentEntity = space.getEntity()
            if (currentEntity != null) removeEntity(currentEntity)
        }
        addEntityAt(entity, position)
    }
    
    fun removeEntity(entity : Entity)
    {
        entities.remove(entity)
        getSpace(entity.position)?.clearEntity()
    }
    
    fun areEnemiesInRoom() : Boolean
    {
        enemies.forEach { if (it is Enemy) return true }
        return false
    }
}