package com.efm.stackableSelfItems

import com.badlogic.gdx.graphics.Texture
import com.efm.assets.Textures
import com.efm.item.StackableSelfItem
import com.efm.level.World

class PotionBig(
        override var amount : Int = 1
              ) : StackableSelfItem
{
    override val name : String = "Big Potion"
    override val maxAmount : Int = 16
    override val baseAPUseCost : Int = 0
    override val hpBoost : Int? = null
    override val apBoost : Int = 10
    
    override fun getTexture() : Texture
    {
        return Textures.potionBig
    }
    
    override fun selected()
    {
    }
    
    override fun confirmed()
    {
    }
    
    override fun use()
    {
        World.hero.gainAP(apBoost)
    }
}
