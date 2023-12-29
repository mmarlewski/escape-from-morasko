package com.efm.entity

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.efm.assets.Tiles
import com.efm.room.RoomPosition

class Prop(
        val style : PropStyle = PropStyle.barrelSmall, override val position : RoomPosition = RoomPosition()
          ) : Character
{
    override var maxHealthPoints : Int = 1
    override var healthPoints : Int = 1
    override var alive : Boolean = true
    
    override fun getTile() : TiledMapTile = style.tiles.tile
    override fun getOutlineYellowTile(n : Int) : TiledMapTile = style.tiles.outlineYellowTile
}

data class PropTiles(
        val tile : TiledMapTile, val outlineYellowTile : TiledMapTile
                    )

enum class PropStyle(val tiles : PropTiles)
{
    barrelSmall(PropTiles(Tiles.barrelSmall, Tiles.barrelSmallOutlineYellow)),
    barrelSmallDamaged1(PropTiles(Tiles.barrelSmallDamaged1, Tiles.barrelSmallOutlineYellow)),
    barrelSmallDamaged2(PropTiles(Tiles.barrelSmallDamaged2, Tiles.barrelSmallOutlineYellow)),
    bookshelfHugeEmpty(PropTiles(Tiles.bookshelfHugeEmpty, Tiles.bookshelfHugeOutlineYellow)),
    
    cabinetWide(PropTiles(Tiles.cabinetWide, Tiles.cabinetWideOutlineYellow)),
    
    chairFront(PropTiles(Tiles.chairFront, Tiles.chairFrontOutlineYellow)),
    chairSideLeft(PropTiles(Tiles.chairSideLeft, Tiles.chairSideLeftOutlineYellow)),
    chairSideRight(PropTiles(Tiles.chairSideRight, Tiles.chairSideRightOutlineYellow)),
    
    crateSmall(PropTiles(Tiles.crateSmall, Tiles.crateSmallOutlineYellow)),
    crateSmallDamaged1(PropTiles(Tiles.crateSmallDamaged1, Tiles.crateSmallOutlineYellow)),
    crateSmallDamaged2(PropTiles(Tiles.crateSmallDamaged2, Tiles.crateSmallOutlineYellow)),
    
    potSmall(PropTiles(Tiles.potSmall, Tiles.potSmallOutlineYellow)),
    potSmallDamaged1(PropTiles(Tiles.potSmallDamaged1, Tiles.potSmallOutlineYellow)),
    potSmallFullWater(PropTiles(Tiles.potSmallFullWater, Tiles.potSmallOutlineYellow)),
    potSmallFullWine(PropTiles(Tiles.potSmallFullWine, Tiles.potSmallOutlineYellow)),
    
    stool(PropTiles(Tiles.stool, Tiles.stoolOutlineYellow)),
    
    tableMedium(PropTiles(Tiles.tableMedium, Tiles.tableMediumOutlineYellow)),
    
    tableSmall(PropTiles(Tiles.tableSmall, Tiles.tableSmallOutlineYellow)),
    
    vase(PropTiles(Tiles.vase, Tiles.vaseOutlineYellow)),
    vaseDamaged(PropTiles(Tiles.vaseDamaged, Tiles.vaseOutlineYellow)),
    vaseFullWater(PropTiles(Tiles.vaseFullWater, Tiles.vaseOutlineYellow)),
    vaseFullWine(PropTiles(Tiles.vaseFullWine, Tiles.vaseOutlineYellow)),
    
    tableMediumWaterWater(PropTiles(Tiles.tableMediumWaterWater, Tiles.tableMediumOutlineYellow)),
    tableMediumWaterWine(PropTiles(Tiles.tableMediumWaterWine, Tiles.tableMediumOutlineYellow)),
    tableMediumWineWater(PropTiles(Tiles.tableMediumWineWater, Tiles.tableMediumOutlineYellow)),
    tableMediumWineWine(PropTiles(Tiles.tableMediumWineWine, Tiles.tableMediumOutlineYellow)),
    
    tableSmallWater(PropTiles(Tiles.tableSmallWater, Tiles.tableSmallOutlineYellow)),
    tableSmallWine(PropTiles(Tiles.tableSmallWine, Tiles.tableSmallOutlineYellow));
    
    companion object
    {
        fun getOrdinal(propStyle : PropStyle) : Int = propStyle.ordinal
        fun getWallStyle(propStyleNumber : Int) = values()[propStyleNumber]
    }
}
