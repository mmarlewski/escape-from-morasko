package com.efm.entities.bosses.slime

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.*
import com.efm.level.World
import com.efm.room.RoomPosition

open class BossSlimeHalf(position : RoomPosition = RoomPosition()) : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 25
    override var healthPoints = 25
    override var alive = true
    override val detectionRange = 1
    override val attackRange = 1
    override var attackDamage = 20
    override val stepsInOneTurn = 2
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override var isFrozen = false
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.slimeYellowIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.slimeYellowIdle1OutlineYellow
            2    -> Tiles.slimeYellowIdle2OutlineYellow
            3    -> Tiles.slimeYellowIdle1OutlineYellow
            4    -> Tiles.slimeYellowIdle2OutlineYellow
            else -> Tiles.slimeYellowIdle1OutlineYellow
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.slimeYellowIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.slimeYellowIdle1
            2    -> Tiles.slimeYellowIdle2
            3    -> Tiles.slimeYellowIdle1
            4    -> Tiles.slimeYellowIdle2
            else -> Tiles.slimeYellowIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.slimeYellowIdle1
            2    -> Tiles.slimeYellowIdle2
            3    -> Tiles.slimeYellowIdle1
            4    -> Tiles.slimeYellowIdle2
            else -> Tiles.slimeYellowIdle1
        }
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return Tiles.slimeYellowAttack
    }
    
    override fun getMoveSound() : Sound?
    {
        return Sounds.slimeMove
    }
    
    override fun enemyAttack()
    {
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(this.position, heroPosition)
        val impactTile = if (heroDirection == null) null else Tiles.getImpactTile(heroDirection)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.action { playSoundOnce(Sounds.slimeAttack) }
        animations += Animation.simultaneous(
                listOf(
                        Animation.showTile(impactTile, heroPosition.copy(), 0.2f),
                        Animation.cameraShake(1, 0.5f)
                      )
                                            )
        animations += Animation.action {
            
            val attackedPosition = World.hero.position
            val attackedSpace = World.currentRoom?.getSpace(attackedPosition)
            val attackedEntity = attackedSpace?.getEntity()
            when (attackedEntity)
            {
                is Character ->
                {
                    attackedEntity.damageCharacter(attackDamage)
                }
            }
        }
        
        Animating.executeAnimations(animations)
    }
    
    override fun onDeath()
    {
        val slimeQuarter1 = BossSlimeQuarter()
        val slimeQuarter2 = BossSlimeQuarter()
        
        var spawnPosition1 : RoomPosition? = null
        var spawnPosition2 : RoomPosition? = null
        
        var radius = 1
        while(radius < 5 && (spawnPosition1 == null || spawnPosition2 == null))
        {
            val squarePerimeterPositions = getSquarePerimeterPositions(this.position, radius)
            val possibleSpawnPositions = squarePerimeterPositions.filter {
                World.currentRoom?.getSpace(it)?.isTraversableFor(slimeQuarter1) ?: false
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
        
        if(spawnPosition1 != null)
        {
            slimeQuarter1.position.set(spawnPosition1)
            slimeQuarter1.createOwnHealthBar()
            World.currentRoom?.addEntityToBeAddedEntities(slimeQuarter1)
        }
        if(spawnPosition2 != null)
        {
            slimeQuarter2.position.set(spawnPosition2)
            slimeQuarter2.createOwnHealthBar()
            World.currentRoom?.addEntityToBeAddedEntities(slimeQuarter2)
        }
    }
    
    override fun getCorpse() : EnemyCorpse?
    {
        return null
    }
    
}
