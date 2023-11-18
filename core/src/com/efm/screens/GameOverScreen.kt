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
        super.inputProcessor = stage
        
        val gameOverText = labelOf("Game Over", Fonts.pixeloid30, Colors.white, Textures.translucentNinePatch)
        
        val menuTextButton = textButtonOf(
                "menu",
                Fonts.inconsolata30,
                Colors.black,
                Textures.upLongNinePatch,
                Textures.downLongNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                         )
        {
            changeScreen(MenuScreen)
        }
        
        val column = columnOf(gameOverText, menuTextButton).align(Align.center)
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
                viewport.worldWidth,
                viewport.worldHeight
                                          )
        EscapeFromMorasko.spriteBatch.end()
        camera.update()
        stage.draw()
    }
    
    override fun resize(width : Int, height : Int)
    {
        viewport.update(width, height, true)
    }
    
    override fun dispose()
    {
        stage.dispose()
    }
}