package com.efm.multiUseMapItems

import com.badlogic.gdx.graphics.Texture
import com.efm.assets.Textures

class SmallAxe : Axe()
{
    override val name : String = "Small Axe"
    override var baseAPUseCost : Int = 1
    override var durability : Int = 8
    override var maxDurability : Int = 8
    override val durabilityUseCost : Int = 1
    override val damage : Int = 3
    
    override fun getTexture() : Texture = Textures.smallAxe
}

class DoubleBitAxe : Axe()
{
    override val name : String = "Double-bit Axe"
    override var baseAPUseCost : Int = 4
    override var durability : Int = 8
    override var maxDurability : Int = 8
    override val durabilityUseCost : Int = 1
    override val damage : Int = 8
    
    override fun getTexture() : Texture = Textures.doubleBitAxe
}

class Hatchet : Axe()
{
    override val name : String = "Hatchet"
    override var baseAPUseCost : Int = 2
    override var durability : Int = 10
    override var maxDurability : Int = 10
    override val durabilityUseCost : Int = 1
    override val damage : Int = 5
    
    override fun getTexture() : Texture = Textures.hatchet
}