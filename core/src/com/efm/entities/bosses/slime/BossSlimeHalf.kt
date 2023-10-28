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
import kotlin.random.Random

class BossSlimeHalf(position : RoomPosition = RoomPosition()) : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 50
    override var healthPoints = 5
    override var alive = true
    override val detectionRange = 1
    override val attackRange = 1
    override val stepsInOneTurn = 2
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    
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
        val swordTile = if (heroDirection == null) null else Tiles.getSwordTile(heroDirection)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.descendTile(swordTile, heroPosition.copy(), 0.2f, 0.25f)
        animations += Animation.action { playSoundOnce(Sounds.slimeAttack) }
        animations += Animation.simultaneous(
                listOf(
                        Animation.showTile(Tiles.impact, heroPosition.copy(), 0.2f),
                        Animation.cameraShake(1, 0.5f)
                      )
                                            )
        animations += Animation.action {
            
            val attackedPosition = World.hero.position
            val attackedSpace = World.currentRoom.getSpace(attackedPosition)
            val attackedEntity = attackedSpace?.getEntity()
            when (attackedEntity)
            {
                is Character ->
                {
                    attackedEntity.damageCharacter(20)
                }
            }
        }
        
        Animating.executeAnimations(animations)
    }
    
    fun adjacentTiles() : MutableList<RoomPosition>
    {
        var possibleSpawnPositions = mutableListOf<RoomPosition>()
        
        val currentRoom = World.currentRoom
        
        for (i in -1..1)
        {
            for (j in -1..1)
            {
                val posCardinal = ((position.positionOffsetBy(i, Direction4.up)).positionOffsetBy(j, Direction4.left))
                if (World.currentRoom.getSpace(posCardinal)?.isTraversableFor(this) == true && World.currentRoom.getSpace(
                                posCardinal
                                                                                                                  )
                                ?.getEntity() == null
                )
                {
                    possibleSpawnPositions.add(posCardinal)
                }
            }
        }
        return possibleSpawnPositions
    }
    
    fun onDeath()
    {
        val currentRoom = World.currentRoom
        val adjacentTiles = adjacentTiles()
        
        
        for (i in 0..1)
        {
            var position1 = Random.nextInt(0, adjacentTiles.size - 1)
            val bossSlimeQuarter = BossSlimeQuarter()
            bossSlimeQuarter.createOwnHealthBar()
            currentRoom.addEntityAt(bossSlimeQuarter, adjacentTiles[position1].x, adjacentTiles[position1].y)
            
            adjacentTiles.removeAt(position1)
        }
        
    }
    
    override fun killCharacter()
    {
        onDeath()
        this.alive = false
        
    }
    
    override fun getCorpse() : EnemyCorpse?
    {
        return null
    }
    
}
