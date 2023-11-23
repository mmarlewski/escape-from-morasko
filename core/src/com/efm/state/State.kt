package com.efm.state

import com.efm.entity.Enemy
import com.efm.entity.Entity
import com.efm.exit.Exit
import com.efm.item.*
import com.efm.room.RoomPosition
import com.efm.room.Space
import com.efm.skill.ActiveSkill

private var prevState : State = State.free.noSelection
private var currState : State = State.free.noSelection

fun getState() : State
{
    return currState
}

fun setState(newState : State)
{
    prevState = currState
    currState = newState
    
    if (prevState != newState)
    {
        println("new state: \n ${currState.javaClass.canonicalName}")
    }
}

sealed class State
{
    var isHeroAlive = true
    var areEnemiesInRoom = false
    
    var tutorialFlags = TutorialFlags
    
    object TutorialFlags
    {
        var tutorialOn = true
        var welcomePopupShown = false
        var cameraPopupShown = false
        //var playerMovedCamera = false
        var movementPopupShown = false
        var playerMoved = false
        var lootingPopupShown = false
        var playerLooted = false
        var equipmentPopupShown = false
    
        fun setDefault()
        {
            tutorialOn = true
            welcomePopupShown = false
            cameraPopupShown = false
            //playerMovedCamera = false
            movementPopupShown = false
            playerMoved = false
            lootingPopupShown = false
            playerLooted = false
            equipmentPopupShown = false
        }
    }
    
    sealed class free : State()
    {
        // select
        
        object noSelection : free()
        
        object nothingSelected : free()
        
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
            var isMoveToAnotherRoom = false
            var isMoveToAnotherLevel = false
        }
        
        object moveSelectedTwice : free()
        {
            var entityOnPosition : Entity? = null
            var pathSpaces : List<Space> = emptyList()
            var isMoveToAnotherRoom = false
            var isMoveToAnotherLevel = false
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
            var targetPositions : List<RoomPosition>? = null
        }
        
        object multiUseMapItemTargetSelectedOnce : free()
        {
            var chosenMultiUseItem : MultiUseMapItem? = null
            var targetPositions : List<RoomPosition>? = null
            var selectedPosition = RoomPosition()
            var effectPositions : List<RoomPosition>? = null
        }
        
        object multiUseMapItemTargetSelectedTwice : free()
        {
            var chosenMultiUseItem : MultiUseMapItem? = null
        }
        
        // StackableMapItem
        
        object stackableMapItemChosen : free()
        {
            var chosenStackableMapItem : StackableMapItem? = null
            var targetPositions : List<RoomPosition>? = null
        }
        
        object stackableMapItemTargetSelectedOnce : free()
        {
            var chosenStackableMapItem : StackableMapItem? = null
            var targetPositions : List<RoomPosition>? = null
            var selectedPosition = RoomPosition()
            var effectPositions : List<RoomPosition>? = null
        }
        
        object stackableMapItemTargetSelectedTwice : free()
        {
            var chosenStackableMapItem : StackableMapItem? = null
        }
        
        // StackableSelfItem
        
        object stackableSelfItemChosen : free()
        {
            var chosenStackableSelfItem : StackableSelfItem? = null
        }
        
        // ActiveSkill
        
        object activeSkillChosen : free()
        {
            var chosenActiveSkill : ActiveSkill? = null
            var targetPositions : List<RoomPosition>? = null
        }
        
        object activeSkillTargetSelectedOnce : free()
        {
            var chosenActiveSkill : ActiveSkill? = null
            var targetPositions : List<RoomPosition>? = null
            var selectedPosition = RoomPosition()
            var effectPositions : List<RoomPosition>? = null
        }
        
        object activeSkillTargetSelectedTwice : free()
        {
            var chosenActiveSkill : ActiveSkill? = null
        }
    }
    
    sealed class constrained : State()
    {
        var isHeroDetected = false
        var areAnyActionPointsLeft = false
        
        // select
        
        object noSelection : constrained()
        
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
            var pathSpaces : List<Space> = emptyList()
            var isMoveToAnotherRoom = false
            var isMoveToAnotherLevel = false
        }
        
        object moveSelectedTwice : constrained()
        {
            var entityOnPosition : Entity? = null
            var pathSpaces : List<Space> = emptyList()
            var isHeroMovingThroughDetectionPosition = false
            var isMoveToAnotherRoom = false
            var isMoveToAnotherLevel = false
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
            var targetPositions : List<RoomPosition>? = null
        }
        
        object multiUseMapItemTargetSelectedOnce : constrained()
        {
            var chosenMultiUseItem : MultiUseMapItem? = null
            var targetPositions : List<RoomPosition>? = null
            var selectedPosition = RoomPosition()
            var effectPositions : List<RoomPosition>? = null
        }
        
        object multiUseMapItemTargetSelectedTwice : constrained()
        {
            var chosenMultiUseItem : MultiUseMapItem? = null
        }
        
        // StackableMapItem
        
        object stackableMapItemChosen : constrained()
        {
            var chosenStackableMapItem : StackableMapItem? = null
            var targetPositions : List<RoomPosition>? = null
        }
        
        object stackableMapItemTargetSelectedOnce : constrained()
        {
            var chosenStackableMapItem : StackableMapItem? = null
            var targetPositions : List<RoomPosition>? = null
            var selectedPosition = RoomPosition()
            var effectPositions : List<RoomPosition>? = null
        }
        
        object stackableMapItemTargetSelectedTwice : constrained()
        {
            var chosenStackableMapItem : StackableMapItem? = null
        }
        
        // StackableSelfItem
        
        object stackableSelfItemChosen : constrained()
        {
            var chosenStackableSelfItem : StackableSelfItem? = null
        }
        
        // ActiveSkill
        
        object activeSkillChosen : constrained()
        {
            var chosenActiveSkill : ActiveSkill? = null
            var targetPositions : List<RoomPosition>? = null
        }
        
        object activeSkillTargetSelectedOnce : constrained()
        {
            var chosenActiveSkill : ActiveSkill? = null
            var targetPositions : List<RoomPosition>? = null
            var selectedPosition = RoomPosition()
            var effectPositions : List<RoomPosition>? = null
        }
        
        object activeSkillTargetSelectedTwice : constrained()
        {
            var chosenActiveSkill : ActiveSkill? = null
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
            var areAnyActionPointsLeft = false
            
            // select
            
            object noSelection : hero()
            
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
                var pathSpaces : List<Space> = emptyList()
                var isMoveToAnotherRoom = false
                var isMoveToAnotherLevel = false
            }
            
            object moveSelectedTwice : hero()
            {
                var entityOnPosition : Entity? = null
                var pathSpaces : List<Space> = emptyList()
                var isMoveToAnotherRoom = false
                var isMoveToAnotherLevel = false
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
                var targetPositions : List<RoomPosition>? = null
            }
            
            object multiUseMapItemTargetSelectedOnce : hero()
            {
                var chosenMultiUseItem : MultiUseMapItem? = null
                var targetPositions : List<RoomPosition>? = null
                var selectedPosition = RoomPosition()
                var effectPositions : List<RoomPosition>? = null
            }
            
            object multiUseMapItemTargetSelectedTwice : hero()
            {
                var chosenMultiUseItem : MultiUseMapItem? = null
            }
            
            // StackableMapItem
            
            object stackableMapItemChosen : hero()
            {
                var chosenStackableMapItem : StackableMapItem? = null
                var targetPositions : List<RoomPosition>? = null
            }
            
            object stackableMapItemTargetSelectedOnce : hero()
            {
                var chosenStackableMapItem : StackableMapItem? = null
                var targetPositions : List<RoomPosition>? = null
                var selectedPosition = RoomPosition()
                var effectPositions : List<RoomPosition>? = null
            }
            
            object stackableMapItemTargetSelectedTwice : hero()
            {
                var chosenStackableMapItem : StackableMapItem? = null
            }
            
            // StackableSelfItem
            
            object stackableSelfItemChosen : hero()
            {
                var chosenStackableSelfItem : StackableSelfItem? = null
            }
            
            // ActiveSkill
            
            object activeSkillChosen : hero()
            {
                var chosenActiveSkill : ActiveSkill? = null
                var targetPositions : List<RoomPosition>? = null
            }
            
            object activeSkillTargetSelectedOnce : hero()
            {
                var chosenActiveSkill : ActiveSkill? = null
                var targetPositions : List<RoomPosition>? = null
                var selectedPosition = RoomPosition()
                var effectPositions : List<RoomPosition>? = null
            }
            
            object activeSkillTargetSelectedTwice : hero()
            {
                var chosenActiveSkill : ActiveSkill? = null
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
        
        sealed class enemies : combat()
        {
            var enemies : List<Enemy>? = null
            var enemyIterator : Iterator<Enemy>? = null
            var currEnemy : Enemy? = null
            
            object enemyUnselected : enemies()
            object enemySelected : enemies()
            object enemyAction : enemies()
        }
    }
    
    object over : State()
}
