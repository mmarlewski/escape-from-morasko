import com.efm.entities.Hero
import com.efm.level.Level
import com.efm.room.Room
import com.efm.room.RoomPosition
import com.efm.stackableMapItems.Bomb
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `hero`
{
    val hero = Hero()
    
    @Test fun `add item to empty equipment`()
    {
        hero.equipment.clear()
        hero.addItemToEquipment(Bomb())
        assertTrue(hero.equipment.size == 1)
    }
    
    @Test fun `add item to full equipment`()
    {
        hero.equipment.clear()
        for (i in 0 until hero.equipmentMax)
        {
            hero.addItemToEquipment(Bomb())
        }
        assertTrue(hero.equipment.size == hero.equipmentMax)
        
        hero.addItemToEquipment(Bomb())
        assertTrue(hero.equipment.size == hero.equipmentMax)
    }
    
    @Test fun `remove item from equipment`()
    {
        val bomb = Bomb()
        
        hero.equipment.clear()
        hero.addItemToEquipment(bomb)
        assertTrue(hero.equipment.size == 1)
        
        hero.removeItemFromEquipment(bomb)
        assertTrue(hero.equipment.size == 0)
        
        hero.removeItemFromEquipment(bomb)
        assertTrue(hero.equipment.size == 0)
    }
}
