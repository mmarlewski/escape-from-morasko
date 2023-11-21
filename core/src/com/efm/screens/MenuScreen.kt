package com.efm.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.efm.*
import com.efm.assets.Textures
import com.efm.ui.menuScreen.PopUpsMenu
import com.efm.ui.menuScreen.TitleAndButtons

object MenuScreen : BaseScreen()
{
    val camera = OrthographicCamera()
    val viewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, camera)
    val stage = Stage(viewport, EscapeFromMorasko.spriteBatch)
    
    var playButton : TextButton? = null
    var startAgainButton : TextButton? = null
    
    var saveExists = false
    
    init
    {
        // input processor
        super.inputProcessor = stage
        
        PopUpsMenu.display()
        TitleAndButtons.display()
    }
    
    override fun render(delta : Float)
    {
        saveExists = saveExists()
        if (playButton?.isVisible == true)
        {
            playButton?.isVisible = saveExists
        }
        
        ScreenUtils.clear(Color.CLEAR)
        
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
