package com.efm.screens

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.efm.*
import com.efm.assets.*
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
    
    val map = TiledMap()
    val mapRenderer = IsometricTiledMapRenderer(map, 1f, EscapeFromMorasko.spriteBatch)
    
    // dragging
    
    var isDragging = false
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
        val table = Table()
        table.setFillParent(true)
        table.align(Align.topLeft)
        table.add(menuTextButton)
        stage.addActor(table)
        
        // map
        
        val groundLayer= TiledMapTileLayer(mapWidth, mapHeight, tileLength, tileLengthHalf).apply { name = "ground" }
        val selectLayer = TiledMapTileLayer(mapWidth, mapHeight, tileLength, tileLengthHalf).apply { name = "select" }
        val entityLayer = TiledMapTileLayer(mapWidth, mapHeight, tileLength, tileLengthHalf).apply { name = "entity" }
        
        map.layers.add(groundLayer)
        map.layers.add(selectLayer)
        map.layers.add(entityLayer)
        
        for (layer in map.layers)
        {
            for (i in 0 until mapHeight)
            {
                for (j in 0 until mapWidth)
                {
                    (layer as TiledMapTileLayer).setCell(j, i, TiledMapTileLayer.Cell())
                }
            }
        }
        
        for (i in 0 until mapHeight)
        {
            for (j in 0 until mapWidth)
            {
                changeMapTile("ground", j, i, Tiles.groundStone)
            }
        }
    }
    
    // other methods
    
    fun changeMapTile(layer : String, x : Int, y : Int, tile : StaticTiledMapTile?)
    {
        (map.layers[layer] as TiledMapTileLayer).getCell(x, y).tile = tile
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
                floor(mapMousePosition.x / tileLengthHalf),
                floor(mapMousePosition.y / tileLengthHalf)
                            )
        isMouseInMap = (
                mapMousePosition.x.toInt() in 0 until mapWidth &&
                        mapMousePosition.y.toInt() in 0 until mapHeight
                )
    }
    
    fun focusCamera(mapPosition : Vector2)
    {
        orthoToIso(
                mapPosition.x * tileLengthHalf,
                mapPosition.y * tileLengthHalf,
                tempVector2
                  )
        gameCamera.position.set(
                tempVector2.x,
                tempVector2.y,
                gameCamera.position.z
                               )
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
    
    // overrridden InputProcessor methods
    
    override fun keyDown(keycode : Int) : Boolean
    {
        when (keycode)
        {
            Keys.S ->
            {
                updateMouse()
                
                if (isMouseInMap)
                {
                    selectPosition.set(mapMousePosition)
                    for (i in 0 until mapHeight)
                    {
                        for (j in 0 until mapWidth)
                        {
                            changeMapTile("entity", j, i, null)
                        }
                    }
                    changeMapTile("entity", mapMousePosition.x.toInt(), mapMousePosition.y.toInt(), Tiles.hero)
                    changeMapTile("select", mapMousePosition.x.toInt(), mapMousePosition.y.toInt(), Tiles.select)
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
        return true
    }
    
    override fun touchUp(screenX : Int, screenY : Int, pointer : Int, button : Int) : Boolean
    {
        isDragging = false
        
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
