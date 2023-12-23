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
}
