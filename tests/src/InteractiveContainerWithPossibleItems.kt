import com.efm.entities.enemies.mimic.EnemyMimicCorpse
import com.efm.entity.Chest
import com.efm.item.*
import com.efm.multiUseMapItems.WoodenSword
import com.efm.room.RoomPosition
import com.efm.stackableMapItems.Bomb
import com.efm.stackableSelfItems.Apple
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class InteractiveContainerWithPossibleItems
{
    private val possibleItemsWithAmountZero = PossibleItems(
            mutableListOf(
                    PossibleItem(Apple(), 1f, 0..0),
                    PossibleItem(Bomb(), 1f, 0..0),
                    PossibleItem(WoodenSword(), 1f, 0..0)
                         )
                                                           )
    private val possibleItemsSureToBeDrawn = PossibleItems(
            mutableListOf(
                    PossibleItem(Apple(), 1f, 1..1),
                    PossibleItem(Bomb(), 1f, 1..1),
                    PossibleItem(WoodenSword(), 1f, 1..1)
                         )
                                                          )
    private val possibleItemsThatGives2Items = PossibleItems(
            mutableListOf(
                    PossibleItem(Apple(), 1f, 1..1),
                    PossibleItem(Bomb(), 1f, 1..1)
                         )
                                                            )
    private val possibleItemsThatGives3Items = PossibleItems(
            mutableListOf(
                    PossibleItem(Apple(), 1f, 1..1),
                    PossibleItem(Bomb(), 1f, 1..1),
                    PossibleItem(WoodenSword(), 1f, 1..1)
                         )
                                                            )
    
    @Before fun `set up`()
    {
    
    }
    
    @Test fun `drawItems in init cannot add items with amount zero`()
    {
        // chest
        val chest = Chest(possibleItemsWithAmountZero)
        assertTrue(chest.items.isEmpty())
        // enemy corpse
        val mimicCorpse = EnemyMimicCorpse(RoomPosition(), possibleItemsWithAmountZero)
        assertTrue(mimicCorpse.items.isEmpty())
    }
    
    @Test fun `items are added when InteractiveContainerWithPossibleItems is created`()
    {
        // chest
        val chest = Chest(possibleItemsSureToBeDrawn)
        assertTrue(chest.items.size == 3)
        assertTrue(chest.findAllStacks(Apple()).size == 1)
        assertTrue((chest.findAllStacks(Apple()).first() as Apple).amount == 1)
        assertTrue(chest.findAllStacks(Bomb()).size == 1)
        assertTrue((chest.findAllStacks(Bomb()).first() as Bomb).amount == 1)
        assertTrue(chest.findAllStacks(WoodenSword()).size == 1)
        // enemy corpse
        val mimicCorpse = EnemyMimicCorpse(RoomPosition(), possibleItemsSureToBeDrawn)
        assertTrue(mimicCorpse.items.size == 3)
        assertTrue(mimicCorpse.findAllStacks(Apple()).size == 1)
        assertTrue((mimicCorpse.findAllStacks(Apple()).first() as Apple).amount == 1)
        assertTrue(mimicCorpse.findAllStacks(Bomb()).size == 1)
        assertTrue((mimicCorpse.findAllStacks(Bomb()).first() as Bomb).amount == 1)
        assertTrue(mimicCorpse.findAllStacks(WoodenSword()).size == 1)
    }
    
    @Test fun `no items are added when using default constructor`()
    {
        // chest
        val chest = Chest()
        assertTrue(chest.items.isEmpty())
        // enemy corpse
        val mimicCorpse = EnemyMimicCorpse()
        assertTrue(mimicCorpse.items.isEmpty())
    }
    
    @Test fun `maxItems of EnemyCorpses is set to the number of drawn items plus 2 when EnemyCorpses is created`()
    {
        val mimicCorpse1 = EnemyMimicCorpse(RoomPosition(), possibleItemsThatGives2Items)
        assertTrue(mimicCorpse1.items.size == 2)
        assertTrue(mimicCorpse1.maxItems == 2 + 2)
        val mimicCorpse2 = EnemyMimicCorpse(RoomPosition(), possibleItemsThatGives3Items)
        assertTrue(mimicCorpse2.items.size == 3)
        assertTrue(mimicCorpse2.maxItems == 3 + 2)
    }
    
    @Test fun `maxItems of EnemyCorpses is set to 2 when loot is empty PossibleItems`()
    {
        val mimicCorpse = EnemyMimicCorpse(RoomPosition(), PossibleItems())
        assertTrue(mimicCorpse.items.size == 0)
        assertTrue(mimicCorpse.maxItems == 2)
    }
    
    @Test fun `maxItems is not changed when loot is null and is equal to its default value 0 when created`()
    {
        val mimicCorpse = EnemyMimicCorpse(RoomPosition(), null)
        assertTrue(mimicCorpse.items.size == 0)
        assertTrue(mimicCorpse.maxItems == 0)
        // when loading it is then set to the amount originally set during first creation
    }
    
    @Test fun `cannot add more items than maxItems of Chest`()
    {
        val chest = Chest(possibleItemsThatGives3Items, maxItems = 2)
        assertTrue(chest.items.size == 2)
        assertTrue(chest.maxItems == 2)
    }
}
