package com.efm.room

import com.badlogic.gdx.graphics.Texture

/**
 * Fragment of a Room's area.
 * @param exists True if the area is accessible for interactive objects.
 * @param ground Surface texture of the area.
 */
class Space(val exists : Boolean, val ground : Texture)
