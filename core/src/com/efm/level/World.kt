package com.efm.level

/**
 * A sequence of Levels.
 * @param levels List of Levels in the World. First level in list is a starting level. Each level leads to a level next in list?
 */
class World(val name : String, val levels : List<Level>)