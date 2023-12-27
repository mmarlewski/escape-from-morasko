import com.efm.entities.StoneColumn
import com.efm.entities.enemies.*
import com.efm.entities.enemies.bat.EnemyBat
import com.efm.entities.enemies.mushroom.EnemyMushroom
import com.efm.entities.enemies.skeleton.EnemySkeleton
import com.efm.room.Base
import com.efm.room.Space
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `space`
{
    @Test fun `setEntity test`()
    {
        val space = Space(0, 0)
        val entity = StoneColumn()
        
        assertNull(space.getEntity())
        space.setEntity(entity)
        assertTrue(space.getEntity() == entity)
    }
    
    @Test fun `clearEntity test`()
    {
        val space = Space(0, 0)
        val entity = StoneColumn()
        
        assertNull(space.getEntity())
        space.setEntity(entity)
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
    
    @Test fun `isTraversableFor test`()
    {
        val space = Space()
        space.changeBase(Base.lava)
        
        assertFalse(space.isTraversableFor(EnemyMushroom()))
        assertTrue(space.isTraversableFor(EnemyBat()))
        
        space.setEntity(EnemySkeleton())
        
        assertFalse(space.isTraversableFor(EnemyMushroom()))
        assertFalse(space.isTraversableFor(EnemyBat()))
    }
    
    @Test fun `isTreadableFor test`()
    {
        val space = Space()
        space.changeBase(Base.lava)
        
        assertFalse(space.isTreadableFor(EnemyMushroom()))
        assertTrue(space.isTreadableFor(EnemyBat()))
        
        space.setEntity(EnemySkeleton())
        
        assertFalse(space.isTreadableFor(EnemyMushroom()))
        assertTrue(space.isTreadableFor(EnemyBat()))
    }
}
