import com.efm.level.Level
import com.efm.level.World
import com.efm.room.Room
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `world`
{
    @Before fun clearLevels()
    {
        World.clearLevels()
    }
    
    @Test fun `addLevel test`()
    {
        val level = Level("level")
        
        assertTrue(World.levels.isEmpty())
        World.addLevel(level)
        assertTrue(World.levels.isNotEmpty())
    }
    
    @Test fun `changeCurrentLevel test with World's Level`()
    {
        val level1 = Level("level1")
        val level2 = Level("level2")
        World.addLevel(level1)
        World.addLevel(level2)
        
        World.changeCurrentLevel(level1)
        assertTrue(World.currentLevel === level1)
        World.changeCurrentLevel(level2)
        assertTrue(World.currentLevel === level2)
    }
    
    @Test fun `changeCurrentLevel test with outside Level`()
    {
        val level1 = Level("level1")
        val level2 = Level("level2")
        World.addLevel(level1)
        
        World.changeCurrentLevel(level1)
        assertTrue(World.currentLevel === level1)
        World.changeCurrentLevel(level2)
        assertTrue(World.currentLevel === level1)
    }
    
    @Test fun `changeCurrentRoom test with currentLevel's Room`()
    {
        val level = Level("level")
        World.addLevel(level)
        World.changeCurrentLevel(level)
        
        val room1 = Room("room1", 2, 2)
        val room2 = Room("room2", 2, 2)
        level.addRoom(room1)
        level.addRoom(room2)
        
        World.changeCurrentRoom(room1)
        assertTrue(World.currentRoom === room1)
        World.changeCurrentRoom(room2)
        assertTrue(World.currentRoom === room2)
    }
    
    @Test fun `changeCurrentRoom test with outside Room`()
    {
        val level = Level("level")
        World.addLevel(level)
        World.changeCurrentLevel(level)
    
        val room1 = Room("room1", 2, 2)
        val room2 = Room("room2", 2, 2)
        level.addRoom(room1)
    
        World.changeCurrentRoom(room1)
        assertTrue(World.currentRoom === room1)
        World.changeCurrentRoom(room2)
        assertFalse(World.currentRoom === room2)
    }
}
