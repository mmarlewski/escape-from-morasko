package com.efm.entity

/**
 * Entity that the Hero can interact with.
 * Interactive Entity can perform an action after being clicked or after an item is used on it.
 */
interface Interactive : Entity
{
    fun interact()
}
