package com.efm.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.efm.*
import com.efm.assets.*

object GameOverScreen : BaseScreen()
{
    val camera = OrthographicCamera()
    val viewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, camera)
    val stage = Stage(viewport, EscapeFromMorasko.spriteBatch)
    
    init
    {
        // input processor
        super.inputProcessor = MenuScreen.stage
        
        val gameOverText = labelOf("Game Over", Fonts.pixeloid30, Colors.white, Textures.translucentNinePatch)
        
        val column = columnOf(rowOf(gameOverText)).align(Align.center)
        column.setFillParent(true)
        stage.addActor(column)
        
    }
    
    override fun render(delta : Float)
    {
        ScreenUtils.clear(Color.CLEAR)
        
        EscapeFromMorasko.spriteBatch.begin()
        EscapeFromMorasko.spriteBatch.draw(
                Textures.mainMenuBackground,
                0f,
                0f,
                MenuScreen.viewport.worldWidth,
                MenuScreen.viewport.worldHeight
                                          )
        EscapeFromMorasko.spriteBatch.end()
        MenuScreen.camera.update()
        MenuScreen.stage.draw()
    }
    
    override fun resize(width : Int, height : Int)
    {
        MenuScreen.viewport.update(width, height, true)
    }
    
    override fun dispose()
    {
        MenuScreen.stage.dispose()
    }
}