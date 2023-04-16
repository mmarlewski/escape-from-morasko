package com.efm.screens

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.efm.*
import com.efm.Map
import com.efm.assets.*
import com.efm.level.World
import com.efm.room.toRoomPosition
import com.efm.room.toVector2
import kotlin.math.floor

object GameScreen : BaseScreen(), InputProcessor
{
    val hudCamera = OrthographicCamera()
    val gameCamera = OrthographicCamera()
    val hudViewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, hudCamera)
    val gameViewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, gameCamera)
    val stage = Stage(hudViewport, EscapeFromMorasko.spriteBatch)
    val inputMultiplexer = InputMultiplexer(stage, this)
    
    // map
    
    val mapRenderer = IsometricTiledMapRenderer(Map.tiledMap, 1f, EscapeFromMorasko.spriteBatch)
    
    // dragging
    
    private var touchStartTime = 0L
    private var isDragging = false
    var dragOrigin = Vector2()
    var dragDifference = Vector2()
    
    // mouse
    
    var screenMousePosition = Vector2()
    var worldMousePosition = Vector2()
    var isMouseInMap = false
    var mapMousePosition = Vector2()
    var selectPosition = Vector2()
    
    // other
    var tempVector2 = Vector2()
    
    //movement
    var prevHeroPosX = 0
    var prevHeroPosY = 0
    var prevSelectPosX = 0
    var prevSelectPosY = 0
    var isMovingMode = true
    
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
            isMovingMode = !isMovingMode
        }
        val table = Table()
        table.setFillParent(true)
        table.align(Align.topLeft)
        table.add(menuTextButton)
        table.add(menuMovingModeButton)
        stage.addActor(table)
        
        // map
        
        updateMapBase()
        updateMapSelect()
        updateMapEntity()
        
        focusCamera(World.hero.position.toVector2())
    }
    
    fun updateMapBase()
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
    
    fun updateMapSelect()
    {
        for (i in 0 until Map.mapHeightInTiles)
        {
            for (j in 0 until Map.mapWidthInTiles)
            {
                val tile = if (selectPosition.x.toInt() == j && selectPosition.y.toInt() == i) Tiles.selectYellow else null
                
                Map.changeTile(MapLayer.select, j, i, tile)
            }
        }
    }
    
    fun updateMapEntity()
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
    
    fun updateMouse(screenX : Int, screenY : Int)
    {
        screenMousePosition.set(screenX.toFloat(), screenY.toFloat())
        worldMousePosition.set(screenMousePosition)
        gameViewport.unproject(worldMousePosition)
        
        updateMouse()
    }
    
    fun updateMouse()
    {
        isoToOrtho(worldMousePosition, mapMousePosition)
        mapMousePosition.set(
                floor(mapMousePosition.x / Map.tileLengthHalfInPixels),
                floor(mapMousePosition.y / Map.tileLengthHalfInPixels)
                            )
        mapMousePosition.y = Map.mapHeightInTiles - mapMousePosition.y - 1
        isMouseInMap = (
                mapMousePosition.x.toInt() in 0 until Map.mapWidthInTiles &&
                        mapMousePosition.y.toInt() in 0 until Map.mapHeightInTiles
                )
    }
    
    fun focusCamera(mapPosition : Vector2)
    {
        orthoToIso(
                mapPosition.x * Map.tileLengthHalfInPixels,
                (Map.mapHeightInTiles - mapPosition.y - 1) * Map.tileLengthHalfInPixels,
                tempVector2
                  )
        gameCamera.position.set(
                tempVector2.x,
                tempVector2.y,
                gameCamera.position.z
                               )
    }
    
    fun moveHero()
    {
        updateMouse()
        if (isMouseInMap)
        {
            selectPosition.set(mapMousePosition)
            if (selectPosition.x.toInt() != prevSelectPosX || selectPosition.y.toInt() != prevSelectPosY)
            {
                Map.clearLayer(MapLayer.select)
                Map.changeTile(MapLayer.select, selectPosition.x.toInt(), selectPosition.y.toInt(), Tiles.selectYellow)
                prevSelectPosX = selectPosition.x.toInt()
                prevSelectPosY = selectPosition.y.toInt()
            }
            else
            {
                Map.clearLayer(MapLayer.entity)
                Map.changeTile(MapLayer.entity, selectPosition.x.toInt(), selectPosition.y.toInt(), Tiles.hero)
                prevHeroPosX = selectPosition.x.toInt()
                prevHeroPosY = selectPosition.y.toInt()
            }
            
        }
        isDragging = false
    }
    
    // overridden BaseScreen methods
    
    override fun render(delta : Float)
    {
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
        when (keycode)
        {
            Keys.S ->
            {
                if(isMouseInMap)
                {
                    updateMouse()
                    
                    selectPosition.set(mapMousePosition)
                    updateMapSelect()
                    
                    World.hero.changePosition(selectPosition.toRoomPosition())
                    World.currentRoom.updateSpacesEntities()
                    updateMapEntity()
                }
            }
            
            Keys.F ->
            {
                focusCamera(selectPosition)
            }
        }
        
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
        touchStartTime = System.currentTimeMillis()
        isDragging = false
        return true
    }
    
    override fun touchUp(screenX : Int, screenY : Int, pointer : Int, button : Int) : Boolean
    {
        if (!isDragging && (System.currentTimeMillis() - touchStartTime) < 800)
        {
            touchDragged(screenX, screenY, pointer)
            if (isMovingMode)
            {
                moveHero()
            }
            return true
        }
        return true
    }
    
    override fun touchDragged(screenX : Int, screenY : Int, pointer : Int) : Boolean
    {
        updateMouse(screenX, screenY)
        
        if (isDragging)
        {
            dragDifference.x = worldMousePosition.x - gameCamera.position.x
            dragDifference.y = worldMousePosition.y - gameCamera.position.y
            gameCamera.position.x = dragOrigin.x - dragDifference.x
            gameCamera.position.y = dragOrigin.y - dragDifference.y
        }
        else
        {
            dragOrigin.set(worldMousePosition)
            isDragging = true
        }
        return true
    }
    
    override fun mouseMoved(screenX : Int, screenY : Int) : Boolean
    {
        updateMouse(screenX, screenY)
        
        return true
    }
    
    override fun scrolled(amountX : Float, amountY : Float) : Boolean
    {
        return true
    }
}
