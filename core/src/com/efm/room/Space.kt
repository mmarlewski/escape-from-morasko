package com.efm.room

import com.efm.entities.Trap
import com.efm.entity.Character
import com.efm.passage.Exit

/**
 * Fragment of the area of a Room. Corresponds to a tile of the Map.
 * @param position Position within the Room.
 * @param isAccessibleForCharacters True if a Character can step in the Space.
 * @param base Contains surface texture of the area.
 * @param character Character present in the Space.
 * @param trap Trap present in the Space.
 * @param exit Exit present in the Space used to leave the Room.
 */
class Space(
        val position : RoomPosition,
        private var isAccessibleForCharacters : Boolean = true,
        var base : Base = Base.stone,
        character : Character? = null,
        var trap : Trap? = null,
        val exit : Exit? = null
           )
{
    // Ensures that when a Character is present in the Space, other Characters can not access the Space.
    var character = character
        set(value)
        {
            field = value
            if (character != null)
                isAccessibleForCharacters = false
        }
    
    // If the Space is created with a Character present, it is not accessible for other Characters.
    init
    {
        if (character != null)
            isAccessibleForCharacters = false
    }
    
}