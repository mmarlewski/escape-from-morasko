package com.efm.entities.enemies

import com.efm.entities.enemies.bat.EnemyBat
import com.efm.entities.enemies.dzik.EnemyBoar
import com.efm.entities.enemies.ghost.EnemyGhost
import com.efm.entities.enemies.mimic.EnemyMimic
import com.efm.entities.enemies.mushroom.EnemyMushroom
import com.efm.entities.enemies.plant.EnemyPlant
import com.efm.entities.enemies.rollingStone.EnemyRollingStone
import com.efm.entities.enemies.skeleton.EnemySkeleton
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