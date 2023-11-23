package com.efm.exit

import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.assets.*
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
            /*
            val enemy = currentRoom.getEnemies().firstOrNull()
            if (enemy != null)
            {
                GameScreen.focusCameraOnRoomPosition(enemy.position)
                val animations = mutableListOf<Animation>()
                animations += Animation.action {
                    Animation.moveTileSmoothlyWithCameraFocus(null, hero.position, enemy.position, 100f)
                    Animation.wait(10f)
                    Animation.moveTileSmoothlyWithCameraFocus(null, enemy.position, hero.position, 100f)
                }
                if (!Animating.isAnimating())
                {
                    Animating.executeAnimations(animations)
                    Gdx.app.log("Exit", "to sie printuje")
                }
            }
            */
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
