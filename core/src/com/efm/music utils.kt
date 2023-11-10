package com.efm

import com.badlogic.gdx.audio.Music

private var currentMusic : Music? = null
private var musicVolume = 0.0f

/**
 * newVolume in range 0f .. 1f
 */
fun setMusicVolume(newVolume : Float)
{
    if (newVolume in 0f..1f)
    {
        musicVolume = newVolume
        currentMusic?.volume = newVolume
    }
}

fun getMusicVolume() : Float
{
    return musicVolume
}

fun playMusicByStoppingAlreadyPlaying(music : Music)
{
    currentMusic?.stop()
    currentMusic = music
    currentMusic?.play()
    currentMusic?.volume = musicVolume
    currentMusic?.isLooping = true
}

fun playMusicIfNotAlreadyPlaying(music : Music)
{
    if (currentMusic != music)
    {
        currentMusic?.stop()
        currentMusic = music
        currentMusic?.play()
        currentMusic?.volume = musicVolume
        currentMusic?.isLooping = true
    }
}
