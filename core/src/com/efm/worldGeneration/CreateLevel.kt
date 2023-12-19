import com.efm.addWalls
import com.efm.entities.walls.WallStyle
import com.efm.level.Level
import com.efm.level.World
import com.efm.room.*
import kotlin.random.Random

class Node(
        val x1: Int,
        val y1: Int,
        val x2: Int,
        val y2: Int,
        var number: Int
          ) {
    val size: Int
        get() = (x2 - x1) * (y2 - y1)
    
    val children: MutableList<Node> = mutableListOf()
}

fun binarySpacePartition(
        node: Node,
        minRoomSize: Int,
        roomNumber: Int,
        bufferSize: Int = 2
                        ) {
    val maxSplitHeight = node.y2 - maxOf(node.y1, node.y1 + minRoomSize) - minRoomSize
    val maxSplitWidth = node.x2 - maxOf(node.x1, node.x1 + minRoomSize) - minRoomSize
    
    if (maxSplitWidth <= 0 && maxSplitHeight <= 0) {
        return
    }
    
    val whatFirst = listOf(true, false).random()
    if (whatFirst) {
        if (maxSplitWidth > 0) {
            val splitWidth = (node.x1 + minRoomSize..node.x2 - minRoomSize).random()
            node.children.add(Node(node.x1, node.y1, splitWidth - 1, node.y2, roomNumber))
            node.children.add(Node(splitWidth + 1, node.y1, node.x2, node.y2, roomNumber))
        }
        
        if (maxSplitHeight > 0) {
            val splitHeight = (node.y1 + minRoomSize..node.y2 - minRoomSize).random()
            node.children.add(Node(node.x1, node.y1, node.x2, splitHeight, roomNumber))
            node.children.add(Node(node.x1, splitHeight + 1, node.x2, node.y2, roomNumber))
        }
    } else {
        if (maxSplitHeight > 0) {
            val splitHeight = (node.y1 + minRoomSize..node.y2 - minRoomSize).random()
            node.children.add(Node(node.x1, node.y1, node.x2, splitHeight, roomNumber))
            node.children.add(Node(node.x1, splitHeight + 1, node.x2, node.y2, roomNumber))
        }
        
        if (maxSplitWidth > 0) {
            val splitWidth = (node.x1 + minRoomSize..node.x2 - minRoomSize).random()
            node.children.add(Node(node.x1, node.y1, splitWidth - 1, node.y2, roomNumber))
            node.children.add(Node(splitWidth + 1, node.y1, node.x2, node.y2, roomNumber))
        }
    }
    
    node.children.forEach {
        binarySpacePartition(it, minRoomSize, bufferSize, roomNumber)
    }
}

fun createRooms(node: Node, roomData: Array<IntArray>, bufferSize: Int = 2) {
    if (node.children.isEmpty()) {
        val roomWidth = node.x2 - node.x1 + 1 - 2 * bufferSize
        val roomHeight = node.y2 - node.y1 + 1 - 2 * bufferSize
        val roomX = node.x1 + bufferSize
        val roomY = node.y1 + bufferSize
        
        if (roomData.sliceArray(roomX until roomX + roomWidth)
                        .any { it -> it.sliceArray(roomY until roomY + roomHeight).any { it != 0 } }) {
            return
        }
        
        for (x in roomX until roomX + roomWidth) {
            for (y in roomY until roomY + roomHeight) {
                roomData[x][y] = node.number
            }
        }
    }
    
    node.children.forEach {
        createRooms(it, roomData, bufferSize)
    }
}

fun filterRoomsByPerlinThreshold(node: Node, perlinMap: Array<DoubleArray>, threshold: Double) {
    val averagePerlinValue = calculateAveragePerlinValue(node, perlinMap)
    if (averagePerlinValue < threshold) {
        node.number = 0
    }
    
    if (node.children.isNotEmpty()) {
        node.children.forEach {
            filterRoomsByPerlinThreshold(it, perlinMap, threshold)
        }
    }
}

fun calculateAveragePerlinValue(room: Node, perlinMap: Array<DoubleArray>): Double {
    val roomWidth = room.x2 - room.x1 + 1
    val roomHeight = room.y2 - room.y1 + 1
    
    var totalPerlinValue = 0.0
    
    for (x in room.x1..room.x2) {
        for (y in room.y1..room.y2) {
            totalPerlinValue += perlinMap[x][y]
        }
    }
    
    return totalPerlinValue / (roomWidth * roomHeight)
}

fun displayMap(roomData: Array<IntArray>) {
    for (element in roomData) {
        for (j in 0 until roomData[0].size) {
            print("${element[j]} ")
        }
        println()  // Move to the next line after each row
    }
}

fun World.createProcGenWorld() {
    val mapWidth = 80
    val mapHeight = 80
    val minRoomSize = 10
    val bufferSize = 1
    
    val roomNumber = 1
    val scale = 2
    val seed = Random.nextInt(0, 100)
    
    val root = Node(0, 0, mapWidth - 1, mapHeight - 1, roomNumber)
    
    binarySpacePartition(root, minRoomSize, roomNumber, bufferSize)
    var roomData = Array(mapWidth) { IntArray(mapHeight) { 0 } }
    
    // Generate Perlin Noise map
    val perlinMap = generatePerlinNoiseMap(mapWidth, mapHeight, scale, seed)
    val perlinThreshold = 0.15
    
    // Filter rooms based on Perlin Noise
    filterRoomsByPerlinThreshold(root, perlinMap, perlinThreshold)
    
    // Create rooms
    createRooms(root, roomData, bufferSize)
    
    // Merge rooms
    mergeRooms(roomData)
    
    // Now 'rooms' is a list of Room objects with their x and y coordinates
    roomData = labelRooms(roomData)
    val patchCenters = findPatchCenters(roomData)
    println("Patch Centers:")
    patchCenters.forEach { println("Patch ${it.x}, ${it.y}") }
    
    val min_room_val = 1
    val max_room_val = getMaxValInMatrix(roomData)
    val level = Level("1")
    var rooms = mutableListOf<Room>()
    for (i in min_room_val..max_room_val)
    {
        val x1 = getRoomX1(i, roomData)
        val x2 = getRoomX2(i, roomData)
        val y1 = getRoomY1(i, roomData)
        val y2 = getRoomY2(i, roomData)
        val height = x2 - x1
        val width = y2 - y1
        val room = Room(i.toString(), height, width)
        val roomBasesArray = getMatrixBasedOnCoordinates(roomData, x1, x2, y1, y2, i)
        //val roomBasesArray = Array(width) { IntArray(height) { 0 } }
        //fillBaseArrayBasedOnRoomData(roomBasesArray, roomData, i ,x1, x2, y1, y2)
        for (y in roomBasesArray.indices)
        {
            for (x in roomBasesArray[y].indices)
            {
                val baseNumber = roomBasesArray[y][x]
                if (baseNumber != 0)
                {
                    val base = Base.getBase(baseNumber)
                    room.changeBaseAt(base, x, y)
                }
                else
                {
                    // delete base and space
                    room.changeBaseAt(null, x, y)
                    room.deleteSpaceAt(x, y)
                }
            }
        }
    
    
    
        room.addWalls(WallStyle.brickRedDark)
        level.addRoom(room)
        rooms.add(room)
    }
    level.startingRoom = rooms[0]
    level.startingPosition.set(RoomPosition(2, 2))
    addLevel(level)
    //displayMap(roomData)
}

fun getMatrixBasedOnCoordinates(roomData : Array<IntArray>, x1 : Int, x2 : Int, y1 : Int, y2 : Int, i : Int) : Array<IntArray>
{
    var result = Array(x2- x1) { IntArray(y2 - y1) { 0 } }
    for (x in x1 until  x2)
    {
        for (y in y1 until  y2)
        {
            if (roomData[x][y] == i)
            {
                print("x1 : $x1 x : $x y1 : $y1 y : $y")
                result[x - x1][y - y1] = i
            }
        }
    }
    return result
}

fun fillBaseArrayBasedOnRoomData(roomBasesArray : Array<IntArray>, roomData : Array<IntArray>, i : Int, x1 : Int, x2 : Int, y1 : Int, y2 : Int)
{
    var relativeX = 0
    var relativeY = 0
    for (x in x1 until  x2)
    {
        for (y in y1 until  y2)
        {
            if (roomData[x][y] == i)
            {
                roomBasesArray[relativeX][relativeY] = i
            }
            relativeY += 1
        }
        relativeY = 0
        relativeX += 1
    }
}

fun getRoomY2(i : Int, roomData : Array<IntArray>) : Int
{
    var highestY = 0
    for (x in roomData.indices)
    {
        for (y in 0 until  roomData[0].size)
        {
            if (roomData[x][y] == i && x > highestY)
            {
                highestY = x
            }
        }
    }
    return highestY
}

fun getRoomX2(i : Int, roomData : Array<IntArray>) : Int
{
    var highestX = 0
    for (element in roomData)
    {
        for (y in 0 until  roomData[0].size)
        {
            if (element[y] == i && y > highestX)
            {
                highestX = y
            }
        }
    }
    return highestX
}

fun getRoomY1(i : Int, roomData : Array<IntArray>) : Int
{
    var lowestY = 1000
    for (x in roomData.indices)
    {
        for (y in 0 until roomData[0].size)
        {
            if (roomData[x][y] == i && x < lowestY)
            {
                lowestY = x
            }
        }
    }
    return lowestY
}

fun getRoomX1(i : Int, roomData : Array<IntArray>) : Int
{
    var lowestX = 1000
    for (element in roomData)
    {
        for (y in 0 until  roomData[0].size)
        {
            if (element[y] == i && y < lowestX)
            {
                lowestX = y
            }
        }
    }
    return lowestX
}

fun getMaxValInMatrix(roomData : Array<IntArray>) : Int
{
    var max_found = 1
    for (i in roomData)
    {
        for (j in i)
        {
            if (j > max_found)
            {
                max_found = j
            }
        }
    }
    return max_found
}

fun generatePerlinNoiseMap(width: Int, height: Int, scale: Int, seed: Int): Array<DoubleArray> {
    val random = Random(seed)
    var permutationTable = (0 until 256).shuffled(random).toIntArray()
    permutationTable += permutationTable
    
    val perlinMap = Array(width) { DoubleArray(height) }
    
    for (i in 0 until width) {
        for (j in 0 until height) {
            val x = i.toDouble() / scale
            val y = j.toDouble() / scale
            perlinMap[i][j] = perlinNoise(x, y, permutationTable)
        }
    }
    
    return perlinMap
}

data class PatchCenter(val x: Double, val y: Double, val value: Int)

fun findPatchCenters(roomData: Array<IntArray>): List<PatchCenter> {
    val patchCenters = mutableListOf<PatchCenter>()
    
    for (i in roomData.indices) {
        for (j in 0 until roomData[0].size) {
            if ((roomData[i][j] != 0) && (!doPatchesContainThisValueAlready(patchCenters, roomData[i][j]))) {
                val patchCenter = calculatePatchCenter(i, j, roomData)
                patchCenters.add(patchCenter)
            }
        }
    }
    
    return patchCenters
}

fun doPatchesContainThisValueAlready(patchCenters : MutableList<PatchCenter>, i : Int) : Boolean
{
    for (element in patchCenters) {
        if (element.value == i) {
            return true
        }
    }
    return false
    
}

fun calculatePatchCenter(startX: Int, startY: Int, roomData: Array<IntArray>): PatchCenter
{
    val patchValues = mutableListOf<Pair<Int, Int>>()
    
    for (i in startX until roomData.size)
    {
        for (j in startY until roomData[0].size)
        {
            if (roomData[i][j] == roomData[startX][startY])
            {
                patchValues.add(Pair(i, j))
            }
        }
    }
    
    // Calculate the average x and y coordinates
    val centerX = patchValues.map { it.first }.average()
    val centerY = patchValues.map { it.second }.average()
    
    return PatchCenter(centerX, centerY, roomData[startX][startY])
}


fun perlinNoise(x: Double, y: Double, permutationTable: IntArray): Double {
    val X = (x.toInt() and 255).toByte().toInt()
    val Y = (y.toInt() and 255).toByte().toInt()
    
    var u = fade(x)
    var v = fade(y)
    
    val a = permutationTable[X] + Y
    val aa = permutationTable[a]
    val ab = permutationTable[a + 1]
    val b = permutationTable[X + 1] + Y
    val ba = permutationTable[b]
    val bb = permutationTable[b + 1]
    
    return lerp(v, lerp(u, grad(aa, x), grad(ba, x - 1)), lerp(u, grad(ab, x - 1), grad(bb, x - 1)))
}

fun fade(t: Double): Double = t * t * t * (t * (t * 6 - 15) + 10)

fun lerp(t: Double, a: Double, b: Double): Double = a + t * (b - a)

fun grad(hashValue: Int, x: Double): Double {
    val h = hashValue and 15
    var grad = 1.0 + (h and 7)  // Gradient value 1-8
    if (h and 8 != 0) {
        grad = -grad  // Randomly invert half of the gradients
    }
    return grad * x
}

fun mergeRooms(roomData: Array<IntArray>) {
    val filterSize = 3
    for (i in 0 until roomData.size - filterSize) {
        for (j in 0 until roomData[0].size - filterSize) {
            val horizontal = roomData[i].slice(j until j + filterSize)
            val vertical = roomData.slice(i until i + filterSize).map { it[j] }
            if (horizontal[0] != 0 && horizontal.last() != 0 && !horizontal.subList(1, filterSize).all { it != 0 }) {
                roomData[i].copyInto(roomData[i], j, 0, filterSize)
            }
            if (vertical[0] != 0 && vertical.last() != 0 && !vertical.subList(1, filterSize - 1).all { it != 0 }) {
                for (k in i until i + filterSize) {
                    roomData[k][j] = vertical[0]
                }
            }
        }
    }
    
    for (i in roomData.size - filterSize until roomData.size) {
        for (j in roomData[0].size - filterSize until roomData[0].size) {
            val horizontal = roomData.map { it[j] }.slice(i - filterSize until i)
            val vertical = roomData[i - filterSize].slice(j until j + filterSize - filterSize + 1)
            if (horizontal[0] != 0 && horizontal.last() != 0 && !horizontal.subList(1, filterSize).all { it != 0 }) {
                for (k in i until i + filterSize) {
                    roomData[k][j] = horizontal[0]
                }
            }
            if (vertical[0] != 0 && vertical.last() != 0 && !vertical.subList(1, filterSize - filterSize + 1).all { it != 0 }) {
                roomData[i].copyInto(roomData[i], j, 0, filterSize)
            }
        }
    }
}


fun labelRooms(roomData: Array<IntArray>): Array<IntArray> {
    val labeledData = Array(roomData.size) { IntArray(roomData[0].size) }
    var currentLabel = 1
    
    for (i in roomData.indices) {
        for (j in roomData[0].indices) {
            if (roomData[i][j] == 1 && labeledData[i][j] == 0) {
                dfs(i, j, roomData, labeledData, currentLabel)
                currentLabel++
            }
        }
    }
    
    return labeledData
}

fun dfs(x: Int, y: Int, roomData: Array<IntArray>, labeledData: Array<IntArray>, label: Int) {
    if (x in 0 until roomData.size &&
            y in 0 until roomData[0].size &&
            roomData[x][y] == 1 &&
            labeledData[x][y] == 0
    ) {
        labeledData[x][y] = label
        dfs(x + 1, y, roomData, labeledData, label)
        dfs(x - 1, y, roomData, labeledData, label)
        dfs(x, y + 1, roomData, labeledData, label)
        dfs(x, y - 1, roomData, labeledData, label)
    }
}
