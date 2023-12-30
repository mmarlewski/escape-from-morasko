package com.efm.multiUseMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.assets.Textures

class SmallAxe : Axe()
{
    override val name : String = "Small Axe"
    override var baseAPUseCost : Int = 1
    override var durability : Int = 16
    override var maxDurability : Int = 16
    override val durabilityUseCost : Int = 1
    override val damage : Int = 4
    
    override fun getTexture() : Texture = Textures.smallAxe
}

class DoubleBitAxe : Axe()
{
    override val name : String = "Double-bit Axe"
    override var baseAPUseCost : Int = 4
    override var durability : Int = 30
    override var maxDurability : Int = 30
    override val durabilityUseCost : Int = 1
    override val damage : Int = 12
    
    override fun getTexture() : Texture = Textures.doubleBitAxe
}

class Hatchet : Axe()
{
    override val name : String = "Hatchet"
    override var baseAPUseCost : Int = 2
    override var durability : Int = 16
    override var maxDurability : Int = 16
    override val durabilityUseCost : Int = 1
    override val damage : Int = 8
    
    override fun getTexture() : Texture = Textures.hatchet
}