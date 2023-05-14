package com.efm

import com.badlogic.gdx.Gdx

object Animating
{
    private var deltaTime = 0f
    private var animations = mutableListOf<Animation>()
    private var animationsIterator = animations.iterator()
    private var currAnimation : Animation = Animation.wait(0f)
    private var nextAnimation : Animation? = Animation.wait(0f)
    
    fun getDeltaTime() : Float
    {
        return deltaTime
    }
    
    fun getCurrentAnimation() : Animation
    {
        return currAnimation
    }
    
    fun isAnimating() : Boolean
    {
        return (currAnimation !is Animation.none)
    }
    
    fun executeAnimations(animationList : MutableList<Animation>)
    {
        animations = animationList
        animationsIterator = animations.iterator()
        nextAnimation = if (animationsIterator.hasNext()) animationsIterator.next() else null
    }
    
    fun update()
    {
        deltaTime += Gdx.graphics.deltaTime
        currAnimation.update()
        
        if (currAnimation.isFinished())
        {
            currAnimation = if (nextAnimation != null) nextAnimation as Animation else Animation.none
            
            deltaTime = 0f
            currAnimation.start()
            
            nextAnimation = if (animationsIterator.hasNext()) animationsIterator.next() else null
        }
    }
}
