package com.efm

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.room.toVector2
import com.efm.screens.GameScreen
import kotlin.math.abs

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
    
    open class sequence(val animations : List<Animation>) : Animation()
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
    
    class simultaneous(animations : List<Animation>) : Animation()
    {
        val mutAnimations = animations.toMutableList()
        
        override fun start()
        {
            for (animation in mutAnimations)
            {
                animation.start()
            }
        }
        
        override fun update()
        {
            val finishedAnimations = mutableListOf<Animation>()
            for (animation in mutAnimations)
            {
                animation.update()
                if (animation.isFinished())
                {
                    finishedAnimations.add(animation)
                }
            }
            mutAnimations.removeAll(finishedAnimations)
        }
        
        override fun isFinished() : Boolean
        {
            return mutAnimations.isEmpty()
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
    
    open class moveCameraWithRoomPositions(
            val from : RoomPosition,
            val to : RoomPosition,
            val seconds : Float
                                          ) : Animation()
    {
        val moveCameraPosition = Vector2()
        
        override fun start()
        {
            resetDeltaTime()
            moveCameraPosition.set(from.toVector2())
        }
        
        override fun update()
        {
            val timeRatio = deltaTimeDifference() / seconds
            val cameraDistanceX = to.x - from.x
            val cameraDistanceY = to.y - from.y
            moveCameraPosition.x = from.x + cameraDistanceX * timeRatio
            moveCameraPosition.y = from.y + cameraDistanceY * timeRatio
            GameScreen.focusCameraOnVector2Position(moveCameraPosition)
        }
        
        override fun isFinished() : Boolean
        {
            return (deltaTimeDifference() > seconds)
        }
    }
    
    open class moveCameraWithIsoPositions(
            val from : Vector2,
            val to : Vector2,
            val seconds : Float
                                         ) : Animation()
    {
        val moveCameraPosition = Vector2()
        
        override fun start()
        {
            resetDeltaTime()
            moveCameraPosition.set(from)
        }
        
        override fun update()
        {
            val timeRatio = deltaTimeDifference() / seconds
            val cameraDistanceX = to.x - from.x
            val cameraDistanceY = to.y - from.y
            moveCameraPosition.x = from.x + cameraDistanceX * timeRatio
            moveCameraPosition.y = from.y + cameraDistanceY * timeRatio
            GameScreen.focusCameraOnIsoPosition(moveCameraPosition)
        }
        
        override fun isFinished() : Boolean
        {
            return (deltaTimeDifference() > seconds)
        }
    }
    
    open class moveCameraSmoothlyWithRoomPositions(
            val from : RoomPosition?,
            val to : RoomPosition,
            val smoothness : Float
                                                  ) : Animation()
    {
        val moveCameraPosition = Vector2()
        
        override fun start()
        {
            resetDeltaTime()
            
            if (from != null)
            {
                moveCameraPosition.set(from.toVector2())
            }
            else
            {
                val isoCameraPosition = GameScreen.getCameraPosition()
                val orthoCameraPosition = isoToOrtho(isoCameraPosition)
                val roomCameraPosition = orthoToRoomPosition(orthoCameraPosition)
                moveCameraPosition.set(roomCameraPosition.toVector2())
            }
        }
        
        override fun update()
        {
            val cameraDistanceX = to.x - moveCameraPosition.x
            val cameraDistanceY = to.y - moveCameraPosition.y
            moveCameraPosition.x += cameraDistanceX * smoothness
            moveCameraPosition.y += cameraDistanceY * smoothness
            GameScreen.focusCameraOnVector2Position(moveCameraPosition)
        }
        
        override fun isFinished() : Boolean
        {
            val tileDistanceX = to.x - moveCameraPosition.x
            val tileDistanceY = to.y - moveCameraPosition.y
            return (abs(tileDistanceX) < smoothness && abs(tileDistanceY) < smoothness)
        }
    }
    
    open class moveCameraSmoothlyWithIsoPositions(
            val from : Vector2?,
            val to : Vector2,
            val smoothness : Float
                                                 ) : Animation()
    {
        val moveCameraPosition = Vector2()
        
        override fun start()
        {
            resetDeltaTime()
            
            if (from != null)
            {
                moveCameraPosition.set(from)
            }
            else
            {
                val isoCameraPosition = GameScreen.getCameraPosition()
                val orthoCameraPosition = isoToOrtho(isoCameraPosition)
                val roomCameraPosition = orthoToRoomPosition(orthoCameraPosition)
                moveCameraPosition.set(roomCameraPosition.toVector2())
            }
        }
        
        override fun update()
        {
            val cameraDistanceX = to.x - moveCameraPosition.x
            val cameraDistanceY = to.y - moveCameraPosition.y
            moveCameraPosition.x += cameraDistanceX * smoothness
            moveCameraPosition.y += cameraDistanceY * smoothness
            GameScreen.focusCameraOnIsoPosition(moveCameraPosition)
        }
        
        override fun isFinished() : Boolean
        {
            val tileDistanceX = to.x - moveCameraPosition.x
            val tileDistanceY = to.y - moveCameraPosition.y
            return (abs(tileDistanceX) < smoothness && abs(tileDistanceY) < smoothness)
        }
    }
    
    class focusCamera(val on : RoomPosition, val seconds : Float) : Animation()
    {
        override fun start()
        {
            resetDeltaTime()
        }
        
        override fun update()
        {
            GameScreen.focusCameraOnRoomPosition(on)
        }
        
        override fun isFinished() : Boolean
        {
            return (deltaTimeDifference() > seconds)
        }
    }
    
    open class ascendTile(
            val tile : TiledMapTile?,
            val on : RoomPosition,
            val seconds : Float,
            val heightPercent : Float
                         ) : Animation()
    {
        var ascendPercent = 0.0f
        
        override fun start()
        {
            resetDeltaTime()
        }
        
        override fun update()
        {
            ascendPercent = deltaTimeDifference() / seconds
        }
        
        override fun isFinished() : Boolean
        {
            return (deltaTimeDifference() > seconds)
        }
    }
    
    class ascendTileWithCameraFocus(
            tile : TiledMapTile?,
            on : RoomPosition,
            seconds : Float,
            heightPercent : Float
                                   ) : ascendTile(tile, on, seconds, heightPercent)
    {
        override fun update()
        {
            super.update()
            
            val orthoPosition = roomPositionToOrtho(on)
            val isoPosition = orthoToIso(orthoPosition)
            val ascendedIsoPosition =
                    Vector2(isoPosition.x, isoPosition.y + ascendPercent * heightPercent * Map.tileLengthInPixels)
            GameScreen.focusCameraOnIsoPosition(ascendedIsoPosition)
        }
    }
    
    open class descendTile(
            val tile : TiledMapTile?,
            val on : RoomPosition,
            val seconds : Float,
            val heightPercent : Float
                          ) : Animation()
    {
        var descendPercent = 0.0f
        
        override fun start()
        {
            resetDeltaTime()
        }
        
        override fun update()
        {
            descendPercent = deltaTimeDifference() / seconds
        }
        
        override fun isFinished() : Boolean
        {
            return (deltaTimeDifference() > seconds)
        }
    }
    
    class descendTileWithCameraFocus(
            tile : TiledMapTile?,
            on : RoomPosition,
            seconds : Float,
            heightPercent : Float
                                    ) : descendTile(tile, on, seconds, heightPercent)
    {
        override fun update()
        {
            super.update()
            
            val orthoPosition = roomPositionToOrtho(on)
            val isoPosition = orthoToIso(orthoPosition)
            val ascendedIsoPosition =
                    Vector2(isoPosition.x, isoPosition.y + (1 - descendPercent) * heightPercent * Map.tileLengthInPixels)
            GameScreen.focusCameraOnIsoPosition(ascendedIsoPosition)
        }
    }
    
    open class moveTile(
            val tile : TiledMapTile?,
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
            tile : TiledMapTile?,
            from : RoomPosition,
            to : RoomPosition,
            seconds : Float
                                 ) : moveTile(tile, from, to, seconds)
    {
        
        override fun update()
        {
            super.update()
            
            GameScreen.focusCameraOnVector2Position(moveTilePosition)
        }
        
    }
    
    open class moveTileSmoothly(
            val tile : TiledMapTile?,
            val from : RoomPosition?,
            val to : RoomPosition,
            val smoothness : Float
                               ) : Animation()
    {
        val moveTilePosition = Vector2()
        
        override fun start()
        {
            resetDeltaTime()
            
            if (from != null)
            {
                moveTilePosition.set(from.toVector2())
            }
            else
            {
                val isoCameraPosition = GameScreen.getCameraPosition()
                val orthoCameraPosition = isoToOrtho(isoCameraPosition)
                val roomCameraPosition = orthoToRoomPosition(orthoCameraPosition)
                moveTilePosition.set(roomCameraPosition.toVector2())
            }
        }
        
        override fun update()
        {
            val tileDistanceX = to.x - moveTilePosition.x
            val tileDistanceY = to.y - moveTilePosition.y
            moveTilePosition.x += tileDistanceX * smoothness
            moveTilePosition.y += tileDistanceY * smoothness
        }
        
        override fun isFinished() : Boolean
        {
            val tileDistanceX = to.x - moveTilePosition.x
            val tileDistanceY = to.y - moveTilePosition.y
            return (abs(tileDistanceX) < smoothness && abs(tileDistanceY) < smoothness)
        }
    }
    
    class moveTileSmoothlyWithCameraFocus(
            tile : TiledMapTile?,
            from : RoomPosition?,
            to : RoomPosition,
            smoothness : Float
                                         ) : moveTileSmoothly(tile, from, to, smoothness)
    {
        
        override fun update()
        {
            super.update()
            
            GameScreen.focusCameraOnVector2Position(moveTilePosition)
        }
        
    }
    
    open class moveTileWithArch(
            tile : TiledMapTile?,
            from : RoomPosition,
            to : RoomPosition,
            seconds : Float,
            val heightPercent : Float
                               ) : moveTile(tile, from, to, seconds)
    {
        var movePercent = 0.0f
    
        override fun update()
        {
            super.update()
        
            movePercent = deltaTimeDifference() / seconds
        }
    }
    
    class moveTileWithArchAndCameraFocus(
            tile : TiledMapTile?,
            from : RoomPosition,
            to : RoomPosition,
            seconds : Float,
            heightPercent : Float
                                        ) : moveTileWithArch(tile, from, to, seconds, heightPercent)
    {
        override fun update()
        {
            super.update()
            
            GameScreen.focusCameraOnVector2Position(moveTilePosition)
        }
    }
    
    open class showTile(
            val tile : TiledMapTile?,
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
    
    open class flashProgressBar(
            val bar : ProgressBar?,
            val label : Label,
            val value : Float,
            val seconds : Float
                               ) : Animation()
    {
        
        override fun start()
        {
            resetDeltaTime()
        }
        
        override fun update()
        {
            label.setText("${value.toInt()} / ${World.hero.maxAbilityPoints}")
            bar?.value = value
        }
        
        override fun isFinished() : Boolean
        {
            return (deltaTimeDifference() > seconds)
        }
    }
    
    class showTileWithCameraFocus(
            tile : TiledMapTile?,
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
    
    companion object
    {
        fun cameraShake(
                moves : Int,
                seconds : Float,
                moveDistance : Float = 3.0f
                       ) : sequence
        {
            val moveSpeed = seconds / (4 + 4 * moves)
            
            val animations = mutableListOf<Animation>()
            
            val centerCameraPosition = GameScreen.getCameraPosition()
            val rightCameraPosition = Vector2(centerCameraPosition.x + moveDistance, centerCameraPosition.y + 0f)
            val leftCameraPosition = Vector2(centerCameraPosition.x - moveDistance, centerCameraPosition.y + 0f)
            
            animations.add(moveCameraWithIsoPositions(centerCameraPosition, rightCameraPosition, moveSpeed * 1))
            animations.add(moveCameraWithIsoPositions(rightCameraPosition, leftCameraPosition, moveSpeed * 2))
            for (i in 1 until moves)
            {
                animations.add(moveCameraWithIsoPositions(leftCameraPosition, rightCameraPosition, moveSpeed * 2))
                animations.add(moveCameraWithIsoPositions(rightCameraPosition, leftCameraPosition, moveSpeed * 2))
            }
            animations.add(moveCameraWithIsoPositions(leftCameraPosition, centerCameraPosition, moveSpeed * 1))
            
            return sequence(animations)
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
