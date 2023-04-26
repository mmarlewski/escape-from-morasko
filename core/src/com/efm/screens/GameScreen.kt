package com.efm.screens

import com.badlogic.gdx.*
import com.badlogic.gdx.Input.Keys
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
import com.efm.entities.Hero
import com.efm.level.World
import com.efm.passage.*
import com.efm.room.*
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
    
    // animation
    var isAnimation = false
    var animations = mutableListOf<Animation>()
    var animation : Animation = wait(0f)
    var nextAnimation : Animation? = wait(0f)
    var isWait = false
    var deltaTime = 0f
    var waitTimeInSeconds = 0f
    
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
    var prevSelectPos = Vector2(0.0F, 0.0F)
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
        
        Map.newLayer(MapLayer.base)
        Map.newLayer(MapLayer.select)
        Map.newLayer(MapLayer.entity)
        
        updateMapBase()
        updateMapSelect()
        updateMapEntity()
        
        focusCamera(World.hero.position)
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
    
    fun focusCamera(position : RoomPosition)
    {
        gameCamera.zoom = 0.25f
        orthoToIso(
                (position.x * Map.tileLengthHalfInPixels).toFloat(),
                ((Map.mapHeightInTiles - position.y - 1) * Map.tileLengthHalfInPixels).toFloat(),
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
        if (isMouseInMap)
        {
            updateMouse()
            
            selectPosition.set(mapMousePosition)
            
            val startPosition = World.hero.position
            val endPosition = selectPosition.toRoomPosition()
            val path = Pathfinding.findPathWithGivenRoom(startPosition, endPosition, World.currentRoom)
            if (selectPosition != prevSelectPos)
            {
                updateMapSelect()
                if (path != null) {
                    for (space in path){
                        Map.changeTile(MapLayer.select, space.position.x, space.position.y, Tiles.selectTeal)
                    }
                }
                prevSelectPos.set(selectPosition)
            }
            else
            {
                val startSpace = World.currentRoom.getSpace(startPosition)
                val endSpace = World.currentRoom.getSpace(endPosition)
                
                if (startSpace == endSpace) return
                
                if (path != null)
                {
                    val action =
                            {
                                var newPosition = endPosition
                                var newRoom = World.currentRoom
                                var newLevel = World.currentLevel
                                
                                val endEntity = endSpace?.getEntity()
                                
                                when (endEntity)
                                {
                                    null, is Hero ->
                                    {
                                    }
                                    
                                    is Exit       ->
                                    {
                                        when (val passage = endEntity.exitPassage)
                                        {
                                            is RoomPassage  ->
                                            {
                                                newPosition = when (endEntity.currentRoom)
                                                {
                                                    passage.roomA -> passage.positionB
                                                    passage.roomB -> passage.positionA
                                                    else          -> newPosition
                                                }
                                                newRoom = when (endEntity.currentRoom)
                                                {
                                                    passage.roomA -> passage.roomB
                                                    passage.roomB -> passage.roomA
                                                    else          -> newRoom
                                                }
                                            }
                                            
                                            is LevelPassage ->
                                            {
                                                newPosition = passage.targetLevel.getStartingPosition()
                                                newRoom = passage.targetLevel.getStartingRoom()
                                                newLevel = passage.targetLevel
                                            }
                                        }
                                    }
                                    
                                    else          ->
                                    {
                                        val lastSpace = path.lastOrNull()
                                        newPosition = if (lastSpace != null)
                                        {
                                            lastSpace.position
                                        }
                                        else
                                        {
                                            startPosition
                                        }
                                    }
                                }
                                
                                World.currentRoom.removeEntity(World.hero)
                                
                                World.changeCurrentRoom(newRoom)
                                World.changeCurrentLevel(newLevel)
                                
                                World.currentRoom.addEntityAt(World.hero, newPosition)
                                World.currentRoom.updateSpacesEntities()
                                
                                updateMapBase()
                                updateMapEntity()
                                updateMapSelect()
                                
                                focusCamera(World.hero.position)
                            }
                    
                    val animations = mutableListOf<Animation>()
                    animations += changeMapTile(MapLayer.entity, startPosition.x, startPosition.y, null)
                    for (space in path)
                    {
                        animations += changeMapTile(MapLayer.select, space.position.x, space.position.y, null)
                        animations += focusGameScreenCamera(space.position)
                        animations += changeMapTile(MapLayer.entity, space.position.x, space.position.y, Tiles.hero)
                        animations += wait(0.1f)
                        animations += changeMapTile(MapLayer.entity, space.position.x, space.position.y, null)
                    }
                    animations += action(action)
                    executeAnimations(animations)
                }
            }
        }
    }
    
    // overridden BaseScreen methods
    
    override fun render(delta : Float)
    {
        if (isAnimation)
        {
            if (isWait)
            {
                deltaTime += Gdx.graphics.deltaTime
                if (deltaTime > (animation as wait).seconds)
                {
                    isWait = false
                }
            }
            else
            {
                if (nextAnimation != null)
                {
                    animation = nextAnimation as Animation
                }
                else
                {
                    isAnimation = false
                }
                
                animation.execute()
                
                val nextAnimationIndex = animations.indexOf(animation) + 1
                nextAnimation = animations.getOrNull(nextAnimationIndex)
            }
        }
        
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
                if (isMouseInMap)
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
                focusCamera(selectPosition.toRoomPosition())
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
