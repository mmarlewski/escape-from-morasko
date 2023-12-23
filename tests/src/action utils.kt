import com.efm.*
import com.efm.room.Base
import com.efm.room.RoomPosition
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `action utils`
{
    @Test fun `getLineFromPositionInDirectionPositions test`()
    {
        assertTrue(
                getLineFromPositionInDirectionPositions(RoomPosition(1, 1), Direction8.down, 5)
                        .contains(RoomPosition(1, 3))
                  )
    }
    
    @Test fun `getSquarePerimeterPositions test`()
    {
        assertTrue(
                getSquarePerimeterPositions(RoomPosition(1, 1), 5)
                        .contains(RoomPosition(1, 6))
                  )
    }
    
    @Test fun `getSquareAreaPositions test`()
    {
        assertTrue(
                getSquareAreaPositions(RoomPosition(1, 1), 5)
                        .contains(RoomPosition(1, 1))
                  )
    }
}
