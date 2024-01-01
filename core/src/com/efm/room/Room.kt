package com.efm.room

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.Map
import com.efm.MapLayer
import com.efm.entities.Hero
import com.efm.entities.bosses.slime.BossSlimeQuarter
import com.efm.entities.walls.Wall
import com.efm.entity.*

/**
 * Room is a part of a Level. It is Displayed in full on game map.
 * Contains Spaces, Entities, Characters and Enemies that occupy Room
 */
class Room(var name : String, var heightInSpaces : Int, var widthInSpaces : Int) : Json.Serializable
{
    fun isPositionWithinBounds(x : Int, y : Int) : Boolean
    {
        return x in 0 until widthInSpaces && y in 0 until heightInSpaces
    }
    
    fun isPositionWithinBounds(pos : RoomPosition) : Boolean
    {
        return pos.x in 0 until widthInSpaces && pos.y in 0 until heightInSpaces
    }
    
    private var spaceArray = arrayOf<Array<Space?>>()
    private val spaceList = mutableListOf<Space>()
    
    private val entities = mutableListOf<Entity>()
    private val characters = mutableListOf<Character>()
    private val enemies = mutableListOf<Enemy>()
    
    private val entitiesToBeAdded = mutableListOf<Entity>()
    
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
    
    fun getSpaces() : List<Space>
    {
        return spaceList
    }
    
    fun deleteSpaceAt(x : Int, y : Int)
    {
        if (isPositionWithinBounds(x, y))
        {
            spaceList.remove(spaceArray[y][x])
            spaceArray[y][x] = null
        }
    }
    
    fun addSpaceAt(x : Int, y : Int, space : Space = Space(x, y))
    {
        if (isPositionWithinBounds(x, y))
        {
            spaceArray[y][x] = space
            spaceList.add(space)
        }
    }
    
    /**
     * re-creates Space list from Space array
     */
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
    
    /**
     * assigns every Entity to corresponding Space according to Entity position
     */
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
    
    /**
     * remove killed characters or replace them with corpses *
     */
    fun removeKilledCharacters()
    {
        val killedCharacters = mutableListOf<Character>()
        val corpsesToAdd = mutableListOf<EnemyCorpse>()
        for (character in characters)
        {
            if (!character.alive)
            {
                character.onDeath()
                killedCharacters.add(character)
                if (character is Enemy)
                {
                    character.healthBar.remove()
                    val corpse = character.getCorpse()
                    if (corpse != null) corpsesToAdd.add(corpse)
                    Map.changeTile(MapLayer.outline, character.position, null)
                }
            }
        }
        enemies.removeAll(killedCharacters)
        characters.removeAll(killedCharacters)
        entities.removeAll(killedCharacters)
        for (corpse in corpsesToAdd) addEntityAt(corpse, corpse.position)
        val howManyKilledCharactersAreQuarterSlime = howManyKilledCharactersAreQuarterSlime(killedCharacters)
        val getAllQuarterSlimeCharacterInRoom = getAllQuarterSlimeCharacterInRoom()
        if (howManyKilledCharactersAreQuarterSlime > 0 && getAllQuarterSlimeCharacterInRoom == 0 && enemies.isEmpty())
        {
            triggerQuarterSlimeOnDeath(killedCharacters)
        }
    }
    
    private fun triggerQuarterSlimeOnDeath(killedCharacters : MutableList<Character>)
    {
        for (enemy in killedCharacters)
        {
            if (enemy is BossSlimeQuarter)
            {
                enemy.finalBossSlimeQuartersKilled()
                return
            }
        }
    }
    
    private fun howManyKilledCharactersAreQuarterSlime(corpsesToAdd : MutableList<Character>) : Int
    {
        var killedQuarterSlimes = 0
        for (enemy in corpsesToAdd)
        {
            if (enemy is BossSlimeQuarter)
            {
                killedQuarterSlimes += 1
            }
        }
        return killedQuarterSlimes
    }
    
    private fun getAllQuarterSlimeCharacterInRoom() : Int
    {
        var enemiesThatAreSlimeQuarter = 0
        for (enemy in enemies)
        {
            if (enemy is BossSlimeQuarter)
            {
                enemiesThatAreSlimeQuarter += 1
            }
        }
        return enemiesThatAreSlimeQuarter
    }
    
    /**
     * adding entities to room can mess things up, so it happens in its own time
     */
    fun addEntityToBeAddedEntities(entity : Entity)
    {
        entitiesToBeAdded.add(entity)
    }
    
    /**
     * adding entities to room can mess things up, so it happens in its own time *
     */
    fun addToBeAddedEntitiesToRoom()
    {
        for (entityToBeAdded in entitiesToBeAdded)
        {
            var isEntityAlreadyInPosition = false
            for (entityAlreadyInRoom in entities)
            {
                if (entityAlreadyInRoom.position == entityToBeAdded.position)
                {
                    isEntityAlreadyInPosition = true
                }
            }
            if (!isEntityAlreadyInPosition)
            {
                addEntity(entityToBeAdded)
            }
        }
        entitiesToBeAdded.clear()
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
        return entities.toList()
    }
    
    fun getCharacters() : List<Character>
    {
        return characters.toList()
    }
    
    fun getEnemies() : List<Enemy>
    {
        return enemies.toList()
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
        if (entity is Character) characters.remove(entity)
        if (entity is Enemy) enemies.remove(entity)
        getSpace(entity.position)?.clearEntity()
    }
    
    fun areEnemiesInRoom() : Boolean
    {
        enemies.forEach { if (it is Enemy) return true }
        return false
    }
    
    private fun surroundingSpaces(space : Space) : MutableList<Space>
    {
        val surroundingSpaces = mutableListOf<Space>()
        space.position.surroundingPositions(1).forEach {
            val surroundingSpace = this.getSpace(it)
            if (surroundingSpace != null) surroundingSpaces.add(surroundingSpace)
        }
        return surroundingSpaces
    }
    
    fun spaceIsAdjacentToWall(space : Space) = surroundingSpaces(space).any { it.getEntity() is Wall }
    
    fun edgePositions() : List<RoomPosition>
    {
        // edges
        val upEdge = 0
        val downEdge = this.heightInSpaces
        val leftEdge = 0
        val rightEdge = this.widthInSpaces
        // find wall positions
        val edgePositions = mutableListOf<RoomPosition>()
        for (y in upEdge until downEdge)
        {
            // left edge
            for (x in leftEdge until rightEdge)
            {
                if (this.getSpace(x, y) != null && this.getSpace(x, y)?.getEntity() !is Wall)
                {
                    val pos = RoomPosition(x, y)
                    if (!edgePositions.contains(pos)) edgePositions.add(pos)
                    break
                }
            }
            // right edge
            for (x in rightEdge - 1 downTo leftEdge)
            {
                if (this.getSpace(x, y) != null && this.getSpace(x, y)?.getEntity() !is Wall)
                {
                    val pos = RoomPosition(x, y)
                    if (!edgePositions.contains(pos)) edgePositions.add(pos)
                    break
                }
            }
        }
        for (x in leftEdge until rightEdge)
        {
            // up edge
            for (y in upEdge until downEdge)
            {
                if (this.getSpace(x, y) != null && this.getSpace(x, y)?.getEntity() !is Wall)
                {
                    val pos = RoomPosition(x, y)
                    if (!edgePositions.contains(pos)) edgePositions.add(pos)
                    break
                }
            }
            // down edge
            for (y in downEdge - 1 downTo upEdge)
            {
                if (this.getSpace(x, y) != null && this.getSpace(x, y)?.getEntity() !is Wall)
                {
                    val pos = RoomPosition(x, y)
                    if (!edgePositions.contains(pos)) edgePositions.add(pos)
                    break
                }
            }
        }
        return edgePositions
    }
    
    fun edgeSpaces() : List<Space>
    {
        val edgeSpaces = mutableListOf<Space>()
        this.edgePositions().forEach {
            val edgeSpace = this.getSpace(it)
            if (edgeSpace != null) edgeSpaces.add(edgeSpace)
        }
        return edgeSpaces
    }
    
    // for serializing
    
    constructor() : this("", 0, 0)
    
    override fun write(json : Json?)
    {
        if (json != null)
        {
            json.writeValue("name", this.name)
            json.writeValue("heightInSpaces", this.heightInSpaces)
            json.writeValue("widthInSpaces", this.widthInSpaces)
            json.writeValue("spaceList", this.spaceList)
            json.writeValue("entities", this.entities)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        if (json != null)
        {
            val jsonName = json.readValue("name", String::class.java, jsonData)
            if (jsonName != null) this.name = jsonName
            
            val jsonHeightInSpaces = json.readValue("heightInSpaces", Int::class.java, jsonData)
            if (jsonHeightInSpaces != null) this.heightInSpaces = jsonHeightInSpaces
            
            val jsonWidthInSpaces = json.readValue("widthInSpaces", Int::class.java, jsonData)
            if (jsonWidthInSpaces != null) this.widthInSpaces = jsonWidthInSpaces
            
            for (i in 0 until this.heightInSpaces)
            {
                var spaceRow = arrayOf<Space?>()
                
                for (j in 0 until this.widthInSpaces)
                {
                    spaceRow += Space(j, i)
                }
                
                this.spaceArray += spaceRow
            }
            
            val jsonSpaceList = json.readValue("spaceList", List::class.java, jsonData)
            if (jsonSpaceList != null)
            {
                for (jsonSpace in jsonSpaceList)
                {
                    if (jsonSpace is Space)
                    {
                        this.spaceList.add(jsonSpace)
                        this.spaceArray[jsonSpace.position.y][jsonSpace.position.x] = jsonSpace
                    }
                }
            }
            
            val jsonEntities = json.readValue("entities", List::class.java, jsonData)
            if (jsonEntities != null)
            {
                for (jsonEntity in jsonEntities)
                {
                    if (jsonEntity !is Hero)
                    {
                        if (jsonEntity is Entity)
                        {
                            this.entities.add(jsonEntity)
                        }
                        if (jsonEntity is Character)
                        {
                            this.characters.add(jsonEntity)
                        }
                        if (jsonEntity is Enemy)
                        {
                            this.enemies.add(jsonEntity)
                        }
                    }
                }
            }
            
            updateSpaceList()
            updateSpacesEntities()
        }
    }
}