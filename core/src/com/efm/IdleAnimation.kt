package com.efm

object IdleAnimation
{
    const val numberOfIdleAnimations = 4
    const val numberOfMoveAnimations = 4
    const val durationOfIdleAnimationInSeconds = 0.5f
    
    var idleAnimationCount = 1
    var idleAnimationTime = 0.0f
    var idleAnimationChange = false
    
    fun update(dt : Float)
    {
        idleAnimationChange = false
        
        idleAnimationTime += dt
        if (idleAnimationTime > durationOfIdleAnimationInSeconds)
        {
            idleAnimationCount++
            idleAnimationTime = 0.0f
            idleAnimationChange = true
            if (idleAnimationCount > numberOfIdleAnimations)
            {
                idleAnimationCount = 1
            }
        }
    }
}
