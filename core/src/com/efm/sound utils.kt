package com.efm

import com.badlogic.gdx.audio.Sound

private var soundVolume = 1f

/**
 * newVolume in range 0f .. 1f
 */
fun setSoundVolume(newVolume : Float)
{
    if (newVolume in 0f..1f)
    {
        soundVolume = newVolume
    }
}

fun getSoundVolume() : Float
{
    return soundVolume
}

fun playSoundOnce(sound : Sound)
{
    sound.play(soundVolume)
}

fun Sound.playOnce()
{
    playSoundOnce(this)
}
