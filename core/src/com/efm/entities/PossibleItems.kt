package com.efm.entities

import com.efm.item.*
import com.efm.multiUseMapItems.Bow
import com.efm.stackableMapItems.Bomb
import com.efm.stackableSelfItems.Apple
import com.efm.stackableSelfItems.Mushroom
import kotlin.random.Random

class PossibleItem(
        val item : Item,
        val chance : Float,
        val amountRange : IntRange,
        var timesLeftPossibleToAdd : Int = Int.MAX_VALUE,
        var amountLeftPossibleToAdd : Int = Int.MAX_VALUE
                  )

class PossibleItems(
        val items : MutableList<PossibleItem> = mutableListOf(), private val maxItemsPossibleToDraw : Int = Int.MAX_VALUE
                   )
{
    fun drawItems(seed : Int = Random.nextInt()) : MutableList<Item>
    {
        val drawnItems = mutableListOf<Item>()
        val generator = Random(seed)
        for (possibleItem in items)
        {
            if (possibleItem.timesLeftPossibleToAdd > 0 && drawnItems.size < maxItemsPossibleToDraw)
            {
                tryToDrawItem(possibleItem, drawnItems, generator)
            }
        }
        return drawnItems
    }
    
    private fun tryToDrawItem(possibleItem : PossibleItem, drawnItems : MutableList<Item>, generator : Random)
    {
        val chanceNotToDrawItem = generator.nextFloat()
        // println(chanceNotToDrawItem.toString() + " " + possibleItem.chance)
        if (chanceNotToDrawItem <= possibleItem.chance)
        {
            drawItem(possibleItem, drawnItems, generator)
        }
    }
    
    private fun drawItem(possibleItem : PossibleItem, drawnItems : MutableList<Item>, generator : Random)
    {
        // important to copy and not use possibleItem.item
        val drawnItem = Class.forName(possibleItem.item::class.qualifiedName).getConstructor().newInstance()
        val amount = possibleItem.amountRange.random(generator)
        if (drawnItem is StackableItem)
        {
            drawStackableItem(possibleItem, drawnItem, amount, drawnItems)
        }
        else if (drawnItem is MultiUseMapItem)
        {
            drawMultiUseMapItem(possibleItem, drawnItem, amount, drawnItems)
        }
    }
    
    private fun drawStackableItem(
            possibleItem : PossibleItem, drawnItem : StackableItem, amount : Int, drawnItems : MutableList<Item>
                                 )
    {
        val amountToAdd = minOf(amount, possibleItem.amountLeftPossibleToAdd)
        drawnItem.amount = amountToAdd
        possibleItem.amountLeftPossibleToAdd -= amountToAdd
        drawnItems.add(drawnItem)
        possibleItem.timesLeftPossibleToAdd--
    }
    
    private fun drawMultiUseMapItem(
            possibleItem : PossibleItem, drawnItem : MultiUseMapItem, amount : Int, drawnItems : MutableList<Item>
                                   )
    {
        val amountToAdd = minOf(amount, possibleItem.amountLeftPossibleToAdd)
        possibleItem.amountLeftPossibleToAdd -= amountToAdd
        for (y in 0 until amountToAdd)
        {
            if (possibleItem.timesLeftPossibleToAdd > 0 && drawnItems.size < maxItemsPossibleToDraw)
            {
                drawnItems.add(drawnItem)
                possibleItem.timesLeftPossibleToAdd--
            }
        }
    }
    
    /*
        val json = Json()
        System.out.println(json.prettyPrint(x))
        
        val file = Gdx.files.local("myfile.txt")
        file.writeString(json.prettyPrint(x), false)
    */
}

fun examplePossibleItems() : PossibleItems
{
    return PossibleItems(
            mutableListOf(
                    PossibleItem(Bomb(), 0.5f, IntRange(1, 2)),
                    PossibleItem(Apple(), 0.8f, IntRange(2, 4)),
                    PossibleItem(Mushroom(), 0.75f, IntRange(2, 6), amountLeftPossibleToAdd = 10),
                    PossibleItem(Bow(), 0.33f, IntRange(1, 1), timesLeftPossibleToAdd = 1)
                         )
                        )
}
