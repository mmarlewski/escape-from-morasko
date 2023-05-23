package com.efm.state

import com.efm.Animating
import com.efm.inventoryTabSlot.InventoryTabSlot
import com.efm.inventoryTabSlot.InventoryTabStackableSelfItemSlot

fun updateState()
{
    val newState = when (val currState = getState())
    {
        is State.free.noSelection                            -> updateFreeNoSelection(currState)
        is State.free.nothingSelected                        -> updateFreeNothingSelected(currState)
        is State.free.entitySelected                         -> updateFreeEntitySelected(currState)
        is State.free.heroSelected                           -> updateFreeHeroSelected(currState)
        
        is State.free.moveSelectedOnce                       -> updateFreeMoveSelectedOnce(currState)
        is State.free.moveSelectedTwice                      -> updateFreeMoveSelectedTwice(currState)
        is State.free.moveSelectedTwiceToLevelExit.waiting   -> updateFreeMoveSelectedTwiceToLevelExitWaiting(currState)
        is State.free.moveSelectedTwiceToLevelExit.confirmed -> updateFreeMoveSelectedTwiceToLevelExitConfirmed(currState)
        is State.free.moveSelectedTwiceToLevelExit.cancelled -> updateFreeMoveSelectedTwiceToLevelExitCancelled(currState)
        
        is State.free.multiUseMapItemChosen                  -> updateFreeMultiUseMapItemChosen(currState)
        is State.free.multiUseMapItemTargetSelectedOnce      -> updateFreeMultiUseMapItemTargetSelectedOnce(currState)
        is State.free.multiUseMapItemTargetSelectedTwice     -> updateFreeMultiUseMapItemTargetSelectedTwice(currState)
        
        is State.free.stackableMapItemChosen                 -> updateFreeStackableMapItemChosen(currState)
        is State.free.stackableMapItemTargetSelectedOnce     -> updateFreeStackableMapItemTargetSelectedOnce(currState)
        is State.free.stackableMapItemTargetSelectedTwice    -> updateFreeStackableMapItemTargetSelectedTwice(currState)
        
        is State.free.stackableSelfItemChosen                -> updateStackableSelfItemChosen(currState)
        
        else                                                 -> currState
    }
    changeState(newState)
}

fun updateState(itemSlot : InventoryTabSlot)
{
    if (Animating.isAnimating())
    {
        return
    }
    
    when (itemSlot)
    {
        /*is InventoryTabMultiUseMapItemSlot      ->
        {
            val newState = State.free.multiUseMapItemChosen
            newState.chosenMultiUseItem = itemSlot.item
            val targetPositions = itemSlot.item.getTargetPositions(World.hero.position)
            newState.targetPositions = targetPositions
            changeState(newState)
        }
        is InventoryTabStackableMapItemSlot  ->
        {
            val newState = State.free.stackableMapItemChosen
            newState.chosenStackableMapItem = itemSlot.item
            val targetPositions = itemSlot.item.getTargetPositions(World.hero.position)
            newState.targetPositions = targetPositions
            changeState(newState)
        }*/
        is InventoryTabStackableSelfItemSlot ->
        {
            when (getState())
            {
                is State.free.heroSelected            ->
                {
                    itemSlot.selected = true
                    val newState = State.free.stackableSelfItemChosen
                    newState.chosenStackableSelfItem = itemSlot.item
                    changeState(newState)
                }
                is State.free.stackableSelfItemChosen ->
                {
                    itemSlot.item.use()
                }
                else                                  ->
                {
                }
            }
        }
    }
}
