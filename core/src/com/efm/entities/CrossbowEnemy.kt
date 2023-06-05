package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.Character
import com.efm.entity.Enemy
import com.efm.entity.Entity
import com.efm.level.World
import com.efm.room.RoomPosition

class CrossbowEnemy : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 10
    override var healthPoints = 10
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
        val animations = mutableListOf<Animation>()
        animations += Animation.action { playSoundOnce(Sounds.woodenSword) }
        animations += Animation.moveTile(Tiles.arrow, position, World.hero.position, 0.25f)
        animations += Animation.action {
            val attackedPosition = World.hero.position
            val attackedSpace = World.currentRoom.getSpace(attackedPosition)
            val attackedEntity = attackedSpace?.getEntity()
            when (attackedEntity)
            {
                is Character ->
                {
                    attackedEntity.damageCharacter(2)
                }
            }
        }
        Animating.executeAnimations(animations)
    }
}
