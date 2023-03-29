package com.efm.room

import com.efm.entity.Entity

class Room(var numberOfRows : Int, var numberOfColumns : Int, val spaces : Array<Array<Space>>)
{
    val passages : List<Passage> = emptyList()
    val entities : List<Entity> = emptyList()
    
    init
    {
        if (spaces.size != numberOfRows || spaces.none { it.size != numberOfColumns })
        {
            throw Exception("Number of spaces does not match dimentions of the room. (Liczba przestrzeni nie odpowiada wymiarom pokoju.)")
        }
    }
}