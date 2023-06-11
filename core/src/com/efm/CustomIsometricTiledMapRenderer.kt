package com.efm

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer
import com.badlogic.gdx.math.*

class CustomIsometricTiledMapRenderer : BatchTiledMapRenderer
{
    private var isoTransform : Matrix4? = null
    private var invIsotransform : Matrix4? = null
    private val screenPos = Vector3()
    private val topRight = Vector2()
    private val bottomLeft = Vector2()
    private val topLeft = Vector2()
    private val bottomRight = Vector2()
    
    constructor(map : TiledMap?) : super(map)
    {
        init()
    }
    
    constructor(map : TiledMap?, batch : Batch?) : super(map, batch)
    {
        init()
    }
    
    constructor(map : TiledMap?, unitScale : Float) : super(map, unitScale)
    {
        init()
    }
    
    constructor(map : TiledMap?, unitScale : Float, batch : Batch?) : super(map, unitScale, batch)
    {
        init()
    }
    
    private fun init()
    {
        // create the isometric transform
        isoTransform = Matrix4()
        isoTransform!!.idt()
        
        // isoTransform.translate(0, 32, 0);
        isoTransform!!.scale((Math.sqrt(2.0) / 2.0).toFloat(), (Math.sqrt(2.0) / 4.0).toFloat(), 1.0f)
        isoTransform!!.rotate(0.0f, 0.0f, 1.0f, -45f)
        
        // ... and the inverse matrix
        invIsotransform = Matrix4(isoTransform)
        invIsotransform!!.inv()
    }
    
    private fun translateScreenToIso(vec : Vector2) : Vector3
    {
        screenPos[vec.x, vec.y] = 0f
        screenPos.mul(invIsotransform)
        return screenPos
    }
    
    override fun renderTileLayer(layer : TiledMapTileLayer)
    {
        val batchColor = batch.color
        val color = Color.toFloatBits(batchColor.r, batchColor.g, batchColor.b, batchColor.a * layer.opacity)
        val tileWidth = layer.tileWidth * unitScale
        val tileHeight = layer.tileHeight * unitScale
        val layerOffsetX = layer.renderOffsetX * unitScale
        // offset in tiled is y down, so we flip it
        val layerOffsetY = -layer.renderOffsetY * unitScale
        val halfTileWidth = tileWidth * 0.5f
        val halfTileHeight = tileHeight * 0.5f
        
        // setting up the screen points
        // COL1
        topRight[viewBounds.x + viewBounds.width - layerOffsetX] = viewBounds.y - layerOffsetY
        // COL2
        bottomLeft[viewBounds.x - layerOffsetX] = viewBounds.y + viewBounds.height - layerOffsetY
        // ROW1
        topLeft[viewBounds.x - layerOffsetX] = viewBounds.y - layerOffsetY
        // ROW2
        bottomRight[viewBounds.x + viewBounds.width - layerOffsetX] = viewBounds.y + viewBounds.height - layerOffsetY
        
        // transforming screen coordinates to iso coordinates
        val row1 = (translateScreenToIso(topLeft).y / tileWidth).toInt() - 2
        val row2 = (translateScreenToIso(bottomRight).y / tileWidth).toInt() + 2
        val col1 = (translateScreenToIso(bottomLeft).x / tileWidth).toInt() - 2
        val col2 = (translateScreenToIso(topRight).x / tileWidth).toInt() + 2
        for (row in row2 downTo row1)
        {
            for (col in col1..col2)
            {
                val x = col * halfTileWidth + row * halfTileWidth
                val y = row * halfTileHeight - col * halfTileHeight
                val cell = layer.getCell(col, row) ?: continue
                val tile = cell.tile
                
                if (tile != null)
                {
                    drawTile(
                            tile,
                            cell.flipHorizontally,
                            cell.flipVertically,
                            cell.rotation,
                            layerOffsetX,
                            layerOffsetY,
                            color,
                            x,
                            y
                            )
                }
                
                if (layer.name == MapLayer.entity.name && Animating.isAnimating())
                {
                    val animation = Animating.getCurrentAnimation()
                    
                    drawAnimation(animation, row, col, color, halfTileWidth, halfTileHeight)
                }
            }
        }
    }
    
    fun drawAnimation(
            animation : Animation,
            row : Int,
            col : Int,
            color : Float,
            halfTileWidth : Float,
            halfTileHeight : Float
                     )
    {
        when (animation)
        {
            is Animation.sequence     ->
            {
                val anim = animation.currAnimation
                
                if (anim != null)
                {
                    drawAnimation(
                            anim,
                            row,
                            col,
                            color,
                            halfTileWidth,
                            halfTileHeight
                                 )
                }
            }
            is Animation.simultaneous ->
            {
                for (anim in animation.mutAnimations)
                {
                    drawAnimation(
                            anim,
                            row,
                            col,
                            color,
                            halfTileWidth,
                            halfTileHeight
                                 )
                }
            }
            is Animation.ascendTile   ->
            {
                val animationPosition = Vector2()
                animationPosition.x = animation.on.x.toFloat()
                animationPosition.y = (Map.mapHeightInTiles - animation.on.y - 1).toFloat()
                val animationTile = animation.tile
                val animationTilePosition = Vector2()
                animationTilePosition.x = animationPosition.x * halfTileWidth + animationPosition.y * halfTileWidth
                animationTilePosition.y = animationPosition.y * halfTileHeight - animationPosition.x * halfTileHeight
                val isAnimationTileOnMapTile =
                        (animationPosition.x.toInt() == col && animationPosition.y.toInt() == row)
                val ascendHeight = animation.ascendPercent * animation.heightPercent * halfTileHeight * 4
                animationTilePosition.y += ascendHeight
                
                if (isAnimationTileOnMapTile)
                {
                    drawTile(
                            animationTile,
                            false,
                            false,
                            0,
                            0f,
                            0f,
                            color,
                            animationTilePosition.x,
                            animationTilePosition.y
                            )
                }
            }
            is Animation.descendTile  ->
            {
                val animationPosition = Vector2()
                animationPosition.x = animation.on.x.toFloat()
                animationPosition.y = (Map.mapHeightInTiles - animation.on.y - 1).toFloat()
                val animationTile = animation.tile
                val animationTilePosition = Vector2()
                animationTilePosition.x = animationPosition.x * halfTileWidth + animationPosition.y * halfTileWidth
                animationTilePosition.y = animationPosition.y * halfTileHeight - animationPosition.x * halfTileHeight
                val isAnimationTileOnMapTile =
                        (animationPosition.x.toInt() == col && animationPosition.y.toInt() == row)
                val descendHeight = (1 - animation.descendPercent) * animation.heightPercent * halfTileHeight * 4
                animationTilePosition.y += descendHeight
                
                if (isAnimationTileOnMapTile)
                {
                    drawTile(
                            animationTile,
                            false,
                            false,
                            0,
                            0f,
                            0f,
                            color,
                            animationTilePosition.x,
                            animationTilePosition.y
                            )
                }
            }
            is Animation.moveTile     ->
            {
                val animationPosition = Vector2()
                animationPosition.x = animation.moveTilePosition.x
                animationPosition.y = Map.mapHeightInTiles - animation.moveTilePosition.y - 1
                val animationTile = animation.tile
                val animationTilePosition = Vector2()
                animationTilePosition.x = animationPosition.x * halfTileWidth + animationPosition.y * halfTileWidth
                animationTilePosition.y = animationPosition.y * halfTileHeight - animationPosition.x * halfTileHeight
                val isAnimationTileOnMapTile =
                        (animationPosition.x.toInt() == col && animationPosition.y.toInt() == row)
                
                if (isAnimationTileOnMapTile)
                {
                    drawTile(
                            animationTile,
                            false,
                            false,
                            0,
                            0f,
                            0f,
                            color,
                            animationTilePosition.x,
                            animationTilePosition.y
                            )
                }
            }
            is Animation.showTile     ->
            {
                val animationPosition = Vector2()
                animationPosition.x = animation.where.x.toFloat()
                animationPosition.y = Map.mapHeightInTiles - animation.where.y.toFloat() - 1
                val animationTile = animation.tile
                val animationTilePosition = Vector2()
                animationTilePosition.x = animationPosition.x * halfTileWidth + animationPosition.y * halfTileWidth
                animationTilePosition.y = animationPosition.y * halfTileHeight - animationPosition.x * halfTileHeight
                val isAnimationTileOnMapTile =
                        (animationPosition.x.toInt() == col && animationPosition.y.toInt() == row)
                
                if (isAnimationTileOnMapTile)
                {
                    drawTile(
                            animationTile,
                            false,
                            false,
                            0,
                            0f,
                            0f,
                            color,
                            animationTilePosition.x,
                            animationTilePosition.y
                            )
                }
            }
            else                      ->
            {
            }
        }
    }
    
    fun drawTile(
            tile : TiledMapTile?,
            flipX : Boolean,
            flipY : Boolean,
            rotations : Int,
            layerOffsetX : Float,
            layerOffsetY : Float,
            color : Float,
            x : Float,
            y : Float
                )
    {
        if (tile == null)
        {
            return
        }
        
        val region = tile.textureRegion
        val x1 = x + tile.offsetX * unitScale + layerOffsetX
        val y1 = y + tile.offsetY * unitScale + layerOffsetY
        val x2 = x1 + region.regionWidth * unitScale
        val y2 = y1 + region.regionHeight * unitScale
        val u1 = region.u
        val v1 = region.v2
        val u2 = region.u2
        val v2 = region.v
        vertices[Batch.X1] = x1
        vertices[Batch.Y1] = y1
        vertices[Batch.C1] = color
        vertices[Batch.U1] = u1
        vertices[Batch.V1] = v1
        vertices[Batch.X2] = x1
        vertices[Batch.Y2] = y2
        vertices[Batch.C2] = color
        vertices[Batch.U2] = u1
        vertices[Batch.V2] = v2
        vertices[Batch.X3] = x2
        vertices[Batch.Y3] = y2
        vertices[Batch.C3] = color
        vertices[Batch.U3] = u2
        vertices[Batch.V3] = v2
        vertices[Batch.X4] = x2
        vertices[Batch.Y4] = y1
        vertices[Batch.C4] = color
        vertices[Batch.U4] = u2
        vertices[Batch.V4] = v1
        if (flipX)
        {
            var temp = vertices[Batch.U1]
            vertices[Batch.U1] = vertices[Batch.U3]
            vertices[Batch.U3] = temp
            temp = vertices[Batch.U2]
            vertices[Batch.U2] = vertices[Batch.U4]
            vertices[Batch.U4] = temp
        }
        if (flipY)
        {
            var temp = vertices[Batch.V1]
            vertices[Batch.V1] = vertices[Batch.V3]
            vertices[Batch.V3] = temp
            temp = vertices[Batch.V2]
            vertices[Batch.V2] = vertices[Batch.V4]
            vertices[Batch.V4] = temp
        }
        if (rotations != 0)
        {
            when (rotations)
            {
                TiledMapTileLayer.Cell.ROTATE_90  ->
                {
                    val tempV = vertices[Batch.V1]
                    vertices[Batch.V1] = vertices[Batch.V2]
                    vertices[Batch.V2] = vertices[Batch.V3]
                    vertices[Batch.V3] = vertices[Batch.V4]
                    vertices[Batch.V4] = tempV
                    val tempU = vertices[Batch.U1]
                    vertices[Batch.U1] = vertices[Batch.U2]
                    vertices[Batch.U2] = vertices[Batch.U3]
                    vertices[Batch.U3] = vertices[Batch.U4]
                    vertices[Batch.U4] = tempU
                }
                TiledMapTileLayer.Cell.ROTATE_180 ->
                {
                    var tempU = vertices[Batch.U1]
                    vertices[Batch.U1] = vertices[Batch.U3]
                    vertices[Batch.U3] = tempU
                    tempU = vertices[Batch.U2]
                    vertices[Batch.U2] = vertices[Batch.U4]
                    vertices[Batch.U4] = tempU
                    var tempV = vertices[Batch.V1]
                    vertices[Batch.V1] = vertices[Batch.V3]
                    vertices[Batch.V3] = tempV
                    tempV = vertices[Batch.V2]
                    vertices[Batch.V2] = vertices[Batch.V4]
                    vertices[Batch.V4] = tempV
                }
                TiledMapTileLayer.Cell.ROTATE_270 ->
                {
                    val tempV = vertices[Batch.V1]
                    vertices[Batch.V1] = vertices[Batch.V4]
                    vertices[Batch.V4] = vertices[Batch.V3]
                    vertices[Batch.V3] = vertices[Batch.V2]
                    vertices[Batch.V2] = tempV
                    val tempU = vertices[Batch.U1]
                    vertices[Batch.U1] = vertices[Batch.U4]
                    vertices[Batch.U4] = vertices[Batch.U3]
                    vertices[Batch.U3] = vertices[Batch.U2]
                    vertices[Batch.U2] = tempU
                }
            }
        }
        batch.draw(region.texture, vertices, 0, NUM_VERTICES)
    }
}
