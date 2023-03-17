package com.efm

import com.badlogic.gdx.audio.Sound

fun playSoundOnce(sound : Sound)
{
    sound.play()
}

fun Sound.playOnce()
{
    playSoundOnce(this)
}
