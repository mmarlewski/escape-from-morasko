import com.efm.entities.Hero
import com.efm.multiUseMapItems.Bow
import com.efm.stackableMapItems.Bomb
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class) class hero
{
    val hero = Hero()
    
    @Test fun `add item to empty equipment`()
    {
        hero.inventory.items.clear()
        hero.inventory.addItem(Bomb())
        assertTrue(hero.inventory.items.size == 1)
    }
    
    @Test fun `add item to full equipment`()
    {
        hero.inventory.items.clear()
        for (i in 0 until hero.inventory.maxItems)
        {
            hero.inventory.addItem(Bow())
        }
        assertTrue(hero.inventory.items.size == hero.inventory.maxItems)
    
        hero.inventory.addItem(Bow())
        assertTrue(hero.inventory.items.size == hero.inventory.maxItems)
    }
    
    @Test fun `remove item from equipment`()
    {
        val bomb = Bomb()
    
        hero.inventory.items.clear()
        hero.inventory.addItem(bomb)
        assertTrue(hero.inventory.items.size == 1)
    
        hero.inventory.removeItem(bomb)
        assertTrue(hero.inventory.items.size == 0)
    
        hero.inventory.removeItem(bomb)
        assertTrue(hero.inventory.items.size == 0)
    }
}
