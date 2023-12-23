import com.efm.*
import com.efm.entities.StoneColumn
import com.efm.entities.enemies.*
import com.efm.room.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `direction`
{
    @Test fun `getDirectionX test`()
    {
        assertTrue(getDirectionX(RoomPosition(1, 1), RoomPosition(2, 1)) == DirectionX.right)
    }
    
    @Test fun `getDirectionY test`()
    {
        assertTrue(getDirectionY(RoomPosition(1, 1), RoomPosition(1, 2)) == DirectionY.down)
    }
    
    @Test fun `getDirection4 test`()
    {
        assertTrue(getDirection4(RoomPosition(1, 1), RoomPosition(1, 2)) == Direction4.down)
    }
    
    @Test fun `getDirection8 test`()
    {
        assertTrue(getDirection8(RoomPosition(1, 1), RoomPosition(2, 2)) == Direction8.downRight)
    }
    
    @Test fun `Direction4 opposite test`()
    {
        assertTrue(Direction4.up.opposite() == Direction4.down)
    }
    
    @Test fun `Direction4 nextInClockwise test`()
    {
        assertTrue(Direction4.up.nextInClockwise() == Direction4.right)
    }
    
    @Test fun `Direction4 nextInCounterclockwise test`()
    {
        assertTrue(Direction4.up.nextInCounterclockwise() == Direction4.left)
    }
    
    @Test fun `Direction4 toDirection8 test`()
    {
        assertTrue(Direction4.up.toDirection8() == Direction8.up)
    }
    
    @Test fun `Direction8 opposite test`()
    {
        assertTrue(Direction8.up.opposite() == Direction8.down)
    }
    
    @Test fun `Direction8 nextInClockwise test`()
    {
        assertTrue(Direction8.up.nextInClockwise() == Direction8.upRight)
    }
    
    @Test fun `Direction8 nextInCounterclockwise test`()
    {
        assertTrue(Direction8.up.nextInCounterclockwise() == Direction8.upLeft)
    }
    
    @Test fun `Direction8 toDirection8 test`()
    {
        assertTrue(Direction8.up.toDirection4() == Direction4.up)
    }
}
