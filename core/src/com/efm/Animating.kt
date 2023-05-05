package com.efm

import com.badlogic.gdx.Gdx

object Animating
{
    var isAnimating = false
    var animations = mutableListOf<Animation>()
    var curranimation : Animation = wait(0f)
    var nextAnimation : Animation? = wait(0f)
    var isWaiting = false
    var deltaTime = 0f
    var waitTimeInSeconds = 0f
    
    fun executeAnimations(animationList : MutableList<Animation>)
    {
        isAnimating = true
        animations = animationList
        nextAnimation = animations.firstOrNull()
    }
    
    fun updateAnimations()
    {
        if (isAnimating)
        {
            if (isWaiting)
            {
                deltaTime += Gdx.graphics.deltaTime
                if (deltaTime > (curranimation as wait).seconds)
                {
                    isWaiting = false
                }
            }
            else
            {
                if (nextAnimation != null)
                {
                    curranimation = nextAnimation as Animation
                }
                else
                {
                    isAnimating = false
                }
                
                curranimation.execute()
                
                val nextAnimationIndex = animations.indexOf(curranimation) + 1
                nextAnimation = animations.getOrNull(nextAnimationIndex)
            }
        }
    }
}
