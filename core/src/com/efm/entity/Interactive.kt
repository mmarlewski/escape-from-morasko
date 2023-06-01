package com.efm.entity

import com.badlogic.gdx.maps.tiled.TiledMapTile

/**
 * Entity that the Hero can interact with.
 * Interactive Entity can perform an action after being clicked or after an item is used on it.
 */
interface Interactive : Entity
{
    fun getOutlineTealTile() : TiledMapTile?
    
    fun interact()
}
