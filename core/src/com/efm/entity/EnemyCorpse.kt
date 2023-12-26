package com.efm.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.item.*
import com.efm.room.RoomPosition
import com.efm.ui.gameScreen.EquipmentStructure
import kotlin.random.Random

/**
 * EnemyCorpse is a Container generated after Enemy death
 */
abstract class EnemyCorpse(
        final override val position : RoomPosition = RoomPosition(),
        loot : PossibleItems? = null,
        seed : Int = Random.nextInt()
                          ) : InteractiveContainerWithPossibleItems(loot,seed), Character
{
    override var maxHealthPoints : Int = 1
    override var healthPoints : Int = 1
    override var alive : Boolean = true
    
    override var maxItems : Int = 0
    
    /**When opened for the first time, draw random items from possibleItems. Size is the number of drawn items plus 2.**/
    override fun interact()
    {
        Gdx.app.log("corpse", "$maxItems")
        if (!opened)
        {
            maxItems = (possibleItems?.items?.size ?: 0) + 2
            drawItems()
            maxItems = items.size + 2
            opened=true
        }
        EquipmentStructure.showHeroAndContainerEquipments(this)
    }
    
    // for serializing
    
    override fun write(json : Json?)
    {
        super<InteractiveContainerWithPossibleItems>.write(json)
        super<Character>.write(json)
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super<InteractiveContainerWithPossibleItems>.read(json, jsonData)
        super<Character>.read(json, jsonData)
    }
}
