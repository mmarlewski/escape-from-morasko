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
import com.efm.entities.enemies.mimic.EnemyMimic
import com.efm.entity.Enemy
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
    var canBeInteractedWith = true
    var isTouched = false
    val screenTouchPosition = Vector2()
    var worldTouchPosition = Vector2()
    val roomTouchPosition = RoomPosition()
    
    init
    {
        // input processor
        super.inputProcessor = inputMultiplexer
        
        // hud
        if (!State.TutorialFlags.tutorialActive)
        {
            ItemsStructure.display()
            LeftStructure.display()
            ProgressBars.display()
            RightStructure.display()
            PopUps.display()
            EquipmentStructure.display()
            TutorialPopups.display()
            SpecialEventsPopups.display()
        }
        else
        {
            TutorialPopups.display()
            interfaceDrawingWithTutorial()
        }
    }
    
    fun updateMapBaseLayer()
    {
        for (i in 0 until Map.mapHeightInTiles)
        {
            for (j in 0 until Map.mapWidthInTiles)
            {
                val space = World.currentRoom?.getSpace(j, i)
                
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
                val space = World.currentRoom?.getSpace(j, i)
                
                val tile = space?.getEntity()?.getTile()
                
                Map.changeTile(MapLayer.entity, j, i, tile)
            }
        }
    }
    
    fun updateMapOutlineLayer()
    {
        for (i in 0 until Map.mapHeightInTiles)
        {
            for (j in 0 until Map.mapWidthInTiles)
            {
                Map.changeTile(MapLayer.outline, j, i, null)
            }
        }
    }
    
    fun updateMapEnemyIdleAnimation()
    {
        for (i in 0 until Map.mapHeightInTiles)
        {
            for (j in 0 until Map.mapWidthInTiles)
            {
                val space = World.currentRoom?.getSpace(j, i)
                
                if (space != null)
                {
                    val entity = space.getEntity()
                    
                    if (entity is Enemy)
                    {
                        val state = getState()
                        
                        if (
                                !(state is State.combat.enemies.enemyUnselected && State.combat.enemies.enemyUnselected.currEnemy == entity) &&
                                !(state is State.combat.enemies.enemySelected && State.combat.enemies.enemySelected.currEnemy == entity) &&
                                !(state is State.combat.enemies.enemyAction && State.combat.enemies.enemyAction.currEnemy == entity)
                        )
                        {
                            val tile = entity.getIdleTile()
                            Map.changeTile(MapLayer.entity, j, i, tile)
                            
                        }
                    }
                }
            }
        }
    }
    
    fun updateMapEnemyIdleOutlineYellowAnimation()
    {
        for (i in 0 until Map.mapHeightInTiles)
        {
            for (j in 0 until Map.mapWidthInTiles)
            {
                val space = World.currentRoom?.getSpace(j, i)
                
                if (space != null)
                {
                    val entity = space.getEntity()
                    
                    // remove check for Mimic for a subtle giveway
                    if (entity is Enemy && (entity !is EnemyMimic || entity.detected()))
                    {
                        val state = getState()
                        
                        if (
                                (state is State.free.entitySelected && State.free.entitySelected.selectedEntity == entity) ||
                                (state is State.constrained.entitySelected && State.constrained.entitySelected.selectedEntity == entity) ||
                                (state is State.constrained.enemySelected && State.constrained.enemySelected.selectedEnemy == entity) ||
                                (state is State.combat.hero.entitySelected && State.combat.hero.entitySelected.selectedEntity == entity) ||
                                (state is State.combat.hero.enemySelected && State.combat.hero.enemySelected.selectedEnemy == entity)
                        )
                        {
                            val tile = entity.getOutlineYellowTile()
                            
                            Map.changeTile(MapLayer.outline, j, i, tile)
                        }
                    }
                }
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
        Animating.update(delta)
        
        // idle animation
        IdleAnimation.update(delta)
        if (IdleAnimation.idleAnimationChange)
        {
            updateMapEnemyIdleAnimation()
            updateMapEnemyIdleOutlineYellowAnimation()
        }
        
        // render
        ScreenUtils.clear(Colors.black)
        hudCamera.update()
        gameCamera.update()
        mapRenderer.setView(gameCamera)
        mapRenderer.render()
        for (enemy in World.currentRoom?.getEnemies() ?: listOf())
        {
            enemy.changeOwnHealthBarPos()
        }
        ProgressBars.flashProgressBar()
        gameStage.draw()
        stage.draw()
        stage.act()
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
        if (canBeInteractedWith)
        {
            currZoom = getCameraZoom()
            
            return true
        }
        return false
    }
    
    override fun tap(x : Float, y : Float, count : Int, button : Int) : Boolean
    {
        if (canBeInteractedWith)
        {
            isTouched = true
            val newScreenTouchPosition = Vector2(x, y)
            updateScreenWorldMapTouchPositions(newScreenTouchPosition)
            return true
        }
        return false
        
    }
    
    override fun longPress(x : Float, y : Float) : Boolean
    {
        if (canBeInteractedWith)
        {
            return true
        }
        return false
    }
    
    override fun fling(velocityX : Float, velocityY : Float, button : Int) : Boolean
    {
        if (canBeInteractedWith)
        {
            return true
        }
        return false
    }
    
    override fun pan(x : Float, y : Float, deltaX : Float, deltaY : Float) : Boolean
    {
        if (canBeInteractedWith)
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
                
                if (gameCamera.position.x < 1000.0f) gameCamera.position.x = 1000.0f
                if (gameCamera.position.x > 3000.0f) gameCamera.position.x = 3000.0f
                if (gameCamera.position.y < 0.0f) gameCamera.position.y = 0.0f
                if (gameCamera.position.y > 1500.0f) gameCamera.position.y = 1500.0f
            }
            else
            {
                dragOriginPosition.set(worldTouchPosition)
                isDragging = true
            }
            
            
            return true
        }
        return false
    }
    
    override fun panStop(x : Float, y : Float, pointer : Int, button : Int) : Boolean
    {
        if (canBeInteractedWith)
        {
            isDragging = false
            
            return true
        }
        return false
    }
    
    override fun zoom(initialDistance : Float, distance : Float) : Boolean
    {
        if (canBeInteractedWith)
        {
            val zoomChange = initialDistance / distance
            val zoomAfterChange = currZoom * zoomChange
            if (zoomAfterChange in 0.25..0.5)
            {
                changeCameraZoom(currZoom * zoomChange)
            }
            
            
            return true
        }
        return false
    }
    
    override fun pinch(
            initialPointer1 : Vector2?, initialPointer2 : Vector2?, pointer1 : Vector2?, pointer2 : Vector2?
                      ) : Boolean
    {
        if (canBeInteractedWith)
        {
            return true
        }
        return false
    }
    
    override fun pinchStop()
    {
    }
}
