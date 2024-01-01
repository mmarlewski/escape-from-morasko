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
                labelOf("current version: 1.1.0", Fonts.pixeloid30, Colors.white, Textures.translucentNinePatch)
        
        val whatsNewLabel = patchNotesLabel(
                "What's new?",
                "- procedural level generation,\n" +
                        "- all items have their statistics shown when selected in equipment,\n" +
                        "- weapons have their statistics displayed when selected while playing\n" +
                        "- new decorative elements spawn in rooms",
                appInfoTitle.width
                                           )
    
        val bugFixesLabel = patchNotesLabel(
                "Bug fixes",
                "- Game Over screen now appears when hero is killed by their own actions,\n" +
                        "- items no longer have amount of 0 when looting,\n" +
                        "- new items no longer appear in corpses after every loading\n" +
                        "- mimic pretends to be a chest more convincingly",
                appInfoTitle.width
                                           )

//        val balancingLabel = patchNotesLabel(
//                "Balancing",
//                "",
//                appInfoTitle.width
//                                            )
    
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
//                rowOf(balancingLabel),
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
