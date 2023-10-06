package com.efm.entities.enemies

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.efm.*
import com.efm.assets.Sounds
import com.efm.assets.Tiles
import com.efm.entity.*
import com.efm.item.Item
import com.efm.item.PossibleItems
import com.efm.level.World
import com.efm.room.RoomPosition

class EnemyBoar(
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
    
    override fun getCorpse() : Entity
    {
        return EnemyBoarCorpse(this.position)
    }
    
    override fun enemyAttack()
    {
        val heroPosition = World.hero.position.copy()
        val heroDirection = getDirection8(this.position, heroPosition)
        val impactTile = if (heroDirection == null) null else Tiles.getImpactTile(heroDirection)
        
        val animations = mutableListOf<Animation>()
        
        animations += Animation.action { playSoundOnce(getAttackSound()) }
        animations += Animation.showTile(impactTile, heroPosition.copy(), 0.5f)
        animations += Animation.action {
            
            val attackedSpace = World.currentRoom.getSpace(heroPosition)
            when (val attackedEntity = attackedSpace?.getEntity())
            {
                is Character -> attackedEntity.damageCharacter(6)
            }
        }
        Animating.executeAnimations(animations)
    }
    
    override fun performTurn()
    {
        var decision = -1
        val pathSpaces = PathFinding.findPathWithGivenRoom(position, World.hero.position, World.currentRoom)
        for (pos in getSquareAreaPositions(position, attackRange))
        {
            // boar attacks only in a straight line
            if ((pos.x == position.x || pos.y == position.y) && (pos == World.hero.position))
                decision = 0
        }
        if (decision != 0)
        {
            if (pathSpaces != null)
                decision = 1
        }
        
        when (decision)
        {
            0 -> enemyAttack()
            
            1 ->
            {
                val stepsSpaces = pathSpaces?.take(stepsInOneTurn)
                if (!stepsSpaces.isNullOrEmpty())
                {
                    val stepsIndex = if (stepsSpaces.size == pathSpaces.size)
                    {
                        stepsSpaces.size - 1
                    }
                    else stepsSpaces.size
                    playSoundOnce(getMoveSound())
                    moveEnemy(position, pathSpaces[stepsIndex].position, stepsSpaces, this)
                }
                // attack after moving
                for (pos in getSquareAreaPositions(position, attackRange))
                {
                    // boar attacks only in a straight line
                    if (pos.x == position.x || pos.y == position.y) if (pos == World.hero.position)
                    {
                        enemyAttack()
                        break
                    }
                }
            }
        }
    }
}

class EnemyBoarCorpse(
        override val position : RoomPosition = RoomPosition()
                     ) : EnemyCorpse
{
    override var loot = PossibleItems()
    override val items : MutableList<Item> = loot.drawItems()
    override var maxItems : Int = 2
    
    override fun getTile() : TiledMapTile = Tiles.boarCorpse
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.boarCorpseOutlineYellow
    
    override fun getOutlineTealTile() : TiledMapTile? = null
}
