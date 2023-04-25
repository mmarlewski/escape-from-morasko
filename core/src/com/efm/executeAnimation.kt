package com.efm

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

abstract class Animation
{
    abstract fun execute()
}

class wait(val seconds : Float) : Animation()
{
    override fun execute()
    {
        GameScreen.deltaTime = 0f
        GameScreen.isWait = true
        GameScreen.waitTimeInSeconds = seconds
    }
}

class changeMapTile(val mapLayer : MapLayer, val x : Int, val y : Int, val tile : TiledMapTile?) : Animation()
{
    override fun execute()
    {
        Map.changeTile(mapLayer, x, y, tile)
    }
}

class focusGameScreenCamera(val position : RoomPosition) : Animation()
{
    override fun execute()
    {
        GameScreen.focusCamera(position)
    }
}

class action(val action : () -> Unit) : Animation()
{
    override fun execute()
    {
        action()
    }
}

fun GameScreen.executeAnimations(animationList : MutableList<Animation>)
{
    isAnimation = true
    animations = animationList
    nextAnimation = animations.firstOrNull()
}
