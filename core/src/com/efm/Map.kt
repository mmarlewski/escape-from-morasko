package com.efm

import com.badlogic.gdx.maps.tiled.*

enum class MapLayer
{
    base, select, entity
}

object Map
{
    val mapWidthInTiles = 10
    val mapHeightInTiles = 10
    
    val tileLengthInPixels = 64
    val tileLengthHalfInPixels = 32
    val tileLengthQuarterInPixels = 16
    
    val tiledMap = TiledMap()
    
    init
    {
        newLayer(MapLayer.base)
        newLayer(MapLayer.select)
        newLayer(MapLayer.entity)
    }
    
    fun clearLayer(mapLayer : MapLayer)
    {
        val layer = tiledMap.layers.get(mapLayer.name) as? TiledMapTileLayer
        
        if (layer != null)
        {
            for (i in 0 until mapHeightInTiles)
            {
                for (j in 0 until mapWidthInTiles)
                {
                    layer.setCell(j, i, TiledMapTileLayer.Cell())
                }
            }
        }
    }
    
    fun clearAllLayers()
    {
        for (mapLayer in MapLayer.values())
        {
            clearLayer(mapLayer)
        }
    }
    
    fun newLayer(mapLayer : MapLayer)
    {
        val newLayer = TiledMapTileLayer(mapWidthInTiles, mapHeightInTiles, tileLengthInPixels, tileLengthHalfInPixels)
        newLayer.name = mapLayer.name
        
        for (i in 0 until mapHeightInTiles)
        {
            for (j in 0 until mapWidthInTiles)
            {
                newLayer.setCell(j, i, TiledMapTileLayer.Cell())
            }
        }
        
        tiledMap.layers.add(newLayer)
    }
    
    fun changeTile(mapLayer : MapLayer, x : Int, y : Int, tile : TiledMapTile?)
    {
        val layer = tiledMap.layers.get(mapLayer.name) as? TiledMapTileLayer
        
        if (layer != null)
        {
            val cell = layer.getCell(x, mapHeightInTiles - y - 1)
            
            if (cell != null)
            {
                cell.tile = tile
            }
        }
    }
}
