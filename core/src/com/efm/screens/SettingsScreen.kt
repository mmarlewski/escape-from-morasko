package com.efm.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
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
    
    var settingsTitle : Image
    var musicLabel : Label
    var soundEffectsLabel : Label
    var musicSlider : Slider
    var soundSlider : Slider
    var backButton : TextButton
    
    init
    {
        // input processor
        super.inputProcessor = stage
        
        // hud
        
        settingsTitle = imageOf(
                Textures.settingsTitle,
                Scaling.none
                               )
        
        musicLabel = labelOf("music", Fonts.pixeloid30, Colors.white, Textures.translucentNinePatch)
        soundEffectsLabel = labelOf("sound", Fonts.pixeloid30, Colors.white, Textures.translucentNinePatch)
        
        musicSlider = sliderOf(
                0.0f,
                1.0f,
                0.1f,
                getMusicVolume(),
                Textures.materialKnobNinePatchBeforeBlue,
                Textures.materialKnobNinePatchAfter,
                Textures.materialKnobNinePatchBeforeBlue,
                Textures.materialKnobNinePatch
                              )
        
        soundSlider = sliderOf(
                0.0f,
                1.0f,
                0.1f,
                getSoundVolume(),
                Textures.materialKnobNinePatchBeforeBlue,
                Textures.materialKnobNinePatchAfter,
                Textures.materialKnobNinePatchBeforeBlue,
                Textures.materialKnobNinePatch
                                                  )
        
        backButton = textButtonOf(
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
                rowOf(musicLabel, musicSlider),
                rowOf(soundEffectsLabel, soundSlider),
                rowOf(backButton)
                             )
        column.setFillParent(true)
        stage.addActor(column)
        
        musicSlider.addListener(object : ChangeListener()
                                {
                                    override fun changed(event : ChangeEvent, actor : Actor)
                                    {
                                        setMusicVolume(musicSlider.value)
                                    }
                                })
        
        soundSlider.addListener(object : ChangeListener()
                                            {
                                                override fun changed(event : ChangeEvent, actor : Actor)
                                                {
                                                    setSoundVolume(soundSlider.value)
                                                }
                                            })
        
    }
    
    override fun render(delta : Float)
    {
//
        // clear screen
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
    
    override fun show()
    {
        super.show()
        musicSlider.value = getMusicVolume()
        soundSlider.value = getSoundVolume()
    }
}
