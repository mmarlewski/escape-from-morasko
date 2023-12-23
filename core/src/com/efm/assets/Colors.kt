package com.efm.assets

import com.badlogic.gdx.graphics.Color

/**
 * Contains all Color assets
 */
object Colors
{
    val clear = Color.CLEAR
    
    val white = Color.WHITE
    val lightGray = Color.LIGHT_GRAY
    val gray = Color.GRAY
    val darkGray = Color.DARK_GRAY
    val black = Color.BLACK
    
    val blue = Color.BLUE
    val navy = Color.NAVY
    val royal = Color.ROYAL
    val slate = Color.SLATE
    val sky = Color.SKY
    val cyan = Color.CYAN
    val teal = Color.TEAL
    
    val green = Color.GREEN
    val chartreuse = Color.CHARTREUSE
    val lime = Color.LIME
    val forest = Color.FOREST
    val olive = Color.OLIVE
    
    val yellow = Color.YELLOW
    val gold = Color.GOLD
    val goldenrod = Color.GOLDENROD
    val orange = Color.ORANGE
    
    val brown = Color.BROWN
    val tan = Color.TAN
    val firebrick = Color.FIREBRICK
    
    val red = Color.RED
    val scarlet = Color.SCARLET
    val coral = Color.CORAL
    val salmon = Color.SALMON
    val pink = Color.PINK
    val magenta = Color.MAGENTA
    
    val purple = Color.PURPLE
    val violet = Color.VIOLET
    val maroon = Color.MAROON
    
    private fun fromRGB(r : Float, g : Float, b : Float) : Color
    {
        return Color(r, g, b, 1.0f)
    }
}
