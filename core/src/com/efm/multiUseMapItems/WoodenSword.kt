package com.efm.multiUseMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.assets.Textures

class WoodenSword : Sword()
{
    override val name : String = "Wooden Sword"
    override var baseAPUseCost : Int = 1
    override var durability : Int = 15
    override var maxDurability : Int = 15
    override val durabilityUseCost : Int = 1
    override val damage : Int = 2
    
    override fun getTexture() : Texture
    {
        return Textures.woodenSword
    }
}
