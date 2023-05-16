package com.efm.item

interface Item
{
    val name : String
    var baseAPUseCost : Int
    
    fun use()
    fun selected()
    fun confirmed()
}