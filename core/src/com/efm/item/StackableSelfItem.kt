package com.efm.item

interface StackableSelfItem : Item
{
    val maxAmount : Int
    var amount : Int
}
