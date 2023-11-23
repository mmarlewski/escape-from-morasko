package com.efm.entities.bosses

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.*
import com.efm.level.World
import com.efm.room.Base
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

class BossNatureGolem : Entity, Enemy
{
    override val position = RoomPosition()
    override var maxHealthPoints = 100
    override var healthPoints = 10
    override var alive = true
    override val detectionRange = 2
    override val attackRange = 5
    override val stepsInOneTurn = 5
    override lateinit var healthBar : ProgressBar
    override lateinit var healthStack : Stack
    override var isFrozen = false
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.golemIdle1
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return when (n)
        {
            1    -> Tiles.golemIdle1OutlineYellow
            2    -> Tiles.golemIdle2OutlineYellow
            3    -> Tiles.golemIdle1OutlineYellow
            4    -> Tiles.golemIdle2OutlineYellow
            else -> Tiles.golemIdle1OutlineYellow
        }
    }
    
    override fun getOutlineRedTile() : TiledMapTile
    {
        return Tiles.golemIdle1OutlineRed
    }
    
    override fun getIdleTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.golemIdle1
            2    -> Tiles.golemIdle2
            3    -> Tiles.golemIdle1
            4    -> Tiles.golemIdle2
            else -> Tiles.golemIdle1
        }
    }
    
    override fun getMoveTile(n : Int) : TiledMapTile?
    {
        return when (n)
        {
            1    -> Tiles.golemMove1
            2    -> Tiles.golemMove1
            3    -> Tiles.golemMove2
            4    -> Tiles.golemMove2
            else -> Tiles.golemMove1
        }
    }
    
    override fun getAttackTile() : TiledMapTile?
    {
        return Tiles.golemAttack
    }
    
    override fun getMoveSound() : Sound?
    {
        return Sounds.golemMove
    }
    
    override fun enemyAttack()
    {
        val golemPosition = this.position
        val golemProjectile = Tiles.golemProjectile
        
        val animations = mutableListOf<Animation>()
        
        animations.add(Animation.action { playSoundOnce(Sounds.golemAttack) })
        for (i in 1..5)
        {
            val projectileSquare = mutableListOf<Animation>()
            for (squarePerimeterPosition in getSquarePerimeterPositions(golemPosition, i))
            {
                if (LineFinding.findLineWithGivenRoom(golemPosition, squarePerimeterPosition, World.currentRoom) != null)
                {
                    val space = World.currentRoom.getSpace(squarePerimeterPosition)
                    val entity = space?.getEntity()
                    projectileSquare.add(Animation.showTile(golemProjectile, squarePerimeterPosition, 0.2f))
                    if (entity != null)
                    {
                        projectileSquare.add(Animation.showTile(Tiles.impact, squarePerimeterPosition, 0.2f))
                        projectileSquare.add(Animation.action { if (entity is Character) entity.damageCharacter(10) })
                    }
                }
            }
            animations.add(Animation.simultaneous(projectileSquare))
        }
        
        Animating.executeAnimations(animations)
    }
    
    override fun performTurn()
    {
        if (!isFrozen)
        {
            var decision = -1
            
            val directPathSpaces =
                    PathFinding.findPathInRoomForEntity(position, World.hero.position, World.currentRoom, this)
            
            var minPathLength = directPathSpaces?.size ?: Int.MAX_VALUE
            var minPathSpaces = directPathSpaces
            
            if (minPathSpaces == null)
            {
                val squarePositions = getSquareAreaPositions(World.hero.position, 2)
                for (squarePosition in squarePositions)
                {
                    val squareSpace = World.currentRoom.getSpace(squarePosition)
                    
                    if (squareSpace != null && squareSpace.isTraversableFor(this))
                    {
                        val pathSpaces =
                                PathFinding.findPathInRoomForEntity(position, squarePosition, World.currentRoom, this)
                        
                        if (!pathSpaces.isNullOrEmpty() && pathSpaces.size < minPathLength)
                        {
                            minPathLength = pathSpaces.size
                            minPathSpaces = pathSpaces
                        }
                    }
                }
            }
            
            for (pos in getSquareAreaPositions(position, attackRange))
            {
                if (pos == World.hero.position)
                {
                    decision = 0
                }
            }
            
            if (decision != 0)
            {
                if (minPathSpaces != null)
                {
                    val pathForThisTurn = minPathSpaces.take(stepsInOneTurn + 1)
                    decision = 1
                    
                    // Replace the tiles that golem goes through in this turn
                    for (space in pathForThisTurn)
                    {
                        replaceTileWithGrass(space.position)
                    }
                }
            }
            
            when (decision)
            {
                0 ->
                {
                    enemyAttack()
                }
                
                1 ->
                {
                    moveTowardsHero(minPathSpaces)
                }
            }
        }
        else
        {
            isFrozen = false
        }
    }
    
    override fun onDeath()
    {
        addBossToDefeatedBossesList(BossNatureGolem())
    }
    
    private fun replaceTileWithGrass(tilePosition : RoomPosition)
    {
        val currentPosition = World.currentRoom.getSpace(tilePosition)
        currentPosition?.changeBase(Base.grass)
        GameScreen.updateMapBaseLayer()
    }
    
}
