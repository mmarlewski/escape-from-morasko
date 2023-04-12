package com.efm.entity

import com.badlogic.gdx.graphics.Texture
import com.efm.room.RoomCoordinates

/** Any interactive object that can be found in a Room.
 * @param roomCoordinates Position within a Room.
 */
interface Entity
{
    val roomCoordinates : RoomCoordinates
    val texture : Texture
    fun getCurrentTexture()
}