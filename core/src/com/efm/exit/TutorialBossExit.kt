package com.efm.exit

import com.efm.Direction4
import com.efm.room.RoomPosition

class TutorialBossExit(
        position : RoomPosition,
        direction : Direction4,
        endRoomName : String,
        endPosition : RoomPosition,
        style : ExitStyle,
        endDirection : Direction4 = direction.opposite()
                      ) : BossExit(
        position, direction, endRoomName, endPosition, style, endDirection
                                  )
{
    override fun interact()
    {
        /*
        if (!getState().tutorialFlags.tutorialActive || getState().tutorialFlags.bossWarningPopupShown)
        {
            super.interact()
        }
        else
        {
            // first time give warning
            TutorialPopups.addPopupToDisplay(TutorialPopups.bossWarningPopup)
            TutorialPopups.bossWarningPopup.isVisible = true
            getState().tutorialFlags.bossWarningPopupShown = true
        }
        */
    }
    
    // for serializing
    constructor() : this(RoomPosition(-1, -1), Direction4.up, "", RoomPosition(-1, -1), ExitStyle.stone)
}