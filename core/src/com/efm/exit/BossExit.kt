package com.efm.exit

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction4
import com.efm.room.RoomPosition
import com.efm.state.getState
import com.efm.ui.gameScreen.*

open class BossExit(
        position : RoomPosition,
        direction : Direction4,
        endRoomName : String,
        endPosition : RoomPosition,
        style : ExitStyle,
        endDirection : Direction4 = direction.opposite(),
        
        ) : RoomExit(
        position, direction, endRoomName, endPosition, style, endDirection
                    )
{
    override var activeWhenNoEnemiesAreInRoom : Boolean = true
    
    override fun getTile() : TiledMapTile?
    {
        return if (isOpen())
        {
            when (direction)
            {
                Direction4.up    -> style.tiles.exitBossUp
                Direction4.right -> style.tiles.exitBossRight
                Direction4.down  -> style.tiles.exitBossDown
                Direction4.left  -> style.tiles.exitBossLeft
            }
        }
        else
        {
            when (direction)
            {
                Direction4.up    -> style.tiles.exitBossUpClosed
                Direction4.right -> style.tiles.exitBossRightClosed
                Direction4.down  -> style.tiles.exitBossDownClosed
                Direction4.left  -> style.tiles.exitBossLeftClosed
            }
        }
    }
    
    override fun interact()
    {
        fun giveBossWarning()
        {
            TutorialPopups.addPopupToDisplay(TutorialPopups.bossWarningPopup)
            TutorialPopups.bossWarningPopup.isVisible = true
            PopUps.setBackgroundVisibility(false)
            LeftStructure.menuButton.isVisible = false
            getState().tutorialFlags.bossWarningPopupShown = true
            getState().tutorialFlags.tutorialActive = false
        }
    
        if (getState().tutorialFlags.tutorialActive && !isOpen() && getState().tutorialFlags.closedExitPopupShown && !getState().tutorialFlags.bossWarningPopupShown)
        {
            giveBossWarning()
        }
        else if (getState().tutorialFlags.tutorialActive && isOpen() && !getState().tutorialFlags.bossWarningPopupShown)
        {
            giveBossWarning()
        }
        else
        {
            super.interact()
        }
    }
    
    // for serializing
    constructor() : this(RoomPosition(-1, -1), Direction4.up, "", RoomPosition(-1, -1), ExitStyle.stone)
}