package com.efm.exit

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.Direction4
import com.efm.room.RoomPosition

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
    
    // for serializing
    constructor() : this(RoomPosition(-1, -1), Direction4.up, "", RoomPosition(-1, -1), ExitStyle.stone)
}