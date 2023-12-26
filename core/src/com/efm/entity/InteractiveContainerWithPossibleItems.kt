package com.efm.entity

import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.item.*
import com.efm.state.getState
import com.efm.ui.gameScreen.EquipmentStructure
import kotlin.random.Random

/**
 * Entity that can be interacted with to access the items it contains.
 * Can be supplied with PossibleItems, from which random items can be drawn and added to the Container.
 */
abstract class InteractiveContainerWithPossibleItems(val possibleItems : PossibleItems? = null,
                                                     private var seed : Int = Random.nextInt()) : Container, Interactive
{
    /** Shows Hero's and Container's equipment view. */
    override fun interact()
    {
        // do not open a container in tutorial if lootingPopup was not shown
        if (!(getState().tutorialFlags.tutorialActive && !getState().tutorialFlags.lootingPopupShown))
        {
            getState().tutorialFlags.playerLooted = true
            EquipmentStructure.showHeroAndContainerEquipments(this)
        }
    }
    
    /** Draw random items from possibleItems and add them to the Container. */
    fun drawItems()
    {
        if (possibleItems!=null)
        {
            val drawnItems = possibleItems.drawItems(seed)
            for (item in drawnItems)
            {
                try
                {
                    addItem(item)
                }
                catch (e : ContainerFullException)
                {
                    // filled container
                    break
                }
            }
        }
    }
    
    // for serializing
    
    override fun write(json : Json?)
    {
        super<Container>.write(json)
        super<Interactive>.write(json)
        
        json?.writeValue("seed", this.seed)
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super<Container>.read(json, jsonData)
        super<Interactive>.read(json, jsonData)
        
        if (json != null) this.seed = json.readValue("seed", Int::class.java, jsonData)
    }
}
