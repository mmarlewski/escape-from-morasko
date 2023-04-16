import com.efm.entities.StoneColumn
import com.efm.room.Base
import com.efm.room.Room
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `room`
{
    @Test fun `isPositionWithinBounds test within bounds`()
    {
        val height = 5
        val width = 5
        
        val room = Room("room", height, width)
        assertTrue(room.isPositionWithinBounds(height - 1, width - 1))
    }
    
    @Test fun `isPositionWithinBounds test outside bounds`()
    {
        val height = 5
        val width = 5
        
        val room = Room("room", height, width)
        assertFalse(room.isPositionWithinBounds(height, width))
    }
    
    @Test fun `deleteSpaceAt test`()
    {
        val height = 5
        val width = 5
        
        val x = 2
        val y = 2
        
        val room = Room("room", height, width)
        assertNotNull(room.getSpace(x, y))
        room.deleteSpaceAt(x, y)
        assertNull(room.getSpace(x, y))
    }
    
    @Test fun `addEntity test`()
    {
        val height = 5
        val width = 5
        
        val entity = StoneColumn()
        val room = Room("room", height, width)
        
        assertTrue(room.getEntities().isEmpty())
        room.addEntity(entity)
        assertTrue(room.getEntities().isNotEmpty())
    }
    
    @Test fun `changeBaseAt test`()
    {
        val height = 5
        val width = 5
        
        val x = 2
        val y = 2
        
        val base = Base.stone
        val room = Room("room", height, width)
        val space = room.getSpace(x, y)
        
        assertNull(space?.getBase())
        room.changeBaseAt(base, x, y)
        assertNotNull(space?.getBase())
    }
    
    @Test fun `addEntityAt, updateSpaceList and updateSpacesEntities test`()
    {
        val height = 2
        val width = 2
        
        val room = Room("room", height, width)
        
        val space00 = room.getSpace(0, 0)
        val space01 = room.getSpace(0, 1)
        val space10 = room.getSpace(1, 0)
        val space11 = room.getSpace(1, 1)
        
        val entity00 = StoneColumn()
        val entity01 = StoneColumn()
        val entity10 = StoneColumn()
        val entity11 = StoneColumn()
        
        room.addEntityAt(entity00, 0, 0)
        room.addEntityAt(entity01, 0, 1)
        room.addEntityAt(entity10, 1, 0)
        room.addEntityAt(entity11, 1, 1)
        
        assertNull(space00?.getEntity())
        assertNull(space01?.getEntity())
        assertNull(space10?.getEntity())
        assertNull(space11?.getEntity())
        
        room.updateSpaceList()
        room.updateSpacesEntities()
        
        assertNotNull(space00?.getEntity())
        assertNotNull(space01?.getEntity())
        assertNotNull(space10?.getEntity())
        assertNotNull(space11?.getEntity())
    }
}
