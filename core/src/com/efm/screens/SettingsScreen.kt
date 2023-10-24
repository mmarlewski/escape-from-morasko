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
    var musicRadioButton : CheckBox
    var musicSlider : Slider
    var soundEffectsRadioButton : CheckBox
    var soundEffectsmusicSlider : Slider
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
        soundEffectsLabel = labelOf("sound effects", Fonts.pixeloid30, Colors.white, Textures.translucentNinePatch)
        
        musicRadioButton = checkBoxOf(
                "", Fonts.pixeloid10, Colors.black,
                Textures.materialCheckboxOn,
                Textures.materialCheckboxOff,
                Textures.materialCheckboxOn,
                Textures.materialCheckboxOff,
                Textures.materialCheckboxOn,
                Textures.materialCheckboxOff
                                     )
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
        
        soundEffectsRadioButton = checkBoxOf(
                "", Fonts.pixeloid10, Colors.black,
                Textures.materialCheckboxOn,
                Textures.materialCheckboxOff,
                Textures.materialCheckboxOn,
                Textures.materialCheckboxOff,
                Textures.materialCheckboxOn,
                Textures.materialCheckboxOff
                                            )
        soundEffectsmusicSlider = sliderOf(
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
                rowOf(musicLabel, musicRadioButton, musicSlider),
                rowOf(soundEffectsLabel, soundEffectsRadioButton, soundEffectsmusicSlider, columnOf().padLeft(120f)),
                rowOf(backButton)
                             )
        column.setFillParent(true)
        stage.addActor(column)
        
        musicRadioButton.addListener(object : ChangeListener()
                                     {
                                         override fun changed(event : ChangeEvent, actor : Actor)
                                         {
                                             if (musicRadioButton.isChecked)
                                             {
                                                 setMusicVolume(0f)
                                             }
                                             else
                                             {
                                                 setMusicVolume(musicSlider.value)
                                             }
                                         }
                                     })
        
        musicSlider.addListener(object : ChangeListener()
                                {
                                    override fun changed(event : ChangeEvent, actor : Actor)
                                    {
                                        if (!musicRadioButton.isChecked)
                                        {
                                            setMusicVolume(musicSlider.value)
                                        }
                                    }
                                })
        
        soundEffectsRadioButton.addListener(object : ChangeListener()
                                            {
                                                override fun changed(event : ChangeEvent, actor : Actor)
                                                {
                                                    if (soundEffectsRadioButton.isChecked)
                                                    {
                                                        setSoundVolume(0f)
                                                    }
                                                    else
                                                    {
                                                        setSoundVolume(soundEffectsmusicSlider.value)
                                                    }
                                                }
                                            })
        
        soundEffectsmusicSlider.addListener(object : ChangeListener()
                                            {
                                                override fun changed(event : ChangeEvent, actor : Actor)
                                                {
                                                    if (!soundEffectsRadioButton.isChecked)
                                                    {
                                                        setSoundVolume(soundEffectsmusicSlider.value)
                                                    }
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
}
