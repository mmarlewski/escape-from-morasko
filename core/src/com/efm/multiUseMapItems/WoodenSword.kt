package com.efm.multiUseMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.assets.Textures

class WoodenSword : Sword()
{
    override val name : String = "Wooden Sword"
    override var baseAPUseCost : Int = 1
    override var durability : Int = 25
    override var maxDurability : Int = 25
    override val durabilityUseCost : Int = 1
    override val damage : Int = 2
    override val statsDescription : String
        get() = "Damage: " + damage + "\n" +
                "Uses left: " + durability + "\n" +
                "AP cost: " + baseAPUseCost
    
    override fun getTexture() : Texture
    {
        return Textures.woodenSword
    }
}
