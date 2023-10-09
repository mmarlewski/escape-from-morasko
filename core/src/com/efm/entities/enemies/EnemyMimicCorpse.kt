package com.efm.entities.enemies

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.*
import com.efm.room.RoomPosition
import com.efm.stackableSelfItems.Mushroom

class EnemyMimicCorpse : EnemyCorpse
{
    override val position = RoomPosition()
    
    override var loot = PossibleItems(
            mutableListOf(
                    PossibleItem(Mushroom(), 0.8f, IntRange(0, 4))
                         )
                                     )
    override val items : MutableList<Item> = loot.drawItems()
    override var maxItems : Int = 4
    
    override fun getTile() : TiledMapTile
    {
        return Tiles.mimicCorpse
    }
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile
    {
        return Tiles.mimicCorpseOutlineYellow
    }
    
    // kiedy uzywany jest Teal Outline ?
    override fun getOutlineTealTile() : TiledMapTile?
    {
        return null
    }
}