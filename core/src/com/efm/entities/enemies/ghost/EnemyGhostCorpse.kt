package com.efm.entities.enemies.ghost

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.entity.EnemyCorpse
import com.efm.item.PossibleItem
import com.efm.item.PossibleItems
import com.efm.multiUseMapItems.*
import com.efm.room.RoomPosition
import com.efm.stackableMapItems.Bomb
import com.efm.stackableMapItems.Explosive
import com.efm.stackableSelfItems.*
import kotlin.random.Random

class EnemyGhostCorpse(position : RoomPosition = RoomPosition(),
                       loot : PossibleItems? = null,
                       seed : Int = Random.nextInt()) : EnemyCorpse(position, loot, seed)
{
    override fun getTile() : TiledMapTile = Tiles.ghostCorpse
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = Tiles.ghostCorpseOutlineYellow
    override fun getOutlineTealTile() : TiledMapTile? = null
}

val defaultLoot = PossibleItems(
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