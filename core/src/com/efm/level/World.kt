package com.efm.level

import com.efm.entities.Hero
import com.efm.room.Room

class World(
        val levels : MutableList<Level>,
        val startingLevel : Level,
        var currentLevel : Level,
        var currentRoom : Room,
        var hero : Hero
           )