package com.efm.stackableSelfItems

import com.badlogic.gdx.graphics.Texture
import com.efm.assets.Textures
import com.efm.item.StackableSelfItem
import com.efm.level.World

class Mushroom(
        override var amount : Int = 1
              ) : StackableSelfItem
{
    override val name : String = "Mushroom"
    override val maxAmount : Int = 16
    override val baseAPUseCost : Int = 0
    override val hpBoost : Int = 5
    override val apBoost : Int = 5
    
    override fun getTexture() : Texture
    {
        return Textures.mushroom
    }
    
    override fun selected()
    {
    }
    
    override fun confirmed()
    {
    }
    
    override fun use()
    {
        World.hero.healCharacter(hpBoost)
        World.hero.gainAP(apBoost)
    }
}