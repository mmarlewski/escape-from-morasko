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
                    Tiles.stoneExitUpOutlineTeal,
                    Tiles.stoneExitRightOutlineTeal,
                    Tiles.stoneExitDownOutlineTeal,
                    Tiles.stoneExitLeftOutlineTeal,
                    Tiles.stoneExitLevelUpOutlineTeal,
                    Tiles.stoneExitLevelRightOutlineTeal,
                    Tiles.stoneExitLevelDownOutlineTeal,
                    Tiles.stoneExitLevelLeftOutlineTeal
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
                    Tiles.metalExitUpOutlineTeal,
                    Tiles.metalExitRightOutlineTeal,
                    Tiles.metalExitDownOutlineTeal,
                    Tiles.metalExitLeftOutlineTeal,
                    Tiles.metalExitLevelUpOutlineTeal,
                    Tiles.metalExitLevelRightOutlineTeal,
                    Tiles.metalExitLevelDownOutlineTeal,
                    Tiles.metalExitLevelLeftOutlineTeal
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
                    Tiles.rockExitUpOutlineTeal,
                    Tiles.rockExitRightOutlineTeal,
                    Tiles.rockExitDownOutlineTeal,
                    Tiles.rockExitLeftOutlineTeal,
                    Tiles.rockExitLevelUpOutlineTeal,
                    Tiles.rockExitLevelRightOutlineTeal,
                    Tiles.rockExitLevelDownOutlineTeal,
                    Tiles.rockExitLevelLeftOutlineTeal
                     )
        );
    
    companion object
    {
        fun getOrdinal(exitStyle : ExitStyle) : Int = exitStyle.ordinal
        fun getWallStyle(exitStyleNumber : Int) = values()[exitStyleNumber]
    }
}