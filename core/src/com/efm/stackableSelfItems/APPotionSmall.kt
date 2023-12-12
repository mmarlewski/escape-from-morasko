package com.efm.stackableSelfItems

import com.badlogic.gdx.graphics.Texture
import com.efm.Animating
import com.efm.Animation
import com.efm.assets.Textures
import com.efm.assets.Tiles
import com.efm.item.StackableSelfItem
import com.efm.level.World

class APPotionSmall(
        override var amount : Int = 1
                   ) : StackableSelfItem
{
    override val name : String = "Small Stamina Potion"
    override val maxAmount : Int = 16
    override val baseAPUseCost : Int = 0
    override val hpBoost : Int = 0
    override val apBoost : Int = 5
    
    override fun getTexture() : Texture
    {
        return Textures.aPPotionSmall
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
        Animating.executeAnimations(mutableListOf(
                Animation.ascendTile(Tiles.apPlus, World.hero.position, 0.5f, 0.25f)))
    }
}
