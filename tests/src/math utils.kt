import com.badlogic.gdx.math.Vector2
import com.efm.*
import com.efm.room.Base
import com.efm.room.RoomPosition
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `math utils`
{
    @Test fun `positionToOrtho test`()
    {
        assertTrue(positionToOrtho(Vector2(1f, 1f)) == Vector2(32.0f, 736.0f))
    }
    
    @Test fun `orthoToPosition test`()
    {
        assertTrue(orthoToPosition(Vector2(1f, 1f)) == Vector2(0.0f, 24.0f))
    }
    
    @Test fun `roomPositionToOrtho test`()
    {
        assertTrue(roomPositionToOrtho(RoomPosition(1, 1)) == Vector2(32.0f, 736.0f))
    }
    
    @Test fun `orthoToRoomPosition test`()
    {
        assertTrue(orthoToRoomPosition(Vector2(1f, 1f)) == RoomPosition(0, 24))
    }
    
    @Test fun `orthoToIso test`()
    {
        assertTrue(orthoToIso(Vector2(1f, 1f)) == Vector2(2.0f, 16.0f))
    }
    
    @Test fun `isoToOrtho test`()
    {
        assertTrue(isoToOrtho(Vector2(1f, 1f)) == Vector2(15.5f, -14.5f))
    }
}
