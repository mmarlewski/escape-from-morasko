import com.efm.entities.Hero
import com.efm.item.ContainerFullException
import com.efm.multiUseMapItems.Bow
import com.efm.skills.Jump
import com.efm.skills.Pockets
import com.efm.stackableMapItems.Bomb
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class hero
{
    val hero = Hero()
    
    @Before fun `set up`()
    {
        hero.inventory.items.clear()
        hero.bodyPartMap.clear()
    }
    
    @Test fun `ability points test`()
    {
        assertTrue(hero.abilityPoints == hero.maxAbilityPoints)
        
        hero.spendAP(2)
        
        assertTrue(hero.abilityPoints == hero.maxAbilityPoints - 2)
        
        hero.gainAP(1)
        
        assertTrue(hero.abilityPoints == hero.maxAbilityPoints - 1)
        
        hero.regainAllAP()
        
        assertTrue(hero.abilityPoints == hero.maxAbilityPoints)
    }
    
    @Test fun `skill test`()
    {
        assertFalse(hero.hasSkill(Jump))
        
        hero.addSkill(Jump)
        
        assertTrue(hero.hasSkill(Jump))
        
        hero.removeSkill(Jump)
        
        assertFalse(hero.hasSkill(Jump))
    }
    
    @Test fun `add item to empty equipment`()
    {
        hero.inventory.addItem(Bomb())
        assertTrue(hero.inventory.items.size == 1)
    }
    
    @Test fun `add item to full equipment`()
    {
        for (i in 0 until hero.inventory.maxItems)
        {
            hero.inventory.addItem(Bow())
        }
        assertTrue(hero.inventory.items.size == hero.inventory.maxItems)
        
        try
        {
            hero.inventory.addItem(Bow())
            assertFalse(true)
        } catch (e : ContainerFullException)
        {
            assertTrue(true)
        }
        assertTrue(hero.inventory.items.size == hero.inventory.maxItems)
    }
    
    @Test fun `remove item from equipment`()
    {
        // addItem adds a copy
        val bombExistingNowhere = Bomb()
        hero.inventory.addItem(bombExistingNowhere)
        
        // findAllStacks finds one stack of Bomb
        assertTrue(hero.inventory.findAllStacks(Bomb()).size == 1)
        val bombInInventory = hero.inventory.findAllStacks(Bomb()).first()
        // inventory contains the copy and not he original
        assertFalse(hero.inventory.items.contains(bombExistingNowhere))
        assertTrue(hero.inventory.items.contains(bombInInventory))
        
        // removing the original does nothing
        hero.inventory.removeItem(bombExistingNowhere)
        assertFalse(hero.inventory.items.contains(bombExistingNowhere))
        assertTrue(hero.inventory.items.contains(bombInInventory))
        
        // removing the copy
        hero.inventory.removeItem(bombInInventory)
        assertFalse(hero.inventory.items.contains(bombExistingNowhere))
        assertFalse(hero.inventory.items.contains(bombInInventory))
    }
    
    @Test fun `adding Pockets increases inventory size`()
    {
        // fill inventory
        for (i in 0 until hero.inventory.maxItems)
        {
            hero.inventory.addItem(Bow())
        }
        assertTrue(hero.inventory.items.size == hero.inventory.maxItems)
        // increase inventory size by adding Pockets Skill
        val oldSize = hero.inventory.maxItems
        hero.addSkill(Pockets)
        val newSize = hero.inventory.maxItems
        assertTrue(newSize == oldSize + Pockets.additionalInventorySlotsAmount)
    }
}
