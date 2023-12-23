package com.efm.inventoryTabSlot

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.efm.item.StackableSelfItem

interface InventoryTabSlot
{
    var item : StackableSelfItem
    var selected : Boolean
    var itemButton : ImageButton
    
    fun highlight()
    
    fun undoHighlight()
}