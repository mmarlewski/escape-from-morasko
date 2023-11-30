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

val bossClasses = listOf<KClass<out Enemy>>(
        BossDragon::class,
        BossNatureGolem::class,
        BossOctopusHead::class,
        BossSlime::class,
        BossWizard::class,
        King::class
                                           )

val defeatedBosses = mutableListOf<KClass<out Enemy>>()

fun addBossToDefeatedBossesList(boss : Enemy)
{
    defeatedBosses.add(boss::class)
}

fun getDefeatedBossesList() : MutableList<KClass<out Enemy>>
{
    return defeatedBosses
}

private fun chooseRandomBossClass(random : Random = Random(Random.nextInt())) : KClass<out Enemy>
{
    return bossClasses.random(random)
}

private fun chooseRandomUndefeatedBossClass(random : Random = Random(Random.nextInt())) : KClass<out Enemy>
{
    return bossClasses.filter { it !in defeatedBosses }.random(random)
}

fun spawnRandomUndefeatedBoss(room : Room, position : RoomPosition, direction : Direction4 = Direction4.left)
{
    val bossClass = chooseRandomUndefeatedBossClass()
    when (bossClass)
    {
        King::class            ->
        {
            spawnChessSet(position.x, position.y, direction, room)
        }
        BossOctopusHead::class ->
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
        else                   ->
        {
            val boss = Class.forName(bossClass.qualifiedName).getConstructor().newInstance() as Enemy
            room.addEntityAt(boss, position)
        }
    }
    //Gdx.app.log("bosses", "$bossClass")
}

fun spawnAllBossesInOneRoom(finalRoom : Room)
{
    for (bossClass in getDefeatedBossesList())
    {
        when (bossClass)
        {
            King::class            ->
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
            BossOctopusHead::class ->
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
            else                   ->
            {
                var bossNotSpawned = true
                while (bossNotSpawned)
                {
                    val posX = Random.nextInt(1, finalRoom.widthInSpaces - 1)
                    val posY = Random.nextInt(1, finalRoom.heightInSpaces - 1)
                    try
                    {
                        val boss = Class.forName(bossClass.qualifiedName).getConstructor().newInstance() as Enemy
                        boss.createOwnHealthBar()
                        finalRoom.addEntityAt(boss, RoomPosition(posX, posY))
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