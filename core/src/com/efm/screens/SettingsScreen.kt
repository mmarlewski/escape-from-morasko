package com.efm.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.efm.*
import com.efm.assets.*

object SettingsScreen : BaseScreen()
{
    val camera = OrthographicCamera()
    val viewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, camera)
    val stage = Stage(viewport, EscapeFromMorasko.spriteBatch)
    
    init
    {
        // input processor
        super.inputProcessor = stage
        
        // hud
        
        val settingsTitle = imageOf(
                Textures.settingsTitle,
                Scaling.none
                                   )
        
        val musicLabel = labelOf("music", Fonts.pixeloid30, Colors.white, Textures.translucentNinePatch)
        val soundEffectsLabel = labelOf("sound effects", Fonts.pixeloid30, Colors.white, Textures.translucentNinePatch)
        
        val musicRadioButton = checkBoxOf(
                "", Fonts.pixeloid10, Colors.black,
                Textures.materialCheckboxOn,
                Textures.materialCheckboxOff,
                Textures.materialCheckboxOn,
                Textures.materialCheckboxOff,
                Textures.materialCheckboxOn,
                Textures.materialCheckboxOff
                                         )
        val musicSlider = sliderOf(
                0.0f,
                1.0f,
                0.1f,
                Textures.materialKnobNinePatchBeforeBlue,
                Textures.materialKnobNinePatchAfter,
                Textures.materialKnobNinePatchBeforeBlue,
                Textures.materialKnobNinePatch
                                  )
        
        val soundEffectsRadioButton = checkBoxOf(
                "", Fonts.pixeloid10, Colors.black,
                Textures.materialCheckboxOn,
                Textures.materialCheckboxOff,
                Textures.materialCheckboxOn,
                Textures.materialCheckboxOff,
                Textures.materialCheckboxOn,
                Textures.materialCheckboxOff
                                                )
        val soundEffectsmusicSlider = sliderOf(
                0.0f,
                1.0f,
                0.1f,
                Textures.materialKnobNinePatchBeforeBlue,
                Textures.materialKnobNinePatchAfter,
                Textures.materialKnobNinePatchBeforeBlue,
                Textures.materialKnobNinePatch
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
        
        val column = columnOf(
                rowOf(settingsTitle),
                rowOf(musicLabel, musicRadioButton, musicSlider),
                rowOf(soundEffectsLabel, soundEffectsRadioButton, soundEffectsmusicSlider, columnOf().padLeft(120f)),
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
