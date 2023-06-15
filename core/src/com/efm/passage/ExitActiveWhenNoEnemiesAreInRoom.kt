package com.efm.passage

import com.efm.level.World

interface ExitActiveWhenNoEnemiesAreInRoom : Exit
{
    override fun interact()
    {
        if (!World.currentRoom.areEnemiesInRoom())
        {
            super.interact()
        }
    }
}