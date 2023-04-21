package com.efm

import com.efm.room.*
import kotlin.math.abs

class Node(val x : Int, val y : Int, val space : Space)
{
    var parent : Node? = null
    val neighbours = mutableListOf<Node>()
    
    var gCost = 0
    var hCost = 0
    val fCost get() = gCost + hCost
}

object Pathfinding
{
    private fun createNodeArrayFromRoom(room : Room) : Array<Array<Node?>>
    {
        var nodeArray = arrayOf<Array<Node?>>()
        
        for (i in 0 until room.heightInSpaces)
        {
            var nodeRow = arrayOf<Node?>()
            
            for (j in 0 until room.widthInSpaces)
            {
                val space = room.getSpace(j, i)
                val node = if (space == null) null else Node(j, i, space)
                nodeRow += node
            }
            
            nodeArray += nodeRow
        }
        
        return nodeArray
    }
    
    private fun addNeighboursToNodeArray(nodeArray : Array<Array<Node?>>)
    {
        val heightInNodes = nodeArray.size
        val widthInNodes = nodeArray.first().size
        
        for (i in 0 until heightInNodes)
        {
            for (j in 0 until widthInNodes)
            {
                val node = getNodeFromNodeArray(j, i, nodeArray)
                
                if (node != null)
                {
                    val upNeighbour = getNodeFromNodeArray(j, i - 1, nodeArray)
                    val rightNeighbour = getNodeFromNodeArray(j + 1, i, nodeArray)
                    val downNeighbour = getNodeFromNodeArray(j, i + 1, nodeArray)
                    val leftNeighbour = getNodeFromNodeArray(j - 1, i, nodeArray)
                    
                    if (upNeighbour != null) node.neighbours.add(upNeighbour)
                    if (rightNeighbour != null) node.neighbours.add(rightNeighbour)
                    if (downNeighbour != null) node.neighbours.add(downNeighbour)
                    if (leftNeighbour != null) node.neighbours.add(leftNeighbour)
                }
            }
        }
    }
    
    private fun isPositionWithinBoundsOfNodeArray(x : Int, y : Int, nodeArray : Array<Array<Node?>>) : Boolean
    {
        val heightInNodes = nodeArray.size
        val widthInNodes = nodeArray.first().size
        
        return x in 0 until widthInNodes && y in 0 until heightInNodes
    }
    
    private fun getNodeFromNodeArray(x : Int, y : Int, nodeArray : Array<Array<Node?>>) : Node?
    {
        return if (isPositionWithinBoundsOfNodeArray(x, y, nodeArray)) nodeArray[y][x] else null
    }
    
    private fun getDistanceBetweenNodes(a : Node, b : Node) : Int
    {
        val yDistance = abs(a.y - b.y)
        val xDistance = abs(a.x - b.x)
        
        return if (yDistance < xDistance) (14 * yDistance + 10 * (xDistance - yDistance)) else (14 * xDistance + 10 * (yDistance - xDistance))
    }
    
    /**
     * Returns path between start and end positions in room.
     * Path is a list of Spaces.
     * Path does not include Spaces on start and end.
     * If there is no path between start and end returns null.
     */
    fun findPathWithGivenRoom(startPosition : RoomPosition, endPosition : RoomPosition, room : Room) : List<Space>?
    {
        val nodeArray = createNodeArrayFromRoom(room)
        addNeighboursToNodeArray(nodeArray)
        
        val startNode = getNodeFromNodeArray(startPosition.x, startPosition.y, nodeArray)
        val endNode = getNodeFromNodeArray(endPosition.x, endPosition.y, nodeArray)
        
        val openNodes = mutableListOf<Node>()
        val closedNodes = mutableListOf<Node>()
        
        val pathNodes = mutableListOf<Node>()
        val pathSpaces = mutableListOf<Space>()
        
        if (startNode == null || endNode == null) return null
        
        openNodes.add(startNode)
        var currentNode : Node = startNode
        var isEndFound = false
        
        while (openNodes.isNotEmpty() && !isEndFound)
        {
            currentNode = openNodes.first()
            
            for (node in openNodes)
            {
                if (currentNode.fCost > node.fCost)
                {
                    currentNode = node
                }
                else if (currentNode.fCost == node.fCost)
                {
                    if (currentNode.hCost > node.hCost)
                    {
                        currentNode = node
                    }
                }
            }
            
            openNodes.remove(currentNode)
            closedNodes.add(currentNode)
            
            for (node in currentNode.neighbours)
            {
                if (node == endNode)
                {
                    isEndFound = true
                    break
                }
                
                if (node !in closedNodes && node.space.isTraversable())
                {
                    val distance = getDistanceBetweenNodes(currentNode, node)
                    
                    if (currentNode.gCost + distance < node.gCost || node !in openNodes)
                    {
                        node.gCost = currentNode.gCost + distance
                        node.hCost = distance
                        node.parent = currentNode
                        
                        if (node !in openNodes)
                        {
                            openNodes.add(node)
                        }
                    }
                }
                
            }
        }
        
        if (!isEndFound) return null
        
        var pathNode = currentNode
        while (pathNode != startNode)
        {
            pathNodes.add(pathNode)
            pathNode = pathNode.parent!!
        }
        pathNodes.reverse()
        
        for (node in pathNodes)
        {
            pathSpaces.add(node.space)
        }
        
        return pathSpaces
    }
}
