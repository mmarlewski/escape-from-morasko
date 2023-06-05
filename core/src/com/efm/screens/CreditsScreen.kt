package com.efm.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.*
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.efm.*
import com.efm.assets.*

object CreditsScreen : BaseScreen()
{
    val camera = OrthographicCamera()
    val viewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, camera)
    val stage = Stage(viewport, EscapeFromMorasko.spriteBatch)
    
    init
    {
        // input processor
        super.inputProcessor = stage
        
        // hud
        
        val creditsTitle = imageOf(
                Textures.creditsTitle,
                Scaling.none
                                  )
        
        val creditsLabel =
                labelOf(
                        "authors:\n\nMarcin Marlewski\n\nWiktor Leszczynski\n\nDominik Jagosz\n\nJerzy Tomaszewski",
                        Fonts.pixeloid30,
                        Colors.white,
                        Textures.translucentNinePatch
                       )
        
        val backButton = textButtonOf(
                "back",
                Fonts.inconsolata30,
                Colors.black,
                Textures.upLongNinePatch,
                Textures.downLongNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                     )
        {
            Sounds.blop.playOnce()
            MenuScreen.setScreen()
        }
        
        creditsLabel.setAlignment(Align.center)
        
        val column = columnOf(
                rowOf(creditsTitle),
                rowOf(creditsLabel),
                rowOf(backButton)
                             )
        column.setFillParent(true)
        stage.addActor(column)
    }
    
    override fun render(delta : Float)
    {
        ScreenUtils.clear(Color.CLEAR)
        
        // Draw the background texture region
        EscapeFromMorasko.spriteBatch.begin()
        EscapeFromMorasko.spriteBatch.draw(Textures.mainMenuBackground, 0f, 0f, viewport.worldWidth, viewport.worldHeight)
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
