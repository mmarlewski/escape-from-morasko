package com.efm.entity

import com.badlogic.gdx.maps.tiled.TiledMapTile

/**
 * Interactive Entity can perform an action after being clicked on
 */
interface Interactive : Entity
{
    fun getOutlineTealTile() : TiledMapTile?
    
    /**
     * what happens after Hero moves next to and uses Interactive Entity
     */
    fun interact()
}
