package com.efm.room

import com.efm.entity.Entity

/**
 * Part of a level. Only one is displayed on screen at any given time.
 * @param spaces Array of Spaces present at any given position within Room. Used to check if a fragment of the Room at a given position is accessible and to get its texture.
 */
class Room(var numberOfRows : Int, var numberOfColumns : Int, val spaces : Array<Array<Space>>)
{
    val passages : List<Passage> = emptyList()
    val entities : List<Entity> = emptyList()
    
    init
    {
        if (spaces.size != numberOfRows || spaces.none { it.size != numberOfColumns })
        {
            throw Exception("Number of spaces does not match dimentions of the room.")
        }
    }
}