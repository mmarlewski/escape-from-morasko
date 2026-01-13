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
        """ mushroom :     LuizMelo            (itch.io)
 bat :          bagzie              (opengameart.org)
 skeleton :     Calciumtrice        (opengameart.org)
 boar :         BenCreating         (opengameart.org)
 rock :         Unnamed             (opengameart.org)
 rock pile :    FunwithPixels       (opengameart.org)
 ghost :        Balmer              (opengameart.org)
 plant :        Aswino              (opengameart.org)
 wizard :       Merry Dream Games   (opengameart.org)
 turret :       bluecarrot16        (opengameart.org)

 goblins :      William.Thompsonj   (opengameart.org)
 barrel :       Lil Pie             (opengameart.org)
 explosion :    Pompei2             (opengameart.org)
 dragon :       ZaPaper             (opengameart.org)
 chess :        Nem_                (opengameart.org)
 slime :        Chrisblue           (opengameart.org)
 golem :        William.Thompsonj   (opengameart.org)
 npc :          Syrsly              (opengameart.org)

 floors :       Anokolisa           (itch.io)
 walls :        hawkbirdtree        (opengameart.org)

 music :
 "Dungeon 01"       Fantasy Musica  (opengameart.org)
 "Dungeon 03"       Fantasy Musica  (opengameart.org)
 "Dungeon 05"       Fantasy Musica  (opengameart.org)
 "Dungeon Deep"     adn_adn         (opengameart.org)
 "Dark Fallout"     remaxim         (opengameart.org)
 "Dungeon Ambience" yd              (opengameart.org)
 "Dungeon Theme"    remaxim         (opengameart.org)
 "Caves"        Alexandr Zhelanov   (opengameart.org)

 ui sound effects : ColorAlpha      (itch.io)

 other sound effects from Pixabay and Mixkit

 made possible with libGDX"""

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
