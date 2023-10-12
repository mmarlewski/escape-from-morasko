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

class Boar(
        override val position : RoomPosition = RoomPosition(),
        override var maxHealthPoints : Int = 20,
        override var healthPoints : Int = maxHealthPoints
          ) : Enemy
{
    override var alive : Boolean = true
    
    override val detectionRange : Int = 3
    override val attackRange : Int = 3
    override val stepsInOneTurn : Int = 2
    
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    
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
    
    override fun getCorpse() : EnemyCorpse = BoarCorpse(this.position)
    
    override fun enemyAttack()
    {
        Gdx.app.log("Boar", "only attack")
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(position, heroPosition)
        val impactTile = if (heroDirection == null) null else Tiles.getImpactTile(heroDirection)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.action { playSoundOnce(getAttackSound()) }
        animations += Animation.showTile(impactTile, heroPosition.copy(), 0.5f)
        animations += Animation.action {
            
            val attackedSpace = World.currentRoom.getSpace(heroPosition)
            when (val attackedEntity = attackedSpace?.getEntity())
            {
                is Character ->
                {
                    attackedEntity.damageCharacter(6)
                }
            }
        }
        Animating.executeAnimations(animations)
    }
    
    fun chargeThenAttack()
    {
        Gdx.app.log("Boar", "charge then attack")
        val animations = mutableListOf<Animation>()
        var endPosition = position
        Gdx.app.log("Boar", "charge")
        val pathSpaces2 = PathFinding.findPathWithGivenRoom(endPosition, World.hero.position, World.currentRoom)
        // move
        val stepsSpaces2 = pathSpaces2?.take(attackRange)
        if (!stepsSpaces2.isNullOrEmpty())
        {
            val stepsIndex2 = if (stepsSpaces2.size == pathSpaces2.size)
            {
                stepsSpaces2.size - 1
            }
            else stepsSpaces2.size
            playSoundOnce(getMoveSound())
            // move Enemy
            val startPosition2 = endPosition
            val endPosition2 = pathSpaces2[stepsIndex2].position
            val path2 = stepsSpaces2
            val enemy2 = this
            val action2 = {
                enemy2.position.set(endPosition2)
                World.currentRoom.updateSpacesEntities()
                GameScreen.updateMapBaseLayer()
                GameScreen.updateMapEntityLayer()
            }
            animations += Animation.action { enemy2.hideOwnHealthBar() }
            animations += Animation.action { com.efm.Map.changeTile(MapLayer.entity, endPosition, null) }
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
            animations += Animation.action(action2)
            
            endPosition = endPosition2
        }
        
        Gdx.app.log("Boar", "attack")
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(endPosition, heroPosition)
        val impactTile = if (heroDirection == null) null else Tiles.getImpactTile(heroDirection)
        
        //val animations = mutableListOf<Animation>()
        
        animations += Animation.action { playSoundOnce(getAttackSound()) }
        animations += Animation.showTile(impactTile, heroPosition.copy(), 0.5f)
        animations += Animation.action {
            
            val attackedSpace = World.currentRoom.getSpace(heroPosition)
            when (val attackedEntity = attackedSpace?.getEntity())
            {
                is Character ->
                {
                    attackedEntity.damageCharacter(6)
                }
            }
        }
        
        animations += Animation.action { this.displayOwnHealthBar() }
        Animating.executeAnimations(animations)
    }
    
    override fun performTurn()
    {
        Gdx.app.log("Boar", "perform turn")
        var decision = -1
        val pathSpaces = PathFinding.findPathWithGivenRoom(position, World.hero.position, World.currentRoom)
        for (pos in getSquareAreaPositions(position, attackRange))
        {
            // boar attacks only in a straight line
            if ((pos.x == position.x || pos.y == position.y) && (pos == World.hero.position))
            {
                if (kotlin.math.abs(pos.x - position.x) > 1 || kotlin.math.abs(pos.y - position.y) > 1)
                {
                    decision = 2
                }
                else
                {
                    decision = 0
                }
            }
        }
        if (decision == -1)
        {
            if (pathSpaces != null) decision = 1
        }
        
        when (decision)
        {
            0 -> enemyAttack()
            1 -> moveThenAttack()
            2 -> chargeThenAttack()
        }
    }
    
    fun moveThenAttack()
    {
        Gdx.app.log("Boar", "move then attack")
        val pathSpaces = PathFinding.findPathWithGivenRoom(position, World.hero.position, World.currentRoom)
        // move
        val stepsSpaces = pathSpaces?.take(stepsInOneTurn)
        if (!stepsSpaces.isNullOrEmpty())
        {
            val stepsIndex = if (stepsSpaces.size == pathSpaces.size)
            {
                stepsSpaces.size - 1
            }
            else stepsSpaces.size
            playSoundOnce(getMoveSound())
            // move Enemy
            val startPosition = position
            var endPosition = pathSpaces[stepsIndex].position
            val path = stepsSpaces
            val enemy = this
            val action = {
                enemy.position.set(endPosition)
                World.currentRoom.updateSpacesEntities()
                GameScreen.updateMapBaseLayer()
                GameScreen.updateMapEntityLayer()
            }
            val animations = mutableListOf<Animation>()
            animations += Animation.action { enemy.hideOwnHealthBar() }
            animations += Animation.action { com.efm.Map.changeTile(MapLayer.entity, enemy.position, null) }
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
            //animations += Animation.action { enemy.displayOwnHealthBar() }
            
            /*
            Gdx.app.log("Boar", "moved")
            Gdx.app.log(
                    "MyTag",
                    endPosition.x.toString() + " " + endPosition.y.toString() + " " + World.hero.position.x.toString() + " " + World.hero.position.y.toString()
                       )
            */
            
            // attack after moving
            for (pos in getSquareAreaPositions(endPosition, attackRange))
            {
                // boar attacks only in a straight line
                if ((pos.x == endPosition.x || pos.y == endPosition.y) && (pos == World.hero.position))
                {
                    Gdx.app.log("Boar", "in range in line")
                    if (kotlin.math.abs(pos.x - endPosition.x) > 1 || kotlin.math.abs(pos.y - endPosition.y) > 1)
                    {
                        Gdx.app.log("Boar", "charge")
                        val pathSpaces2 =
                                PathFinding.findPathWithGivenRoom(endPosition, World.hero.position, World.currentRoom)
                        // move
                        val stepsSpaces2 = pathSpaces2?.take(attackRange)
                        if (!stepsSpaces2.isNullOrEmpty())
                        {
                            val stepsIndex2 = if (stepsSpaces2.size == pathSpaces2.size)
                            {
                                stepsSpaces2.size - 1
                            }
                            else stepsSpaces2.size
                            playSoundOnce(getMoveSound())
                            // move Enemy
                            val startPosition2 = endPosition
                            val endPosition2 = pathSpaces2[stepsIndex2].position
                            val path2 = stepsSpaces2
                            val enemy2 = this
                            val action2 = {
                                enemy2.position.set(endPosition2)
                                World.currentRoom.updateSpacesEntities()
                                GameScreen.updateMapBaseLayer()
                                GameScreen.updateMapEntityLayer()
                            }
                            //animations += Animation.action { enemy2.hideOwnHealthBar() }
                            animations += Animation.action { com.efm.Map.changeTile(MapLayer.entity, endPosition, null) }
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
                            animations += Animation.action(action2)
                            
                            endPosition = endPosition2
                        }
                    }
                    Gdx.app.log("Boar", "attack")
                    val heroPosition = World.hero.position.copy()
                    val heroDirection = getDirection8(endPosition, heroPosition)
                    val impactTile = if (heroDirection == null) null else Tiles.getImpactTile(heroDirection)
                    
                    //val animations = mutableListOf<Animation>()
                    
                    animations += Animation.action { playSoundOnce(getAttackSound()) }
                    animations += Animation.showTile(impactTile, heroPosition.copy(), 0.5f)
                    animations += Animation.action {
                        
                        val attackedSpace = World.currentRoom.getSpace(heroPosition)
                        when (val attackedEntity = attackedSpace?.getEntity())
                        {
                            is Character ->
                            {
                                attackedEntity.damageCharacter(6)
                            }
                        }
                    }
                    break
                }
            }
            animations += Animation.action { enemy.displayOwnHealthBar() }
            Animating.executeAnimations(animations)
        }
    }
}
