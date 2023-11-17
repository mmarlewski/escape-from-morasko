package com.efm

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.efm.level.World
import com.efm.multiUseMapItems.*
import com.efm.screens.MenuScreen
import com.efm.skills.*
import com.efm.stackableMapItems.Bomb
import com.efm.stackableMapItems.Explosive
import com.efm.stackableSelfItems.*
import com.efm.ui.gameScreen.ProgressBars

object EscapeFromMorasko : Game()
{
    lateinit var spriteBatch : SpriteBatch
    
    override fun create()
    {
        spriteBatch = SpriteBatch()
        
//        val loadFromSave = true
        val loadFromSave = false
        
        if (loadFromSave)
        {
            loadWorld()
            World.currentRoom.addEntityAt(World.hero, World.hero.position)
        }
        else
        {
//            World.createWorldPrototypeTwo()
//        World.createWorldBoarTest()
        World.createWorldPrototypeThree()
            
            val startingLevel = World.getLevels().first()
            World.changeCurrentLevel(startingLevel)
            World.changeCurrentRoom(startingLevel.getStartingRoom())
            World.currentRoom.addEntityAt(World.hero, startingLevel.getStartingPosition())
            World.currentRoom.updateSpacesEntities()
            
            World.hero.inventory.addItem(SmallAxe())
            World.hero.inventory.addItem(Sledgehammer())
            World.hero.inventory.addItem(Bow())
            World.hero.inventory.addItem(Staff())
            World.hero.inventory.addItem(Bomb())
            World.hero.inventory.addItem(Explosive())
            World.hero.inventory.addItem(Explosive())
            World.hero.inventory.addItem(Explosive())
            World.hero.inventory.addItem(Apple())
            World.hero.inventory.addItem(Fish())
            World.hero.inventory.addItem(Mushroom())
            World.hero.addSkill(LavaWalking)
            World.hero.addSkill(Push)
            World.hero.addSkill(Invisibility)
            World.hero.addSkill(Freeze)
            World.hero.addSkill(GrassHealing)
        }
        
        changeScreen(MenuScreen)
    }
    
    override fun dispose()
    {
        spriteBatch.dispose()
    }
}
