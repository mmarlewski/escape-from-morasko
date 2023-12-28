package com.efm.entities.enemies

import com.efm.entities.enemies.Boar.EnemyBoar
import com.efm.entities.enemies.Boar.EnemyGhost
import com.efm.entity.Enemy

enum class Enemies(val enemy : Enemy)
{
    BAT(EnemyBat()),
    BOAR(EnemyBoar()),
    GHOST(EnemyGhost()),
    MIMIC(EnemyMimic()),
    MUSHROOM(EnemyMushroom()),
    PLANT(EnemyPlant()),
    ROLLING_STONE(EnemyRollingStone()),
    SKELETON(EnemySkeleton()),
    SLIME_QUARTER(EnemySlimeQuarter()),
    TURRET(EnemyTurret()),
    WIZARD(EnemyWizard())
}