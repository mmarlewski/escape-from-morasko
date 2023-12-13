package com.efm.entities.enemies.Boar

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.*
import com.efm.multiUseMapItems.*
import com.efm.room.RoomPosition
import com.efm.stackableMapItems.Bomb
import com.efm.stackableMapItems.Explosive
import com.efm.stackableSelfItems.*

class EnemyGhostCorpse(position : RoomPosition) : EnemyCorpse(position)
{
    override var maxItems : Int = 5
    
    init
    {
        loot = PossibleItems(
                mutableListOf(
                        PossibleItem(APPotionSmall(), 0.33f, IntRange(0, 4)),
                        PossibleItem(APPotionBig(), 0.67f, IntRange(0, 2)),
                        PossibleItem(HPPotionSmall(), 0.33f, IntRange(0, 4)),
                        PossibleItem(HPPotionBig(), 0.67f, IntRange(0, 2)),
                        PossibleItem(Staff(), 0.2f, 1..1),
                        PossibleItem(TurquoiseSword(), 0.2f, 1..1),
                        PossibleItem(AmberSword(), 0.2f, 1..1),
                        PossibleItem(Bomb(), 0.5f, IntRange(0, 2)),
                        PossibleItem(Explosive(), 0.1f, IntRange(1, 1))
                             )
                            )
    }
    
    override val items : MutableList<Item> = loot?.drawItems() ?: mutableListOf()
    
    override fun getTile() : TiledMapTile = Tiles.ghostCorpse
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.ghostCorpseOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile? = null
}
