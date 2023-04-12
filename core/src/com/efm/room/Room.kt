package com.efm.room

import com.efm.entity.Enemy
import com.efm.entity.Entity

/**
 * Part of a level. Only one is displayed on screen at any given time.
 * @param spaces Array of Spaces present at any given position within the Room. Used to check if a fragment of the Room at a given position is accessible and to get its texture.
 * @param passages List of Passages leading to other Rooms
 * @param entities List of Entities inside the Room
 * @param enemies List of Enemies inside the Room
 */
class Room(val numberOfRows : Int, val numberOfColumns : Int, val spaces : Array<Array<Space>>)
{
    val passages : List<Passage> = emptyList()
    val entities : List<Entity> = emptyList()
    val enemies : List<Enemy> = emptyList()
    
    init
    {
        if (spaces.size != numberOfRows || spaces.none { it.size != numberOfColumns })
        {
            throw Exception("Number of spaces does not match dimensions of the room.")
        }
    }
}