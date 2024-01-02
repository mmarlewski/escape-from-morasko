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
import com.efm.entity.*
import com.efm.item.PossibleItems
import com.efm.level.World
import com.efm.room.Base
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

class BossOctopusHead : BaseBoss(), Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 50
    override var healthPoints = 50
    override var alive = true
    override val attackRange = 0
    override var attackDamage = 30
    override val stepsInOneTurn = 0
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    var isShowingHead = false
    var showHeadNextTurn = false
    var showHeadIndefinitely = false
    var currTentacleNum = 0
    val tentacles = mutableListOf<BossOctopusTentacle>()
    override var isFrozen = false
    override var loot : PossibleItems = PossibleItems()
    
    fun addTentacle(newTentacle : BossOctopusTentacle)
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
        if (!isFrozen)
        {
            val tentaclesToRemove = mutableListOf<BossOctopusTentacle>()
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
                    World.currentRoom?.changeBaseAt(Base.waterOctopus, this.position)
                    GameScreen.updateMapBaseLayer()
                    isShowingHead = false
                }
            }
            else
            {
                if (showHeadNextTurn)
                {
                    playSoundOnce(Sounds.octopusHeadEmerge)
                    World.currentRoom?.changeBaseAt(Base.water, this.position)
                    GameScreen.updateMapBaseLayer()
                    isShowingHead = true
                    showHeadNextTurn = false
                }
            }
        }
        else
        {
            isFrozen = false
        }
    }
    
    override fun damageCharacter(dmgAmount : Int)
    {
        if (isShowingHead)
        {
            this.healthPoints -= dmgAmount
            if (this.healthPoints <= 0)
            {
                this.alive = false
            }
        }
    }
    
    override fun onDeath()
    {
        if (World.currentRoom?.name != "finalRoom")
        {
            showSkillAssignPopUpAfterBossKill(this)
            addBossToDefeatedBossesList(Boss.OctopusHead)
        }
        increaseHeroStats(5, 3)
    }
    
    // for serializing
    
    override fun write(json : Json?)
    {
        super<Enemy>.write(json)
        
        if (json != null)
        {
            var isShowingHead = false
            var showHeadNextTurn = false
            var showHeadIndefinitely = false
            var currTentacleNum = 0
            val tentacles = mutableListOf<BossOctopusTentacle>()
            json.writeValue("isShowingHead", this.isShowingHead)
            json.writeValue("showHeadNextTurn", this.showHeadNextTurn)
            json.writeValue("showHeadIndefinitely", this.showHeadIndefinitely)
            json.writeValue("currTentacleNum", this.currTentacleNum)
            json.writeValue("tentacles", this.tentacles)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super<Enemy>.read(json, jsonData)
        
        if (json != null)
        {
            val jsonIsShowingHead = json.readValue("isShowingHead", Boolean::class.java, jsonData)
            if (jsonIsShowingHead != null) this.isShowingHead = jsonIsShowingHead
            val jsonShowHeadNextTurn = json.readValue("showHeadNextTurn", Boolean::class.java, jsonData)
            if (jsonShowHeadNextTurn != null) this.showHeadNextTurn = jsonShowHeadNextTurn
            val jsonShowHeadIndefinitely = json.readValue("showHeadIndefinitely", Boolean::class.java, jsonData)
            if (jsonShowHeadIndefinitely != null) this.showHeadIndefinitely = jsonShowHeadIndefinitely
            val jsonCurrTentacleNum = json.readValue("currTentacleNum", Int::class.java, jsonData)
            if (jsonCurrTentacleNum != null) this.currTentacleNum = jsonCurrTentacleNum
            val jsonTentacles = json.readValue("tentacles", List::class.java, jsonData)
            if (jsonTentacles != null)
            {
                for (tentacle in jsonTentacles)
                {
                    this.tentacles.add(tentacle as BossOctopusTentacle)
                }
            }
        }
    }
}
