package com.efm.entities.enemies

import com.efm.entities.enemies.bat.EnemyBat
import com.efm.entities.enemies.dzik.EnemyBoar
import com.efm.entities.enemies.ghost.EnemyGhost
import com.efm.entities.enemies.mimic.EnemyMimic
import com.efm.entities.enemies.mushroom.EnemyMushroom
import com.efm.entities.enemies.plant.EnemyPlant
import com.efm.entities.enemies.rollingStone.EnemyRollingStone
import com.efm.entities.enemies.skeleton.EnemySkeleton
import com.efm.entities.enemies.turret.EnemyTurret
import com.efm.entities.enemies.wizard.EnemyWizard
import com.efm.entity.Enemy
import kotlin.reflect.KClass

/**
 * Types of Enemies found in Rooms.
 */
enum class Enemies(private val kClass : KClass<out Enemy>)
{
    BAT(EnemyBat::class),
    BOAR(EnemyBoar::class),
    GHOST(EnemyGhost::class),
    MIMIC(EnemyMimic::class),
    MUSHROOM(EnemyMushroom::class),
    PLANT(EnemyPlant::class),
    ROLLING_STONE(EnemyRollingStone::class),
    SKELETON(EnemySkeleton::class),
    SLIME_QUARTER(EnemySlimeQuarter::class),
    TURRET(EnemyTurret::class),
    WIZARD(EnemyWizard::class);
    
    /** Returns new instance of the Enemy with default values. */
    fun new() = EnemyFactory.default(this.kClass)
}

private object EnemyFactory
{
    fun default(enemyClass : KClass<out Enemy>) =
            Class.forName(enemyClass.qualifiedName).getConstructor().newInstance() as Enemy
    
    fun default(enemy : Enemy) = Class.forName(enemy::class.qualifiedName).getConstructor().newInstance() as Enemy
}