package com.efm.item

import com.efm.multiUseMapItems.*
import com.efm.stackableMapItems.*
import com.efm.stackableSelfItems.*
import kotlin.reflect.KClass

enum class Items(private val kClass : KClass<out Item>)
{
    BOW(Bow::class),
    SLEDGEHAMMER(Sledgehammer::class),
    SMALL_AXE(SmallAxe::class),
    STAFF(Staff::class),
    IRON_SWORD(IronSword::class),
    AMBER_SWORD(AmberSword::class),
    TURQUOISE_SWORD(TurquoiseSword::class),
    WOODEN_SWORD(WoodenSword::class),
    BOMB(Bomb::class),
    EXPLOSIVE(Explosive::class),
    SHURIKEN(Shuriken::class),
    APPLE(Apple::class),
    AP_POTION_BIG(APPotionBig::class),
    AP_POTION_SMALL(APPotionSmall::class),
    FISH(Fish::class),
    HP_POTION_BIG(HPPotionBig::class),
    HP_POTION_SMALL(HPPotionSmall::class),
    MUSHROOM(Mushroom::class);
    
    fun new() = ItemFactory.default(this.kClass)
}

private object ItemFactory
{
    fun default(itemClass : KClass<out Item>) =
            Class.forName(itemClass.qualifiedName).getConstructor().newInstance() as Item
    
    fun default(item : Item) = Class.forName(item::class.qualifiedName).getConstructor().newInstance() as Item
}