import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.efm.MapLayer
import com.efm.Map
import com.efm.assets.Tiles
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(HeadlessTestRunner::class)
class `map`
{
    @Before fun removeAllLayers()
    {
        for (mapLayer in MapLayer.values())
        {
            val layer = Map.tiledMap.layers[mapLayer.name]
            Map.tiledMap.layers.remove(layer)
        }
    }
    
    @Test fun `clearLayer clears all tiles of layer`()
    {
        Map.clearLayer(MapLayer.base)
        
        val layer = Map.tiledMap.layers.get(MapLayer.base.name) as? TiledMapTileLayer
        
        if (layer != null)
        {
            for (i in 0 until Map.mapHeightInTiles)
            {
                for (j in 0 until Map.mapWidthInTiles)
                {
                    val cell = layer.getCell(j, i)
                    
                    if (cell != null)
                    {
                        if (cell.tile != null)
                        {
                            fail()
                        }
                    }
                }
            }
        }
    }
    
    @Test fun `clearAllLayers clears all tiles of all layers`()
    {
        Map.clearAllLayers()
        
        for (mapLayer in MapLayer.values())
        {
            val layer = Map.tiledMap.layers.get(mapLayer.name) as? TiledMapTileLayer
            
            if (layer != null)
            {
                for (i in 0 until Map.mapHeightInTiles)
                {
                    for (j in 0 until Map.mapWidthInTiles)
                    {
                        val cell = layer.getCell(j, i)
                        
                        if (cell != null)
                        {
                            if (cell.tile != null)
                            {
                                fail()
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Test fun `newLayer adds new layer with new cells`()
    {
        val nonExistingLayer = Map.tiledMap.layers.get(MapLayer.base.name) as? TiledMapTileLayer
        assertNull(nonExistingLayer)
        
        Map.newLayer(MapLayer.base)
        val existingLayer = Map.tiledMap.layers.get(MapLayer.base.name) as? TiledMapTileLayer
        assertNotNull(existingLayer)
        
        if (existingLayer != null)
        {
            for (i in 0 until Map.mapHeightInTiles)
            {
                for (j in 0 until Map.mapWidthInTiles)
                {
                    val cell = existingLayer.getCell(j, i)
                    
                    if (cell == null)
                    {
                        fail()
                    }
                }
            }
        }
    }
    
    @Test fun `changeTile within bounds changes tile in existing layer`()
    {
        val x = 2
        val y = 3
        
        Map.newLayer(MapLayer.entity)
        Map.changeTile(MapLayer.entity, x, y, Tiles.hero)
        
        val layer = Map.tiledMap.layers.get(MapLayer.entity.name) as? TiledMapTileLayer
        
        if (layer != null)
        {
            val cell = layer.getCell(x, Map.mapHeightInTiles - y - 1)
            
            if (cell != null)
            {
                assertTrue(cell.tile == Tiles.hero)
            }
            else
            {
                fail()
            }
        }
        else
        {
            fail()
        }
    }
    
    @Test fun `changeTile outside bounds does nothing`()
    {
        Map.newLayer(MapLayer.entity)
        Map.changeTile(MapLayer.entity, 2, 3, Tiles.hero)
    }
    
    @Test fun `changeTile with non-existing layer doesn't add new layer and does nothing`()
    {
        val mapLayer = MapLayer.entity
        
        Map.changeTile(mapLayer, 2, 3, Tiles.hero)
        
        val layer = Map.tiledMap.layers.get(mapLayer.name) as? TiledMapTileLayer
        
        assertNull(layer)
    }
}
