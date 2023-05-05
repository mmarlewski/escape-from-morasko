package com.efm.screens

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.efm.*
import com.efm.Map
import com.efm.assets.*
import com.efm.level.World
import com.efm.room.RoomPosition

object GameScreen : BaseScreen(), InputProcessor
{
    val hudCamera = OrthographicCamera()
    val gameCamera = OrthographicCamera()
    val hudViewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, hudCamera)
    val gameViewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, gameCamera)
    val stage = Stage(hudViewport, EscapeFromMorasko.spriteBatch)
    val inputMultiplexer = InputMultiplexer(stage, this)
    val mapRenderer = IsometricTiledMapRenderer(Map.tiledMap, 1f, EscapeFromMorasko.spriteBatch)
    
    // dragging
    var isDragging = false
    val dragOriginPosition = Vector2()
    
    // touch
    var isTouchDown = false
    var isTouched = false
    val screenTouchPosition = Vector2()
    var worldTouchPosition = Vector2()
    val roomTouchPosition = RoomPosition()
    
    init
    {
        // input processor
        super.inputProcessor = inputMultiplexer
        
        // hud
        val menuTextButton = textButtonOf(
                "back to menu",
                Fonts.pixeloid20,
                Color.FOREST,
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
        val menuMovingModeButton = textButtonOf(
                "moving",
                Fonts.pixeloid20,
                Color.FOREST,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                               )
        {
            playSoundOnce(Sounds.blop)
            when (currState)
            {
                noPositionSelected ->
                {
                    noPositionSelected.isMovingMode = !noPositionSelected.isMovingMode
                }
                else               ->
                {
                    Map.clearLayer(MapLayer.select)
                    currState = noPositionSelected
                    noPositionSelected.isMovingMode = false
                }
            }
        }
        val table = Table()
        table.setFillParent(true)
        table.align(Align.topLeft)
        table.add(menuTextButton)
        table.add(menuMovingModeButton)
        stage.addActor(table)
        
        // map
        updateMapBaseLayer()
        updateMapEntityLayer()
        Map.changeTile(MapLayer.select, World.hero.position, Tiles.selectGreen)
        
        // camera
        changeCameraZoom(0.25f)
        focusCameraOnRoomPosition(World.hero.position)
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
    
    // overridden InputProcessor methods
    
    override fun keyDown(keycode : Int) : Boolean
    {
        return true
    }
    
    override fun keyUp(keycode : Int) : Boolean
    {
        return true
    }
    
    override fun keyTyped(character : Char) : Boolean
    {
        return true
    }
    
    override fun touchDown(screenX : Int, screenY : Int, pointer : Int, button : Int) : Boolean
    {
        isTouchDown = true
        val newScreenTouchPosition = Vector2(screenX.toFloat(), screenY.toFloat())
        updateScreenWorldMapTouchPositions(newScreenTouchPosition)
        
        return true
    }
    
    override fun touchUp(screenX : Int, screenY : Int, pointer : Int, button : Int) : Boolean
    {
        if (isDragging)
        {
            isDragging = false
        }
        else
        {
            if (isTouchDown)
            {
                isTouchDown = false
                isTouched = true
            }
        }
        
        return true
    }
    
    override fun touchDragged(screenX : Int, screenY : Int, pointer : Int) : Boolean
    {
        updateScreenWorldMapTouchPositions(Vector2(screenX.toFloat(), screenY.toFloat()))
        
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
    
    override fun mouseMoved(screenX : Int, screenY : Int) : Boolean
    {
        return true
    }
    
    override fun scrolled(amountX : Float, amountY : Float) : Boolean
    {
        return true
    }
}
