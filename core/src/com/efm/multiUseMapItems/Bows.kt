package com.efm.multiUseMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.assets.Textures

class Bow : BaseBow()
{
    override val name : String = "Bow"
    override val baseAPUseCost : Int = 1
    override var durability : Int = 20
    override var maxDurability : Int = 20
    override val durabilityUseCost : Int = 1
    override val damage : Int = 3
    override val statsDescription : String
        get() = "Damage: " + damage + "\n" +
                "AP cost: " + baseAPUseCost + "\n" +
                "Uses left: " + durability
    
    override fun getTexture() : Texture = Textures.bow
}

class SunBow : BaseBow()
{
    override val name : String = "Sun Bow"
    override val baseAPUseCost : Int = 1
    override var durability : Int = 20
    override var maxDurability : Int = 20
    override val durabilityUseCost : Int = 1
    override val damage : Int = 6
    override val statsDescription : String
        get() = "Damage: " + damage + "\n" +
                "AP cost: " + baseAPUseCost + "\n" +
                "Uses left: " + durability
    
    override fun getTexture() : Texture = Textures.sunBow
}
