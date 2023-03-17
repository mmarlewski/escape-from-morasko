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
        val medicalTextButton = textButtonOf(
                "play medical",
                Fonts.pixeloid30,
                Colors.firebrick,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                            )
        {
            playMusic(Musics.medical)
        }
        val executiveTextButton = textButtonOf(
                "play executive",
                Fonts.pixeloid20,
                Colors.forest,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                              )
        {
            playMusic(Musics.executive)
        }
        val gameTextButton = textButtonOf(
                "go to game",
                Fonts.freezing32,
                Colors.sky,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                         )
        {
            Sounds.blop.playOnce()
            GameScreen.setScreen()
        }
        val exitTextButton = textButtonOf(
                "exit",
                Fonts.pixeloid20,
                Colors.forest,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                         )
        {
            exitGame()
        }
        val label = labelOf(
                "label",
                Fonts.pixeloid30,
                Colors.yellow,
                Textures.upNinePatch
                           )
        val progressBar = progressBarOf(
                0.0f,
                1.0f,
                0.1f,
                Textures.knobBackgroundNinePatch,
                Textures.knobBeforeNinePatch,
                Textures.knobAfterNinePatch
                                       )
        val slider = sliderOf(
                0.0f,
                1.0f,
                0.1f,
                Textures.knobBackgroundNinePatch,
                Textures.knobBeforeNinePatch,
                Textures.knobAfterNinePatch,
                Textures.knobNinePatch
                             )
        val imageButton = imageButtonOf(
                Textures.coin,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                       )
        {
            Sounds.coin.playOnce()
        }
        val imageTextButton = imageTextButtonOf(
                "image text",
                Fonts.pixeloid30,
                Colors.maroon,
                Textures.coin,
                Textures.upNinePatch,
                Textures.downNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                               )
        {
            Sounds.jump.playOnce()
        }
        val image = imageOf(
                Textures.coin,
                Scaling.contain
                           )
        val checkBox = checkBoxOf(
                "meow",
                Fonts.inconsolata20,
                Colors.chartreuse,
                Textures.checkBoxOn,
                Textures.checkBoxOff,
                Textures.checkBoxOnOver,
                Textures.checkBoxOffOver,
                Textures.checkBoxOnDisabled,
                Textures.checkBoxOffDisabled
                                 )
        val radioButton = checkBoxOf(
                "woof",
                Fonts.inconsolata20,
                Colors.firebrick,
                Textures.radioButtonOn,
                Textures.radioButtonOff,
                Textures.radioButtonOnOver,
                Textures.radioButtonOffOver,
                Textures.radioButtonOnDisabled,
                Textures.radioButtonOffDisabled
                                    )
        val textField1 = textFieldOf(
                "field1",
                Fonts.freezing32,
                Colors.blue,
                Colors.sky,
                Colors.forest,
                Textures.upNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch,
                Textures.cursorNinePatch,
                Textures.selectionNinePatch
                                    )
        val textField2 = textFieldOf(
                "field2",
                Fonts.freezing32,
                Colors.blue,
                Colors.sky,
                Colors.forest,
                Textures.upNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch,
                Textures.cursorNinePatch,
                Textures.selectionNinePatch
                                    )
        val textArea = textAreaOf(
                5,
                "area",
                Fonts.freezing32,
                Colors.blue,
                Colors.sky,
                Colors.forest,
                Textures.upNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch,
                Textures.cursorNinePatch,
                Textures.selectionNinePatch
                                 )
        val root = columnOf(
                rowOf(
                        label,
                        gameTextButton
                     ),
                rowOf(
                        medicalTextButton,
                        executiveTextButton
                     ),
                rowOf(
                        textField1,
                        textField2
                     ),
                rowOf(
                        exitTextButton,
                        image,
                        imageButton,
                        imageTextButton
                     ),
                rowOf(
                        progressBar,
                        slider
                     ),
                rowOf(
                        checkBox,
                        radioButton
                     ),
                textArea
                           )
        stage.setRootActor(root)
        //stage.isDebugAll = true
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
