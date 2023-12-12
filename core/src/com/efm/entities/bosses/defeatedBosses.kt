package com.efm.entities.bosses

import com.efm.Direction4
import com.efm.entities.bosses.slime.BossSlime
import com.efm.entities.enemies.chess.King
import com.efm.entities.enemies.chess.spawnChessSet
import com.efm.entity.Enemy
import com.efm.entity.Entity
import com.efm.room.*
import kotlin.random.Random
import kotlin.reflect.KClass

enum class Boss(val bossClass : KClass<out Enemy>)
{
    Dragon(BossDragon::class),
    NatureGolem(BossNatureGolem::class),
    OctopusHead(BossOctopusHead::class),
    Slime(BossSlime::class),
    Wizard(BossWizard::class),
    Chess(King::class)
}

val defeatedBosses = mutableListOf<Boss>()

fun addBossToDefeatedBossesList(boss : Boss)
{
    defeatedBosses.add(boss)
}

fun getDefeatedBossesList() : MutableList<Boss>
{
    return defeatedBosses
}

private fun chooseRandomBoss(random : Random = Random(Random.nextInt())) : Boss
{
    return Boss.values().random(random)
}

private fun chooseRandomUndefeatedBoss(random : Random = Random(Random.nextInt())) : Boss
{
    return Boss.values().filter { it !in defeatedBosses }.random(random)
}

fun spawnRandomUndefeatedBoss(room : Room, position : RoomPosition, direction : Direction4 = Direction4.left)
{
    val boss = chooseRandomUndefeatedBoss()
    when (boss)
    {
        Boss.Chess       ->
        {
            spawnChessSet(position.x, position.y, direction, room)
        }
        
        Boss.OctopusHead ->
        {
            val head = BossOctopusHead()
            val tentacle1 = BossOctopusTentacle()
            head.addTentacle(tentacle1)
            val tentacle2 = BossOctopusTentacle()
            head.addTentacle(tentacle2)
            room.changeBaseAt(Base.water, position.x - 2, position.y)
            room.addEntityAt(tentacle1, position.x - 2, position.y)
            room.changeBaseAt(Base.water, position.x - 1, position.y)
            room.changeBaseAt(Base.waterOctopus, position.x, position.y)
            room.addEntityAt(head, position.x, position.y)
            room.changeBaseAt(Base.water, position.x + 1, position.y)
            room.changeBaseAt(Base.water, position.x + 2, position.y)
            room.addEntityAt(tentacle2, position.x + 2, position.y)
        }
        
        else             ->
        {
            val bossInstance = Class.forName(boss.bossClass.qualifiedName).getConstructor().newInstance() as Enemy
            room.addEntityAt(bossInstance, position)
        }
    }
}

fun spawnAllBossesInOneRoom(finalRoom : Room)
{
    for (boss in getDefeatedBossesList())
    {
        when (boss)
        {
            Boss.Chess       ->
            {
                var bossNotSpawned = true
                while (bossNotSpawned)
                {
                    val posX = Random.nextInt(1, finalRoom.widthInSpaces - 1)
                    val posY = Random.nextInt(1, finalRoom.heightInSpaces - 1)
                    try
                    {
                        spawnChessSet(posX, posY, Direction4.down, finalRoom)
                    }
                    finally
                    {
                        if (finalRoom.getSpace(RoomPosition(posX, posY))?.getEntity() != null)
                        {
                            bossNotSpawned = false
                        }
                    }
                }
            }
            
            Boss.OctopusHead ->
            {
                val head = BossOctopusHead()
                head.createOwnHealthBar()
                val tentacle1 = BossOctopusTentacle()
                tentacle1.createOwnHealthBar()
                head.addTentacle(tentacle1)
                val tentacle2 = BossOctopusTentacle()
                tentacle2.createOwnHealthBar()
                head.addTentacle(tentacle2)
                var bossNotSpawned = true
                while (bossNotSpawned)
                {
                    val posX = Random.nextInt(1, finalRoom.widthInSpaces - 1)
                    val posY = Random.nextInt(1, finalRoom.heightInSpaces - 1)
                    try
                    {
                        finalRoom.changeBaseAt(Base.water, posX - 2, posY)
                        finalRoom.addEntityAt(tentacle1, posX - 2, posY)
                        finalRoom.changeBaseAt(Base.water, posX - 1, posY)
                        finalRoom.changeBaseAt(Base.waterOctopus, posX, posY)
                        finalRoom.addEntityAt(head, posX, posY)
                        finalRoom.changeBaseAt(Base.water, posX + 1, posY)
                        finalRoom.changeBaseAt(Base.water, posX + 2, posY)
                        finalRoom.addEntityAt(tentacle2, posX + 2, posY)
                    }
                    finally
                    {
                        if (finalRoom.getSpace(RoomPosition(posX, posY))?.getEntity() != null)
                        {
                            bossNotSpawned = false
                        }
                    }
                }
            }
            
            else             ->
            {
                var bossNotSpawned = true
                while (bossNotSpawned)
                {
                    val posX = Random.nextInt(1, finalRoom.widthInSpaces - 1)
                    val posY = Random.nextInt(1, finalRoom.heightInSpaces - 1)
                    try
                    {
                        val bossInstance =
                                Class.forName(boss.bossClass.qualifiedName).getConstructor().newInstance() as Enemy
                        bossInstance.createOwnHealthBar()
                        finalRoom.addEntityAt(bossInstance, RoomPosition(posX, posY))
                    }
                    finally
                    {
                        if (finalRoom.getSpace(RoomPosition(posX, posY))?.getEntity() != null)
                        {
                            bossNotSpawned = false
                        }
                    }
                }
            }
        }
    }
}
