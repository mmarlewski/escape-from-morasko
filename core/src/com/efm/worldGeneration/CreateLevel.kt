package com.efm.worldGeneration

import com.efm.Direction4
import com.efm.entities.StoneWall
import com.efm.level.Level
import com.efm.room.*
import kotlin.random.Random

class CreateLevel ()
{
    fun createLevel() : Level
    {
        //get starting room
        val dungeonMap = Array(10) { IntArray(10) { 0 } }
        val desiredNbrOfRooms = Random.nextInt(5,8)
        var rooms : MutableList<Room> = mutableListOf()
        var createdLevel : Level = Level("1")
        var posX = Random.nextInt(2, 8)
        var posY = Random.nextInt(2, 8)
        dungeonMap[posX][posY] = 1
        performRandomWalk(desiredNbrOfRooms, posX, posY, dungeonMap)
        //na podstawie tej macierzy sprawdzać ile powinno być przejść między jakimi pokojami
        //for loop po dungeonMap, może jakiś słownik, że jaki pokój ma jaki numer i wtedy na
        //podstawie macierzy brać przejścia
        //nie da się stworzyć przejścia do pokoju null, więc najpierw same pokoje, potem przejścia między nimi
        //macierz pozwoli pamiętać jak są względem siebie ułożone i z którego do którego robić przejścia
        for (i in 0..desiredNbrOfRooms)
        {
            val currentRoom = Room((i+1).toString(), Random.nextInt(10, 15), Random.nextInt(10, 15))
            fillRoomContents(currentRoom)
            rooms.add(currentRoom)
        }
        createdLevel.changeStartingRoom(rooms[0])
        createdLevel.changeStartingPosition(RoomPosition(1, 1))
        //createdLevel.addRoom()
        return createdLevel
    }
    
    private fun fillRoomContents(currentRoom : Room)
    {
        for (y in 1 until currentRoom.heightInSpaces) for (x in 1 until currentRoom.widthInSpaces) currentRoom.changeBaseAt(
                Base.values()[y % 4], x, y)
        // add walls on left side (facing right)
                for (y in 1 until currentRoom.heightInSpaces) currentRoom.addEntityAt(StoneWall(Direction4.right), 0, y)
        // add walls on top side (facing down)
                for (x in 1 until currentRoom.widthInSpaces) currentRoom.addEntityAt(StoneWall(Direction4.down), x, 0)
    }
    
    fun performRandomWalk(steps: Int, startingX : Int, startingY : Int, dungeonMap : Array<IntArray>)
    {
        var currentX = startingX
        var currentY = startingY
        
        for (step in 2 until steps + 2)
        {
            var keepLooking = true
            while(keepLooking)
            {
                val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
                val randomDirection = directions[Random.nextInt(4)]
    
                val newX = currentX + randomDirection.first
                val newY = currentY + randomDirection.second
    
                if (newX in 0 until 10 && newY in 0 until 10)
                {
                    if (dungeonMap[newX][newY] != 1)
                    {
                        currentX = newX
                        currentY = newY
                        dungeonMap[currentY][currentX] = step
                        keepLooking = false
                    }
        
                }
            }
            
        }
    }
}