package com.efm.entities

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.*
import com.efm.level.World
import com.efm.room.Base
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

class EnemyOctopusHead : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 50
    override var healthPoints = 50
    override var alive = true
    override val detectionRange = 3
    override val attackRange = 0
    override val stepsInOneTurn = 0
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    var isShowingHead = false
    var showHeadNextTurn = false
    var showHeadIndefinitely = false
    var currTentacleNum = 0
    val tentacles = mutableListOf<EnemyOctopusTentacle>()
    
    fun addTentacle(newTentacle : EnemyOctopusTentacle)
    {
        currTentacleNum++
        tentacles.add(newTentacle)
    }
    
    override fun getTile() : TiledMapTile?
    {
        if (isShowingHead)
        {
            return Tiles.octopusHeadIdle1
        }
        else
        {
            return null
        }
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile?
    {
        if (isShowingHead)
        {
            return when (n)
            {
                1    -> Tiles.octopusHeadIdle1OutlineYellow
                2    -> Tiles.octopusHeadIdle2OutlineYellow
                3    -> Tiles.octopusHeadIdle1OutlineYellow
                4    -> Tiles.octopusHeadIdle2OutlineYellow
                else -> Tiles.octopusHeadIdle1OutlineYellow
            }
        }
        else
        {
            return null
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile?
    {
        if (isShowingHead)
        {
            return Tiles.octopusHeadIdle1OutlineRed
        }
        else
        {
            return null
        }
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile?
    {
        if (isShowingHead)
        {
            return when (n)
            {
                1    -> Tiles.octopusHeadIdle1
                2    -> Tiles.octopusHeadIdle2
                3    -> Tiles.octopusHeadIdle1
                4    -> Tiles.octopusHeadIdle2
                else -> Tiles.octopusHeadIdle1
            }
        }
        else
        {
            return null
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return null
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return null
    }
    
    override fun getMoveSound() : Sound?
    {
        return null
    }
    
    override fun enemyAttack()
    {
    }
    
    override fun performTurn()
    {
        val tentaclesToRemove = mutableListOf<EnemyOctopusTentacle>()
        for (tentacle in tentacles)
        {
            if (!tentacle.alive)
            {
                tentaclesToRemove.add(tentacle)
            }
        }
        tentacles.removeAll(tentaclesToRemove)
        
        if (currTentacleNum > tentacles.size)
        {
            currTentacleNum = tentacles.size
            showHeadNextTurn = true
            if (currTentacleNum == 0)
            {
                showHeadIndefinitely = true
            }
        }
        
        if (isShowingHead)
        {
            if (!showHeadIndefinitely)
            {
                playSoundOnce(Sounds.octopusHeadSubmerge)
                World.currentRoom.changeBaseAt(Base.waterOctopus, this.position)
                GameScreen.updateMapBaseLayer()
                isShowingHead = false
            }
        }
        else
        {
            if (showHeadNextTurn)
            {
                playSoundOnce(Sounds.octopusHeadEmerge)
                World.currentRoom.changeBaseAt(Base.water, this.position)
                GameScreen.updateMapBaseLayer()
                isShowingHead = true
                showHeadNextTurn = false
            }
        }
    }
    
    override fun damageCharacter(dmgAmount : Int)
    {
        if (isShowingHead)
        {
            this.healthPoints -= dmgAmount
            if (this.healthPoints <= 0)
            {
                killCharacter()
            }
        }
    }
}
