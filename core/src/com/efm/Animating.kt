package com.efm

/**
 * Handles execution of Animations on game map
 */
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
    
    /**
     * starts execution of Animations on game map
     */
    fun executeAnimations(animationList : MutableList<Animation>)
    {
        animations = animationList
        animationsIterator = animations.iterator()
        nextAnimation = if (animationsIterator.hasNext()) animationsIterator.next() else null
    }
    
    /**
     * updates Animations according to delta time
     */
    fun update(dt : Float)
    {
        deltaTime += dt
        currAnimation.update()
        
        if (currAnimation.isFinished())
        {
            currAnimation = if (nextAnimation != null) nextAnimation as Animation else Animation.none
            
            currAnimation.start()
            
            nextAnimation = if (animationsIterator.hasNext()) animationsIterator.next() else null
        }
    }
}
