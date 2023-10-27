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
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen
import com.efm.state.State
import com.efm.state.getState
import kotlin.math.roundToInt
import kotlin.random.Random

class EnemyGhost(
        override val position : RoomPosition = RoomPosition(),
        override var maxHealthPoints : Int = 40,
        override var healthPoints : Int = maxHealthPoints,
        private val generator : Random = Random(Random.nextInt())
                ) : Enemy
{
    override var alive : Boolean = true
    
    override val detectionRange : Int = 4
    override val attackRange : Int = 1
    override val stepsInOneTurn : Int = 4
    
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    
    private val maxTurnsUntilTryToDisappear : Int = 4
    private var turnsUntilTryToDisappear : Int = maxTurnsUntilTryToDisappear
    
    var room : Room? = null
    var positionsWhereGhostCanDisappear : MutableList<RoomPosition>? = null
    var closestDisappearPosition : RoomPosition? = null
    
    override fun getTile() : TiledMapTile = when
    {
        turnsUntilTryToDisappear >= 0.67 * maxTurnsUntilTryToDisappear -> Tiles.ghost3Idle1
        0 >= turnsUntilTryToDisappear                                  -> Tiles.ghost1Idle1
        else                                                           -> Tiles.ghost2Idle1
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile = when
    {
        turnsUntilTryToDisappear >= 0.67 * maxTurnsUntilTryToDisappear -> when (n)
        {
            1    -> Tiles.ghost3Idle1
            2    -> Tiles.ghost3Idle2
            3    -> Tiles.ghost3Idle3
            4    -> Tiles.ghost3Idle2
            else -> Tiles.ghost3Idle1
        }
        0 >= turnsUntilTryToDisappear                                  -> when (n)
        {
            1    -> Tiles.ghost1Idle1
            2    -> Tiles.ghost1Idle2
            3    -> Tiles.ghost1Idle3
            4    -> Tiles.ghost1Idle2
            else -> Tiles.ghost1Idle1
        }
        else                                                           -> when (n)
        {
            1    -> Tiles.ghost2Idle1
            2    -> Tiles.ghost2Idle2
            3    -> Tiles.ghost2Idle3
            4    -> Tiles.ghost2Idle2
            else -> Tiles.ghost2Idle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile = when
    {
        turnsUntilTryToDisappear >= 0.67 * maxTurnsUntilTryToDisappear -> when (n)
        {
            1    -> Tiles.ghost3Move1
            2    -> Tiles.ghost3Move2
            3    -> Tiles.ghost3Move3
            4    -> Tiles.ghost3Move2
            else -> Tiles.ghost3Move1
        }
        0 >= turnsUntilTryToDisappear                                  -> when (n)
        {
            1    -> Tiles.ghost1Move1
            2    -> Tiles.ghost1Move2
            3    -> Tiles.ghost1Move3
            4    -> Tiles.ghost1Move2
            else -> Tiles.ghost1Move1
        }
        else                                                           -> when (n)
        {
            1    -> Tiles.ghost2Move1
            2    -> Tiles.ghost2Move2
            3    -> Tiles.ghost2Move3
            4    -> Tiles.ghost2Move2
            else -> Tiles.ghost2Move1
        }
    }
    
    override fun getAttackTile() : TiledMapTile = when
    {
        turnsUntilTryToDisappear >= 0.67 * maxTurnsUntilTryToDisappear -> Tiles.ghost3Idle3
        0 >= turnsUntilTryToDisappear                                  -> Tiles.ghost1Idle3
        else                                                           -> Tiles.ghost2Idle3
    }
    
    override fun getOutlineRedTile() : TiledMapTile = Tiles.ghostIdle1OutlineRed
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = when (n)
    {
        1    -> Tiles.ghostIdle1OutlineYellow
        2    -> Tiles.ghostIdle2OutlineYellow
        3    -> Tiles.ghostIdle3OutlineYellow
        4    -> Tiles.ghostIdle2OutlineYellow
        else -> Tiles.ghostIdle1OutlineYellow
    }
    
    override fun getMoveSound() : Sound? = null//Sounds.ghostAppear
    
    private fun getAttackSound() : Sound = Sounds.ghostAppear
    
    override fun getCorpse() : EnemyCorpse = EnemyGhostCorpse(this.position)
    
    override fun performTurn()
    {
        Gdx.app.log("EnemyGhost", "perform turn")
        
        val doNothing = -1
        val tryToDisappear = 0
        val attackThenRunAwayFromHero = 1
        val runAwayFromHero = 2
        val wanderAroundInAStraightLine = 3
        
        val decision = if (ghostsKnowsAboutHero())
        {
            if (turnsUntilTryToDisappear <= 0) tryToDisappear
            else if (heroIsInAttackRange()) attackThenRunAwayFromHero
            else runAwayFromHero
        }
        else    // currently performTurn() is called only in combat, so this is not reachable
        {
            if (generator.nextFloat() >= 0.5) wanderAroundInAStraightLine
            else doNothing
        }
        
        when (decision)
        {
            tryToDisappear              -> tryToDisappear()
            attackThenRunAwayFromHero   -> attackThenRunAwayFromHero()
            runAwayFromHero             -> runAwayFromHero()
            wanderAroundInAStraightLine -> wanderAroundInAStraightLine()
        }
        
        turnsUntilTryToDisappear--
    }
    
    override fun enemyAttack()
    {
    }
    
    private fun ghostsKnowsAboutHero() : Boolean
    {
        val state = getState()
        return (state is State.constrained && state.isHeroDetected) || state is State.combat
    }
    
    private fun heroIsInAttackRange() : Boolean
    {
        for (pos in getSquareAreaPositions(position, attackRange))
        {
            if (pos == World.hero.position) return true
        }
        return false
    }
    
    private fun findPositionsWhereGhostCanDisappear() : MutableList<RoomPosition>
    {
        // edges
        val upEdge = 0
        val downEdge = World.currentRoom.heightInSpaces
        val leftEdge = 0
        val rightEdge = World.currentRoom.widthInSpaces
        
        val disappearSpacesLeft = Array<Int?>(World.currentRoom.heightInSpaces) { null }
        for (x in leftEdge until rightEdge)
        {
            for (y in upEdge until downEdge)
            {
                if (World.currentRoom.getSpace(x, y)
                                ?.getBase()?.isTreadable == true && disappearSpacesLeft[y] == null
                ) disappearSpacesLeft[y] = x
            }
        }
        val disappearSpacesRight = Array<Int?>(World.currentRoom.heightInSpaces) { null }
        for (x in rightEdge - 1 downTo leftEdge)
        {
            for (y in upEdge until downEdge)
            {
                if (World.currentRoom.getSpace(x, y)
                                ?.getBase()?.isTreadable == true && disappearSpacesRight[y] == null
                ) disappearSpacesRight[y] = x
            }
        }
        val disappearSpacesUp = Array<Int?>(World.currentRoom.widthInSpaces) { null }
        for (y in upEdge until downEdge)
        {
            for (x in leftEdge until rightEdge)
            {
                if (World.currentRoom.getSpace(x, y)
                                ?.getBase()?.isTreadable == true && disappearSpacesUp[x] == null
                ) disappearSpacesUp[x] = y
            }
        }
        val disappearSpacesDown = Array<Int?>(World.currentRoom.widthInSpaces) { null }
        for (y in downEdge - 1 downTo upEdge)
        {
            for (x in leftEdge until rightEdge)
            {
                if (World.currentRoom.getSpace(x, y)
                                ?.getBase()?.isTreadable == true && disappearSpacesDown[x] == null
                ) disappearSpacesDown[x] = y
            }
        }
        /*
        Gdx.app.log("EnemyGhost",disappearSpacesLeft.contentToString())
        Gdx.app.log("EnemyGhost",disappearSpacesRight.contentToString())
        Gdx.app.log("EnemyGhost",disappearSpacesUp.contentToString())
        Gdx.app.log("EnemyGhost",disappearSpacesDown.contentToString())
        */
        val disappearSpaces = mutableListOf<RoomPosition>()
        for (y in 0 until World.currentRoom.heightInSpaces)
        {
            val x = disappearSpacesLeft[y]
            if (x != null) disappearSpaces.add(RoomPosition(x, y))
        }
        for (y in 0 until World.currentRoom.heightInSpaces)
        {
            val x = disappearSpacesRight[y]
            if (x != null) disappearSpaces.add(RoomPosition(x, y))
        }
        for (x in 0 until World.currentRoom.widthInSpaces)
        {
            val y = disappearSpacesUp[x]
            if (y != null) disappearSpaces.add(RoomPosition(x, y))
        }
        for (x in 0 until World.currentRoom.widthInSpaces)
        {
            val y = disappearSpacesDown[x]
            if (y != null) disappearSpaces.add(RoomPosition(x, y))
        }
        
        return disappearSpaces
    }
    
    private fun tryToDisappear()
    {
        Gdx.app.log("EnemyGhost", "try to disappear")
        
        // initialize animations
        val animations = mutableListOf<Animation>()
        animations += Animation.action { this.hideOwnHealthBar() }
        
        // find positions where Ghost can disappear if there are not found or they are found for wrong room
        if (positionsWhereGhostCanDisappear == null || this.room != World.currentRoom) positionsWhereGhostCanDisappear =
                findPositionsWhereGhostCanDisappear()
        
        // if ghost is on the edge
        if (positionsWhereGhostCanDisappear?.contains(position) == true)
        {
            // disappear (go into edge)
            this.healthBar.remove()
            World.currentRoom.removeEntity(this)
            return
        }
        else
        {
            // if Ghost is not on the edge
            
            // can ghost go to closest position where it can disappear
            val pom = closestDisappearPosition
            val reachable = if (pom != null)
            {
                !PathFinding.findPathWithGivenRoom(position, pom, World.currentRoom).isNullOrEmpty()
            }
            else false
            
            // if ghost can not go find new closest position where it can disappear
            if (!reachable)
            {
                var maxDistance = Int.MAX_VALUE
                for (pos in positionsWhereGhostCanDisappear!!)
                {
                    val path = PathFinding.findPathWithGivenRoom(position, pos, World.currentRoom)
                    if (!path.isNullOrEmpty() && World.currentRoom.getSpace(pos)?.isTraversable() == true)
                    {
                        val distance = path.size
                        if (distance < maxDistance)
                        {
                            maxDistance = distance
                            closestDisappearPosition = pos
                        }
                    }
                }
            }
            Gdx.app.log("EnemyGhost", "miejsce $closestDisappearPosition")
    
            // move to closest disappear position
    
            val pathSpaces = PathFinding.findPathWithGivenRoom(position, closestDisappearPosition!!, World.currentRoom)
            // move slower when disappearing
            val stepsSpaces = pathSpaces?.take((0.75 * stepsInOneTurn).roundToInt())
            if (!stepsSpaces.isNullOrEmpty())
            {
                val stepsIndex = if (stepsSpaces.size == pathSpaces.size)
                {
                    stepsSpaces.size - 1
                }
                else stepsSpaces.size
                // playSoundOnce(getMoveSound())
                val startPosition = position
                val endPosition = pathSpaces[stepsIndex].position
                Gdx.app.log("EnemyGhost", "ide do $endPosition")
                val path = stepsSpaces
                val enemy = this
                
                val beforeMoveAction = {
                    World.currentRoom.getSpace(enemy.position)?.clearEntity()
                    GameScreen.updateMapBaseLayer()
                    GameScreen.updateMapEntityLayer()
                }
                val afterMoveAction = {
                    enemy.position.set(endPosition)
                    World.currentRoom.updateSpacesEntities()
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
            }
        }
        
        // conclude animations
        animations += Animation.action { this.displayOwnHealthBar() }
        Animating.executeAnimations(animations)
        Gdx.app.log("EnemyGhost", "tried")
    }
    
    private fun attackThenRunAwayFromHero()
    {
        Gdx.app.log("EnemyGhost", "attack then run away")
    
        // initialize animations
        val animations = mutableListOf<Animation>()
        animations += Animation.action { this.hideOwnHealthBar() }
    
        // attack
    
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(position, heroPosition)
        val impactTile = if (heroDirection == null) null else Tiles.getImpactTile(heroDirection)
    
        animations += Animation.action { playSoundOnce(getAttackSound()) }
        animations += Animation.showTile(impactTile, heroPosition.copy(), 0.5f)
        animations += Animation.action {
            val attackedSpace = World.currentRoom.getSpace(heroPosition)
            when (val attackedEntity = attackedSpace?.getEntity())
            {
                is Character -> attackedEntity.damageCharacter(20)
            }
        }
    
        // run away
    
        // find position that Ghost can move to that is furthest from Hero
        var positionFurthestFromHero = position
        var maxDistanceFromHero =
                PathFinding.findPathWithGivenRoom(World.hero.position, position, World.currentRoom)?.size ?: Int.MAX_VALUE
        for (pos in getSquareAreaPositions(position, stepsInOneTurn))
        {
            // maybe change to distanceBetween if performance is bad?
            val reachable = !PathFinding.findPathWithGivenRoom(position, pos, World.currentRoom)
                    .isNullOrEmpty() && World.currentRoom.getSpace(pos)?.isTraversable() == true    // do not go into walls
            val distanceFromHero =
                    PathFinding.findPathWithGivenRoom(World.hero.position, pos, World.currentRoom)?.size ?: Int.MAX_VALUE
            if (distanceFromHero > maxDistanceFromHero && reachable)
            {
                maxDistanceFromHero = distanceFromHero
                positionFurthestFromHero = pos
            }
        }
    
        // do not move from and to same place
        if (position != positionFurthestFromHero)
        {
            val pathSpaces = PathFinding.findPathWithGivenRoom(position, positionFurthestFromHero, World.currentRoom)
            // move slower after attackig
            val stepsSpaces = pathSpaces?.take((0.75 * stepsInOneTurn).roundToInt())
            if (!stepsSpaces.isNullOrEmpty())
            {
                val stepsIndex = if (stepsSpaces.size == pathSpaces.size)
                {
                    stepsSpaces.size - 1
                }
                else stepsSpaces.size
                // playSoundOnce(getMoveSound())
                val startPosition = position
                val endPosition = pathSpaces[stepsIndex].position
                val path = stepsSpaces
                val enemy = this
            
                val beforeMoveAction = {
                    World.currentRoom.getSpace(enemy.position)?.clearEntity()
                    GameScreen.updateMapBaseLayer()
                    GameScreen.updateMapEntityLayer()
                }
                val afterMoveAction = {
                    enemy.position.set(endPosition)
                    World.currentRoom.updateSpacesEntities()
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
            }
        }
    
        // conclude animations
        animations += Animation.action { this.displayOwnHealthBar() }
        Animating.executeAnimations(animations)
    }
    
    private fun runAwayFromHero()
    {
        Gdx.app.log("EnemyGhost", "run away")
    
        // initialize animations
        val animations = mutableListOf<Animation>()
        animations += Animation.action { this.hideOwnHealthBar() }
    
        // run away
    
        // find position that Ghost can move to that is furthest from Hero
        var positionFurthestFromHero = position
        var maxDistanceFromHero =
                PathFinding.findPathWithGivenRoom(World.hero.position, position, World.currentRoom)?.size ?: Int.MAX_VALUE
        for (pos in getSquareAreaPositions(position, stepsInOneTurn))
        {
            // maybe change to distanceBetween if performance is bad?
            val reachable = !PathFinding.findPathWithGivenRoom(position, pos, World.currentRoom)
                    .isNullOrEmpty() && World.currentRoom.getSpace(pos)?.isTraversable() == true    // do not go into walls
            val distanceFromHero =
                    PathFinding.findPathWithGivenRoom(World.hero.position, pos, World.currentRoom)?.size ?: Int.MAX_VALUE
            if (distanceFromHero > maxDistanceFromHero && reachable)
            {
                maxDistanceFromHero = distanceFromHero
                positionFurthestFromHero = pos
            }
        }
    
        // do not move from and to same place
        if (position != positionFurthestFromHero)
        {
            val pathSpaces = PathFinding.findPathWithGivenRoom(position, positionFurthestFromHero, World.currentRoom)
            val stepsSpaces = pathSpaces?.take(stepsInOneTurn)
            if (!stepsSpaces.isNullOrEmpty())
            {
                val stepsIndex = if (stepsSpaces.size == pathSpaces.size)
                {
                    stepsSpaces.size - 1
                }
                else stepsSpaces.size
                // playSoundOnce(getMoveSound())
                val startPosition = position
                val endPosition = pathSpaces[stepsIndex].position
                val path = stepsSpaces
                val enemy = this
            
                val beforeMoveAction = {
                    World.currentRoom.getSpace(enemy.position)?.clearEntity()
                    GameScreen.updateMapBaseLayer()
                    GameScreen.updateMapEntityLayer()
                }
                val afterMoveAction = {
                    enemy.position.set(endPosition)
                    World.currentRoom.updateSpacesEntities()
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
            }
        }
    
        // conclude animations
        animations += Animation.action { this.displayOwnHealthBar() }
        Animating.executeAnimations(animations)
    }
    
    private fun wanderAroundInAStraightLine()
    {
        Gdx.app.log("EnemyGhost", "wander around")
    }
}
