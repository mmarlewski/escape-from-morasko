package com.efm.inventoryTabSlot

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.efm.item.StackableSelfItem

/** ItemSlot in an InventoryTab for StackableSelfItem. Consists of an Item and a button.
 * @property item Item in the ItemSlot.
 * @property selected Is the Item selected for use (itemButton clicked once).
 * @property itemButton Button in an InventoryTab with the Item's icon.
 */
interface InventoryTabSlot
{
    var item : StackableSelfItem
    var selected : Boolean
    var itemButton : ImageButton
    
    fun highlight()
    
    fun undoHighlight()
}