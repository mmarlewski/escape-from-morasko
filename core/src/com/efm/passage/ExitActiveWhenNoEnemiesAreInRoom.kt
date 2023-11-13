package com.efm.passage

import com.efm.Direction4
import com.efm.entity.Entity
import com.efm.level.World

open class ExitActiveWhenNoEnemiesAreInRoom(dir : Direction4, pass : Passage?) : Exit(dir, pass)
{
    override fun interact()
    {
        if (!World.currentRoom.areEnemiesInRoom())
        {
            super.interact()
        }
    }
    
    // for serialization
    constructor() : this(Direction4.up, null)
}