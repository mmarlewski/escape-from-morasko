package com.efm

import com.badlogic.gdx.math.Vector2
import com.efm.room.RoomPosition
import kotlin.math.floor

fun positionToOrtho(position : Vector2) : Vector2
{
    val orthoPosition = Vector2()
    orthoPosition.x = position.x * Map.tileLengthHalfInPixels
    orthoPosition.y = (Map.mapHeightInTiles - position.y - 1) * Map.tileLengthHalfInPixels
    return orthoPosition
}

fun orthoToPosition(orthoPosition : Vector2) : Vector2
{
    val roomPosition = Vector2()
    roomPosition.x = floor(orthoPosition.x / Map.tileLengthHalfInPixels)
    roomPosition.y = floor(orthoPosition.y / Map.tileLengthHalfInPixels)
    roomPosition.y = Map.mapHeightInTiles - roomPosition.y - 1
    return roomPosition
}

fun roomPositionToOrtho(roomPosition : RoomPosition) : Vector2
{
    val orthoPosition = Vector2()
    orthoPosition.x = (roomPosition.x * Map.tileLengthHalfInPixels).toFloat()
    orthoPosition.y = ((Map.mapHeightInTiles - roomPosition.y - 1) * Map.tileLengthHalfInPixels).toFloat()
    return orthoPosition
}

fun orthoToRoomPosition(orthoPosition : Vector2) : RoomPosition
{
    val roomPosition = RoomPosition()
    roomPosition.x = floor(orthoPosition.x / Map.tileLengthHalfInPixels).toInt()
    roomPosition.y = floor(orthoPosition.y / Map.tileLengthHalfInPixels).toInt()
    roomPosition.y = Map.mapHeightInTiles - roomPosition.y - 1
    return roomPosition
}

fun orthoToIso(orthoPosition : Vector2) : Vector2
{
    return orthoToIso(orthoPosition.x, orthoPosition.y)
}

fun orthoToIso(orthoX : Float, orthoY : Float) : Vector2
{
    val isoPosition = Vector2()
    isoPosition.x = orthoX + orthoY
    isoPosition.y = 0.5f * (orthoY - orthoX)
    isoPosition.y += Map.tileLengthQuarterInPixels
    return isoPosition
}

fun isoToOrtho(isoPosition : Vector2) : Vector2
{
    return isoToOrtho(isoPosition.x, isoPosition.y)
}

fun isoToOrtho(isoX : Float, isoY : Float) : Vector2
{
    val orthoPosition = Vector2()
    orthoPosition.x = 0.5f * isoX - (isoY - Map.tileLengthQuarterInPixels)
    orthoPosition.y = 0.5f * isoX + (isoY - Map.tileLengthQuarterInPixels)
    return orthoPosition
}
