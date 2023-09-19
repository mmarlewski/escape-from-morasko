import com.efm.entities.Chest
import com.efm.item.StackableItem
import com.efm.level.World
import com.efm.stackableMapItems.Bomb
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class) class chest
{
    val hero = World.hero
    val chest = Chest()
    
    @Before fun `set up`()
    {
        hero.equipment.clear()
    }
    
    @Test fun `takeItemFromChest adds items to stack if there is one in hero equipment`()
    {
        hero.addItemToEquipment(Bomb(2))
        
        val item = Bomb(2)
        chest.items.add(item)
        
        chest.takeItemFromChest(item)
        
        val bombInEq : StackableItem? = hero.equipment.find { it is Bomb } as StackableItem?
        
        assertTrue(bombInEq?.amount == 4)
    }
    
    @Test fun `takeItem removes items from chest after adding them to hero equipment`()
    {
        hero.addItemToEquipment(Bomb(2))
        
        val item = Bomb(2)
        chest.items.add(item)
        
        chest.takeItemFromChest(item)
        
        assertNull(chest.items.find { it is Bomb })
    }
}
