package com.efm.entity

import com.efm.item.*
import com.efm.room.RoomPosition
import com.efm.ui.gameScreen.EquipmentStructure

abstract class EnemyCorpse(
        override val position : RoomPosition
                          ) : Interactive, Container, Character
{
    /** can be empty PossibleItems() **/
    abstract val loot : PossibleItems
    /** items = loot.drawItems() **/
    abstract override val items : MutableList<Item>
    
    override var maxHealthPoints : Int = 1
    override var healthPoints : Int = 1
    override var alive : Boolean = true
    
    override fun interact()
    {
        EquipmentStructure.showHeroAndContainerEquipments(this)
    }
}
