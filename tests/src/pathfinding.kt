import com.efm.PathFinding
import com.efm.entities.StoneColumn
import com.efm.entities.enemies.EnemySkeleton
import com.efm.room.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `pathfinding`
{
    @Test fun `findPath with start outside Room returns null`()
    {
        val room = Room("room", 5, 5)
        val start = RoomPosition(6, 6)
        val end = RoomPosition(1, 1)
        
        val path = PathFinding.findPathInRoomForEntity(start, end, room, EnemySkeleton())
        
        assertNull(path)
    }
    
    @Test fun `findPath with end outside Room returns null`()
    {
        val room = Room("room", 5, 5)
        val start = RoomPosition(1, 1)
        val end = RoomPosition(6, 6)
        
        val path = PathFinding.findPathInRoomForEntity(start, end, room, EnemySkeleton())
        
        assertNull(path)
    }
    
    @Test fun `findPath with non-traversable start returns path`()
    {
        val room = Room("room", 5, 5)
        for (i in 0 until 5)
        {
            for (j in 0 until 5)
            {
                room.changeBaseAt(Base.stone, i, j)
            }
        }
        room.addEntityAt(StoneColumn(), 1, 1)
        
        val start = RoomPosition(1, 1)
        val end = RoomPosition(3, 3)
        
        val path = PathFinding.findPathInRoomForEntity(start, end, room, EnemySkeleton())
        
        assertNotNull(path)
    }
    
    @Test fun `findPath with non-traversable end returns path`()
    {
        val room = Room("room", 5, 5)
        for (i in 0 until 5)
        {
            for (j in 0 until 5)
            {
                room.changeBaseAt(Base.stone, i, j)
            }
        }
        room.addEntityAt(StoneColumn(), 3, 3)
        
        val start = RoomPosition(1, 1)
        val end = RoomPosition(3, 3)
        
        val path = PathFinding.findPathInRoomForEntity(start, end, room, EnemySkeleton())
        
        assertNotNull(path)
    }
    
    @Test fun `findPath with end in part of Room sealed by Lava returns null`()
    {
        val room = Room("room", 5, 5)
        for (i in 0 until 5)
        {
            for (j in 0 until 5)
            {
                room.changeBaseAt(Base.stone, i, j)
            }
        }
        room.changeBaseAt(Base.lava, 2, 2)
        room.changeBaseAt(Base.lava, 3, 2)
        room.changeBaseAt(Base.lava, 4, 2)
        room.changeBaseAt(Base.lava, 2, 3)
        room.changeBaseAt(Base.lava, 2, 4)
        
        val start = RoomPosition(1, 1)
        val end = RoomPosition(3, 3)
        
        val path = PathFinding.findPathInRoomForEntity(start, end, room, EnemySkeleton())
        
        assertNull(path)
    }
    
    @Test fun `findPath with end in part of Room almost sealed by Lava returns path`()
    {
        val room = Room("room", 5, 5)
        for (i in 0 until 5)
        {
            for (j in 0 until 5)
            {
                room.changeBaseAt(Base.stone, i, j)
            }
        }
        room.changeBaseAt(Base.lava, 2, 2)
        room.changeBaseAt(Base.lava, 3, 2)
        room.changeBaseAt(Base.lava, 4, 2)
        room.changeBaseAt(Base.lava, 2, 3)
        
        val start = RoomPosition(1, 1)
        val end = RoomPosition(3, 3)
        
        val path = PathFinding.findPathInRoomForEntity(start, end, room, EnemySkeleton())
        
        assertNotNull(path)
    }
    
    @Test fun `findPath with end in part of Room sealed by Entities returns null`()
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
        
        val path = PathFinding.findPathInRoomForEntity(start, end, room, EnemySkeleton())
        
        assertNull(path)
    }
    
    @Test fun `findPath with end in part of Room almost sealed by Entities returns path`()
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
        room.updateSpacesEntities()
        
        val start = RoomPosition(1, 1)
        val end = RoomPosition(3, 3)
        
        val path = PathFinding.findPathInRoomForEntity(start, end, room, EnemySkeleton())
        
        assertNotNull(path)
    }
}
