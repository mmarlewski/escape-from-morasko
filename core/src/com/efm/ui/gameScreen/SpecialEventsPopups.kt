package com.efm.ui.gameScreen

import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.efm.*
import com.efm.screens.GameScreen

object SpecialEventsPopups
{
    var gainHealthPopup : Window
    var strongerWeaponPopup : Window
    var gainAbilityPopup : Window
    var skillExchangePopup : Window
    var swapTilesPopup : Window
    
    fun gainHealthPopup() : Window
    {
        val gainHealthPopup = specialEventPopup("Life Infusion",
                                                "Sacrifice some of your skill to gain resilience. Max Health increases, " +
                                                        "but say goodbye to a portion of your precious Ability Points. " +
                                                        "Do you accept this offer?",
                                                {},
                                                {})
        
        gainHealthPopup.isVisible = false
        return gainHealthPopup
    }
    
    fun strongerWeaponPopup() : Window
    {
        val strongerWeaponPopup = specialEventPopup("Bargain of Blades",
                                                    "Fancy a more powerful weapon? Embrace it, but be prepared to lose " +
                                                            "a chunk of your Max Health in exchange for the destructive might. " +
                                                            "Do you accept this offer?",
                                                    {},
                                                    {})
        strongerWeaponPopup.isVisible = false
        return strongerWeaponPopup
    }
    
    fun gainAbilityPopup() : Window
    {
        val gainAbilityPopup = specialEventPopup("Mind Over Matter",
                                                 "Opt for increased mental prowess. Max Ability Points rise, but " +
                                                         "at the cost of sacrificing a portion of your Max Health. " +
                                                         "Do you accept this offer?",
                                                 {},
                                                 {})
        gainAbilityPopup.isVisible = false
        return gainAbilityPopup
    }
    
    fun skillExchangePopup() : Window
    {
        val skillExchangePopup = specialEventPopup("Body and Soul Exchange",
                                                   "Exchange your skills on a specific body part. A gamble with the unknown," +
                                                           " as you let go of familiarity for the potential of newfound strength. " +
                                                           "Do you accept this offer?",
                                                   {},
                                                   {})
        skillExchangePopup.isVisible = false
        return skillExchangePopup
    }
    
    fun swapTilesPopup() : Window
    {
        val swapTilesPopup = specialEventPopup("Elemental Swap",
                                               "Have control over the environment. Transform lava tiles into water and vice versa. A strategic move that can alter the course of your journey. " +
                                                       "Do you accept this offer?",
                                               {},
                                               {})
        swapTilesPopup.isVisible = false
        return swapTilesPopup
    }
    
    init
    {
        gainHealthPopup = gainHealthPopup()
        strongerWeaponPopup = strongerWeaponPopup()
        gainAbilityPopup = gainAbilityPopup()
        skillExchangePopup = skillExchangePopup()
        swapTilesPopup = swapTilesPopup()
    }
    
    fun addPopupToDisplay(popup : Window)
    {
        val window = columnOf(rowOf(popup)).align(Align.center)
        window.setFillParent(true)
        GameScreen.stage.addActor(window)
    }
    
    fun display()
    {
        addPopupToDisplay(gainHealthPopup)
        addPopupToDisplay(strongerWeaponPopup)
        addPopupToDisplay(gainAbilityPopup)
        addPopupToDisplay(skillExchangePopup)
        addPopupToDisplay(swapTilesPopup)
    }
    
}