package com.efm.exit

import com.efm.*
import com.efm.level.World
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen
import com.efm.state.getState
import com.efm.ui.gameScreen.*

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
            LeftStructure.menuButton.isVisible = false
            RightStructure.moveButton.isVisible = false
            TutorialPopups.addPopupToDisplay(TutorialPopups.cantLeavePopup)
            TutorialPopups.cantLeavePopup.isVisible = true
        }
    }
}
