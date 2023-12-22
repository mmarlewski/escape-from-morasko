package com.efm.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.efm.*
import com.efm.assets.*

object AppInfoScreen : BaseScreen()
{
    val camera = OrthographicCamera()
    val viewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, camera)
    val stage = Stage(viewport, EscapeFromMorasko.spriteBatch)
    
    init
    {
        // input processor
        super.inputProcessor = stage
        
        // hud
        
        val appInfoTitle = imageOf(
                Textures.appInfo,
                Scaling.none
                                  )
        
        val versionLabel =
                labelOf("current version: 1.0.1", Fonts.pixeloid30, Colors.white, Textures.translucentNinePatch)
        
        val whatsNewLabel = patchNotesLabel(
                "What's new?",
                "- it is now possible to interact with objects such as chests, corpses and NPCs without the need of using movement mode, if the player’s character is next to them,\n" +
                        "- passages between rooms change visually to show if it is possible to use them,\n" +
                        "- added an option to exit movement mode by pressing movement button or player’s character,\n" +
                        "- removed “quit game” button,\n" +
                        "- added barrel throwing skill,\n" +
                        "- expanded the tutorial,\n" +
                        "- added an option to attack enemies with fists.",
                appInfoTitle.width
                                           )
        
        val bugFixesLabel = patchNotesLabel(
                "Bug fixes",
                "- fixed a movement bug that caused players to lose action points, despite not changing player’s position,\n" +
                        "- UI is no longer visible when special event pop-ups are shown,\n" +
                        "- missing elements of UI are now drawn after closing the game and pressing continue,\n" +
                        "- the game now ends immediately after the player's death.",
                appInfoTitle.width
                                           )
        
        val balancingLabel = patchNotesLabel(
                "Balancing",
                "- weapons are now more durable,\n" +
                        "- more weapons appear in chests,\n" +
                        "- enemies deal more damage.",
                appInfoTitle.width
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
            Sounds.ui_1.playOnce()
            MenuScreen.setScreen()
        }
        
        val column = columnOf(
                rowOf(appInfoTitle),
                rowOf(versionLabel),
                rowOf(whatsNewLabel),
                rowOf(bugFixesLabel),
                rowOf(balancingLabel),
                rowOf(backButton)
                             )
        
        val scrollPaneStyle = ScrollPane.ScrollPaneStyle()
        val scrollPane = ScrollPane(column, scrollPaneStyle)
        
        scrollPane.setFillParent(true)
        stage.addActor(scrollPane)
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
        stage.act()
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
