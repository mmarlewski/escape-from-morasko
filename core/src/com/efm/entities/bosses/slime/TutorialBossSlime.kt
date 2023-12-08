package com.efm.entities.bosses.slime

import com.efm.entities.bosses.addBossToDefeatedBossesList
import com.efm.getSquarePerimeterPositions
import com.efm.level.World
import com.efm.room.RoomPosition

/**
 * Weaker Boss Slime **/
class TutorialBossSlime : BossSlime()
{
    override var maxHealthPoints = 30
    override var healthPoints = 30
    override var attackDamage = 25
    
    override fun onDeath()
    {
        val slimeHalf1 = TutorialBossSlimeHalf()
        val slimeHalf2 = TutorialBossSlimeHalf()
        
        var spawnPosition1 : RoomPosition? = null
        var spawnPosition2 : RoomPosition? = null
        
        var radius = 1
        while (radius < 5 && (spawnPosition1 == null || spawnPosition2 == null))
        {
            val squarePerimeterPositions = getSquarePerimeterPositions(this.position, radius)
            val possibleSpawnPositions = squarePerimeterPositions.filter {
                World.currentRoom.getSpace(it)?.isTraversableFor(slimeHalf1) ?: false
            }
            
            when (possibleSpawnPositions.size)
            {
                0    ->
                {
                }
                
                1    ->
                {
                    if (spawnPosition1 == null) spawnPosition1 = possibleSpawnPositions.first()
                    else spawnPosition2 = possibleSpawnPositions.first()
                }
                
                else ->
                {
                    if (spawnPosition1 == null) spawnPosition1 = possibleSpawnPositions.random()
                    spawnPosition2 = possibleSpawnPositions.filter { it != spawnPosition1 }.random()
                }
            }
            
            radius++
        }
        
        if (spawnPosition1 != null)
        {
            slimeHalf1.position.set(spawnPosition1)
            slimeHalf1.createOwnHealthBar()
            World.currentRoom.addEntityToBeAddedEntities(slimeHalf1)
        }
        if (spawnPosition2 != null)
        {
            slimeHalf2.position.set(spawnPosition2)
            slimeHalf2.createOwnHealthBar()
            World.currentRoom.addEntityToBeAddedEntities(slimeHalf2)
        }
        World.currentRoom.updateSpacesEntities()
        addBossToDefeatedBossesList(BossSlime())
    }
}

class TutorialBossSlimeHalf : BossSlimeHalf()
{
    override var maxHealthPoints = 15
    override var healthPoints = 15
    override var attackDamage = 15
}