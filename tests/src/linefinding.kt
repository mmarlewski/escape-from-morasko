import com.efm.LineFinding
import com.efm.PathFinding
import com.efm.entities.StoneColumn
import com.efm.entities.enemies.EnemySkeleton
import com.efm.room.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `linefinding`
{
    @Test fun `findLine with start outside Room returns null`()
    {
        val room = Room("room", 5, 5)
        val start = RoomPosition(6, 6)
        val end = RoomPosition(1, 1)
        
        val line = LineFinding.findLineWithGivenRoom(start, end, room)
        
        assertNull(line)
    }
    
    @Test fun `findLine with end outside Room returns null`()
    {
        val room = Room("room", 5, 5)
        val start = RoomPosition(1, 1)
        val end = RoomPosition(6, 6)
        
        val line = LineFinding.findLineWithGivenRoom(start, end, room)
        
        assertNull(line)
    }
    
    @Test fun `findLine with end in part of Room sealed by Entities returns null`()
    {
        val room = Room("room", 5, 5)
        for (i in 0 until 5)
        {
            for (j in 0 until 5)
            {
                room.changeBaseAt(Base.stone, i, j)
            }
        }
        room.addEntityAt(StoneColumn(), 2, 2)
        room.addEntityAt(StoneColumn(), 3, 2)
        room.addEntityAt(StoneColumn(), 4, 2)
        room.addEntityAt(StoneColumn(), 2, 3)
        room.addEntityAt(StoneColumn(), 2, 4)
        room.updateSpacesEntities()
        
        val start = RoomPosition(1, 1)
        val end = RoomPosition(3, 3)
        
        val line = LineFinding.findLineWithGivenRoom(start, end, room)
        
        assertNull(line)
    }
}
