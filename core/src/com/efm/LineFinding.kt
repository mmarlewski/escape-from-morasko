package com.efm

import com.badlogic.gdx.math.Vector2
import com.efm.room.*
import kotlin.math.*

object LineFinding
{
    private fun diagonalDistance(start : Vector2, end : Vector2) : Int
    {
        val dx = end.x - start.x
        val dy = end.y - start.y
        return max(abs(dx), abs(dy)).toInt()
    }
    
    private fun lerpPosition(start : Vector2, end : Vector2, t : Float) : Vector2
    {
        return Vector2(round(lerp(start.x, end.x, t)), round(lerp(start.y, end.y, t)))
    }
    
    private fun lerp(start : Float, end : Float, t : Float) : Float
    {
        return start + t * (end - start)
    }
    
    /**
     * Returns line between start and end positions in room.
     * Line is a list of RoomPositions.
     * Line does not include Positions on start and end.
     * If there is no line between start and end returns null.
     */
    fun findLineWithGivenRoom(startPosition : RoomPosition, endPosition : RoomPosition, room : Room) : List<RoomPosition>?
    {
        val startSpace = room.getSpace(startPosition)
        val endSpace = room.getSpace(endPosition)
        val distance = diagonalDistance(startPosition.toVector2(), endPosition.toVector2())
        val linePositions = mutableListOf<RoomPosition>()
        var isLineObstructed = false
        
        if (startSpace != null && endSpace != null && distance > 0)
        {
            for (i in 0..distance)
            {
                val newLinePosition =
                        lerpPosition(startPosition.toVector2(), endPosition.toVector2(), i.toFloat() / distance)
                linePositions.add(newLinePosition.toRoomPosition())
            }
        }
        else
        {
            return null
        }
        
        for (position in linePositions)
        {
            if (position != startPosition && position != endPosition)
            {
                val space = room.getSpace(position)
                if (space != null)
                {
                    val entity = space.getEntity()
                    if (entity != null)
                    {
                        isLineObstructed = true
                        break
                    }
                }
                else
                {
                    isLineObstructed = true
                    break
                }
            }
        }
        
        return if (isLineObstructed) null else linePositions.toList()
    }
}
