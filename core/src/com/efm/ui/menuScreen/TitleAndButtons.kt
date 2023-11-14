package com.efm.ui.menuScreen

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Scaling
import com.efm.*
import com.efm.assets.*
import com.efm.screens.*
import com.efm.ui.gameScreen.PopUps

object TitleAndButtons
{
    
    lateinit var gameTitle : Image
    lateinit var playButton : TextButton
    lateinit var startAgainButton : TextButton
    lateinit var creditsButton : TextButton
    lateinit var exitButton : ImageButton
    lateinit var infoButton : ImageButton
    lateinit var settingsButton : ImageButton
    
    fun gameTitle() : Image
    {
        val gameTitle = imageOf(
                Textures.title,
                Scaling.none
                               )
        
        return gameTitle
    }
    
    fun playButton() : TextButton
    {
        val playTextButton = textButtonOf(
                "continue",
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
            playMusicIfNotAlreadyPlaying(Musics.versaLifeAmbient)
            GameScreen.setScreen()
        }
        
        return playTextButton
    }
    
    fun startAgainButton() : TextButton
    {
        val startAgainTextButton = textButtonOf(
                "start again",
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
            PopUps.setOverwriteSaveVisibility(true)
            setButtonsVisibility(false)
        }
        
        return startAgainTextButton
    }
    
    fun creditsButton() : TextButton
    {
        val creditsTextButton = textButtonOf(
                "credits",
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
            CreditsScreen.setScreen()
        }
        
        return creditsTextButton
    }
    
    fun exitButton() : ImageButton
    {
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
        
        return exitButton
    }
    
    fun settingsButton() : ImageButton
    {
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
            SettingsScreen.setScreen()
        }
        
        return settingsButton
    }
    
    fun infoButton() : ImageButton
    {
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
            AppInfoScreen.setScreen()
        }
        
        return infoButton
    }
    
    init
    {
        gameTitle = gameTitle()
        playButton = playButton()
        startAgainButton = startAgainButton()
        creditsButton = creditsButton()
        infoButton = infoButton()
        settingsButton = settingsButton()
        exitButton = exitButton()
        
    }
    
    fun setButtonsVisibility(visibility : Boolean)
    {
        gameTitle.isVisible = visibility
        playButton.isVisible = visibility
        startAgainButton.isVisible = visibility
        creditsButton.isVisible = visibility
        infoButton.isVisible = visibility
        settingsButton.isVisible = visibility
        exitButton.isVisible = visibility
    }
    
    fun display()
    {
        val column = columnOf(
                rowOf(
                        gameTitle
                     ),
                rowOf(
                        playButton
                     ),
                
                rowOf(
                        startAgainButton
                     ),
                rowOf(
                        creditsButton
                     ),
                
                rowOf(
                        columnOf(settingsButton),
                        columnOf(infoButton).padLeft(20f).padRight(20f),
                        columnOf(exitButton)
                     )
                             )
        column.setFillParent(true)
        MenuScreen.stage.addActor(column)
    }
}