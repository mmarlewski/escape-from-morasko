package com.efm.entities.enemies.rollingStone

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.*
import com.efm.item.PossibleItems
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.room.Space
import com.efm.screens.GameScreen

class EnemyRollingStone : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 15
    override var healthPoints = 15
    override var alive = true
    override val detectionRange = 3
    override val attackRange = 1
    override var attackDamage = 20
    override var stepsInOneTurn = 3
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override var isFrozen = false
    override val roamingChance : Float = 0.0f
    override var loot : PossibleItems = PossibleItems()
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.rockIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.rockIdle1OutlineYellow
            2    -> Tiles.rockIdle2OutlineYellow
            else -> Tiles.rockIdle1OutlineYellow
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.rockIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.rockIdle1
            2    -> Tiles.rockIdle2
            else -> Tiles.rockIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.rockMove1
            2    -> Tiles.rockMove2
            else -> Tiles.rockMove1
        }
    }
    
    override fun getAttackTile() : TiledMapTile
    {
        return Tiles.rockMove1
    }
    
    override fun getMoveSound() : Sound
    {
        return Sounds.rockMove
    }
    
    override fun enemyAttack()
    {
        val animations = mutableListOf<Animation>()
    
        // charge
    
        // position before charging
        var initialPosition = position
        val worldCurrentRoom = World.currentRoom
        val pathSpaces = if (worldCurrentRoom != null) PathFinding.findPathInRoomForEntity(
                initialPosition,
                World.hero.position,
                worldCurrentRoom,
                this
                                                                                          )
        else null
        val stepsSpaces = pathSpaces?.take(stepsInOneTurn)
        if (!stepsSpaces.isNullOrEmpty())
        {
            val stepsIndex = if (stepsSpaces.size == pathSpaces.size)
            {
                stepsSpaces.size - 1
            }
            else stepsSpaces.size
            getMoveSound().let { playSoundOnce(it) }
            val startPosition = initialPosition
            val endPosition = pathSpaces[stepsIndex].position
            val path = stepsSpaces
            val enemy = this
            val action = {
                enemy.position.set(endPosition)
                World.currentRoom?.updateSpacesEntities()
                GameScreen.updateMapBaseLayer()
                GameScreen.updateMapEntityLayer()
            }
            animations += Animation.action { enemy.hideOwnHealthBar() }
            animations += Animation.action { com.efm.Map.changeTile(MapLayer.entity, initialPosition, null) }
            val prevMovePosition = startPosition.copy()
            path.forEachIndexed { index, space ->
                val n = (index % IdleAnimation.numberOfMoveAnimations) + 1
                val moveTile = enemy.getMoveTile(n)
                animations += Animation.moveTileWithCameraFocus(
                        moveTile, prevMovePosition.copy(), space.position.copy(), 0.1f
                                                               )
                animations += Animation.showTileWithCameraFocus(moveTile, space.position.copy(), 0.01f)
                prevMovePosition.set(space.position)
            }
            animations += Animation.moveTileWithCameraFocus(enemy.getTile(), prevMovePosition, endPosition, 0.1f)
            animations += Animation.action(action)
            // position before attacking
            initialPosition = endPosition
        }
    
        // attack
    
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(initialPosition, heroPosition)
        val impactTile = if (heroDirection == null) null else Tiles.getImpactTile(heroDirection)
        animations += Animation.showTile(impactTile, heroPosition.copy(), 0.5f)
        animations += Animation.action {
            val attackedSpace = World.currentRoom?.getSpace(heroPosition)
            when (val attackedEntity = attackedSpace?.getEntity())
            {
                is Character ->
                {
                    // the longer the charge, the stronger the attack
                    attackedEntity.damageCharacter(attackDamage + (stepsSpaces?.size?.times(3) ?: 0))
                }
            }
        }
    
        // conclude animation
    
        animations += Animation.action { this.displayOwnHealthBar() }
        Animating.executeAnimations(animations)
    }
    
    override fun getCorpse() : EnemyCorpse = EnemyRollingStoneCorpse(this.position, loot)
    
    override fun performTurn()
    {
        if (!isFrozen)
        {
            val worldCurrentRoom = World.currentRoom
            val pathSpaces = if (worldCurrentRoom != null) PathFinding.findPathInRoomForEntity(
                    position,
                    World.hero.position,
                    worldCurrentRoom,
                    this
                                                                                              )
            else null
            if (checkIfHeroHasSameXorY(pathSpaces))
            {
                this.stepsInOneTurn = 100
                enemyAttack()
                this.stepsInOneTurn = 3
            }
            else
            {
                moveTowardsHero(pathSpaces)
            }
        } else
        {
            isFrozen = false
        }
    }
    
    private fun checkIfHeroHasSameXorY(pathSpaces : List<Space>?) : Boolean
    {
        val heroPos = World.hero.position
        if (pathSpaces != null) {
            if (this.position.x == heroPos.x && pathSpaces.size == (kotlin.math.abs(this.position.y - heroPos.y) - 1))
            {
                return true
            }
            if (this.position.y == heroPos.y && pathSpaces.size == (kotlin.math.abs(this.position.x - heroPos.x) - 1))
            {
                return true
            }
        }
        return false
    }
}