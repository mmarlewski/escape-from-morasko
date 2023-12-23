import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.efm.*
import com.efm.Map
import com.efm.assets.Tiles
import com.efm.room.RoomPosition
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `room position`
{
    @Test fun `positionOffsetBy4 test`()
    {
        assertTrue(RoomPosition(1, 1).positionOffsetBy(3, Direction4.down) == RoomPosition(1, 4))
    }
    
    @Test fun `positionOffsetBy8 test`()
    {
        assertTrue(RoomPosition(1, 1).positionOffsetBy(3, Direction8.downRight) == RoomPosition(4, 4))
    }
    
    @Test fun `adjacentPosition4 test`()
    {
        assertTrue(RoomPosition(1, 1).adjacentPosition(Direction4.down) == RoomPosition(1, 2))
    }
    
    @Test fun `adjacentPosition8 test`()
    {
        assertTrue(RoomPosition(1, 1).adjacentPosition(Direction8.downRight) == RoomPosition(2, 2))
    }
    
    @Test fun `surroundingPositions test`()
    {
        assertTrue(RoomPosition(1, 1).surroundingPositions(2).contains(RoomPosition(3, 3)))
    }
    
    @Test fun `isAdjacentTo test`()
    {
        assertTrue(RoomPosition(1, 1).isAdjacentTo(RoomPosition(2, 2)))
    }
}
