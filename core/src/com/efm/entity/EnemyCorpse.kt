package com.efm.entity

import com.efm.entities.PossibleItems
import com.efm.item.Container
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
