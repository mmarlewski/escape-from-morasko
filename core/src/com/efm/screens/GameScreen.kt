package com.efm.screens

import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.efm.*
import com.efm.Map
import com.efm.assets.*
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.state.*
import com.efm.ui.gameScreen.ItemsStructure
import com.efm.ui.gameScreen.LeftStructure

object GameScreen : BaseScreen(), GestureListener
{
    val hudCamera = OrthographicCamera()
    val gameCamera = OrthographicCamera()
    val hudViewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, hudCamera)
    val gameViewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, gameCamera)
    val stage = Stage(hudViewport, EscapeFromMorasko.spriteBatch)
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
    
    // hud
    lateinit var menuTextButton : TextButton
    lateinit var xButton : TextButton
    var healthBar : ProgressBar
    var healthBarLabel : Label
    var abilityBar : ProgressBar
    var abilityBarLabel : Label
    var settingsPopUp : Window
    var menuPause : Window
    
    init
    {
        // input processor
        super.inputProcessor = inputMultiplexer
        
        // hud
        //special empty space function
        fun emptySpace(width : Float) : Actor
        {
            return Actor().apply {
                this.width = width
            }
        }
        
        val endTurnPopUp = windowAreaOf(
                "end turn?\n\n you still have some AP left",
                Fonts.pixeloid20,
                Colors.black,
                Textures.pauseBackgroundNinePatch,
                                       )
        endTurnPopUp.isVisible = false
        
        settingsPopUp = settingsPause("SETTINGS", Fonts.pixeloid30, Colors.black, Textures.pauseBackgroundNinePatch)
        settingsPopUp.isVisible = false
        
        menuPause = menuPopup(
                "PAUSE",
                Fonts.pixeloid30,
                Colors.black,
                Textures.pauseBackgroundNinePatch
                             )
        menuPause.isVisible = false
        
        xButton = textButtonOf(
                "X",
                Fonts.pixeloid20,
                Colors.black,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                              ) {
            playSoundOnce(Sounds.blop)
            xButton.isVisible = false
            
            Map.clearLayer(MapLayer.select)
            Map.clearLayer(MapLayer.outline)
            
            val newState = when (val currState = getState())
            {
                is State.free        -> State.free.noSelection.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                }
                is State.constrained -> State.constrained.noSelection.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.isHeroDetected = currState.isHeroDetected
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
                is State.combat.hero -> State.combat.hero.noSelection.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.areAnyActionPointsLeft = currState.areAnyActionPointsLeft
                }
                else                 -> currState
            }
            setState(newState)
        }
        
        // hud top left

        
        //bars
        val healthBarValueCurrent = World.hero.healthPoints //"100"
        val healthBarValueMax = World.hero.maxHealthPoints //"100"
        healthBar = progressBarOf(
                0.0f,
                healthBarValueMax.toFloat(),
                1.0f,
                World.hero.healthPoints.toFloat(),
                Textures.knobBackgroundNinePatch,
                Textures.knobHealthbarAfterNinePatch,
                Textures.knobBeforeNinePatch,
                128f,
                24f
                                 )
        healthBarLabel = labelOf(
                "$healthBarValueCurrent / $healthBarValueMax", Fonts.pixeloid20, Colors.black, Textures.translucentNinePatch
                                )
        
        val abilityBarValueCurrent = World.hero.abilityPoints
        val abilityBarValueMax = World.hero.maxAbilityPoints
        abilityBar = progressBarOf(
                0.0f,
                abilityBarValueMax.toFloat(),
                1.0f,
                World.hero.abilityPoints.toFloat(),
                Textures.knobBackgroundNinePatch,
                Textures.knobAbilitybarAfterNinePatch,
                Textures.knobBeforeNinePatch,
                128f,
                24f
                                  )
        abilityBarLabel = labelOf(
                "$abilityBarValueCurrent / $abilityBarValueMax",
                Fonts.pixeloid20,
                Colors.black,
                Textures.translucentNinePatch
                                 )
        
        //item buttons
    
        // hud top right - states
        
        val endTurnButton = imageButtonOf(
                Textures.nextTurn,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                         ) {
            endTurnPopUp.isVisible = endTurnPopUp.isVisible != true
            playSoundOnce(Sounds.blop)
        }
        
        //top left icons
    
        //top right icons
        val columnTopRight = columnOf(
                rowOf(endTurnButton)
                                     ).align(Align.topRight)
        //bars setup
        val healthBarWidth = 300F
        val healthStack = Stack()
        val healthBarContainer : Container<ProgressBar> = Container(healthBar)
        healthBarContainer.width(healthBarWidth)
        healthStack.add(healthBarContainer)
        healthStack.add(healthBarLabel)
        
        val abilityBarWidth = 300F
        val abilityStack = Stack()
        val abilityBarContainer : Container<ProgressBar> = Container(abilityBar)
        abilityBarContainer.width(abilityBarWidth)
        abilityStack.add(abilityBarContainer)
        abilityStack.add(abilityBarLabel)
        //bars
        val columnTop = columnOf(
                rowOf(healthStack, abilityStack)
                                ).align(Align.top)
        
        //bottom right icons
        val columnBottomRight = columnOf(
                rowOf(xButton)
                                        ).align(Align.bottomRight)
        
        val columnMiddlePause = columnOf(rowOf(menuPause)).align(Align.center)
        val columnMiddlePopUp = columnOf(rowOf(endTurnPopUp)).align(Align.center)
        val columnMiddleSettings = columnOf(rowOf(settingsPopUp)).align(Align.center)
        
        //padding so it looks nice
    
        columnTopRight.pad(16f)
        columnTop.pad(16f)
    
        columnBottomRight.pad(16f)
    
        //set the size to fill the phone screen
    
        columnTopRight.setFillParent(true)
        columnTop.setFillParent(true)
    
        columnBottomRight.setFillParent(true)
        columnMiddlePause.setFillParent(true)
        columnMiddlePopUp.setFillParent(true)
        columnMiddleSettings.setFillParent(true)
    
        //display
    
        GameScreen.stage.addActor(columnTopRight)
        GameScreen.stage.addActor(columnTop)
    
        GameScreen.stage.addActor(columnBottomRight)
        GameScreen.stage.addActor(columnMiddlePause)
        GameScreen.stage.addActor(columnMiddlePopUp)
        GameScreen.stage.addActor(columnMiddleSettings)
    
    
    
    
    
    
        ItemsStructure.display()
        LeftStructure.display()
    
        // xButton is visible only after pressing on hero
        xButton.isVisible = false
    
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
