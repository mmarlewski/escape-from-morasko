package com.efm.ui.gameScreen

import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.assets.*
import com.efm.level.World
import com.efm.screens.GameScreen
import com.efm.skill.Skill
import com.efm.skills.Jump

object PopUps
{
    
    lateinit var endTurn : com.badlogic.gdx.scenes.scene2d.ui.Window
    lateinit var menuPause : com.badlogic.gdx.scenes.scene2d.ui.Window
    lateinit var settings : com.badlogic.gdx.scenes.scene2d.ui.Window
    lateinit var skillAssignment : Window
    
    fun setBackgroundVisibility(boolean : Boolean)
    {
        GameScreen.canBeInteractedWith = boolean
        
        RightStructure.setVisibility(boolean)
        ItemsStructure.fillItemsStructureWithItemsAndSkills()
        ItemsStructure.setVisibility(boolean)
        if (boolean) ItemsStructure.setWeaponDisplay()
        ProgressBars.setVisibilty(boolean)
        LeftStructure.setVisibility(boolean)
    }
    
    fun skillAssignment(leftSkill : Skill, middleSkill : Skill, rightSkill : Skill) : Window
    {
        val skillAssignmentPopup = skillsAssignmentOverlay(leftSkill, middleSkill, rightSkill,
                                                           {
                                                               //when skill assigned
                                                               playSoundOnce(Sounds.ui_2)
                                                               World.hero.addSkill(it)
                                                               setBackgroundVisibility(true)
                                                           },
                                                           {
                                                               //when skill reassigned
                                                               playSoundOnce(Sounds.ui_2)
                                                               World.hero.addSkill(it)
                                                               setBackgroundVisibility(true)
                                                           })
        
        skillAssignmentPopup.isVisible = false
        
        return skillAssignmentPopup
    }
    
    fun endTurn() : com.badlogic.gdx.scenes.scene2d.ui.Window
    {
        val endTurnPopUp = yesNoPopup(
                "End turn?",
                "You still have some AP left.",
                {
                    endCurrentTurn()
                    LeftStructure.menuButton.isVisible = true
                },
                { LeftStructure.menuButton.isVisible = true }
                                     )
        
        endTurnPopUp.isVisible = false
        
        return endTurnPopUp
        
    }
    
    fun settings() : com.badlogic.gdx.scenes.scene2d.ui.Window
    {
        val settingsPopUp = settingsPause(
                "SETTINGS",
                Fonts.pixeloid30, Colors.black, Textures.pauseBackgroundNinePatch
                                         )
        settingsPopUp.isVisible = false
        
        return settingsPopUp
    }
    
    fun menuPause() : com.badlogic.gdx.scenes.scene2d.ui.Window
    {
        
        val menuPausePopUp = menuPopup(
                "PAUSE",
                Fonts.pixeloid30,
                Colors.black,
                Textures.pauseBackgroundNinePatch
                                      )
        menuPausePopUp.isVisible = false
        
        
        return menuPausePopUp
    }
    
    init
    {
        endTurn = endTurn()
        menuPause = menuPause()
        settings = settings()
        
        skillAssignment = skillAssignment(Jump, Jump, Jump)
    }
    
    fun setSkillAssignmentVisibility(visibility : Boolean)
    {
        skillAssignment.isVisible = visibility
    }
    
    fun setEndTurnVisibility(visibility : Boolean)
    {
        endTurn.isVisible = visibility
    }
    
    fun setSettingsVisibility(visibility : Boolean)
    {
        settings.isVisible = visibility
    }
    
    fun setMenuVisibility(visibility : Boolean)
    {
        menuPause.isVisible = visibility
    }
    
    fun display()
    {
        val endTurnWindow = columnOf(rowOf(endTurn)).align(Align.center)
        val pauseWindow = columnOf(rowOf(menuPause)).align(Align.center)
        val settingsWindow = columnOf(rowOf(settings)).align(Align.center)
        val skillAssignmentWindow = columnOf(rowOf(skillAssignment)).align(Align.center)
        
        
        settingsWindow.setFillParent(true)
        endTurnWindow.setFillParent(true)
        pauseWindow.setFillParent(true)
        skillAssignmentWindow.setFillParent(true)
        
        GameScreen.stage.addActor(endTurnWindow)
        GameScreen.stage.addActor(settingsWindow)
        GameScreen.stage.addActor(pauseWindow)
        GameScreen.stage.addActor(skillAssignmentWindow)
        
    }
}