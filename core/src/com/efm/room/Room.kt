package com.efm.room

import com.efm.entity.Character

/**
 * Part of a level. Only one is displayed on screen at any given time.
 * @param spacesArray Array of Spaces present at any given position within the Room. Used to check if a fragment of the Room at a given position is accessible and to get its texture.
 * @param spacesList List of all Spaces in the spacesArray.
 * @param characters List of Characters (Entities with a turn) inside the Room.
 */
class Room(
        val numberOfRows : Int,
        val numberOfColumns : Int,
        val spacesArray : Array<Array<Space>>,
        val spacesList : MutableList<Space> = mutableListOf(),
        val characters : MutableList<Character> = mutableListOf()
          )
{
    init
    {
        if (spacesArray.size != numberOfRows || spacesArray.none { it.size != numberOfColumns })
        {
            throw Exception("Number of spaces does not match dimensions of the room.")
        }
        // Adding Spaces from spacesArray to spacesList.
        for (i in 0 until numberOfRows)
        {
            for (j in 0 until numberOfColumns)
            {
                spacesList.add(spacesArray[i][j])
            }
        }
    }
}