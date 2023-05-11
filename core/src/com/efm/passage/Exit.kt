package com.efm.passage

import com.efm.Direction
import com.efm.entity.Entity
import com.efm.room.Room

/**
 * Interactive object found in a Room used to leave it. Created based on a Passage.
 */
interface Exit : Entity
{
    val exitPassage : Passage
    val currentRoom : Room
    val direction : Direction
}
