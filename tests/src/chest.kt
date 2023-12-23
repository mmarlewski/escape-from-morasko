import com.efm.entities.Chest
import com.efm.level.World
import com.efm.stackableMapItems.Bomb
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class chest
{
    val hero = World.hero
    val chest = Chest()
    
    @Before fun `set up`()
    {
        hero.inventory.items.clear()
    }
    
    @Test fun `addItem adds items to chest`()
    {
        val item = Bomb(2)
        chest.addItem(item)
        
        val bombsInEq = chest.findAllStacks(item) as List<Bomb>
        assertTrue(bombsInEq.size == 1)
        assertTrue(bombsInEq[0].amount == 2)
    }
    
    @Test fun `takeItemFromChest adds items to stack if there is one in hero equipment`()
    {
        hero.inventory.addItem(Bomb(2))
    
        val bombExistingNowhere = Bomb(2)
        // addItem adds a copy
        chest.addItem(bombExistingNowhere)
    
        val bombInChest = chest.findAllStacks(Bomb()).first()
        chest.takeItemFromChest(bombInChest)
        assertTrue(chest.findAllStacks(Bomb()).isEmpty())
    
        val bombsInEq = hero.inventory.findAllStacks(Bomb()) as List<Bomb>
        assertTrue(bombsInEq.size == 1)
        assertTrue(bombsInEq.first().amount == 4)
    }
    
    @Test fun `takeItem removes items from chest after adding them to hero equipment`()
    {
        hero.inventory.addItem(Bomb(2))
    
        val bombExistingNowhere = Bomb(2)
        chest.addItem(bombExistingNowhere)
    
        val bombInChest = chest.findAllStacks(Bomb()).first()
        chest.takeItemFromChest(bombInChest)
    
        assertNull(chest.items.find { it is Bomb })
        assertTrue(chest.findAllStacks(Bomb()).isEmpty())
    }
}
