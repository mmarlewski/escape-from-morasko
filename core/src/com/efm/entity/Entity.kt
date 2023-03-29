package com.efm.entity

import com.efm.RoomCoordinates

interface Entity
{
    val roomCoordinates : RoomCoordinates
    fun getCurrentTexture()
}