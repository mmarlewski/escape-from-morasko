package com.efm.entities

import com.efm.item.*
import com.efm.multiUseMapItems.Bow
import com.efm.stackableMapItems.Bomb
import com.efm.stackableSelfItems.Apple
import com.efm.stackableSelfItems.Mushroom
import kotlin.random.Random

class PossibleItem(
        val item : Item, val chance : Float, val amountRange : IntRange, var timesLeftPossibleToAdd : Int = Int.MAX_VALUE
                  )

class PossibleItems(val items : MutableList<PossibleItem> = mutableListOf())
{
    /*
        val json = Json()
        System.out.println(json.prettyPrint(x))
        
        val file = Gdx.files.local("myfile.txt")
        file.writeString(json.prettyPrint(x), false)
    */
    
    fun drawItems(seed : Int) : MutableList<Item>
    {
        val drawnItems = mutableListOf<Item>()
        val generator = Random(seed)
        for (possibleItem in items)
        {
            val chanceNotToDrawItem = generator.nextFloat()
            // println(chanceNotToDrawItem.toString() + " " + possibleItem.chance)
            if (chanceNotToDrawItem <= possibleItem.chance)
            {
                val drawnItem = possibleItem.item
                val amount = possibleItem.amountRange.random(generator)
                if (drawnItem is StackableItem)
                {
                    drawnItem.amount = amount
                    if (possibleItem.timesLeftPossibleToAdd > 0)
                    {
                        drawnItems.add(drawnItem)
                        possibleItem.timesLeftPossibleToAdd--
                    }
                }
                else if (drawnItem is MultiUseMapItem)
                {
                    for (y in 0 until amount)
                    {
                        if (possibleItem.timesLeftPossibleToAdd > 0)
                        {
                            drawnItems.add(drawnItem)
                            possibleItem.timesLeftPossibleToAdd--
                        }
                    }
                }
            }
        }
        return drawnItems
    }
    
}

fun examplePossibleItems() : PossibleItems
{
    return PossibleItems(
            mutableListOf(
                    PossibleItem(Bomb(), 0.5f, IntRange(1, 2)),
                    PossibleItem(Apple(), 0.8f, IntRange(2, 4)),
                    PossibleItem(Mushroom(), 0.75f, IntRange(2, 8)),
                    PossibleItem(Bow(), 0.33f, IntRange(1, 1), 1)
                         )
                        )
}
