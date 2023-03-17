import com.efm.EscapeFromMorasko
import com.efm.changeScreen
import com.efm.screens.GameScreen
import com.efm.screens.MenuScreen
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

//@RunWith(HeadlessTestRunner::class)
class `screen utils`
{
    //@Test
    fun `changeScreen changes screen`()
    {
        val screen1 = MenuScreen
        val screen2 = GameScreen
        
        changeScreen(screen1)
        Assert.assertTrue(EscapeFromMorasko.screen == screen1)
        
        changeScreen(screen2)
        Assert.assertTrue(EscapeFromMorasko.screen == screen2)
    }
}
