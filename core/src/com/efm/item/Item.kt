package com.efm.item

import com.badlogic.gdx.graphics.Texture

/**
 * Item can be used by Hero in combat and occupies one slot in inventory
 */
interface Item : Cloneable
{
    val name : String
    val baseAPUseCost : Int
    
    fun getTexture() : Texture?
    fun selected()
    fun confirmed()
    
    public override fun clone() : Item = super.clone() as Item
}
