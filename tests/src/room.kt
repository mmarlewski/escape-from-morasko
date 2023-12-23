import com.efm.entities.StoneColumn
import com.efm.room.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `room`
{
    val height = 4
    val width = 4
    var room : Room = Room("room", height, width)
    
    @Before fun `set up`()
    {
        room = Room("room", height, width)
    }
    
    @Test fun `isPositionWithinBounds test within bounds`()
    {
        assertTrue(room.isPositionWithinBounds(height - 1, width - 1))
    }
    
    @Test fun `isPositionWithinBounds test outside bounds`()
    {
        assertFalse(room.isPositionWithinBounds(height, width))
    }
    
    @Test fun `deleteSpaceAt test`()
    {
        val x = 2
        val y = 2
        
        assertNotNull(room.getSpace(x, y))
        room.deleteSpaceAt(x, y)
        assertNull(room.getSpace(x, y))
    }
    
    @Test fun `addSpaceAt test`()
    {
        val x = 2
        val y = 2
        
        room.deleteSpaceAt(x, y)
        assertNull(room.getSpace(x, y))
        room.addSpaceAt(x, y)
        assertNotNull(room.getSpace(x, y))
    }
    
    @Test fun `addEntity adds Entity to list in Room and to Space in Room when Space exists in position of Entity`()
    {
        val entity = StoneColumn()
        entity.setPosition(1, 1)
        
        room.addEntity(entity)
        assertTrue(room.getEntities().contains(entity))
        assertTrue(room.getSpace(1, 1)?.getEntity() == entity)
    }
    
    @Test fun `addEntity adds Entity to list in Room and logs warning when no Space exists in position of Entity`()
    {
        val entity = StoneColumn()
        entity.setPosition(1, 1)
        room.deleteSpaceAt(1, 1)
        
        room.addEntity(entity)
        assertTrue(room.getEntities().contains(entity))
        assertNull(room.getSpace(1, 1)?.getEntity())
    }
    
    @Test fun `changeBaseAt test`()
    {
        val x = 2
        val y = 2
        val base = Base.stone
        val space = room.getSpace(x, y)
        
        assertNull(space?.getBase())
        room.changeBaseAt(base, x, y)
        assertNotNull(space?.getBase())
    }
    
    @Test fun `addEntityAt adds Entity and sets Entity's position when Space exists in position of Entity`()
    {
        val entity = StoneColumn()
        
        // sets position
        assertTrue(entity.position == RoomPosition(0, 0))
        room.addEntityAt(entity, 1, 1)
        assertTrue(entity.position == RoomPosition(1, 1))
        // adds Entity
        assertTrue(room.getEntities().contains(entity))
        assertTrue(room.getSpace(1, 1)?.getEntity() == entity)
    }
    
    @Test fun `addEntityAt adds Entity and sets Entity's position when no Space exists in position of Entity`()
    {
        val entity = StoneColumn()
        room.deleteSpaceAt(1, 1)
        
        // sets position
        assertTrue(entity.position == RoomPosition(0, 0))
        room.addEntityAt(entity, 1, 1)
        assertTrue(entity.position == RoomPosition(1, 1))
        // adds Entity
        assertTrue(room.getEntities().contains(entity))
        assertNull(room.getSpace(1, 1)?.getEntity())
    }
    
    @Test fun `updateSpaceList test`()
    {
        val x = 2
        val y = 2
        
        room.deleteSpaceAt(x, y)
        var containsSpace = false
        for (space in room.getSpaces())
        {
            if (space.position.x == x && space.position.y == y)
            {
                containsSpace = true
                break
            }
        }
        assertTrue(containsSpace)
        room.updateSpaceList()
        containsSpace = false
        for (space in room.getSpaces())
        {
            if (space.position.x == x && space.position.y == y)
            {
                containsSpace = true
                break
            }
        }
        assertFalse(containsSpace)
    }
}
