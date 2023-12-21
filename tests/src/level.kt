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
    
    @Test fun `changeStartingPosition test`()
    {
        val level = Level("level")
        val position1 = RoomPosition(1, 1)
        val position2 = RoomPosition(2, 2)
        
        level.startingPosition.set(position1)
        assertTrue(level.startingPosition == position1)
        level.startingPosition.set(position2)
        assertTrue(level.startingPosition == position2)
    }
}
