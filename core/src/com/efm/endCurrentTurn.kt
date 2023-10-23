package com.efm

import com.efm.level.World
import com.efm.state.*

fun endCurrentTurn()
{
    val currState = getState()
    
    when (currState)
    {
        is State.constrained ->
        {
            World.hero.regainAllAP()
            World.hero.setCanMoveToTrue()
        }
        
        is State.combat.hero ->
        {
            Map.clearLayer(MapLayer.select)
            Map.clearLayer(MapLayer.outline)
            
            val animation = Animation.showTileWithCameraFocus(null, World.hero.position.copy(), 1f)
            Animating.executeAnimations(mutableListOf(animation))
            
            val newState = State.combat.enemies.enemyUnselected.apply {
                this.isHeroAlive = currState.isHeroAlive
                this.areEnemiesInRoom = currState.areEnemiesInRoom
                this.enemies = World.currentRoom.getEnemies()
                this.enemyIterator = this.enemies?.iterator()
                this.currEnemy = when (val enemyIterator = this.enemyIterator)
                {
                    null -> null
                    else -> when (enemyIterator.hasNext())
                    {
                        true  -> enemyIterator.next()
                        false -> null
                    }
                }
            }
            setState(newState)
            World.hero.setCanMoveToTrue()
        }
        
        else                 ->
        {
            World.hero.setCanMoveToTrue()
        }
    }
}
