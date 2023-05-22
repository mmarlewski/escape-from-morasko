package com.efm.item


/**
 * Bombs etc
 */
interface StackableMapItem : Item
{
    val maxAmount : Int
    var amount : Int
}