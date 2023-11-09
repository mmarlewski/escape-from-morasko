import com.efm.room.Base
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class) class `base enum`
{
    @Before fun `set up`()
    {
    
    }
    
    @Test fun `test companion functions`()
    {
        println(Base.grass.ordinal)
        println(Base.getOrdinal(Base.grass))
        println(Base.getBase(3))
    }
}
