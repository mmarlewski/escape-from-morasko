import com.efm.entities.*
import com.efm.item.StackableItem
import com.efm.item.examplePossibleItems
import com.efm.multiUseMapItems.Bow
import com.efm.stackableSelfItems.Mushroom
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class) class `possible items`
{
    private val possibleItems = examplePossibleItems()
    
    private fun printChestItems(chest : Chest, name : String = "Chest")
    {
        println("$name items:")
        for (item in chest.items)
        {
            var amount = 1
            if (item is StackableItem) amount = item.amount
            println("${item.name} $amount")
        }
    }
    
    @Before fun `set up`()
    {
    
    }
    
    @Test fun `making Chest without PossibleItems makes empty Chest`()
    {
        val chest = Chest()
        assertTrue(chest.items.isEmpty())
    }
    
    @Test fun `making Chest with PossibleItems makes Chest with random Items from PossibleItems`()
    {
        val chest = Chest(possibleItems)
        // printChestItems(chest)
    }
    
    @Test fun `making Chests with same seeds makes Chests with same Items from PossibleItems`()
    {
        val seed = 2
        val chest1 = Chest(possibleItems, seed)
        // printChestItems(chest1, "Chest1")
        val chest2 = Chest(possibleItems, seed)
        // printChestItems(chest2, "Chest2")
        for (i in 0 until chest1.items.size)
        {
            assertTrue(chest1.items[i]::class == chest2.items[i]::class)
            if (chest1.items[i] is StackableItem)
            {
                assertTrue((chest1.items[i] as StackableItem).amount == (chest1.items[i] as StackableItem).amount)
            }
        }
    }
    
    @Test fun `cannot add same item more times than initial timesLeftPossibleToAdd`()
    {
        // Bow has initial timesLeftPossibleToAdd=1 and will not be added the second time
        val seed = 43
        val chest1 = Chest(possibleItems, seed)
        // printChestItems(chest1,"Chest1")
        val chest2 = Chest(possibleItems, seed)
        // printChestItems(chest2,"Chest2")
        assertTrue(chest1.findAllStacks(Bow()).isNotEmpty())
        assertTrue(chest2.findAllStacks(Bow()).isEmpty())
    }
    
    @Test fun `cannot add more of an item than initial amountLeftPossibleToAdd`()
    {
        // Mushroom has initial amountLeftPossibleToAdd=10
        // first added 6
        // second added 10-6=3
        // third added 0
        val seed = 5
        val chest1 = Chest(possibleItems, seed)
        // printChestItems(chest1, "Chest1")
        val chest2 = Chest(possibleItems, seed)
        // printChestItems(chest2,"Chest2")
        val chest3 = Chest(possibleItems, seed)
        // printChestItems(chest3, "Chest3")
        assertTrue(chest1.findAllStacks(Mushroom()).isNotEmpty())
        assertTrue(chest2.findAllStacks(Mushroom()).isNotEmpty())
        assertTrue(chest3.findAllStacks(Mushroom()).isEmpty())
    }
}
