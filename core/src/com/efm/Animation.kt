package com.efm

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.math.Vector2
import com.efm.room.RoomPosition
import com.efm.room.toVector2
import com.efm.screens.GameScreen

sealed class Animation
{
    var startDeltaTime = 0f
    
    fun resetDeltaTime()
    {
        startDeltaTime = Animating.getDeltaTime()
    }
    
    fun deltaTimeDifference() : Float
    {
        return Animating.getDeltaTime() - startDeltaTime
    }
    
    abstract fun start()
    
    abstract fun update()
    
    abstract fun isFinished() : Boolean
    
    class sequence(val animations : MutableList<Animation>) : Animation()
    {
        val animationIterator = animations.iterator()
        var currAnimation : Animation? = null
        
        override fun start()
        {
            currAnimation = if (animationIterator.hasNext()) animationIterator.next() else null
            currAnimation?.start()
        }
        
        override fun update()
        {
            val animation = currAnimation
            
            if (animation != null)
            {
                animation.update()
                
                if (animation.isFinished())
                {
                    currAnimation = if (animationIterator.hasNext()) animationIterator.next() else null
                    currAnimation?.start()
                }
            }
        }
        
        override fun isFinished() : Boolean
        {
            return (currAnimation == null)
        }
    }
    
    class simultaneous(val animations : MutableList<Animation>) : Animation()
    {
        override fun start()
        {
            for (animation in animations)
            {
                animation.start()
            }
        }
        
        override fun update()
        {
            val finishedAnimations = mutableListOf<Animation>()
            for (animation in animations)
            {
                animation.update()
                if (animation.isFinished())
                {
                    finishedAnimations.add(animation)
                }
            }
            animations.removeAll(finishedAnimations)
        }
        
        override fun isFinished() : Boolean
        {
            return animations.isEmpty()
        }
    }
    
    object none : Animation()
    {
        override fun start()
        {
        }
        
        override fun update()
        {
        }
        
        override fun isFinished() : Boolean
        {
            return true
        }
    }
    
    class wait(val seconds : Float) : Animation()
    {
        override fun start()
        {
            resetDeltaTime()
        }
        
        override fun update()
        {
        }
        
        override fun isFinished() : Boolean
        {
            return (deltaTimeDifference() > seconds)
        }
    }
    
    open class moveTile(
            val tile : TiledMapTile,
            val from : RoomPosition,
            val to : RoomPosition,
            val seconds : Float
                       ) : Animation()
    {
        val moveTilePosition = Vector2()
        
        override fun start()
        {
            resetDeltaTime()
            moveTilePosition.set(from.toVector2())
        }
        
        override fun update()
        {
            val timeRatio = deltaTimeDifference() / seconds
            val tileDistanceX = to.x - from.x
            val tileDistanceY = to.y - from.y
            moveTilePosition.x = from.x + tileDistanceX * timeRatio
            moveTilePosition.y = from.y + tileDistanceY * timeRatio
        }
        
        override fun isFinished() : Boolean
        {
            return (deltaTimeDifference() > seconds)
        }
    }
    
    class moveTileWithCameraFocus(
            tile : TiledMapTile,
            from : RoomPosition,
            to : RoomPosition,
            seconds : Float
                                 ) : moveTile(tile, from, to, seconds)
    {
    
        override fun update()
        {
            super.update()
            
            GameScreen.focusCameraOnVector2(moveTilePosition)
        }
    
    }
    
    open class showTile(
            val tile : TiledMapTile,
            val where : RoomPosition,
            val seconds : Float
                       ) : Animation()
    {
        
        override fun start()
        {
            resetDeltaTime()
        }
        
        override fun update()
        {
        }
        
        override fun isFinished() : Boolean
        {
            return (deltaTimeDifference() > seconds)
        }
    }
    
    class showTileWithCameraFocus(
            tile : TiledMapTile,
            where : RoomPosition,
            seconds : Float
                                 ) : showTile(tile, where, seconds)
    {
        override fun start()
        {
            super.start()
            
            GameScreen.focusCameraOnRoomPosition(where)
        }
    
    }
    
    class action(val action : () -> Unit) : Animation()
    {
        override fun start()
        {
            action()
        }
        
        override fun update()
        {
        }
        
        override fun isFinished() : Boolean
        {
            return true
        }
    }
}
