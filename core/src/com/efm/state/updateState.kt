package com.efm.state

import com.efm.*
import com.efm.inventoryTabSlot.InventoryTabSlot
import com.efm.inventoryTabSlot.InventoryTabStackableSelfItemSlot
import com.efm.screens.GameScreen
import com.efm.level.World
import com.efm.screens.GameOverScreen

/**
 * updates current State and sets it to new one if necessary
 */
fun updateState()
{
    val currState = getState()
    
    // handle Hero death on every State except State.combat.enemies
    val newState = if (currState !is State.combat.enemies && !World.hero.alive)
    {
        changeScreen(GameOverScreen)
        State.over
    }
    else when (currState)
    {
        is State.free           -> when (currState)
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
            
            is State.free.activeSkillChosen                      -> updateFreeActiveSkillChosen(currState)
            is State.free.activeSkillTargetSelectedOnce          -> updateFreeActiveSkillTargetSelectedOnce(currState)
            is State.free.activeSkillTargetSelectedTwice         -> updateFreeActiveSkillTargetSelectedTwice(currState)
        }
        
        is State.constrained    -> when (currState)
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
            
            is State.constrained.activeSkillChosen                       -> updateConstrainedActiveSkillChosen(currState)
            is State.constrained.activeSkillTargetSelectedOnce           -> updateConstrainedActiveSkillTargetSelectedOnce(
                    currState
                                                                                                                          )
            
            is State.constrained.activeSkillTargetSelectedTwice          -> updateConstrainedActiveSkillTargetSelectedTwice(
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
        
        is State.combat.hero    -> when (currState)
        {
            is State.combat.hero.noSelection                             -> updateCombatHeroNoSelection(currState)
            is State.combat.hero.nothingSelected                         -> updateCombatHeroNothingSelected(currState)
            is State.combat.hero.entitySelected                          -> updateCombatHeroEntitySelected(currState)
            is State.combat.hero.enemySelected                           -> updateCombatHeroEnemySelected(currState)
            is State.combat.hero.heroSelected                            -> updateCombatHeroHeroSelected(currState)
            
            is State.combat.hero.moveSelectedOnce                        -> updateCombatHeroMoveSelectedOnce(currState)
            is State.combat.hero.moveSelectedTwice                       -> updateCombatHeroMoveSelectedTwice(currState)
            is State.combat.hero.moveSelectedTwiceToLevelExit.waiting    -> updateCombatHeroMoveSelectedTwiceToLevelExitWaiting(
                    currState
                                                                                                                               )
            
            is State.combat.hero.moveSelectedTwiceToLevelExit.confirmed  -> updateCombatHeroMoveSelectedTwiceToLevelExitConfirmed(
                    currState
                                                                                                                                 )
            
            is State.combat.hero.moveSelectedTwiceToLevelExit.cancelled  -> updateCombatHeroMoveSelectedTwiceToLevelExitCancelled(
                    currState
                                                                                                                                 )
            
            is State.combat.hero.multiUseMapItemChosen                   -> updateCombatHeroMultiUseMapItemChosen(currState)
            is State.combat.hero.multiUseMapItemTargetSelectedOnce       -> updateCombatHeroMultiUseMapItemTargetSelectedOnce(
                    currState
                                                                                                                             )
            
            is State.combat.hero.multiUseMapItemTargetSelectedTwice      -> updateCombatHeroMultiUseMapItemTargetSelectedTwice(
                    currState
                                                                                                                              )
            
            is State.combat.hero.stackableMapItemChosen                  -> updateCombatHeroStackableMapItemChosen(currState)
            is State.combat.hero.stackableMapItemTargetSelectedOnce      -> updateCombatHeroStackableMapItemTargetSelectedOnce(
                    currState
                                                                                                                              )
            
            is State.combat.hero.stackableMapItemTargetSelectedTwice     -> updateCombatHeroStackableMapItemTargetSelectedTwice(
                    currState
                                                                                                                               )
            
            is State.combat.hero.stackableSelfItemChosen                 -> updateCombatHeroStackableSelfItemChosen(
                    currState
                                                                                                                   )
            
            is State.combat.hero.activeSkillChosen                       -> updateCombatHeroActiveSkillChosen(currState)
            is State.combat.hero.activeSkillTargetSelectedOnce           -> updateCombatHeroActiveSkillTargetSelectedOnce(
                    currState
                                                                                                                         )
            
            is State.combat.hero.activeSkillTargetSelectedTwice          -> updateCombatHeroActiveSkillTargetSelectedTwice(
                    currState
                                                                                                                          )
            
            is State.combat.hero.turnEnded                               -> updateCombatHeroTurnEnded(currState)
            is State.combat.hero.turnEndedWithActionPointsLeft.waiting   -> updateCombatHeroTurnEndedWithActionPointsLeftWaiting(
                    currState
                                                                                                                                )
            
            is State.combat.hero.turnEndedWithActionPointsLeft.confirmed -> updateCombatHeroTurnEndedWithActionPointsLeftConfirmed(
                    currState
                                                                                                                                  )
            
            is State.combat.hero.turnEndedWithActionPointsLeft.cancelled -> updateCombatHeroTurnEndedWithActionPointsLeftCancelled(
                    currState
                                                                                                                                  )
        }
        
        is State.combat.enemies -> when (currState)
        {
            is State.combat.enemies.enemyUnselected -> updateCombatEnemiesEnemyUnselected(currState)
            is State.combat.enemies.enemySelected   -> updateCombatEnemiesEnemySelected(currState)
            is State.combat.enemies.enemyAction     -> updateCombatEnemiesEnemyAction(currState)
        }
    
        else                    -> currState
    }
    
    setState(newState)
    
    // zmiana stanu mogaca zmienic wyglad exitow
    if (!(currState is State.free && newState is State.free) && !(currState is State.constrained && newState is State.constrained) && !(currState is State.combat && newState is State.combat)) GameScreen.updateMapEntityLayer()
    
    
    if ((currState is State.combat.enemies && newState !is State.combat.enemies) || (newState != currState && newState in listOf(
                    
                    State.free.moveSelectedTwice,
                    State.free.multiUseMapItemTargetSelectedTwice,
                    State.free.stackableMapItemTargetSelectedTwice,
                    State.free.stackableSelfItemChosen,
                    State.free.activeSkillTargetSelectedTwice,
                    
                    State.constrained.moveSelectedTwice,
                    State.constrained.multiUseMapItemTargetSelectedTwice,
                    State.constrained.stackableMapItemTargetSelectedTwice,
                    State.constrained.stackableSelfItemChosen,
                    State.constrained.activeSkillTargetSelectedTwice,
                    
                    State.combat.hero.moveSelectedTwice,
                    State.combat.hero.multiUseMapItemTargetSelectedTwice,
                    State.combat.hero.stackableMapItemTargetSelectedTwice,
                    State.combat.hero.stackableSelfItemChosen,
                    State.combat.hero.activeSkillTargetSelectedTwice
                                                           )
                    )
    )
    {
        saveGame()
    }
    
    if (newState is State.over)
    {
        deleteSave()
    }
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
