package com.efm.stackableSelfItems

import com.badlogic.gdx.graphics.Texture
import com.efm.Animating
import com.efm.Animation
import com.efm.assets.Textures
import com.efm.assets.Tiles
import com.efm.item.StackableSelfItem
import com.efm.level.World

class HPPotionSmall(
        override var amount : Int = 1
                   ) : StackableSelfItem
{
    override val name : String = "Small Health Potion"
    override val maxAmount : Int = 16
    override val baseAPUseCost : Int = 0
    override val hpBoost : Int = 15
    override val apBoost : Int = 0
    
    override fun getTexture() : Texture
    {
        return Textures.hPPotionSmall
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
        Animating.executeAnimations(
                mutableListOf(
                        Animation.ascendTile(Tiles.apPlus, World.hero.position, 0.5f, 0.25f)
                             )
                                   )
    }
}