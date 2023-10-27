package com.efm.entities.enemies.Boar

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.Enemy
import com.efm.entity.EnemyCorpse
import com.efm.getSquareAreaPositions
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.state.State
import com.efm.state.getState
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
        else
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
    
    private fun tryToDisappear()
    {
        Gdx.app.log("EnemyGhost", "try to disappear")
    }
    
    private fun attackThenRunAwayFromHero()
    {
        Gdx.app.log("EnemyGhost", "attack then run away")
    }
    
    private fun runAwayFromHero()
    {
        Gdx.app.log("EnemyGhost", "run away")
    }
    
    private fun wanderAroundInAStraightLine()
    {
        Gdx.app.log("EnemyGhost", "wander around")
    }
}

