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
                "AP cost: " + baseAPUseCost + "\n" +
                "Uses left: " + durability
    
    override fun getTexture() : Texture
    {
        return Textures.woodenSword
    }
}

class IronSword : Sword()
{
    override val name : String = "Iron Sword"
    override var baseAPUseCost : Int = 1
    override var durability : Int = 20
    override var maxDurability : Int = 20
    override val durabilityUseCost : Int = 1
    override val damage : Int = 4
    override val statsDescription : String
        get() = "Damage: " + damage + "\n" +
                "AP cost: " + baseAPUseCost + "\n" +
                "Uses left: " + durability
    
    override fun getTexture() : Texture
    {
        return Textures.ironSword
    }
}

class TurquoiseSword : Sword()
{
    override val name : String = "Turquoise Sword"
    override var baseAPUseCost : Int = 2
    override var durability : Int = 10
    override var maxDurability : Int = 10
    override val durabilityUseCost : Int = 1
    override val damage : Int = 8
    override val statsDescription : String
        get() = "Damage: " + damage + "\n" +
                "AP cost: " + baseAPUseCost + "\n" +
                "Uses left: " + durability
    
    override fun getTexture() : Texture
    {
        return Textures.turquoiseSword
    }
}

class AmberSword : Sword()
{
    override val name : String = "Amber Sword"
    override var baseAPUseCost : Int = 2
    override var durability : Int = 15
    override var maxDurability : Int = 15
    override val durabilityUseCost : Int = 1
    override val damage : Int = 6
    override val statsDescription : String
        get() = "Damage: " + damage + "\n" +
                "AP cost: " + baseAPUseCost + "\n" +
                "Uses left: " + durability
    
    override fun getTexture() : Texture
    {
        return Textures.amberSword
    }
}