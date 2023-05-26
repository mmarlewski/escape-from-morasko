package com.efm.state

import com.efm.Animating
import com.efm.inventoryTabSlot.InventoryTabSlot
import com.efm.inventoryTabSlot.InventoryTabStackableSelfItemSlot

fun updateState()
{
    val newState = when (val currState = getState())
    {
        is State.free        -> when (currState)
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
            
            is State.free.stackableSelfItemChosen                -> updateFreeStackableSelfItemChosen(currState)
        }
        
        is State.constrained -> when (currState)
        {
            is State.constrained.noSelection                             -> updateConstrainedNoSelection(currState)
            is State.constrained.nothingSelected                         -> updateConstrainedNothingSelected(currState)
            is State.constrained.entitySelected                          -> updateConstrainedEntitySelected(currState)
            is State.constrained.enemySelected                           -> updateConstrainedEnemySelected(currState)
            is State.constrained.heroSelected                            -> updateConstrainedHeroSelected(currState)
    
            is State.constrained.moveSelectedOnce                        -> updateConstrainedMoveSelectedOnce(currState)
            is State.constrained.moveSelectedTwice                       -> updateConstrainedMoveSelectedTwice(currState)
            is State.constrained.moveSelectedTwiceToLevelExit.waiting    -> updateConstrainedMoveSelectedTwiceToLevelExitWaiting(
                    currState
                                                                                                                                )
            is State.constrained.moveSelectedTwiceToLevelExit.confirmed  -> updateConstrainedMoveSelectedTwiceToLevelExitConfirmed(
                    currState
                                                                                                                                  )
            is State.constrained.moveSelectedTwiceToLevelExit.cancelled  -> updateConstrainedMoveSelectedTwiceToLevelExitCancelled(
                    currState
                                                                                                                                  )
    
            is State.constrained.multiUseMapItemChosen                   -> updateConstrainedMultiUseMapItemChosen(currState)
            is State.constrained.multiUseMapItemTargetSelectedOnce       -> updateConstrainedMultiUseMapItemTargetSelectedOnce(
                    currState
                                                                                                                              )
            is State.constrained.multiUseMapItemTargetSelectedTwice      -> updateConstrainedMultiUseMapItemTargetSelectedTwice(
                    currState
                                                                                                                               )
            
            is State.constrained.stackableMapItemChosen                  -> updateConstrainedStackableMapItemChosen(currState)
            is State.constrained.stackableMapItemTargetSelectedOnce      -> updateConstrainedStackableMapItemTargetSelectedOnce(
                    currState
                                                                                                                               )
            is State.constrained.stackableMapItemTargetSelectedTwice     -> updateConstrainedStackableMapItemTargetSelectedTwice(
                    currState
                                                                                                                                )
            
            is State.constrained.stackableSelfItemChosen                 -> updateConstrainedStackableSelfItemChosen(
                    currState
                                                                                                                    )
            
            is State.constrained.turnEnded                               -> updateConstrainedTurnEnded(currState)
            is State.constrained.turnEndedWithActionPointsLeft.waiting   -> updateConstrainedTurnEndedWithActionPointsLeftWaiting(
                    currState
                                                                                                                                 )
            is State.constrained.turnEndedWithActionPointsLeft.confirmed -> updateConstrainedTurnEndedWithActionPointsLeftConfirmed(
                    currState
                                                                                                                                   )
            is State.constrained.turnEndedWithActionPointsLeft.cancelled -> updateConstrainedTurnEndedWithActionPointsLeftCancelled(
                    currState
                                                                                                                                   )
        }
        else                 -> currState
    }
    setState(newState)
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
                    setState(newState)
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
