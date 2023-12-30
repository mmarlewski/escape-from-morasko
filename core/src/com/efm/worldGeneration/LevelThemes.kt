package com.efm.worldGeneration

import com.efm.entities.Modifier
import com.efm.entities.enemies.Enemies
import com.efm.entities.walls.WallStyle
import com.efm.item.Item
import com.efm.item.Items
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
    val commonEnemies : List<Enemies>
    val rareEnemies : List<Enemies>
    val items : List<Items>
    val modifiers : List<Modifier>
}

object GrassyTheme : LevelTheme
{
    // Base.water should also appear
    override val bases : List<Base> = listOf(Base.rock) + Base.grassTiles
    override val walls : List<WallStyle> = WallStyle.stoneWalls
    override val enemies : List<Enemies> = listOf(
            // common everywhere
            Enemies.MUSHROOM, Enemies.SKELETON, Enemies.BAT, Enemies.SLIME_QUARTER,
            // rare everywhere
            Enemies.MIMIC, Enemies.GHOST,
            // theme specific
            Enemies.ROLLING_STONE, Enemies.PLANT, Enemies.BOAR
                                                 )
    
    override val commonEnemies : List<Enemies> = listOf(
            // common everywhere
            Enemies.MUSHROOM, Enemies.SKELETON, Enemies.BAT, Enemies.SLIME_QUARTER,
            // theme specific
            Enemies.ROLLING_STONE, Enemies.PLANT, Enemies.BOAR
                                                       )
    
    override val rareEnemies : List<Enemies> = listOf(
            // rare everywhere
            Enemies.MIMIC, Enemies.GHOST,
                                                     )
    override val items : List<Items> = getAllItems()
    override val modifiers : List<Modifier> = getAllModifiers()
}

object BrickyTileyTheme : LevelTheme
{
    // Base.lava should also appear
    override val bases : List<Base> = listOf(Base.metal) + Base.tiledTiles + Base.woodTiles + Base.tiledTilesWithBlood
    override val walls : List<WallStyle> = WallStyle.brickWalls + listOf(WallStyle.metal)
    override val enemies : List<Enemies> = listOf(
            // common everywhere
            Enemies.MUSHROOM, Enemies.SKELETON, Enemies.BAT, Enemies.SLIME_QUARTER,
            // rare everywhere  // should be
            Enemies.MIMIC, Enemies.GHOST,
            // theme specific
            Enemies.TURRET, Enemies.WIZARD
                                                 )
    
    override val commonEnemies : List<Enemies> = listOf(
            // common everywhere
            Enemies.MUSHROOM, Enemies.SKELETON, Enemies.BAT, Enemies.SLIME_QUARTER,
            // theme specific
            Enemies.TURRET, Enemies.WIZARD
                                                       )
    
    override val rareEnemies : List<Enemies> = listOf(
            // rare everywhere
            Enemies.MIMIC, Enemies.GHOST,
                                                     )
    override val items : List<Items> = getAllItems()
    override val modifiers : List<Modifier> = getAllModifiers()
}

fun getAllItems() : List<Items>
{
    return listOf(
            Items.BOW,
            Items.SLEDGEHAMMER,
            Items.SMALL_AXE,
            Items.STAFF,
            Items.IRON_SWORD,
            Items.AMBER_SWORD,
            Items.TURQUOISE_SWORD,
            Items.WOODEN_SWORD,
            Items.BOMB,
            Items.EXPLOSIVE,
            Items.SHURIKEN,
            Items.APPLE,
            Items.AP_POTION_BIG,
            Items.AP_POTION_SMALL,
            Items.FISH,
            Items.HP_POTION_BIG,
            Items.HP_POTION_SMALL,
            Items.MUSHROOM
                 )
}
fun getAllModifiers() : List<Modifier>
{
    return listOf(
            Modifier.GainHpLoseAp,
            Modifier.GainApLoseHp,
            Modifier.SwapWaterAndLava,
            Modifier.AddRandomSkill,
            Modifier.StrongerWeaponsLoseHp
                 )
}
