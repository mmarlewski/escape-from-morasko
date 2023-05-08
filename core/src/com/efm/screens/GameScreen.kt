package com.efm.screens

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.*
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.efm.*
import com.efm.Map
import com.efm.assets.*
import com.efm.level.World
import com.efm.room.RoomPosition
import kotlin.math.abs

object GameScreen : BaseScreen(), GestureListener
{
    val hudCamera = OrthographicCamera()
    val gameCamera = OrthographicCamera()
    val hudViewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, hudCamera)
    val gameViewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, gameCamera)
    val stage = Stage(hudViewport, EscapeFromMorasko.spriteBatch)
    val inputMultiplexer = InputMultiplexer(stage, GestureDetector(this))
    val mapRenderer = IsometricTiledMapRenderer(Map.tiledMap, 1f, EscapeFromMorasko.spriteBatch)
    
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
    
    // hud
    lateinit var menuTextButton : TextButton
    lateinit var xButton : TextButton
    
    init
    {
        // input processor
        super.inputProcessor = inputMultiplexer
        
        // hud
        val xButton = imageButtonOf(
                Textures.close,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                   )
        {
            playSoundOnce(Sounds.blop)
            xButton.isVisible = false
            Map.clearLayer(MapLayer.select)
            changeState(State.free.noSelection)
        }
    
        // hud top left
        val returnToMenuButton = imageButtonOf(
                Textures.menuList,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                              )
        {
            playSoundOnce(Sounds.blop)
            changeScreen(MenuScreen)
        }
    
        // hud top right - states
        val stateIndicatorFreeToMoveIcon = imageOf(
                Textures.freeToMove,
                Scaling.none
                                                  )
        val stateIndicatorWaitingForPlayerTurnIcon = imageOf(
                Textures.waitingForPlayerTurn,
                Scaling.none
                                                            )
    
        val endTurnButton = imageButtonOf(
                Textures.nextTurn,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                         )
        {
            playSoundOnce(Sounds.blop)
        }
    
        val tableTopLeft = Table()
        tableTopLeft.setFillParent(true)
        stage.addActor(tableTopLeft)
        tableTopLeft.pad(15f)
    
        val tableTopRight = Table()
        tableTopRight.setFillParent(true)
        stage.addActor(tableTopRight)
        tableTopRight.pad(15f)
    
        val tableBottomLeft = Table()
        tableBottomLeft.setFillParent(true)
        stage.addActor(tableBottomLeft)
        tableBottomLeft.pad(15f)
    
        val tableBottomRight = Table()
        tableBottomRight.setFillParent(true)
        stage.addActor(tableBottomRight)
        tableBottomRight.pad(15f)
    
        tableTopLeft.add(returnToMenuButton).expand().top().left()
    
        tableTopRight.add(endTurnButton).expand().top().right()

//            tableBottomLeft.add(stateIndicatorWaitingForPlayerTurnIcon).expand().bottom().left()
    
        tableBottomRight.add(xButton).expand().bottom().right()
        
        // map
        updateMapBaseLayer()
        updateMapEntityLayer()
        
        // camera
        changeCameraZoom(currZoom)
        focusCameraOnRoomPosition(World.hero.position)
        
        // hud
        xButton.isVisible = false
        
        // state
        changeState(State.free.noSelection)
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
    
    fun focusCameraOnRoomPosition(roomPosition : RoomPosition)
    {
        val orthoPosition = roomToOrtho(roomPosition)
        val isoPosition = orthoToIso(orthoPosition)
        gameCamera.position.set(isoPosition, 0f)
    }
    
    fun updateScreenWorldMapTouchPositions(newScreenTouchPosition : Vector2)
    {
        val newIsoWorldTouchPosition = gameViewport.unproject(newScreenTouchPosition)
        val newOrthoWorldTouchPosition = isoToOrtho(newIsoWorldTouchPosition)
        val newRoomTouchPosition = orthoToRoom(newOrthoWorldTouchPosition)
        
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
        Animating.updateAnimations()
        
        // render
        ScreenUtils.clear(Colors.black)
        hudCamera.update()
        gameCamera.update()
        mapRenderer.setView(gameCamera)
        mapRenderer.render()
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
        if (Animating.isAnimating)
        {
            return true
        }
        
        updateScreenWorldMapTouchPositions(Vector2(x, y))
        
        if (isDragging)
        {
            val dragDifference = Vector2(
                    worldTouchPosition.x - gameCamera.position.x,
                    worldTouchPosition.y - gameCamera.position.y
                                        )
            val newCameraPosition = Vector3(
                    dragOriginPosition.x - dragDifference.x,
                    dragOriginPosition.y - dragDifference.y,
                    0f
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
            initialPointer1 : Vector2?,
            initialPointer2 : Vector2?,
            pointer1 : Vector2?,
            pointer2 : Vector2?
                      ) : Boolean
    {
        return true
    }
    
    override fun pinchStop()
    {
    }
}
