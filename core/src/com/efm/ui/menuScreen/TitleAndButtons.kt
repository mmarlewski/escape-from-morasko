package com.efm.ui.menuScreen

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Scaling
import com.efm.*
import com.efm.Map
import com.efm.assets.*
import com.efm.entities.enemies.mimic.EnemyMimic
import com.efm.level.World
import com.efm.screens.*
import com.efm.ui.gameScreen.ItemsStructure

object TitleAndButtons
{
    
    lateinit var gameTitle : Image
    lateinit var playButton : TextButton
    lateinit var startAgainButton : TextButton
    lateinit var creditsButton : TextButton
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
            Sounds.ui_5.playOnce()
            playMusicIfNotAlreadyPlaying(Musics.ambient_5)
            
            // remove enemy health stacks
            for (level in World.levels)
            {
                for (room in level.rooms)
                {
                    for (enemy in room.getEnemies())
                    {
                        enemy.healthStack.remove()
                    }
                }
            }
            // load game
            loadGame()
            // add Hero to currentRoom
            World.currentRoom?.addEntityAt(World.hero, World.hero.position)
            // set new enemy health stacks
            for (level in World.levels)
            {
                for (room in level.rooms)
                {
                    for (enemy in room.getEnemies())
                    {
                        enemy.createOwnHealthBar()
                        enemy.hideOwnHealthBar()
                    }
                }
            }
            // display new enemy health stacks
            for (enemy in World.currentRoom?.getEnemies() ?: listOf())
            {
                if (enemy !is EnemyMimic || enemy.detected()) enemy.displayOwnHealthBar()
            }
            // update Room, Map, UI
            World.currentRoom?.updateSpacesEntities()
            Map.clearAllLayers()
            GameScreen.updateMapBaseLayer()
            GameScreen.updateMapEntityLayer()
            ItemsStructure.fillItemsStructureWithItemsAndSkills()
            // camera
            GameScreen.changeCameraZoom(GameScreen.currZoom)
            GameScreen.focusCameraOnRoomPosition(World.hero.position)
            
            changeScreen(GameScreen)
        }
        
        return playTextButton
    }
    
    fun startAgainButton() : TextButton
    {
        val startAgainTextButton = textButtonOf(
                "new game",
                Fonts.inconsolata30,
                Colors.black,
                Textures.upLongNinePatch,
                Textures.downLongNinePatch,
                Textures.overNinePatch,
                Textures.disabledNinePatch,
                Textures.focusedNinePatch
                                               )
        {
            Sounds.ui_5.playOnce()
            playMusicIfNotAlreadyPlaying(Musics.ambient_5)
            if (playButton.isVisible)
            {
                setButtonsVisibility(false)
                PopUpsMenu.setOverwriteSaveVisibility(true)
            }
            else
            {
                PopUpsMenu.startNewGame()
            }
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
            Sounds.ui_1.playOnce()
            CreditsScreen.setScreen()
        }
        
        return creditsTextButton
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
            Sounds.ui_1.playOnce()
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
            Sounds.ui_1.playOnce()
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
        
    }
    
    fun setButtonsVisibility(visibility : Boolean)
    {
        gameTitle.isVisible = visibility
        playButton.isVisible = visibility
        startAgainButton.isVisible = visibility
        creditsButton.isVisible = visibility
        infoButton.isVisible = visibility
        settingsButton.isVisible = visibility
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
                        columnOf(infoButton).padLeft(24f)
                     )
                             )
        column.setFillParent(true)
        MenuScreen.stage.addActor(column)
        MenuScreen.playButton = playButton
        MenuScreen.startAgainButton = startAgainButton
    }
}