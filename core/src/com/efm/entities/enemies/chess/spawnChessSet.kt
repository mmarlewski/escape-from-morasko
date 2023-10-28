package com.efm.entities.enemies.chess

import com.efm.Direction4
import com.efm.room.Room

fun spawnChessSet(kingPosX : Int, kingPosY : Int, direction : Direction4, room : Room)
{
    if (checkIfAreaIsAvailable(kingPosX, kingPosY, direction, room))
    {
        placeEnemies(kingPosX, kingPosY, direction, room)
    }
}

fun checkIfAreaIsAvailable(posX : Int, posY : Int, direction : Direction4, room : Room) : Boolean
{
    var xLowerBound = 0
    var xUpperBound = 0
    var yLowerBound = 0
    var yUpperBound = 0
    when (direction)
    {
        Direction4.up ->
        {
            xLowerBound = -3
            xUpperBound = 4
            yLowerBound = -1
            yUpperBound = 0
        }
        Direction4.down ->
        {
            xLowerBound = -4
            xUpperBound = 3
            yLowerBound = 0
            yUpperBound = 1
        }
        Direction4.left ->
        {
            xLowerBound = -1
            xUpperBound = 0
            yLowerBound = -4
            yUpperBound = 3
        }
        Direction4.right ->
        {
            xLowerBound = 0
            xUpperBound = 1
            yLowerBound = -3
            yUpperBound = 4
        }
    }
   
    for (x in xLowerBound..xUpperBound)
    {
        for (y in yLowerBound..yUpperBound)
        {
            var currentX = posX + x
            var currentY = posY + y
            if (room.isPositionWithinBounds(currentX, currentY))
            {
                var currentSpace = room.getSpace(currentX, currentY)
                if (currentSpace != null) {
                    if (currentSpace.getEntity() != null) {
                        return false
                    }
                }
            } else
            {
                continue
            }
            
        }
    }
    return true
}

fun placeEnemies(kingPosX : Int, kingPosY : Int, direction : Direction4, room : Room)
{
    when (direction) {
        Direction4.up ->
        {
            room.addEntityAt(King(), kingPosX, kingPosY)
            //queen
            room.addEntityAt(Queen(), kingPosX + 1, kingPosY)
            //bishops
            room.addEntityAt(Bishop(), kingPosX + 2, kingPosY)
            room.addEntityAt(Bishop(), kingPosX - 1, kingPosY)
            //knights
            room.addEntityAt(Knight(), kingPosX + 3, kingPosY)
            room.addEntityAt(Knight(), kingPosX - 2, kingPosY)
            //rooks
            room.addEntityAt(Rook(), kingPosX + 4, kingPosY)
            room.addEntityAt(Rook(), kingPosX - 3, kingPosY)
            //pawns
            for (i in -3..4)
            {
                val pawn = Pawn()
                pawn.setChessPieceDirection(direction)
                room.addEntityAt(pawn, kingPosX + i, kingPosY - 1)
            }
        }
        Direction4.down ->
        {
            room.addEntityAt(King(), kingPosX, kingPosY)
            //queen
            room.addEntityAt(Queen(), kingPosX - 1, kingPosY)
            //bishops
            room.addEntityAt(Bishop(), kingPosX - 2, kingPosY)
            room.addEntityAt(Bishop(), kingPosX + 1, kingPosY)
            //knights
            room.addEntityAt(Knight(), kingPosX - 3, kingPosY)
            room.addEntityAt(Knight(), kingPosX + 2, kingPosY)
            //rooks
            room.addEntityAt(Rook(), kingPosX - 4, kingPosY)
            room.addEntityAt(Rook(), kingPosX + 3, kingPosY)
            //pawns
            for (i in -4..3)
            {
                val pawn = Pawn()
                pawn.setChessPieceDirection(direction)
                room.addEntityAt(pawn, kingPosX + i, kingPosY + 1)
            }
        }
        Direction4.left ->
        {
            room.addEntityAt(King(), kingPosX, kingPosY)
            //queen
            room.addEntityAt(Queen(), kingPosX, kingPosY - 1)
            //bishops
            room.addEntityAt(Bishop(), kingPosX, kingPosY + 1)
            room.addEntityAt(Bishop(), kingPosX, kingPosY - 2)
            //knights
            room.addEntityAt(Knight(), kingPosX, kingPosY + 2)
            room.addEntityAt(Knight(), kingPosX, kingPosY - 3)
            //rooks
            room.addEntityAt(Rook(), kingPosX, kingPosY + 3)
            room.addEntityAt(Rook(), kingPosX, kingPosY - 4)
            //pawns
            for (i in -4..3)
            {
                val pawn = Pawn()
                pawn.setChessPieceDirection(direction)
                room.addEntityAt(pawn, kingPosX - 1, kingPosY + i)
            }
        }
        Direction4.right ->
        {
            room.addEntityAt(King(), kingPosX, kingPosY)
            //queen
            room.addEntityAt(Queen(), kingPosX, kingPosY + 1)
            //bishops
            room.addEntityAt(Bishop(), kingPosX, kingPosY - 1)
            room.addEntityAt(Bishop(), kingPosX, kingPosY + 2)
            //knights
            room.addEntityAt(Knight(), kingPosX, kingPosY - 2)
            room.addEntityAt(Knight(), kingPosX, kingPosY + 3)
            //rooks
            room.addEntityAt(Rook(), kingPosX, kingPosY - 3)
            room.addEntityAt(Rook(), kingPosX, kingPosY + 4)
            //pawns
            for (i in -3..4)
            {
                val pawn = Pawn()
                pawn.setChessPieceDirection(direction)
                room.addEntityAt(pawn, kingPosX + 1, kingPosY + i)
            }
        }
    }
}