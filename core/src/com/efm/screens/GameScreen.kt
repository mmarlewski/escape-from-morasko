package com.efm.screens

import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.efm.*
import com.efm.Map
import com.efm.assets.Colors
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.state.*
import com.efm.ui.gameScreen.*

object GameScreen : BaseScreen(), GestureListener
{
    val hudCamera = OrthographicCamera()
    val gameCamera = OrthographicCamera()
    val hudViewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, hudCamera)
    val gameViewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, gameCamera)
    val stage = Stage(hudViewport, EscapeFromMorasko.spriteBatch)
    val gameStage = Stage(gameViewport, EscapeFromMorasko.spriteBatch)
    val inputMultiplexer = InputMultiplexer(stage, GestureDetector(this))
    val mapRenderer = CustomIsometricTiledMapRenderer(Map.tiledMap, 1f, EscapeFromMorasko.spriteBatch)
    
    // dragging
    var isDragging = false
    val dragOriginPosition = Vector2()
    
    // zooming
    var currZoom = 0.33f
    
    // touch
    var isTouched = false
    val screenTouchPosition = Vector2()
    var worldTouchPosition = Vector2()
    val roomTouchPosition = RoomPosition()
    
    init
    {
        // input processor
        super.inputProcessor = inputMultiplexer
        
        // hud
        
        ItemsStructure.display()
        LeftStructure.display()
        ProgressBars.display()
        RightStructure.display()
        PopUps.display()
        
        // map
        updateMapBaseLayer()
        updateMapEntityLayer()
        
        // camera
        changeCameraZoom(currZoom)
        focusCameraOnRoomPosition(World.hero.position)
        
        // state
        val areEnemiesInRoom = World.currentRoom.areEnemiesInRoom()
        val initState = when (areEnemiesInRoom)
        {
            true  -> State.constrained.noSelection
            false -> State.free.noSelection
        }
        for (level in World.getLevels())
        {
            for (room in level.getRooms())
            {
                for (enemy in room.getEnemies())
                {
                    enemy.createOwnHealthBar()
                    enemy.hideOwnHealthBar()
                }
            }
        }
        for (enemy in World.currentRoom.getEnemies())
        {
            enemy.displayOwnHealthBar()
        }
        
        
        initState.areEnemiesInRoom = areEnemiesInRoom
        setState(initState)
    }
    
    fun updateMapBaseLayer()
    {
        for (i in 0 until Map.mapHeightInTiles)
        {
            for (j in 0 until Map.mapWidthInTiles)
            {
                val space = World.currentRoom.getSpace(j, i)
                
                val tile = space?.getBase()?.tile
                
                Map.changeTile(MapLayer.base, j, i, tile)
            }
        }
    }
    
    fun updateMapEntityLayer()
    {
        for (i in 0 until Map.mapHeightInTiles)
        {
            for (j in 0 until Map.mapWidthInTiles)
            {
                val space = World.currentRoom.getSpace(j, i)
                
                val tile = space?.getEntity()?.getTile()
                
                Map.changeTile(MapLayer.entity, j, i, tile)
            }
        }
    }
    
    fun getCameraZoom() : Float
    {
        return gameCamera.zoom
    }
    
    fun changeCameraZoom(newZoom : Float)
    {
        gameCamera.zoom = newZoom
    }
    
    fun getCameraPosition() : Vector2
    {
        return Vector2(gameCamera.position.x, gameCamera.position.y)
    }
    
    fun focusCameraOnIsoPosition(position : Vector2)
    {
        gameCamera.position.set(position, 0f)
    }
    
    fun focusCameraOnOrthoPosition(position : Vector2)
    {
        val isoPosition = orthoToIso(position)
        gameCamera.position.set(isoPosition, 0f)
    }
    
    fun focusCameraOnVector2Position(position : Vector2)
    {
        val orthoPosition = positionToOrtho(position)
        val isoPosition = orthoToIso(orthoPosition)
        gameCamera.position.set(isoPosition, 0f)
    }
    
    fun focusCameraOnRoomPosition(roomPosition : RoomPosition)
    {
        val orthoPosition = roomPositionToOrtho(roomPosition)
        val isoPosition = orthoToIso(orthoPosition)
        gameCamera.position.set(isoPosition, 0f)
    }
    
    fun updateScreenWorldMapTouchPositions(newScreenTouchPosition : Vector2)
    {
        val newIsoWorldTouchPosition = gameViewport.unproject(newScreenTouchPosition)
        val newOrthoWorldTouchPosition = isoToOrtho(newIsoWorldTouchPosition)
        val newRoomTouchPosition = orthoToRoomPosition(newOrthoWorldTouchPosition)
        
        screenTouchPosition.set(newScreenTouchPosition)
        worldTouchPosition.set(newIsoWorldTouchPosition)
        roomTouchPosition.set(newRoomTouchPosition)
    }
    
    // overridden BaseScreen methods
    
    override fun render(delta : Float)
    {
        // update
        updateState()
        isTouched = false
        
        // animation
        Animating.update()
        
        // render
        ScreenUtils.clear(Colors.black)
        hudCamera.update()
        gameCamera.update()
        mapRenderer.setView(gameCamera)
        mapRenderer.render()
        for (enemy in World.currentRoom.getEnemies())
        {
            enemy.changeOwnHealthBarPos()
        }
//        ProgressBars.handleProgressBarsFlashing()
        gameStage.draw()
        stage.draw()
    }
    
    override fun resize(width : Int, height : Int)
    {
        hudViewport.update(width, height, false)
        gameViewport.update(width, height, false)
    }
    
    override fun dispose()
    {
        stage.dispose()
        gameStage.dispose()
    }
    
    // overridden GestureListener methods
    
    override fun touchDown(x : Float, y : Float, pointer : Int, button : Int) : Boolean
    {
        currZoom = getCameraZoom()
        
        return true
    }
    
    override fun tap(x : Float, y : Float, count : Int, button : Int) : Boolean
    {
        isTouched = true
        val newScreenTouchPosition = Vector2(x, y)
        updateScreenWorldMapTouchPositions(newScreenTouchPosition)
        return true
    }
    
    override fun longPress(x : Float, y : Float) : Boolean
    {
        return true
    }
    
    override fun fling(velocityX : Float, velocityY : Float, button : Int) : Boolean
    {
        return true
    }
    
    override fun pan(x : Float, y : Float, deltaX : Float, deltaY : Float) : Boolean
    {
        if (Animating.isAnimating())
        {
            return true
        }
        
        updateScreenWorldMapTouchPositions(Vector2(x, y))
        
        if (isDragging)
        {
            val dragDifference = Vector2(
                    worldTouchPosition.x - gameCamera.position.x, worldTouchPosition.y - gameCamera.position.y
                                        )
            val newCameraPosition = Vector3(
                    dragOriginPosition.x - dragDifference.x, dragOriginPosition.y - dragDifference.y, 0f
                                           )
            gameCamera.position.set(newCameraPosition)
        }
        else
        {
            dragOriginPosition.set(worldTouchPosition)
            isDragging = true
        }
        
        return true
    }
    
    override fun panStop(x : Float, y : Float, pointer : Int, button : Int) : Boolean
    {
        isDragging = false
        
        return true
    }
    
    override fun zoom(initialDistance : Float, distance : Float) : Boolean
    {
        val zoomChange = initialDistance / distance
        changeCameraZoom(currZoom * zoomChange)
        
        return true
    }
    
    override fun pinch(
            initialPointer1 : Vector2?, initialPointer2 : Vector2?, pointer1 : Vector2?, pointer2 : Vector2?
                      ) : Boolean
    {
        return true
    }
    
    override fun pinchStop()
    {
    }
}
