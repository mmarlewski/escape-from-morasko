package com.efm.entities.enemies

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.*
import com.efm.room.RoomPosition

class EnemyRollingStone : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 5
    override var healthPoints = 5
    override var alive = true
    override val detectionRange = 3
    override val attackRange = 1
    override val stepsInOneTurn = 3
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override fun getTile() : TiledMapTile?
    {
        return Tiles.rockIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.rockIdle1OutlineYellow
            2    -> Tiles.rockIdle2OutlineYellow
            else -> Tiles.rockIdle1OutlineYellow
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile?
    {
        return Tiles.rockIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.rockIdle1
            2    -> Tiles.rockIdle2
            else -> Tiles.rockIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.rockMove1
            2    -> Tiles.rockMove2
            else -> Tiles.rockMove1
        }
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return Tiles.rockMove1
    }
    
    override fun getMoveSound() : Sound?
    {
        return Sounds.rockMove
    }
    
    override fun enemyAttack()
    {
        TODO("Not yet implemented")
    }
    
    override fun getCorpse() : EnemyCorpse = EnemyRollingStoneCorpse(this.position)
}