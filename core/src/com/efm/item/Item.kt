package com.efm.item

interface Item
{
    val name : String
    val baseAPUseCost : Int
    
    /** Effects (graphical) occurring when Item is selected  */
    fun selected()
    /** Graphical effects occurring after the use of Item has been confirmed */
    fun confirmed()
}
