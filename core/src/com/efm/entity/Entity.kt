package com.efm.entity

import com.efm.room.RoomCoordinates

/** Any interactive object that can be found in a Room.
 * @param roomCoordinates Position within a Room.
 */
interface Entity
{
    val roomCoordinates : RoomCoordinates
    fun getCurrentTexture()
}