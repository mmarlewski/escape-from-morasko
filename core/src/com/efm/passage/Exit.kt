package com.efm.entities.exits

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.*
import com.efm.*
import com.efm.assets.*
import com.efm.entity.Interactive
import com.efm.level.World
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.screens.GameScreen

/**
 * Interactive object found in a Room used to leave it. Created based on a Passage.
 */
interface Exit : Interactive
{
    override val position : RoomPosition
    var direction : Direction4
    var style : ExitStyle
    
    override fun getOutlineYellowTile(n : Int) : TiledMapTile? = null
    
    override fun write(json : Json?)
    {
        super.write(json)
        if (json != null)
        {
            json.writeValue("direction", this.direction)
            json.writeValue("style", this.style.name)
        }
    }
    
    override fun read(json : Json?, jsonData : JsonValue?)
    {
        super.read(json, jsonData)
        if (json != null)
        {
            val jsonDirection = json.readValue("direction", Direction4::class.java, jsonData)
            if (jsonDirection != null) this.direction = jsonDirection
            val jsonStyle = json.readValue("style", String::class.java, jsonData)
            if (jsonStyle != null) this.style = ExitStyle.valueOf(jsonStyle)
        }
    }
}

open class RoomExit(
        override val position : RoomPosition,
        override var direction : Direction4,
        var endRoomName : String,
        var endPosition : RoomPosition,
        override var style : ExitStyle
                   ) : Exit
{
    override fun getOutlineTealTile() : TiledMapTile? = when (direction)
    {
        Direction4.up    -> style.tiles.exitUpOutlineTeal
        Direction4.right -> style.tiles.exitRightOutlineTeal
        Direction4.down  -> style.tiles.exitDownOutlineTeal
        Direction4.left  -> style.tiles.exitLeftOutlineTeal
    }
    
    override fun getTile() : TiledMapTile? = when (direction)
    {
        Direction4.up    -> style.tiles.exitUp
        Direction4.right -> style.tiles.exitRight
        Direction4.down  -> style.tiles.exitDown
        Direction4.left  -> style.tiles.exitLeft
    }
    
    override fun interact()
    {
        travelBetweenRooms()
        Gdx.app.log("Exit", "adjusting camera")
        adjustCameraAfterMoving()
        adjustMapLayersAfterMoving()
    }
    
    fun travelBetweenRooms()
    {
        val newRoom : Room? = World.currentLevel.rooms.find { it.name == endRoomName }
        val newPosition : RoomPosition = endPosition
        
        println("endRoomName : $endRoomName")
        println("newRoom : $newRoom")
        println("World.currentRoom.name : ${World.currentRoom.name}")
        
        if (newRoom == null) throw Exception("Cannot find the Room that the Exit leads to.")
        
        World.currentRoom.removeEntity(World.hero)
        World.changeCurrentRoom(newRoom)
        World.currentRoom.addEntityAt(World.hero, newPosition)
    }
    
}

open class LevelExit(
        override val position : RoomPosition,
        override var direction : Direction4,
        var endLevelName : String,
        override var style : ExitStyle
                    ) : Exit
{
    override fun getOutlineTealTile() : TiledMapTile? = when (direction)
    {
        Direction4.up    -> style.tiles.exitLevelUpOutlineTeal
        Direction4.right -> style.tiles.exitLevelRightOutlineTeal
        Direction4.down  -> style.tiles.exitLevelDownOutlineTeal
        Direction4.left  -> style.tiles.exitLevelLeftOutlineTeal
    }
    
    override fun getTile() : TiledMapTile? = when (direction)
    {
        Direction4.up    -> style.tiles.exitLevelUp
        Direction4.right -> style.tiles.exitLevelRight
        Direction4.down  -> style.tiles.exitLevelDown
        Direction4.left  -> style.tiles.exitLevelLeft
    }
    
    override fun interact()
    {
        showNextLevelPopup()
        Gdx.app.log("Exit", "adjusting camera")
        adjustCameraAfterMoving()
        adjustMapLayersAfterMoving()
    }
    
    fun showNextLevelPopup()
    {
        val popup = nextLevelPopup()
        popup.isVisible = true
        val nextLevelPopupWindow = columnOf(rowOf(popup)).align(Align.center)
        nextLevelPopupWindow.setFillParent(true)
        GameScreen.stage.addActor(nextLevelPopupWindow)
    }
    
    fun nextLevelPopup() : Window
    {
        val nextLevelPopUp = windowAreaOf(
                "You are about to leave this level\n\nAre you sure?",
                Fonts.pixeloid20,
                Colors.black,
                Textures.pauseBackgroundNinePatch
                                         ) { travelBetweenLevels() }
        nextLevelPopUp.isVisible = false
        return nextLevelPopUp
    }
    
    fun travelBetweenLevels()
    {
        val newLevel = World.getLevels().find { it.name == endLevelName }
        val newPosition = newLevel?.getStartingPosition()
        val newRoom = newLevel?.getStartingRoom()
        Gdx.app.log("Exit", "travelling...")
        World.currentRoom.removeEntity(World.hero)
        if (newLevel != null) World.changeCurrentLevel(newLevel)
        if (newRoom != null) World.changeCurrentRoom(newRoom)
        if (newPosition != null) World.currentRoom.addEntityAt(World.hero, newPosition)
        Gdx.app.log("Exit", "travelled")
        Gdx.app.log("Exit", "adjusting camera again")
        adjustCameraAfterMoving()
        adjustMapLayersAfterMoving()
    }
}

internal class ExitTiles(
        val exitUp : TiledMapTile?,
        val exitRight : TiledMapTile?,
        val exitDown : TiledMapTile?,
        val exitLeft : TiledMapTile?,
        val exitLevelUp : TiledMapTile?,
        val exitLevelRight : TiledMapTile?,
        val exitLevelDown : TiledMapTile?,
        val exitLevelLeft : TiledMapTile?,
        val exitUpOutlineTeal : TiledMapTile?,
        val exitRightOutlineTeal : TiledMapTile?,
        val exitDownOutlineTeal : TiledMapTile?,
        val exitLeftOutlineTeal : TiledMapTile?,
        val exitLevelUpOutlineTeal : TiledMapTile?,
        val exitLevelRightOutlineTeal : TiledMapTile?,
        val exitLevelDownOutlineTeal : TiledMapTile?,
        val exitLevelLeftOutlineTeal : TiledMapTile?
                        )

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

class RoomExitActiveWhenNoEnemiesAreInRoom(
        position : RoomPosition, direction : Direction4, endRoomName : String, endPosition : RoomPosition, style : ExitStyle
                                          ) : RoomExit(position, direction, endRoomName, endPosition, style)
{
    override fun interact()
    {
        if (!World.currentRoom.areEnemiesInRoom())
        {
            super.interact()
        }
    }
}

class LevelExitActiveWhenNoEnemiesAreInRoom(
        position : RoomPosition, direction : Direction4, endLevelName : String, style : ExitStyle
                                           ) : LevelExit(position, direction, endLevelName, style)
{
    override fun interact()
    {
        if (!World.currentRoom.areEnemiesInRoom())
        {
            super.interact()
        }
    }
}