package com.efm.entities.enemies.Boar

import com.badlogic.gdx.Gdx
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
import com.efm.screens.GameScreen

class EnemyBoar(
        override val position : RoomPosition = RoomPosition(),
        override var maxHealthPoints : Int = 20,
        override var healthPoints : Int = maxHealthPoints
               ) : Enemy
{
    override var alive : Boolean = true
    
    override val detectionRange : Int = 3
    override val attackRange : Int = 3
    override var attackDamage = 20
    override val stepsInOneTurn : Int = 2
    
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override var isFrozen = false
    
    override fun getTile() : TiledMapTile = Tiles.boarIdle1
    
    override fun getIdleTile(n : Int) : TiledMapTile = when (n)
    {
        1    -> Tiles.boarIdle1
        2    -> Tiles.boarIdle2
        3    -> Tiles.boarIdle1
        4    -> Tiles.boarIdle2
        else -> Tiles.boarIdle1
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile = when (n)
    {
        1    -> Tiles.boarMove1
        2    -> Tiles.boarMove2
        3    -> Tiles.boarMove1
        4    -> Tiles.boarMove2
        else -> Tiles.boarMove1
    }
    
    override fun getAttackTile() : TiledMapTile = Tiles.boarAttack
    
    override fun getOutlineRedTile() : TiledMapTile = Tiles.boarIdle1OutlineRed
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = when (n)
    {
        1    -> Tiles.boarIdle1OutlineYellow
        2    -> Tiles.boarIdle2OutlineYellow
        3    -> Tiles.boarIdle1OutlineYellow
        4    -> Tiles.boarIdle2OutlineYellow
        else -> Tiles.boarIdle1OutlineYellow
    }
    
    override fun getMoveSound() : Sound = Sounds.boarMove
    
    private fun getAttackSound() : Sound = Sounds.boarAttack
    
    override fun getCorpse() : EnemyCorpse = EnemyBoarCorpse(this.position)
    
    override fun performTurn()
    {
        if (!isFrozen)
        {
            Gdx.app.log("EnemyBoar", "perform turn")
            
            val doNothing = -1
            val justAttack = 0
            val chargeThenAttack = 1
            val moveThenAttack = 2
            
            var decision = doNothing
            
            val worldCurrentRoom = World.currentRoom
            val pathSpaces = if (worldCurrentRoom != null) PathFinding.findPathInRoomForEntity(
                    position,
                    World.hero.position,
                    worldCurrentRoom,
                    this
                                                                                              )
            else null
            for (pos in getSquareAreaPositions(position, attackRange))
            {
                // boar attacks only in a straight line
                if ((pos.x == position.x || pos.y == position.y) && (pos == World.hero.position))
                {
                    // there are spaces to charge
                    if (kotlin.math.abs(pos.x - position.x) > 1 || kotlin.math.abs(pos.y - position.y) > 1)
                    {
                        decision = chargeThenAttack
                    }
                    // Boar is standing next to Hero
                    else
                    {
                        decision = justAttack
                    }
                }
            }
            if (decision == doNothing && pathSpaces != null)
            {
                decision = moveThenAttack
            }
            
            when (decision)
            {
                justAttack       -> enemyAttack()
                chargeThenAttack -> chargeThenAttack()
                moveThenAttack   -> moveThenAttack()
            }
        }
        else
        {
            isFrozen = false
        }
    }
    
    override fun enemyAttack()
    {
        // just attack when standing next to Hero
        Gdx.app.log("EnemyBoar", "only attack")
        
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(position, heroPosition)
        val impactTile = if (heroDirection == null) null else Tiles.getImpactTile(heroDirection)
        
        val animations = mutableListOf<Animation>()
        animations += Animation.action { playSoundOnce(getAttackSound()) }
        animations += Animation.showTile(impactTile, heroPosition.copy(), 0.5f)
        animations += Animation.action {
            val attackedSpace = World.currentRoom?.getSpace(heroPosition)
            when (val attackedEntity = attackedSpace?.getEntity())
            {
                is Character -> attackedEntity.damageCharacter(attackDamage)
            }
        }
        Animating.executeAnimations(animations)
    }
    
    private fun chargeThenAttack()
    {
        // charge than attack if Hero is in range
        Gdx.app.log("EnemyBoar", "charge then attack")
        val animations = mutableListOf<Animation>()
        
        // charge
        
        Gdx.app.log("EnemyBoar", "charge")
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
        val stepsSpaces = pathSpaces?.take(attackRange)
        if (!stepsSpaces.isNullOrEmpty())
        {
            val stepsIndex = if (stepsSpaces.size == pathSpaces.size)
            {
                stepsSpaces.size - 1
            }
            else stepsSpaces.size
            playSoundOnce(getMoveSound())
            
            val startPosition = initialPosition
            val endPosition = pathSpaces[stepsIndex].position
            val path = stepsSpaces
            val enemy = this
            
            val beforeMoveAction = {
                enemy.hideOwnHealthBar()
                World.currentRoom?.getSpace(enemy.position)?.clearEntity()
                GameScreen.updateMapBaseLayer()
                GameScreen.updateMapEntityLayer()
            }
            val afterMoveAction = {
                enemy.position.set(endPosition)
                World.currentRoom?.updateSpacesEntities()
                GameScreen.updateMapBaseLayer()
                GameScreen.updateMapEntityLayer()
            }
            
            animations += Animation.action(beforeMoveAction)
            //animations += Animation.wait(1f)
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
            animations += Animation.action(afterMoveAction)
            
            // position before attacking
            initialPosition = endPosition
        }
        
        // attack
        
        Gdx.app.log("EnemyBoar", "attack")
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(initialPosition, heroPosition)
        val impactTile = if (heroDirection == null) null else Tiles.getImpactTile(heroDirection)
        animations += Animation.action { playSoundOnce(getAttackSound()) }
        animations += Animation.showTile(impactTile, heroPosition.copy(), 0.5f)
        animations += Animation.action {
            val attackedSpace = World.currentRoom?.getSpace(heroPosition)
            when (val attackedEntity = attackedSpace?.getEntity())
            {
                is Character ->
                {
                    // the longer the charge, the stronger the attack
                    attackedEntity.damageCharacter(20 + (stepsSpaces?.size?.times(10) ?: 0))
                }
            }
        }
        
        // conclude animation
        
        animations += Animation.action { this.displayOwnHealthBar() }
        Animating.executeAnimations(animations)
    }
    
    private fun moveThenAttack()
    {
        // Boar can attack after moving
        Gdx.app.log("EnemyBoar", "move then attack")
        val animations = mutableListOf<Animation>()
        
        // move
        val worldCurrentRoom = World.currentRoom
        val pathSpaces = if (worldCurrentRoom != null) PathFinding.findPathInRoomForEntity(
                position,
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
            playSoundOnce(getMoveSound())
            val startPosition = position
            // position after moving and before attacking/charging
            var endPosition = pathSpaces[stepsIndex].position
            val path = stepsSpaces
            val enemy = this
            
            val beforeMoveAction = {
                enemy.hideOwnHealthBar()
                World.currentRoom?.getSpace(enemy.position)?.clearEntity()
                GameScreen.updateMapBaseLayer()
                GameScreen.updateMapEntityLayer()
            }
            val pom = endPosition
            val afterMoveAction = {
                enemy.position.set(pom)
                World.currentRoom?.updateSpacesEntities()
                GameScreen.updateMapBaseLayer()
                GameScreen.updateMapEntityLayer()
            }
            
            animations += Animation.action(beforeMoveAction)
            //animations += Animation.wait(1f)
            val prevMovePosition = startPosition.copy()
            path.forEachIndexed { index, space ->
                Gdx.app.log("EnemyBoar", "go by ${space.position}")
                val n = (index % IdleAnimation.numberOfMoveAnimations) + 1
                val moveTile = enemy.getMoveTile(n)
                animations += Animation.moveTileWithCameraFocus(
                        moveTile, prevMovePosition.copy(), space.position.copy(), 0.1f
                                                               )
                animations += Animation.showTileWithCameraFocus(moveTile, space.position.copy(), 0.01f)
                prevMovePosition.set(space.position)
            }
            animations += Animation.moveTileWithCameraFocus(enemy.getTile(), prevMovePosition, pom, 0.1f)
            animations += Animation.action(afterMoveAction)
            
            // attack after moving
            
            for (pos in getSquareAreaPositions(endPosition, attackRange))
            {
                // boar attacks only in a straight line
                if ((pos.x == endPosition.x || pos.y == endPosition.y) && (pos == World.hero.position))
                {
                    // there are spaces to charge
                    if (kotlin.math.abs(pos.x - endPosition.x) > 1 || kotlin.math.abs(pos.y - endPosition.y) > 1)
                    {
                        // intimidating pose before charging
                        animations += Animation.showTileWithCameraFocus(enemy.getIdleTile(1), pom, 0.33f)
                        animations += Animation.showTileWithCameraFocus(enemy.getIdleTile(2), pom, 0.33f)
                        
                        // charge
                        
                        Gdx.app.log("EnemyBoar", "charge")
                        val worldCurrentRoom = World.currentRoom
                        val pathSpaces2 = if (worldCurrentRoom != null) PathFinding.findPathInRoomForEntity(
                                endPosition,
                                World.hero.position,
                                worldCurrentRoom,
                                this
                                                                                                          )
                        else null
                        val stepsSpaces2 = pathSpaces2?.take(attackRange)
                        if (!stepsSpaces2.isNullOrEmpty())
                        {
                            val stepsIndex2 = if (stepsSpaces2.size == pathSpaces2.size)
                            {
                                stepsSpaces2.size - 1
                            }
                            else stepsSpaces2.size
                            playSoundOnce(getMoveSound())
                            
                            val startPosition2 = endPosition
                            val endPosition2 = pathSpaces2[stepsIndex2].position
                            val path2 = stepsSpaces2
                            val enemy2 = this
                            
                            val beforeMoveAction2 = {
                                World.currentRoom?.getSpace(enemy2.position)?.clearEntity()
                                GameScreen.updateMapBaseLayer()
                                GameScreen.updateMapEntityLayer()
                            }
                            val afterMoveAction2 = {
                                enemy.position.set(endPosition2)
                                World.currentRoom?.updateSpacesEntities()
                                GameScreen.updateMapBaseLayer()
                                GameScreen.updateMapEntityLayer()
                            }
                            
                            animations += Animation.action(beforeMoveAction2)
                            //animations += Animation.wait(1f)
                            val prevMovePosition2 = startPosition2.copy()
                            path2.forEachIndexed { index, space ->
                                val n = (index % IdleAnimation.numberOfMoveAnimations) + 1
                                val moveTile = enemy2.getMoveTile(n)
                                animations += Animation.moveTileWithCameraFocus(
                                        moveTile, prevMovePosition2.copy(), space.position.copy(), 0.1f
                                                                               )
                                animations += Animation.showTileWithCameraFocus(moveTile, space.position.copy(), 0.01f)
                                prevMovePosition2.set(space.position)
                            }
                            animations += Animation.moveTileWithCameraFocus(
                                    enemy2.getTile(), prevMovePosition2, endPosition2, 0.1f
                                                                           )
                            animations += Animation.action(afterMoveAction2)
                            
                            // position after charging before attacking
                            endPosition = endPosition2
                        }
                    }
                    
                    // attack
                    
                    Gdx.app.log("EnemyBoar", "attack")
                    val heroPosition = World.hero.position.copy()
                    val heroDirection = getDirection8(endPosition, heroPosition)
                    val impactTile = if (heroDirection == null) null else Tiles.getImpactTile(heroDirection)
                    
                    animations += Animation.action { playSoundOnce(getAttackSound()) }
                    animations += Animation.showTile(impactTile, heroPosition.copy(), 0.5f)
                    animations += Animation.action {
                        val attackedSpace = World.currentRoom?.getSpace(heroPosition)
                        when (val attackedEntity = attackedSpace?.getEntity())
                        {
                            is Character ->
                            {
                                // the longer the charge, the stronger the attack
                                attackedEntity.damageCharacter(20 + stepsSpaces.size.times(10))
                            }
                        }
                    }
                    break
                }
            }
            
            // conclude animation
            
            animations += Animation.action { enemy.displayOwnHealthBar() }
            Animating.executeAnimations(animations)
        }
    }
}
