package com.efm

import com.badlogic.gdx.audio.Music

private var currentMusic : Music? = null

fun playMusic(music : Music)
{
    currentMusic?.stop()
    currentMusic = music
    currentMusic?.play()
    currentMusic?.isLooping = true
}
