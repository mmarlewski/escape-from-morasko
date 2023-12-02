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
                    
                    Tiles.stoneExitUpClosed,
                    Tiles.stoneExitRightClosed,
                    Tiles.stoneExitDownClosed,
                    Tiles.stoneExitLeftClosed,
                    
                    Tiles.stoneExitLevelUpClosed,
                    Tiles.stoneExitLevelRightClosed,
                    Tiles.stoneExitLevelDownClosed,
                    Tiles.stoneExitLevelLeftClosed
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
                    
                    Tiles.metalExitUpClosed,
                    Tiles.metalExitRightClosed,
                    Tiles.metalExitDownClosed,
                    Tiles.metalExitLeftClosed,
                    
                    Tiles.metalExitLevelUpClosed,
                    Tiles.metalExitLevelRightClosed,
                    Tiles.metalExitLevelDownClosed,
                    Tiles.metalExitLevelLeftClosed
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
                    
                    Tiles.rockExitUpClosed,
                    Tiles.rockExitRightClosed,
                    Tiles.rockExitDownClosed,
                    Tiles.rockExitLeftClosed,
                    
                    Tiles.rockExitLevelUpClosed,
                    Tiles.rockExitLevelRightClosed,
                    Tiles.rockExitLevelDownClosed,
                    Tiles.rockExitLevelLeftClosed
                     )
        );
    
    companion object
    {
        fun getOrdinal(exitStyle : ExitStyle) : Int = exitStyle.ordinal
        fun getWallStyle(exitStyleNumber : Int) = values()[exitStyleNumber]
    }
}
