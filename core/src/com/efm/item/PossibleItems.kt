package com.efm.item

import com.efm.multiUseMapItems.Bow
import com.efm.stackableMapItems.Bomb
import com.efm.stackableSelfItems.Apple
import com.efm.stackableSelfItems.Mushroom
import kotlin.math.max
import kotlin.random.Random

class PossibleItem(
        val item : Item,
        val chance : Float,
        val amountRange : IntRange
                  )

/**
 * Structure from which Items can be drawn.
 * @param maxItemsPossibleToDraw maximum number of drawn items
 */
class PossibleItems(
        val items : MutableList<PossibleItem> = mutableListOf(), private val maxItemsPossibleToDraw : Int = items.size
                   )
{
    fun drawItems(seed : Int = Random.nextInt()) : MutableList<Item>
    {
        val drawnItems = mutableListOf<Item>()
        val generator = Random(seed)
        for (possibleItem in items)
        {
            if (drawnItems.size < maxItemsPossibleToDraw)
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
        // val drawnItem = Class.forName(possibleItem.item::class.qualifiedName).getConstructor().newInstance()
        val drawnItem = possibleItem.item.clone()
        val amount = possibleItem.amountRange.random(generator)
        if (drawnItem is StackableItem)
        {
            drawStackableItem(drawnItem, amount, drawnItems)
        }
        else if (drawnItem is MultiUseMapItem)
        {
            drawMultiUseMapItem(drawnItem, amount, drawnItems)
        }
    }
    
    private fun drawStackableItem(
            drawnItem : StackableItem, amount : Int, drawnItems : MutableList<Item>
                                 )
    {
        drawnItem.amount = amount
        drawnItems.add(drawnItem)
    }
    
    private fun drawMultiUseMapItem(
            drawnItem : MultiUseMapItem, amount : Int, drawnItems : MutableList<Item>
                                   )
    {
        for (y in 0 until amount)
        {
            if (drawnItems.size < maxItemsPossibleToDraw)
            {
                // important to copy and not use drawnItem more than once
                drawnItems.add(drawnItem.clone())
            }
        }
    }
}

fun examplePossibleItems() : PossibleItems
{
    return PossibleItems(
            mutableListOf(
                    PossibleItem(Bomb(), 0.5f, IntRange(1, 2)),
                    PossibleItem(Apple(), 0.8f, IntRange(2, 4)),
                    PossibleItem(Mushroom(), 0.75f, IntRange(2, 6)),
                    PossibleItem(Bow(), 0.33f, IntRange(1, 1))
                         )
                        )
}
