package com.efm

import com.efm.entity.Enemy

var defeatedBosses = mutableListOf<Enemy>()

fun addBossToDefeatedBossesList(boss : Enemy)
{
    defeatedBosses.add(boss)
}

fun getDefeatedBossesList() : MutableList<Enemy>
{
    return defeatedBosses;
}