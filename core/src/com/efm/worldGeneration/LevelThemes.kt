package com.efm.worldGeneration

import com.efm.entities.enemies.Enemies
import com.efm.entities.walls.WallStyle
import com.efm.room.Base

enum class LevelThemes(val theme : LevelTheme)
{
    GRASSY(GrassyTheme),
    BRICKY_TILEY(BrickyTileyTheme)
}

interface LevelTheme
{
    val bases : List<Base>
    val walls : List<WallStyle>
    val enemies : List<Enemies>
}

object GrassyTheme : LevelTheme
{
    // Base.water should also appear
    override val bases : List<Base> = listOf(Base.rock) + Base.grassTiles
    override val walls : List<WallStyle> = WallStyle.stoneWalls
    override val enemies : List<Enemies> = listOf(
            // common everywhere
            Enemies.MUSHROOM, Enemies.SKELETON, Enemies.BAT, Enemies.SLIME_QUARTER,
            // rare everywhere  // should be
            Enemies.MIMIC, Enemies.GHOST,
            // theme specific
            Enemies.ROLLING_STONE, Enemies.PLANT, Enemies.BOAR
                                                 )
}

object BrickyTileyTheme : LevelTheme
{
    // Base.lava should also appear
    override val bases : List<Base> = listOf(Base.metal) + Base.tiledTiles + Base.woodTiles
    override val walls : List<WallStyle> = WallStyle.brickWalls + listOf(WallStyle.metal)
    override val enemies : List<Enemies> = listOf(
            // common everywhere
            Enemies.MUSHROOM, Enemies.SKELETON, Enemies.BAT, Enemies.SLIME_QUARTER,
            // rare everywhere  // should be
            Enemies.MIMIC, Enemies.GHOST,
            // theme specific
            Enemies.TURRET, Enemies.WIZARD
                                                 )
}