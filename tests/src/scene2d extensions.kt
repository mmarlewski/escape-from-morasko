import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Scaling
import com.efm.*
import com.efm.assets.Textures
import org.junit.Assert

//@RunWith(HeadlessTestRunner::class)
class `scene2d extensions`
{
    //@Test
    fun `setRootActor sets root actor`()
    {
        val stage = Stage()
        val image = imageOf(Textures.coin, Scaling.fill)
        stage.setRootActor(image)
        
        Assert.assertTrue(stage.root == image)
    }
}
