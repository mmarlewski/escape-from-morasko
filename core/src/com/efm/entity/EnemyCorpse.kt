package com.efm.entity

import com.efm.item.Container
import com.efm.item.PossibleItems
import com.efm.ui.gameScreen.EquipmentStructure

interface EnemyCorpse : Interactive, Container
{
    var loot : PossibleItems
        get() = PossibleItems()
        set(value)
        {
            loot = value
        }
    
    override fun interact()
    {
        EquipmentStructure.showHeroAndContainerEquipments(this)
    }
}
