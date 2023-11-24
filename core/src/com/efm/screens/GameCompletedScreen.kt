package com.efm.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.efm.*
import com.efm.assets.*

object GameCompletedScreen : BaseScreen()
{
    val camera = OrthographicCamera()
    val viewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, camera)
    val stage = Stage(viewport, EscapeFromMorasko.spriteBatch)
    
    init
    {
        // input processor
        super.inputProcessor = MenuScreen.stage
        
        val gameOverText = labelOf("Congratulations!", Fonts.pixeloid30, Colors.white, Textures.translucentNinePatch)
        val gameOverSubtext =
                labelOf("You've completed the game", Fonts.pixeloid30, Colors.white, Textures.translucentNinePatch)
        gameOverText.setFontScale(3f)
        
        val menuTextButton = textButtonOf(
                "back to menu",
                Fonts.inconsolata30,
                Colors.black,
                Textures.upLongNinePatch,
                Textures.downLongNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                         )
        {
            Sounds.ui_1.playOnce()
            changeScreen(MenuScreen)
        }
        
        val tableGameOver = Table()
        tableGameOver.setFillParent(true)
        tableGameOver.add(gameOverText).align(Align.center).row()
        tableGameOver.add(gameOverSubtext).align(Align.center).row()
        
        val tableButton = Table()
        tableButton.setFillParent(true)
        
        val screenHeight = Gdx.graphics.height.toFloat()
        tableButton.add(menuTextButton).padTop(screenHeight / 2).align(Align.center)
        
        stage.addActor(tableGameOver)
        stage.addActor(tableButton)
        
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