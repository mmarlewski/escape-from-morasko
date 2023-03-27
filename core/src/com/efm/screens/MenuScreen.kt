package com.efm.screens

import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.*
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.efm.*
import com.efm.assets.*
import com.efm.assets.Colors

object MenuScreen : BaseScreen()
{
    val camera = OrthographicCamera()
    val viewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, camera)
    val stage = Stage(viewport, EscapeFromMorasko.spriteBatch)
    
    init
    {
        // input processor
        super.inputProcessor = stage
        
        // hud
    
        val gameTitle = imageOf(
                Textures.title,
                Scaling.contain
                                   )
        
        
        val playTextButton = textButtonOf(
                "continue",
                Fonts.inconsolata30,
                Colors.black,
                Textures.upLongNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                         )
        {
            Sounds.blop.playOnce()
            GameScreen.setScreen()
        }
    
        val startAgainTextButton = textButtonOf(
                "start again",
                Fonts.inconsolata30,
                Colors.black,
                Textures.upLongNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                         )
        {
            Sounds.blop.playOnce()
        }
    
        val creditsTextButton = textButtonOf(
                "credits",
                Fonts.inconsolata30,
                Colors.black,
                Textures.upLongNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                               )
        {
            Sounds.blop.playOnce()
        }
        
        val exitButton = imageButtonOf(
                Textures.exit,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
        )
        {
            Sounds.blop.playOnce()
            exitGame()
        }
        val settingsButton = imageButtonOf(
                Textures.settings,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                               )
        {
            Sounds.blop.playOnce()
        }
    
        val infoButton = imageButtonOf(
                Textures.info,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                         )
        {
            Sounds.blop.playOnce()
        }
//        val label = labelOf(
//                "label",
//                Fonts.pixeloid30,
//                Colors.yellow,
//                Textures.upNinePatch
//                           )
//        val progressBar = progressBarOf(
//                0.0f,
//                1.0f,
//                0.1f,
//                Textures.knobBackgroundNinePatch,
//                Textures.knobBeforeNinePatch,
//                Textures.knobAfterNinePatch
//                                       )
//        val slider = sliderOf(
//                0.0f,
//                1.0f,
//                0.1f,
//                Textures.knobBackgroundNinePatch,
//                Textures.knobBeforeNinePatch,
//                Textures.knobAfterNinePatch,
//                Textures.knobNinePatch
//                             )
//        val checkBox = checkBoxOf(
//                "meow",
//                Fonts.inconsolata20,
//                Colors.chartreuse,
//                Textures.checkBoxOn,
//                Textures.checkBoxOff,
//                Textures.checkBoxOnOver,
//                Textures.checkBoxOffOver,
//                Textures.checkBoxOnDisabled,
//                Textures.checkBoxOffDisabled
//                                 )
//        val radioButton = checkBoxOf(
//                "woof",
//                Fonts.inconsolata20,
//                Colors.firebrick,
//                Textures.radioButtonOn,
//                Textures.radioButtonOff,
//                Textures.radioButtonOnOver,
//                Textures.radioButtonOffOver,
//                Textures.radioButtonOnDisabled,
//                Textures.radioButtonOffDisabled
//                                    )
//        val textArea = textAreaOf(
//                5,
//                "area",
//                Fonts.freezing32,
//                Colors.blue,
//                Colors.sky,
//                Colors.forest,
//                Textures.upNinePatch,
//                Textures.disabledNinePatch,
//                Textures.focusedNinePatch,
//                Textures.cursorNinePatch,
//                Textures.selectionNinePatch
//                                 )
        val column = columnOf(
                rowOf(
                        gameTitle
                     ),
                rowOf(),
                rowOf(),
                rowOf(),
                rowOf(
                        playTextButton
                     ),
                rowOf(),
                rowOf(),
                rowOf(),

                rowOf(
                        startAgainTextButton
                     ),
                rowOf(),
                rowOf(),
                rowOf(),
                rowOf(
                        creditsTextButton
                     ),
                rowOf(),
                rowOf(),
                rowOf(),

                rowOf(
                        settingsButton,
                        infoButton,
                        exitButton
                     )
                             )
        column.setFillParent(true)
        stage.addActor(column)
    }
    
    override fun render(delta : Float)
    {
        ScreenUtils.clear(Colors.black)
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
