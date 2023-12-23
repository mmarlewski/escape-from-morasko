import com.efm.*
import com.efm.entities.StoneColumn
import com.efm.entities.enemies.EnemySkeleton
import com.efm.room.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `save utils`
{
    @Test fun `save test`()
    {
        deleteSave()
        
        assertFalse(saveExists())
        
        saveGame()
        
        assertTrue(saveExists())
        
        loadGame()
        
        assertTrue(saveExists())
        
        deleteSave()
        
        assertFalse(saveExists())
    }
}
