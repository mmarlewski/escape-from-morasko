import com.efm.entities.StoneColumn
import com.efm.room.Base
import com.efm.room.Space
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `space`
{
    @Test fun `changeEntity test`()
    {
        val space = Space(0, 0)
        val entity = StoneColumn()
        
        assertNull(space.getEntity())
        space.changeEntity(entity)
        assertTrue(space.getEntity() == entity)
    }
    
    @Test fun `clearEntity test`()
    {
        val space = Space(0, 0)
        val entity = StoneColumn()
        
        assertNull(space.getEntity())
        space.changeEntity(entity)
        assertTrue(space.getEntity() == entity)
        space.clearEntity()
        assertNull(space.getEntity())
    }
    
    @Test fun `changeBase test`()
    {
        val space = Space(0, 0)
        val base = Base.stone
        
        assertNull(space.getBase())
        space.changeBase(base)
        assertTrue(space.getBase() == base)
    }
}
