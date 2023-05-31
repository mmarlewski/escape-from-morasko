package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction
import com.efm.assets.Tiles
import com.efm.entity.Enemy
import com.efm.entity.Entity
import com.efm.level.World
import com.efm.room.RoomPosition

class MiniEnemy : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 25
    override var healthPoints = 25
    override var alive = true
    override val detectionRange = 3
    override val attackRange = 1
    override val stepsInOneTurn = 3
    override fun getTile() : TiledMapTile
    {
        return Tiles.miniEnemy
    }
    
    override fun getOutlineTile() : TiledMapTile
    {
        return Tiles.miniEnemyOutlineRed
    }
    
    override fun enemyAttack()
    {
        World.hero.damageCharacter(1)
        //animate sword
    }
}
