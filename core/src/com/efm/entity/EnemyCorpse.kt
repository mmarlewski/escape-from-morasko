package com.efm.entity

import com.efm.item.*
import com.efm.ui.gameScreen.EquipmentStructure

interface EnemyCorpse : Interactive, Container
{
    /** can be empty PossibleItems() **/
    var loot : PossibleItems
    /** items = loot.drawItems() **/
    override val items : MutableList<Item>
    
    override fun interact()
    {
        EquipmentStructure.showHeroAndContainerEquipments(this)
    }
}
