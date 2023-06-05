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

class MiniEnemy : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 5
    override var healthPoints = 5
    override var alive = true
    override val detectionRange = 3
    override val attackRange = 1
    override val stepsInOneTurn = 3
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.miniEnemy
    }
    
    override fun getOutlineYellowTile() : TiledMapTile
    {
        return Tiles.miniEnemyOutlineYellow
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.miniEnemyOutlineRed
    }
    
    override fun enemyAttack()
    {
        val animations = mutableListOf<Animation>()
        animations += Animation.action { playSoundOnce(Sounds.woodenSword) }
        animations += Animation.showTile(Tiles.woodenSword, World.hero.position, 0.5f)
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