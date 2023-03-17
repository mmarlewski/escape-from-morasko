package com.efm

import com.badlogic.gdx.math.Vector2

fun orthoToIso(orthoPosition : Vector2, isoPosition : Vector2)
{
    orthoToIso(orthoPosition.x, orthoPosition.y, isoPosition)
}

fun orthoToIso(orthoX : Float, orthoY : Float, isoPosition : Vector2)
{
    isoPosition.x = orthoX + orthoY
    isoPosition.y = 0.5f * (orthoY - orthoX)
    
    isoPosition.y += tileLengthQuarter
}

fun isoToOrtho(isoPosition : Vector2, orthoPosition : Vector2)
{
    isoToOrtho(isoPosition.x, isoPosition.y, orthoPosition)
}

fun isoToOrtho(isoX : Float, isoY : Float, orthoPosition : Vector2)
{
    orthoPosition.x = 0.5f * isoX - (isoY - tileLengthQuarter)
    orthoPosition.y = 0.5f * isoX + (isoY - tileLengthQuarter)
}
