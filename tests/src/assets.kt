import com.efm.*
import com.efm.assets.*
import com.efm.entities.StoneColumn
import com.efm.entities.enemies.*
import com.efm.room.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `assets`
{
    @Test fun `font loading test`()
    {
        assertTrue(Fonts.load("inconsolata/10.fnt") == Fonts.inconsolata10)
    }
    
    @Test fun `music loading test`()
    {
        assertTrue(Musics.load("ambient_1.mp3") == Musics.ambient_1)
    }
    
    @Test fun `sound loading test`()
    {
        assertTrue(Sounds.load("ui_1.mp3") == Sounds.ui_1)
    }
    
    @Test fun `texture loading test`()
    {
        assertTrue(Textures.load("items/fist.png") == Textures.fist)
    }
}
