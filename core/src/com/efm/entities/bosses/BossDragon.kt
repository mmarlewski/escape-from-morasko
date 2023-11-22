package com.efm.entities.bosses

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entities.bosses.slime.BossSlime
import com.efm.entities.enemies.EnemyBatCorpse
import com.efm.entities.exits.ExitStyle
import com.efm.entity.*
import com.efm.level.World
import com.efm.room.Base
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

class BossDragon : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 50
    override var healthPoints = 50
    override var alive = true
    override val detectionRange = 3
    override val attackRange = 3
    override val stepsInOneTurn = 10
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    var isSitting = true
    var count = 0
    override var isFrozen = false
    
    override fun getTile() : TiledMapTile
    {
        if (isSitting)
        {
            return Tiles.dragonSittingIdle1
        }
        else
        {
            return Tiles.dragonIdle1
        }
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        if (isSitting)
        {
            return when (n)
            {
                1    -> Tiles.dragonSittingIdle1OutlineYellow
                2    -> Tiles.dragonSittingIdle1OutlineYellow
                3    -> Tiles.dragonSittingIdle2OutlineYellow
                4    -> Tiles.dragonSittingIdle2OutlineYellow
                else -> Tiles.dragonSittingIdle1OutlineYellow
            }
        }
        else
        {
            return when (n)
            {
                1    -> Tiles.dragonIdle1OutlineYellow
                2    -> Tiles.dragonIdle1OutlineYellow
                3    -> Tiles.dragonIdle2OutlineYellow
                4    -> Tiles.dragonIdle2OutlineYellow
                else -> Tiles.dragonIdle1OutlineYellow
            }
        }
        
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        if (isSitting)
        {
            return Tiles.dragonSittingIdle1OutlineRed
        }
        else
        {
            return Tiles.dragonIdle1OutlineRed
        }
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile?
    {
        if (isSitting)
        {
            return when (n)
            {
                1    -> Tiles.dragonSittingIdle1
                2    -> Tiles.dragonSittingIdle1
                3    -> Tiles.dragonSittingIdle2
                4    -> Tiles.dragonSittingIdle2
                else -> Tiles.dragonSittingIdle1
            }
        }
        else
        {
            return when (n)
            {
                1    -> Tiles.dragonIdle1
                2    -> Tiles.dragonIdle1
                3    -> Tiles.dragonIdle2
                4    -> Tiles.dragonIdle2
                else -> Tiles.dragonIdle1
            }
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        if (isSitting)
        {
            return null
        }
        else
        {
            return when (n)
            {
                1    -> Tiles.dragonMove1
                2    -> Tiles.dragonMove1
                3    -> Tiles.dragonMove2
                4    -> Tiles.dragonMove2
                else -> Tiles.dragonMove1
            }
        }
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        if (isSitting)
        {
            return Tiles.dragonSittingAttack
        }
        else
        {
            return Tiles.dragonAttack
        }
    }
    
    override fun getMoveSound() : Sound?
    {
        if (isSitting)
        {
            return null
        }
        else
        {
            return Sounds.dragonMove
        }
    }
    
    fun turnTileIntoLava(tilePosition : RoomPosition)
    {
        val projectileTile = Tiles.dragonProjectile
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.action { playSoundOnce(Sounds.dragonSittingAttack) }
        animations += Animation.moveTileWithArch(projectileTile, position, tilePosition, 1.0f, 1.0f)
        animations += Animation.showTile(Tiles.fire, tilePosition, 0.1f)
        animations += Animation.action {
            
            val attackedPosition = tilePosition
            val attackedSpace = World.currentRoom.getSpace(attackedPosition)
            val attackedEntity = attackedSpace?.getEntity()
            when (attackedEntity)
            {
                this -> Unit
                null ->
                {
                    attackedSpace?.changeBase(Base.lava)
                    GameScreen.updateMapBaseLayer()
                }
            }
        }
        Animating.executeAnimations(animations)
    }
    
    fun breatheFire(tilePosition : RoomPosition)
    {
        val fireDirection = getDirection8(this.position, tilePosition)
        
        if (fireDirection != null)
        {
            val linePositions = mutableListOf<RoomPosition>()
            val positionCounterclockwisePlusOne =
                    this.position.positionOffsetBy(1, fireDirection.nextInCounterclockwise().nextInCounterclockwise())
                            .positionOffsetBy(1, fireDirection)
            val positionClockwisePlusOne =
                    this.position.positionOffsetBy(1, fireDirection.nextInClockwise().nextInClockwise())
                            .positionOffsetBy(1, fireDirection)
            linePositions.addAll(getLineFromPositionInDirectionPositions(this.position, fireDirection, 4))
            linePositions.addAll(getLineFromPositionInDirectionPositions(positionCounterclockwisePlusOne, fireDirection, 3))
            linePositions.addAll(getLineFromPositionInDirectionPositions(positionClockwisePlusOne, fireDirection, 3))
            
            val fireTile = Tiles.getFireTile(fireDirection)
            
            val animations = mutableListOf<Animation>()
            
            val showFireTiles = mutableListOf<Animation>()
            showFireTiles.add(Animation.action { playSoundOnce(Sounds.dragonAttack) })
            for (position in linePositions)
            {
                showFireTiles.add(Animation.showTile(fireTile, position, 1.0f))
                showFireTiles.add(Animation.cameraShake(3, 1.0f))
                showFireTiles.add(Animation.action {
                    
                    val attackedPosition = position
                    val attackedSpace = World.currentRoom.getSpace(attackedPosition)
                    val attackedEntity = attackedSpace?.getEntity()
                    when (attackedEntity)
                    {
                        is Character ->
                        {
                            attackedEntity.damageCharacter(5)
                        }
                    }
                })
            }
            
            animations.add(Animation.simultaneous(showFireTiles))
            Animating.executeAnimations(animations)
        }
    }
    
    override fun performTurn()
    {
        if (!isFrozen)
        {
            val doNothing = 0
            val turnTileIntoLava = 1
            val attackHero = 2
            val goSitting = 3
            val goFlying = 4
            
            var decision = doNothing
            
            if (isSitting)
            {
                if (count >= 5)
                {
                    count = 0
                    decision = goFlying
                }
                else
                {
                    count++
                    decision = turnTileIntoLava
                }
            }
            else
            {
                if (count >= 5)
                {
                    count = 0
                    decision = goSitting
                }
                else
                {
                    count++
                    decision = attackHero
                }
            }
            
            when (decision)
            {
                turnTileIntoLava ->
                {
                    val attackAreaPositions = getSquareAreaPositions(position, attackRange)
                    val validAttackAreaPositions = attackAreaPositions.filter {
                        val space = World.currentRoom.getSpace(it)
                        val entity = space?.getEntity()
                        val base = space?.getBase()
                        space != null && entity == null && base != Base.lava
                    }
                    val tilePosition = validAttackAreaPositions.random()
                    turnTileIntoLava(tilePosition)
                }
                
                attackHero       ->
                {
                    val attackAreaPositions = getSquareAreaPositions(position, attackRange)
                    
                    if (World.hero.position in attackAreaPositions)
                    {
                        breatheFire(World.hero.position)
                    }
                    else
                    {
                        val pathSpaces =
                                PathFinding.findPathInRoomForEntity(position, World.hero.position, World.currentRoom, this)
                        
                        val stepsSpaces = pathSpaces?.take(stepsInOneTurn)
                        if (!stepsSpaces.isNullOrEmpty())
                        {
                            val stepsIndex =
                                    if (stepsSpaces.size == pathSpaces.size) stepsSpaces.size - 1 else stepsSpaces.size
                            getMoveSound()?.let { playSoundOnce(it) }
                            moveEnemy(position, pathSpaces[stepsIndex].position, stepsSpaces, this)
                        }
                    }
                }
                
                goSitting        ->
                {
                    isSitting = true
                }
                
                goFlying         ->
                {
                    isSitting = false
                }
                
                else             ->
                {
                }
            }
        }
        else
        {
            isFrozen = false
        }
    }
    
    override fun enemyAttack()
    {
    }
    
    override fun onDeath()
    {
        addBossToDefeatedBossesList(BossDragon())
    }
    
    // for serializing
    
    override fun write(json : Json?)
    {
        super<Enemy>.write(json)
        
        if (json != null)
        {
            json.writeValue("isSitting", this.isSitting)
            json.writeValue("count", this.count)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super<Enemy>.read(json, jsonData)
        
        if (json != null)
        {
            val jsonIsSitting = json.readValue("isSitting", Boolean::class.java, jsonData)
            if (jsonIsSitting != null) this.isSitting = jsonIsSitting
            val jsonCount = json.readValue("count", Int::class.java, jsonData)
            if (jsonCount != null) this.count = jsonCount
        }
    }
}
