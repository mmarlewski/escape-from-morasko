package com.efm

enum class Direction
{
    up, right, down, left;
    
    fun opposite() : Direction
    {
        return when (this)
        {
            up    -> down
            right -> left
            down  -> up
            left  -> right
        }
    }
}