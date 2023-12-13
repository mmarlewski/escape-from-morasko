package com.efm.multiUseMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.assets.Textures

class IronSword : Sword()
{
    override val name : String = "Iron Sword"
    override var baseAPUseCost : Int = 1
    override var durability : Int = 20
    override var maxDurability : Int = 20
    override val durabilityUseCost : Int = 1
    override val damage : Int = 4
    
    override fun getTexture() : Texture
    {
        return Textures.ironSword
    }
}

class TurquoiseSword : Sword()
{
    override val name : String = "Turquoise Sword"
    override var baseAPUseCost : Int = 2
    override var durability : Int = 7
    override var maxDurability : Int = 7
    override val durabilityUseCost : Int = 1
    override val damage : Int = 8
    
    override fun getTexture() : Texture
    {
        return Textures.turquoiseSword
    }
}

class AmberSword : Sword()
{
    override val name : String = "Amber Sword"
    override var baseAPUseCost : Int = 2
    override var durability : Int = 9
    override var maxDurability : Int = 9
    override val durabilityUseCost : Int = 1
    override val damage : Int = 6
    
    override fun getTexture() : Texture
    {
        return Textures.amberSword
    }
}