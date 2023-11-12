package com.efm

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Json
import com.efm.level.Level
import com.efm.level.World

val json = Json()

fun saveWorld()
{
    println("saving")
    val file = Gdx.files.local("save.txt")
    file.writeString(json.prettyPrint(World.currentLevel), false)
//    try
//    {
//        file.writeString(json.toJson(World.currentLevel), false)
//    }
//    catch (exception : Exception)
//    {
//        println(exception.message)
//    }
}

fun loadWorld()
{
    println("loading")
    val file = Gdx.files.local("save.txt")
    if (file.exists())
    {
        World.set(json.fromJson(Level::class.java,file))
//        try
//        {
//            World.set(json.fromJson(Level::class.java,file))
//        }
//        catch (exception : Exception)
//        {
//            println(exception.message)
//        }
    }
}
