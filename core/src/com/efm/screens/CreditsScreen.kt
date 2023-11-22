package com.efm.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.*
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.efm.*
import com.efm.assets.*

val assetsCreditsString =
        " mushroom :     LuizMelo            (itch.io)\n" +
                " bat :          bagzie              (opengameart.org)\n" +
                " skeleton :     Calciumtrice        (opengameart.org)\n" +
                " boar :         BenCreating         (opengameart.org)\n" +
                " rock :         Unnamed             (opengameart.org)\n" +
                " rock pile :    FunwithPixels       (opengameart.org)\n" +
                " ghost :        Balmer              (opengameart.org)\n" +
                " plant :        Aswino              (opengameart.org)\n" +
                " wizard :       Merry Dream Games   (opengameart.org)\n" +
                " turret :       bluecarrot16        (opengameart.org)\n" +
                "\n" +
                " goblins :      William.Thompsonj   (opengameart.org)\n" +
                " barrel :       Lil Pie             (opengameart.org)\n" +
                " explosion :    Pompei2             (opengameart.org)\n" +
                " dragon :       ZaPaper             (opengameart.org)\n" +
                " chess :        Nem_                (opengameart.org)\n" +
                " slime :        Chrisblue           (opengameart.org)\n" +
                " golem :        William.Thompsonj   (opengameart.org)\n" +
                "\n" +
                " floors :       Anokolisa           (itch.io)\n" +
                " walls :        hawkbirdtree        (opengameart.org)\n" +
                "\n" +
                " music :\n" +
                " \"Dungeon 01\"       Fantasy Musica  (opengameart.org)\n" +
                " \"Dungeon 03\"       Fantasy Musica  (opengameart.org)\n" +
                " \"Dungeon 05\"       Fantasy Musica  (opengameart.org)\n" +
                " \"Dungeon Deep\"     adn_adn         (opengameart.org)\n" +
                " \"Dark Fallout\"     remaxim         (opengameart.org)\n" +
                " \"Dungeon Ambience\" yd              (opengameart.org)\n" +
                " \"Dungeon Theme\"    remaxim         (opengameart.org)\n" +
                " \"Caves\"        Alexandr Zhelanov   (opengameart.org)\n" +
                "\n" +
                " ui sound effects : ColorAlpha      (itch.io)\n" +
                "\n" +
                " other sound effects from Pixabay and Mixkit"

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
        
        val authorsLabel =
                labelOf(
                        "authors:\n\nMarcin Marlewski\nWiktor Leszczynski\nDominik Jagosz\nJerzy Tomaszewski",
                        Fonts.pixeloid30,
                        Colors.white,
                        Textures.translucentNinePatch
                       )
        val assetsLabel =
                labelOf(
                        "assets:\n\n$assetsCreditsString",
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
            Sounds.ui_1.playOnce()
            MenuScreen.setScreen()
        }
        
        authorsLabel.setAlignment(Align.center)
        assetsLabel.setAlignment(Align.center)
        
        val column = columnOf(
                rowOf(creditsTitle),
                rowOf(authorsLabel),
                rowOf(assetsLabel),
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
