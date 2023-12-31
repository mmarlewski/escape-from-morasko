package com.efm.stackableSelfItems

import com.badlogic.gdx.graphics.Texture
import com.efm.Animating
import com.efm.Animation
import com.efm.assets.Textures
import com.efm.assets.Tiles
import com.efm.item.StackableSelfItem
import com.efm.level.World

class Apple(
        override var amount : Int = 1
           ) : StackableSelfItem
{
    override val name : String = "Apple"
    override val maxAmount : Int = 16
    override val baseAPUseCost : Int = 0
    override val hpBoost : Int = 5
    override val apBoost : Int = 0
    override val statsDescription : String
        get() = "HP gain: " + hpBoost + "\n" +
                "AP gain: " + apBoost + "\n" +
                "AP cost: " + baseAPUseCost
    
    override fun getTexture() : Texture
    {
        return Textures.apple
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
        Animating.executeAnimations(
                mutableListOf(
                        Animation.ascendTile(Tiles.hpPlus, World.hero.position, 0.5f, 0.25f)
                             )
                                   )
    }
}
