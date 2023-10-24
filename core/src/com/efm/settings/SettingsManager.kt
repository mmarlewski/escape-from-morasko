package com.efm.settings

class SettingsManager private constructor()
{
    private var musicVolume : Float = 1.0f // Default to maximum volume
    private var soundVolume : Float = 1.0f
    
    companion object
    {
        private val instance = SettingsManager()
        
        fun getInstance() : SettingsManager
        {
            return instance
        }
    }
    
    fun getMusicVolume() : Float
    {
        return musicVolume
    }
    
    fun setMusicVolume(volume : Float)
    {
        musicVolume = volume
    }
    
    fun getSoundVolume() : Float
    {
        return soundVolume
    }
    
    fun setSoundVolume(volume : Float)
    {
        soundVolume = volume
    }
}