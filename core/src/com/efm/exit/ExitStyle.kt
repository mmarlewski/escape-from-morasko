package com.efm.exit

import com.efm.assets.Tiles

enum class ExitStyle(internal val tiles : ExitTiles)
{
    stone(
            ExitTiles(
                    Tiles.stoneExitUp,
                    Tiles.stoneExitRight,
                    Tiles.stoneExitDown,
                    Tiles.stoneExitLeft,
        
                    Tiles.stoneExitLevelUp,
                    Tiles.stoneExitLevelRight,
                    Tiles.stoneExitLevelDown,
                    Tiles.stoneExitLevelLeft,
        
                    Tiles.stoneExitBossUp,
                    Tiles.stoneExitBossRight,
                    Tiles.stoneExitBossDown,
                    Tiles.stoneExitBossLeft,
        
                    Tiles.stoneExitUpClosed,
                    Tiles.stoneExitRightClosed,
                    Tiles.stoneExitDownClosed,
                    Tiles.stoneExitLeftClosed,
        
                    Tiles.stoneExitLevelUpClosed,
                    Tiles.stoneExitLevelRightClosed,
                    Tiles.stoneExitLevelDownClosed,
                    Tiles.stoneExitLevelLeftClosed,
        
                    Tiles.stoneExitBossUpClosed,
                    Tiles.stoneExitBossRightClosed,
                    Tiles.stoneExitBossDownClosed,
                    Tiles.stoneExitBossLeftClosed
                     )
         ),
    metal(
            ExitTiles(
                    Tiles.metalExitUp,
                    Tiles.metalExitRight,
                    Tiles.metalExitDown,
                    Tiles.metalExitLeft,
        
                    Tiles.metalExitLevelUp,
                    Tiles.metalExitLevelRight,
                    Tiles.metalExitLevelDown,
                    Tiles.metalExitLevelLeft,
        
                    null,
                    null,
                    null,
                    null,
        
                    Tiles.metalExitUpClosed,
                    Tiles.metalExitRightClosed,
                    Tiles.metalExitDownClosed,
                    Tiles.metalExitLeftClosed,
        
                    Tiles.metalExitLevelUpClosed,
                    Tiles.metalExitLevelRightClosed,
                    Tiles.metalExitLevelDownClosed,
                    Tiles.metalExitLevelLeftClosed,
        
                    null,
                    null,
                    null,
                    null
                     )
         ),
    rock(
            ExitTiles(
                    Tiles.rockExitUp,
                    Tiles.rockExitRight,
                    Tiles.rockExitDown,
                    Tiles.rockExitLeft,
        
                    Tiles.rockExitLevelUp,
                    Tiles.rockExitLevelRight,
                    Tiles.rockExitLevelDown,
                    Tiles.rockExitLevelLeft,
        
                    null,
                    null,
                    null,
                    null,
        
                    Tiles.rockExitUpClosed,
                    Tiles.rockExitRightClosed,
                    Tiles.rockExitDownClosed,
                    Tiles.rockExitLeftClosed,
        
                    Tiles.rockExitLevelUpClosed,
                    Tiles.rockExitLevelRightClosed,
                    Tiles.rockExitLevelDownClosed,
                    Tiles.rockExitLevelLeftClosed,
        
                    null,
                    null,
                    null,
                    null
                     )
        );
    
    companion object
    {
        fun getOrdinal(exitStyle : ExitStyle) : Int = exitStyle.ordinal
        fun getWallStyle(exitStyleNumber : Int) = values()[exitStyleNumber]
    }
}
