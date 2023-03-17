import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.backends.headless.HeadlessApplication
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration
import com.badlogic.gdx.ApplicationListener
import org.junit.runners.BlockJUnit4ClassRunner
import org.mockito.Mockito.mock

class HeadlessTestRunner(klass : Class<Any>) : BlockJUnit4ClassRunner(klass), ApplicationListener
{
    init
    {
        val conf = HeadlessApplicationConfiguration()
        HeadlessApplication(this, conf)
        Gdx.gl = mock(GL20::class.java)
    }
    
    override fun create()
    {
    }
    
    override fun resize(width : Int, height : Int)
    {
    }
    
    override fun render()
    {
    }
    
    override fun pause()
    {
    }
    
    override fun resume()
    {
    }
    
    override fun dispose()
    {
    }
}
