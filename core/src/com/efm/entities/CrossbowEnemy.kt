package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Enemy
import com.efm.entity.Entity
import com.efm.level.World
import com.efm.room.RoomPosition

class CrossbowEnemy : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 50
    override var healthPoints = 50
    override var alive = true
    override val detectionRange = 2
    override val attackRange = 2
    override val stepsInOneTurn = 1

    override fun getTile() : TiledMapTile
    {
        return Tiles.crossbowEnemy
    }

    override fun getOutlineYellowTile() : TiledMapTile
    {
        return Tiles.crossbowEnemyOutlineYellow
    }

    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.crossbowEnemyOutlineRed
    }
    
    override fun enemyAttack()
    {
        World.hero.damageCharacter(2)
        //animate arrow
    }
}
