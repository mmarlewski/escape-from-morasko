import com.badlogic.gdx.Gdx
import com.efm.*
import com.efm.entities.enemies.EnemyBat
import com.efm.entities.enemies.EnemyMushroom
import com.efm.entity.Enemy
import com.efm.level.Level
import com.efm.level.World
import com.efm.multiUseMapItems.WoodenSword
import com.efm.room.Room
import com.efm.state.*
import com.efm.ui.gameScreen.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `damage and scaling`
{
    private val hero = World.hero
    private val level1 = Level()
    private val level2 = Level()
    private val level1room1 = Room()
    private val level1room2 = Room()
    private val level2room1 = Room()
    private val enemyMushroom = object : EnemyMushroom()
    {
        override fun scaleOwnStats()
        {
            val turnsElapsed = World.hero.turnsElapsed
            maxHealthPoints += turnsElapsed
            healthPoints += turnsElapsed
            attackDamage += turnsElapsed / 3
            Gdx.app.log("Scaling", "Turns elapsed : $turnsElapsed")
            // createOwnHealthBar()
        }
    }
    private val enemyMushroomInitialMaxHp = enemyMushroom.maxHealthPoints
    private val enemyMushroomInitialHp = enemyMushroom.healthPoints
    private val enemyMushroomInitialDmg = enemyMushroom.attackDamage
    private val woodenSword = WoodenSword()
    
    @Before fun `set up`()
    {
        level1.addRoom(level1room1)
        level1.addRoom(level1room2)
        World.addLevel(level1)
        level2.addRoom(level2room1)
        World.addLevel(level2)
        World.currentLevel = level1
        World.currentRoom = level1room1
        hero.reset()
        level1room2.addEntityAt(enemyMushroom, 0, 0)
    }
    
    @Test fun `WoodenSword kills EnemyMushroom in 4 hits when Hero and Enemies are in starting configuration`()
    {
        for (i in 1..1000)
        {
            enemyMushroom.alive = true
            enemyMushroom.healthPoints = enemyMushroom.maxHealthPoints
            for (j in 1..4)
            {
                enemyMushroom.damageCharacter(woodenSword.damage * hero.weaponDamageMultiplier)
            }
            assertFalse(enemyMushroom.alive)
        }
    }
    
    @Test fun `WoodenSword kills EnemyBat in 3 hits when Hero and Enemies are in starting configuration`()
    {
        for (i in 1..1000)
        {
            val enemyBat = EnemyBat()
            for (j in 1..3)
            {
                enemyBat.damageCharacter(woodenSword.damage * hero.weaponDamageMultiplier)
            }
            assertFalse(enemyBat.alive)
        }
    }
    
    @Test fun `ending turn when in State Combat increases turnsElapsed`()
    {
        val turnsElapsedNow = World.hero.turnsElapsed
        val turnsToEnd = 100
        for (j in 1..turnsToEnd)
        {
            setState(State.combat.hero.noSelection)
            endCurrentTurnWithoutUIParts()
        }
        assertTrue(World.hero.turnsElapsed == turnsElapsedNow + turnsToEnd)
    }
    
    @Test fun `ending turn when in State Constrained increases turnsElapsed`()
    {
        val turnsElapsedNow = World.hero.turnsElapsed
        val turnsToEnd = 100
        for (j in 1..turnsToEnd)
        {
            setState(State.constrained.noSelection)
            endCurrentTurnWithoutUIParts()
        }
        assertTrue(World.hero.turnsElapsed == turnsElapsedNow + turnsToEnd)
    }
    
    @Test fun `ending turn when in State Free does not increase turnsElapsed`()
    {
        val turnsElapsedNow = World.hero.turnsElapsed
        val turnsToEnd = 100
        for (j in 1..turnsToEnd)
        {
            setState(State.free.noSelection)
            endCurrentTurnWithoutUIParts()
        }
        assertTrue(World.hero.turnsElapsed == turnsElapsedNow)
    }
    
    @Test fun `changeCurrentRoom scales stats of Enemies in newCurrentRoom by a fraction of turnsElapsed`()
    {
        assertTrue(enemyMushroomInitialMaxHp == enemyMushroom.maxHealthPoints)
        assertTrue(enemyMushroomInitialHp == enemyMushroom.healthPoints)
        assertTrue(enemyMushroomInitialDmg == enemyMushroom.attackDamage)
        val turnsToEnd = 120
        for (j in 1..turnsToEnd)
        {
            setState(State.constrained.noSelection)
            endCurrentTurnWithoutUIParts()
        }
        assertTrue(enemyMushroomInitialMaxHp == enemyMushroom.maxHealthPoints)
        assertTrue(enemyMushroomInitialHp == enemyMushroom.healthPoints)
        assertTrue(enemyMushroomInitialDmg == enemyMushroom.attackDamage)
        World.changeCurrentRoom(level1room2)
        assertTrue(enemyMushroomInitialMaxHp + World.hero.turnsElapsed == enemyMushroom.maxHealthPoints)
        assertTrue(enemyMushroomInitialHp + World.hero.turnsElapsed == enemyMushroom.healthPoints)
        assertTrue(enemyMushroomInitialDmg + World.hero.turnsElapsed / 3 == enemyMushroom.attackDamage)
    }
    
    @Test fun `changeCurrentRoom changes current room if currentLevel is not null and newCurrentRoom is in currentLevel`()
    {
        assertTrue(World.currentRoom == level1room1)
        World.changeCurrentRoom(level1room2)
        assertTrue(World.currentRoom == level1room2)
    }
    
    @Test fun `changeCurrentRoom does not change current room if newCurrentRoom is not in currentLevel`()
    {
        assertTrue(World.currentRoom == level1room1)
        World.changeCurrentRoom(level2room1)
        assertTrue(World.currentRoom == level1room1)
    }
}

private fun endCurrentTurnWithoutUIParts()
{
    saveGame()
    
    val isHeroVisible = World.hero.isVisible
    val currState = getState()
    // tutorial popups
    if (currState.tutorialFlags.tutorialActive && currState.tutorialFlags.turnsPopupShown)
        currState.tutorialFlags.playerEndedTurn = true
    var newState = currState
    
    when (currState)
    {
        is State.constrained ->
        {
            World.hero.regainAllAP()
            World.hero.updateActiveSkillCoolDown()
            World.hero.incrementTurnsElapsed()
            //ItemsStructure.fillItemsStructureWithItemsAndSkills()
            
            if (!isHeroVisible)
            {
                newState = State.free.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                }
            }
            // enemies roaming
            //val enemyRoamingAnimations = mutableListOf<Animation>()
            val enemyIterator = World.currentRoom?.getEnemies()?.iterator() ?: listOf<Enemy>().iterator()
            while (enemyIterator.hasNext())
            {
                val enemy = enemyIterator.next()
                if (enemyIterator.hasNext())
                {
                    Gdx.app.log("Roaming", enemy::class.simpleName)
                    //enemyRoamingAnimations += enemy.getRoamingAnimations()
                }
                else
                {
                    Gdx.app.log("Roaming last", enemy::class.simpleName)
                    //enemyRoamingAnimations += enemy.getRoamingAnimations(focusCameraOnHero = true)
                }
            }
            //Animating.executeAnimations(enemyRoamingAnimations)
            /*
            // tutorial popups
            if (newState.tutorialFlags.tutorialActive && newState.tutorialFlags.playerEndedTurn && !newState.tutorialFlags.combatPopupShown)
            {
                TutorialPopups.combatPopup.isVisible = true
                PopUps.setBackgroundVisibility(false)
                LeftStructure.menuButton.isVisible = false
                newState.tutorialFlags.combatPopupShown = true
            }
            */
        }
        
        is State.combat.hero ->
        {
            World.hero.updateActiveSkillCoolDown()
            World.hero.incrementTurnsElapsed()
            //ItemsStructure.fillItemsStructureWithItemsAndSkills()
            
            //Map.clearLayer(MapLayer.select)
            //Map.clearLayer(MapLayer.outline)
            
            if (!isHeroVisible)
            {
                newState = State.free.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                }
            }
            else
            {
                //val animation = Animation.showTileWithCameraFocus(null, World.hero.position.copy(), 1f)
                //Animating.executeAnimations(mutableListOf(animation))
                
                newState = State.combat.enemies.enemyUnselected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                    this.enemies = World.currentRoom?.getEnemies() ?: listOf()
                    this.enemyIterator = this.enemies?.iterator()
                    this.currEnemy = when (val enemyIterator = this.enemyIterator)
                    {
                        null -> null
                        else -> when (enemyIterator.hasNext())
                        {
                            true  -> enemyIterator.next()
                            false -> null
                        }
                    }
                }
            }
        }
        
        else                 ->
        {
            if (!isHeroVisible)
            {
                newState = State.free.heroSelected.apply {
                    this.isHeroAlive = currState.isHeroAlive
                    this.areEnemiesInRoom = currState.areEnemiesInRoom
                }
            }
        }
    }
    
    setState(newState)
    
    World.hero.isVisible = true
    World.hero.setCanMoveToTrue()
    //GameScreen.updateMapEntityLayer()
}
