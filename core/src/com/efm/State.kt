package com.efm

import com.efm.entity.Enemy
import com.efm.entity.Entity
import com.efm.level.World
import com.efm.passage.Exit
import com.efm.room.RoomPosition
import com.efm.room.Space

private var currState : State = State.free.noSelection

fun getState() : State
{
    return currState
}

fun changeState(newState : State)
{
    currState = newState
}

sealed class State
{
    var isHeroAlive = true
    
    sealed class free : State()
    {
        var isEnemyInRoom = false
        
        // select
        
        object noSelection:free()
        
        object nothingSelected : free()
        {
        }
        
        object entitySelected : free()
        {
            var selectedEntity : Entity? = null
        }
        
        object heroSelected : free()
        
        // move
        
        object moveSelectedOnce : free()
        {
            val selectedPosition = RoomPosition()
            var pathSpaces : List<Space> = emptyList()
        }
        
        object moveSelectedTwice : free()
        {
            var entityOnPosition : Entity? = null
            var pathSpaces : List<Space> = emptyList()
        }
        
        sealed class moveSelectedTwiceToLevelExit : free()
        {
            var selectedLevelExit : Exit? = null
            
            object waiting : moveSelectedTwiceToLevelExit()
            object confirmed : moveSelectedTwiceToLevelExit()
            object cancelled : moveSelectedTwiceToLevelExit()
        }
        
        // MultiUseMapItem
        
        object multiUseMapItemChosen : free()
        {
            var chosenMultiUseItem : MultiUseMapItem? = null
            var targetSpaces : List<Space>? = null
        }
        
        object multiUseMapItemTargetSelectedOnce : free()
        {
            val selectedPosition = RoomPosition()
            var effectSpaces : List<Space>? = null
        }
        
        object multiUseMapItemTargetSelectedTwice : free()
        {
        }
        
        // StackableMapItem
        
        object stackableMapItemChosen : free()
        {
            val chosenStackableMapItem : StackableMapItem? = null
            var targetSpaces : List<Space>? = null
        }
        
        object stackableMapItemTargetSelectedOnce : free()
        {
            val selectedPosition = RoomPosition()
            var effectSpaces : List<Space>? = null
        }
        
        object stackableMapItemTargetSelectedTwice : free()
        {
        }
        
        // StackableSelfItem
        
        object stackableSelfItemChosen : free()
        {
            val chosenStackableSelfItem : StackableSelfItem? = null
        }
    }
    
    sealed class constrained : State()
    {
        var isHeroDetected = false
        var areAnyActionPointsLeft = false
        
        // select
        
        object nothingSelected : constrained()
        
        object entitySelected : constrained()
        {
            var selectedEntity : Entity? = null
        }
        
        object enemySelected : constrained()
        {
            var selectedEnemy : Enemy? = null
        }
        
        object heroSelected : constrained()
        
        // move
        
        object moveSelectedOnce : constrained()
        {
            val selectedPosition = RoomPosition()
            var pathSpaces : List<Space>? = null
        }
        
        object moveSelectedTwice : constrained()
        {
            val selectedPosition = RoomPosition()
            var entityOnPosition : Entity? = null
            var pathSpaces : List<Space>? = null
        }
        
        sealed class moveSelectedTwiceToLevelExit : constrained()
        {
            var selectedLevelExit : Exit? = null
            
            object waiting : moveSelectedTwiceToLevelExit()
            object confirmed : moveSelectedTwiceToLevelExit()
            object cancelled : moveSelectedTwiceToLevelExit()
        }
        
        // MultiUseMapItem
        
        object multiUseMapItemChosen : constrained()
        {
            var chosenMultiUseItem : MultiUseMapItem? = null
            var targetSpaces : List<Space>? = null
        }
        
        object multiUseMapItemTargetSelectedOnce : constrained()
        {
            val selectedPosition = RoomPosition()
            var effectSpaces : List<Space>? = null
        }
        
        object multiUseMapItemTargetSelectedTwice : constrained()
        {
            val selectedPosition = RoomPosition()
        }
        
        // StackableMapItem
        
        object stackableMapItemChosen : constrained()
        {
            val chosenStackableMapItem : StackableMapItem? = null
            var targetSpaces : List<Space>? = null
        }
        
        object stackableMapItemTargetSelectedOnce : constrained()
        {
            val selectedPosition = RoomPosition()
            var effectSpaces : List<Space>? = null
        }
        
        object stackableMapItemTargetSelectedTwice : constrained()
        {
            val selectedPosition = RoomPosition()
        }
        
        // StackableSelfItem
        
        object stackableSelfItemChosen : constrained()
        {
            val chosenStackableSelfItem : StackableSelfItem? = null
        }
        
        // turn
        
        object turnEnded : constrained()
        
        sealed class turnEndedWithActionPointsLeft : constrained()
        {
            object waiting : turnEndedWithActionPointsLeft()
            object confirmed : turnEndedWithActionPointsLeft()
            object cancelled : turnEndedWithActionPointsLeft()
        }
    }
    
    sealed class combat : State()
    {
        sealed class hero : combat()
        {
            var areAnyEnemiesLeft = false
            var areAnyActionPointsLeft = false
            
            // select
            
            object nothingSelected : hero()
            
            object entitySelected : hero()
            {
                var selectedEntity : Entity? = null
            }
            
            object enemySelected : hero()
            {
                var selectedEnemy : Enemy? = null
            }
            
            object heroSelected : hero()
            
            // move
            
            object moveSelectedOnce : hero()
            {
                val selectedPosition = RoomPosition()
                var pathSpaces : List<Space>? = null
            }
            
            object moveSelectedTwice : hero()
            {
                val selectedPosition = RoomPosition()
                var entityOnPosition : Entity? = null
                var pathSpaces : List<Space>? = null
            }
            
            sealed class moveSelectedTwiceToLevelExit : hero()
            {
                var selectedLevelExit : Exit? = null
                
                object waiting : moveSelectedTwiceToLevelExit()
                object confirmed : moveSelectedTwiceToLevelExit()
                object cancelled : moveSelectedTwiceToLevelExit()
            }
            
            // MultiUseMapItem
            
            object multiUseMapItemChosen : hero()
            {
                var chosenMultiUseItem : MultiUseMapItem? = null
                var targetSpaces : List<Space>? = null
            }
            
            object multiUseMapItemTargetSelectedOnce : hero()
            {
                val selectedPosition = RoomPosition()
                var effectSpaces : List<Space>? = null
            }
            
            object multiUseMapItemTargetSelectedTwice : hero()
            {
                val selectedPosition = RoomPosition()
            }
            
            // StackableMapItem
            
            object stackableMapItemChosen : hero()
            {
                val chosenStackableMapItem : StackableMapItem? = null
                var targetSpaces : List<Space>? = null
            }
            
            object stackableMapItemTargetSelectedOnce : hero()
            {
                val selectedPosition = RoomPosition()
                var effectSpaces : List<Space>? = null
            }
            
            object stackableMapItemTargetSelectedTwice : hero()
            {
                val selectedPosition = RoomPosition()
            }
            
            // StackableSelfItem
            
            object stackableSelfItemChosen : hero()
            {
                val chosenStackableSelfItem : StackableSelfItem? = null
            }
            
            // turn
            
            object turnEnded : hero()
            
            sealed class turnEndedWithActionPointsLeft : hero()
            {
                object waiting : turnEndedWithActionPointsLeft()
                object confirmed : turnEndedWithActionPointsLeft()
                object cancelled : turnEndedWithActionPointsLeft()
            }
        }
        
        sealed class enemy : combat()
        {
            var enemies : List<Enemy>? = null
            var enemyIterator : Iterator<Enemy>? = null
            var currEnemy : Enemy? = null
            
            object enemyMove : enemy()
            object enemyAttack : enemy()
        }
    }
    
    object over : State()
}
