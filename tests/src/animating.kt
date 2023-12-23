import com.efm.*
import com.efm.entities.StoneColumn
import com.efm.entities.enemies.*
import com.efm.room.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `animating`
{
    @Test fun `animating test`()
    {
        Animating.executeAnimations(mutableListOf(Animation.wait(1f)))
        
        assertTrue(Animating.isAnimating())
        assertTrue(Animating.getCurrentAnimation() is Animation.wait)
    }
}
