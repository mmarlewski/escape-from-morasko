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
    val king = King()
    king.setChessPieceDirection(direction)
    val queen = Queen()
    queen.setChessPieceDirection(direction)
    val bishop1 = Bishop()
    bishop1.setChessPieceDirection(direction)
    val bishop2 = Bishop()
    bishop2.setChessPieceDirection(direction)
    val knight1 = Knight()
    knight1.setChessPieceDirection(direction)
    val knight2 = Knight()
    knight2.setChessPieceDirection(direction)
    val rook1 = Rook()
    rook1.setChessPieceDirection(direction)
    val rook2 = Rook()
    rook2.setChessPieceDirection(direction)
    when (direction) {
        Direction4.up ->
        {
            //knights
            room.addEntityAt(knight1, kingPosX + 3, kingPosY)
            room.addEntityAt(knight2, kingPosX - 2, kingPosY)
            //pawns
            for (i in -3..4)
            {
                val pawn = Pawn()
                pawn.setChessPieceDirection(direction)
                room.addEntityAt(pawn, kingPosX + i, kingPosY - 1)
            }
            room.addEntityAt(king, kingPosX, kingPosY)
            //queen
            room.addEntityAt(queen, kingPosX + 1, kingPosY)
            //bishops
            room.addEntityAt(bishop1, kingPosX + 2, kingPosY)
            room.addEntityAt(bishop2, kingPosX - 1, kingPosY)
            //rooks
            room.addEntityAt(rook1, kingPosX + 4, kingPosY)
            room.addEntityAt(rook2, kingPosX - 3, kingPosY)
        }
        Direction4.down ->
        {
            //knights
            room.addEntityAt(knight1, kingPosX - 3, kingPosY)
            room.addEntityAt(knight2, kingPosX + 2, kingPosY)
            //pawns
            for (i in -4..3)
            {
                val pawn = Pawn()
                pawn.setChessPieceDirection(direction)
                room.addEntityAt(pawn, kingPosX + i, kingPosY + 1)
            }
            room.addEntityAt(king, kingPosX, kingPosY)
            //queen
            room.addEntityAt(queen, kingPosX - 1, kingPosY)
            //bishops
            room.addEntityAt(bishop1, kingPosX - 2, kingPosY)
            room.addEntityAt(bishop2, kingPosX + 1, kingPosY)
            //rooks
            room.addEntityAt(rook1, kingPosX - 4, kingPosY)
            room.addEntityAt(rook2, kingPosX + 3, kingPosY)
        }
        Direction4.left ->
        {
            //knights
            room.addEntityAt(knight1, kingPosX, kingPosY + 2)
            room.addEntityAt(knight2, kingPosX, kingPosY - 3)
            //pawns
            for (i in -4..3)
            {
                val pawn = Pawn()
                pawn.setChessPieceDirection(direction)
                room.addEntityAt(pawn, kingPosX - 1, kingPosY + i)
            }
            room.addEntityAt(king, kingPosX, kingPosY)
            //queen
            room.addEntityAt(queen, kingPosX, kingPosY - 1)
            //bishops
            room.addEntityAt(bishop1, kingPosX, kingPosY + 1)
            room.addEntityAt(bishop2, kingPosX, kingPosY - 2)
            //rooks
            room.addEntityAt(rook1, kingPosX, kingPosY + 3)
            room.addEntityAt(rook2, kingPosX, kingPosY - 4)
        }
        Direction4.right ->
        {
            //knights
            room.addEntityAt(knight1, kingPosX, kingPosY - 2)
            room.addEntityAt(knight2, kingPosX, kingPosY + 3)
            //pawns
            for (i in -3..4)
            {
                val pawn = Pawn()
                pawn.setChessPieceDirection(direction)
                room.addEntityAt(pawn, kingPosX + 1, kingPosY + i)
            }
            room.addEntityAt(king, kingPosX, kingPosY)
            //queen
            room.addEntityAt(queen, kingPosX, kingPosY + 1)
            //bishops
            room.addEntityAt(bishop1, kingPosX, kingPosY - 1)
            room.addEntityAt(bishop2, kingPosX, kingPosY + 2)
            //rooks
            room.addEntityAt(rook1, kingPosX, kingPosY - 3)
            room.addEntityAt(rook2, kingPosX, kingPosY + 4)
        }
    }
}