package com.efm.inventoryTabSlot

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.efm.assets.Sounds
import com.efm.item.StackableSelfItem
import com.efm.itemButtonWithLabel
import com.efm.playSoundOnce
import com.efm.state.updateState

/** ItemSlot in an InventoryTab for StackableSelfItem. Consists of an Item and a button.
 * @property item Item in the ItemSlot.
 * @property selected Is the Item selected for use (itemButton clicked once).
 * @property itemButton Button in an InventoryTab with the Item's icon.
 */
class InventoryTabStackableSelfItemSlot(
        override var item : StackableSelfItem,
        image : Texture,
        up : NinePatch,
        down : NinePatch,
        over : NinePatch,
        disabled : NinePatch,
        focused : NinePatch
                                       ) : InventoryTabSlot
{
    override var selected = false
    override var itemButton : ImageButton = itemButtonWithLabel(
            image, item.amount.toString(), up, down, over, disabled, focused
                                                               ) {
        playSoundOnce(Sounds.ui_1)
        updateState(this)
    }
    
    override fun highlight()
    {
    }
    
    override fun undoHighlight()
    {
    }
}