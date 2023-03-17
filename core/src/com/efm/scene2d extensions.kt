package com.efm

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.Layout

fun Stage.setRootActor(actor : Actor)
{
    if (actor is Layout)
    {
        actor.setFillParent(true)
    }
    this.addActor(actor)
}
