import com.efm.entities.*
import com.efm.entity.Chest
import com.efm.item.*
import com.efm.multiUseMapItems.WoodenSword
import com.efm.stackableMapItems.Bomb
import com.efm.stackableSelfItems.Apple
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `possible items`
{
    private val possibleItems = examplePossibleItems()
    val possibleItemsWithAmountZero = PossibleItems(
            mutableListOf(
                    PossibleItem(Apple(), 1f, 0..0),
                    PossibleItem(Bomb(), 1f, 0..0),
                    PossibleItem(WoodenSword(), 1f, 0..0)
                         )
                                                   )
    
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
    
    @Test fun `drawItems can draw items with amount zero`()
    {
        val drawnItems = possibleItemsWithAmountZero.drawItems()
        assertNotNull(drawnItems.find { it is StackableItem && it.amount == 0 })
    }
    
    @Test fun `making Chest without PossibleItems makes empty Chest`()
    {
        val chest = Chest()
        assertTrue(chest.items.isEmpty())
    }
    
    @Test fun `making Chest with PossibleItems makes Chest with random Items from PossibleItems`()
    {
        val chest = Chest(possibleItems)
        printChestItems(chest)
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
}
