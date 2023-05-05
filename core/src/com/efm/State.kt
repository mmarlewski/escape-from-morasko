package com.efm

import com.efm.room.RoomPosition
import com.efm.room.Space

interface State

object noPositionSelected : State
{
    var isMovingMode = true
}

object movePositionSelectedOnce : State
{
    val selectedPosition = RoomPosition()
    var pathSpaces : List<Space>? = null
}

object movePositionSelectedTwice : State

var currState : State = noPositionSelected
