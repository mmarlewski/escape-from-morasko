package com.efm.entity

abstract class BaseBoss : Enemy
{
    final override val detectionRange : Int = 20
    final override val roamingChance : Float = 0.0f
}