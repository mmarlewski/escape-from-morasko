package com.efm.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.efm.*
import com.efm.assets.Textures
import com.efm.ui.gameScreen.Equipment

object EquipmentScreen : BaseScreen()
{
    val camera = OrthographicCamera()
    val viewport = ExtendViewport(minScreenWidth, minScreenHeight, maxScreenWidth, maxScreenHeight, camera)
    val stage = Stage(viewport, EscapeFromMorasko.spriteBatch)
    
    init
    {
        // input processor
        super.inputProcessor = stage
        
        // hud
        
        Equipment.display()
    }
    
    override fun render(delta : Float)
    {
        ScreenUtils.clear(Color.CLEAR)
        
        // Draw the background texture region
        EscapeFromMorasko.spriteBatch.begin()
        EscapeFromMorasko.spriteBatch.draw(Textures.translucent, 0f, 0f, viewport.worldWidth, viewport.worldHeight)
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
