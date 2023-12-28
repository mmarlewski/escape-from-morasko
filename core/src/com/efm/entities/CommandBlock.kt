package com.efm.entities

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.Interactive
import com.efm.room.RoomPosition

/**
 * Interactive Entity made for testing, that performs supplied action.
 * @param action code run on interaction
 */
class CommandBlock(
        val action : () -> Unit = { }
                  ) : Interactive
{
    override val position : RoomPosition = RoomPosition(0, 0)
    
    override fun getTile() : TiledMapTile = Tiles.beamDiagonalHorizontal1
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.beamVertical1
    
    override fun getOutlineTealTile() : TiledMapTile = Tiles.beamHorizontal1
    
    override fun interact()
    {
        action()
    }
}
