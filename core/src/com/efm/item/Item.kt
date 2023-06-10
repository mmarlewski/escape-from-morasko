package com.efm.item

import com.badlogic.gdx.graphics.Texture

interface Item
{
    val name : String
    val baseAPUseCost : Int
    
    fun getTexture() : Texture?
    
    /** Effects (graphical) occurring when Item is selected  */
    fun selected()
    /** Graphical effects occurring after the use of Item has been confirmed */
    fun confirmed()
}
