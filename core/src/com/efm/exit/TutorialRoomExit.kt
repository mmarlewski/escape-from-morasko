package com.efm.exit

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.assets.*
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen
import com.efm.state.getState

class TutorialRoomExit(
        position : RoomPosition,
        direction : Direction4,
        endRoomName : String,
        endPosition : RoomPosition,
        style : ExitStyle,
        endDirection : Direction4 = direction.opposite(),
        activeWhenNoEnemiesAreInRoom : Boolean = false
                      ) : RoomExit(
        position, direction, endRoomName, endPosition, style, endDirection, activeWhenNoEnemiesAreInRoom
                                  )
{
    // for serializing
    constructor() : this(RoomPosition(-1, -1), Direction4.up, "", RoomPosition(-1, -1), ExitStyle.stone)
    
    override fun interact()
    {
        if (getState().tutorialFlags.equipmentPopupShown)
        {
            super.interact()
            val enemy = World.currentRoom.getEnemies().firstOrNull()
            if (enemy != null)
            {
                GameScreen.focusCameraOnRoomPosition(enemy.position)
                val animations = mutableListOf<Animation>()
                animations += Animation.moveTileSmoothlyWithCameraFocus(
                        null,
                        World.hero.position.copy(),
                        enemy.position.copy(),
                        0.1f
                                                                       )
                animations += Animation.wait(1f)
                animations += Animation.moveTileSmoothlyWithCameraFocus(
                        null,
                        enemy.position.copy(),
                        World.hero.position.copy(),
                        0.1f
                                                                       )
                Animating.executeAnimations(animations)
            }
        }
        else
        {
            //showPopup()
            val popUp = windowAreaOf("Try looting first!",
                                     Fonts.pixeloid20,
                                     Colors.black,
                                     Textures.pauseBackgroundNinePatch,
                                     {},
                                     {})
            popUp.isVisible = false
            popUp.isVisible = true
            val nextLevelPopupWindow = columnOf(rowOf(popUp)).align(Align.center)
            nextLevelPopupWindow.setFillParent(true)
            GameScreen.stage.addActor(nextLevelPopupWindow)
        }
    }
}
