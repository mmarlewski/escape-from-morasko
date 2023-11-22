import com.efm.level.Level
import com.efm.room.Room
import com.efm.room.RoomPosition
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `level`
{
    @Test fun `addRoom test`()
    {
        val level = Level("level")
        val room = Room("room", 2, 2)
        
        assertTrue(level.rooms.isEmpty())
        level.addRoom(room)
        assertTrue(level.rooms.isNotEmpty())
    }
    
    @Test fun `changeStartingRoom test`()
    {
        val level = Level("level")
        val room1 = Room("room1", 2, 2)
        val room2 = Room("room2", 2, 2)
        
        level.changeStartingRoom(room1)
        assertTrue(level.getStartingRoom() === room1)
        level.changeStartingRoom(room2)
        assertTrue(level.getStartingRoom() === room2)
    }
    
    @Test fun `changeStartingPosition test`()
    {
        val level = Level("level")
        val position1 = RoomPosition(1, 1)
        val position2 = RoomPosition(2, 2)
        
        level.changeStartingPosition(position1)
        assertTrue(level.getStartingPosition() == position1)
        level.changeStartingPosition(position2)
        assertTrue(level.getStartingPosition() == position2)
    }
}
