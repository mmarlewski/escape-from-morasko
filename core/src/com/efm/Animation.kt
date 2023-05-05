package com.efm

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

interface Animation
{
    fun execute()
}

class wait(val seconds : Float) : Animation
{
    override fun execute()
    {
        Animating.deltaTime = 0f
        Animating.isWaiting = true
        Animating.waitTimeInSeconds = seconds
    }
}

class changeMapTile(val mapLayer : MapLayer, val x : Int, val y : Int, val tile : TiledMapTile?) : Animation
{
    override fun execute()
    {
        Map.changeTile(mapLayer, x, y, tile)
    }
}

class focusGameScreenCamera(val position : RoomPosition) : Animation
{
    override fun execute()
    {
        GameScreen.focusCameraOnRoomPosition(position)
    }
}

class action(val action : () -> Unit) : Animation
{
    override fun execute()
    {
        action()
    }
}
