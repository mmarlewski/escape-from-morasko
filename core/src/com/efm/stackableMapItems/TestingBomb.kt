package com.efm.stackableMapItems

class TestingBomb(override var amount : Int = Int.MAX_VALUE / 32) : Bomb()
{
    override val name : String = "Testing Bomb"
    override val baseAPUseCost : Int = 0
    override val damage : Int = Int.MAX_VALUE / 32
    override val maxAmount : Int = Int.MAX_VALUE / 32
    override val statsDescription : String
        get() = "Damage: " + damage + "\n" +
                "AP cost: " + baseAPUseCost
}