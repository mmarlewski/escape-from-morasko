import com.efm.*
import com.efm.entities.walls.Wall
import com.efm.entities.walls.WallStyle
import com.efm.entity.*
import com.efm.exit.*
import com.efm.level.Level
import com.efm.level.World
import com.efm.room.*
import com.efm.stackableSelfItems.Apple
import com.efm.worldGeneration.LevelTheme
import com.efm.worldGeneration.LevelThemes
import kotlin.math.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextFloat
import kotlin.random.Random.Default.nextInt

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

fun World.createProcGenWorld()
{
    val levels = mutableListOf<Level>()
    for (i in 1..4)
    {
        levels.add(createLevel(i))
    }
    for (level in levels)
    {
        addLevel(level)
    }
}

fun createLevel(levelNumber : Int) : Level
{
    val mapWidth = 60
    val mapHeight = 60
    val minRoomSize = 8
    val bufferSize = 1
    
    val roomNumber = 1
    val scale = 1.7
    val seed = nextInt(0, 100)
    
    val root = Node(0, 0, mapWidth - 1, mapHeight - 1, roomNumber)
    
    binarySpacePartition(root, minRoomSize, roomNumber, bufferSize)
    var roomData = Array(mapWidth) { IntArray(mapHeight) { 0 } }
    
    // Generate Perlin Noise map
    val perlinMap = generatePerlinNoiseMap(mapWidth, mapHeight, scale, seed)
    val perlinThreshold = -2.0
    
    // Filter rooms based on Perlin Noise
    filterRoomsByPerlinThreshold(root, perlinMap, perlinThreshold)
    
    // Create rooms
    createRooms(root, roomData, bufferSize)
    
    // Merge rooms
    mergeRooms(roomData)
    
    roomData = labelRooms(roomData)
    val patchCenters = findPatchCenters(roomData)
    
    val possibleRoomsValues = getPossibleRoomValues(roomData)
    val level = Level(levelNumber.toString())
    val levelTheme = LevelThemes.values().random().theme
    val allowedBases = levelTheme.bases
    val allowedWalls = levelTheme.walls
    val rooms = mutableListOf<Room>()
    for (i in possibleRoomsValues)
    {
        val x1 = getRoomX1(i, roomData)
        val x2 = getRoomX2(i, roomData)
        val y1 = getRoomY1(i, roomData)
        val y2 = getRoomY2(i, roomData)
        val height = x2 - x1
        val width = y2 - y1
        val room = Room(i.toString(), height, width)
        val roomBasesArray = getMatrixBasedOnCoordinates(roomData, x1, x2, y1, y2, i)
        val basesForThisRoom = getRandomBasesThatFitTogether(allowedBases)
        for (y in roomBasesArray.indices)
        {
            for (x in roomBasesArray[y].indices)
            {
                val baseNumber = roomBasesArray[y][x]
                if (baseNumber != 0)
                {
                    room.changeBaseAt(basesForThisRoom.random(), y, x)
                }
                else
                {
                    // delete base and space
                    room.changeBaseAt(null, y, x)
                    room.deleteSpaceAt(y, x)
                }
            }
        }
        room.updateSpaceList()
        room.updateSpacesEntities()
        val spacesWithoutBase = mutableListOf<Space>()
        for (space in room.getSpaces())
        {
            if (space.getBase() == null)
            {
                spacesWithoutBase.add(space)
            }
        }
        for (space in spacesWithoutBase)
        {
            room.deleteSpaceAt(space.position.x, space.position.y)
        }
        room.updateSpaceList()
        room.updateSpacesEntities()
        randomizeBasesForARoom(room, allowedBases, roomBasesArray, 4)
        room.addWalls(allowedWalls.random())
        room.updateSpaceList()
        room.updateSpacesEntities()
        
        level.addRoom(room)
        rooms.add(room)
    }
    //add Passages
    createPassages(rooms, patchCenters, possibleRoomsValues, level)
    for (room in rooms)
    {
        val x1 = getRoomX1(room.name.toInt(), roomData)
        val x2 = getRoomX2(room.name.toInt(), roomData)
        val y1 = getRoomY1(room.name.toInt(), roomData)
        val y2 = getRoomY2(room.name.toInt(), roomData)
        val roomBasesArray = getMatrixBasedOnCoordinates(roomData, x1, x2, y1, y2, room.name.toInt())
        randomizeBasesForARoomAndAwayFromDoors(room, listOf(Base.water), roomBasesArray, 3)
        randomizeBasesForARoomAndAwayFromDoors(room, listOf(Base.lava), roomBasesArray, 1)
        spawnEnemiesInTheRoom(room, levelTheme, levelNumber)
        spawnPropsInTheRoom(room)
    }
    val bossRoom = Room("boss_room", 20, 20)
    for (patch in patchCenters)
    {
        val possibleFurthestRoom = findRoomByName(findFurthestPatch(patchCenters, patch)?.value.toString(), rooms)
        if (possibleFurthestRoom != null)
        {
            createBossRoom(possibleFurthestRoom, level, bossRoom, levelNumber)
            break
        }
    }
    level.addRoom(bossRoom)
    rooms.add(bossRoom)
    level.startingRoom = rooms.first()
    addChestToStartingRoom(rooms.first())
    level.startingPosition.set(RoomPosition(2, 2))
    return level
}

fun randomizeBasesForARoomAndAwayFromDoors(room : Room, allowedBases : List<Base>, roomBasesArray : Array<IntArray>, repetitions : Int)
{
    for (i in 1..repetitions)
    {
        var randomY = roomBasesArray.indices.random()
        var randomX = roomBasesArray[randomY].indices.random()
        var pickedPoint = roomBasesArray[randomY][randomX]
        while (pickedPoint == 0)
        {
            randomY = roomBasesArray.indices.random()
            randomX = roomBasesArray[randomY].indices.random()
            pickedPoint = roomBasesArray[randomY][randomX]
        }
        val randomBase = allowedBases.random()
        val randomSpace = room.getSpace(RoomPosition(randomX, randomY))
        if (randomSpace != null && checkIfNoOtherPassagesNearby(room, randomSpace))
        {
            if (randomSpace.getEntity() == null)
            {
                if (randomBase == Base.water)
                {
                    recursivelySpreadBaseFromPoint(room, listOf(randomBase), randomX, randomY, 14, 3)
                }
                else
                {
                    recursivelySpreadBaseFromPoint(room, listOf(randomBase), randomX, randomY, 16, 2)
                }
            }
        }
    }
}

fun addChestToStartingRoom(first : Room)
{
    val chest = Chest()
    chest.addItem(Apple())
    val position = findRandomFreePositionInRoom(first)
    if (position != null)
    {
        first.addEntityAt(chest, position)
    }
}

fun spawnEnemiesInTheRoom(room : Room, theme : LevelTheme, levelNumber : Int)
{
    if (room.name != "1")
    {
        val roomSize = getRoomSize(room)
        val amountOfEnemies = nextInt((roomSize / 80), (roomSize / 40) + 1)
        for (i in 0..amountOfEnemies)
        {
            val positionToSpawnAt = findRandomFreePositionInRoom(room)
            if (positionToSpawnAt != null && room.getSpace(positionToSpawnAt)
                            ?.let { checkIfNoOtherPassagesNearby(room, it) } == true
            )
            {
                val enemy =
                        if (nextFloat() < 0.8)
                            theme.commonEnemies.random().new()
                        else
                            theme.rareEnemies.random().new()
                room.addEntityAt(enemy, positionToSpawnAt)
            }
            
        }
    }
}

fun spawnPropsInTheRoom(room : Room)
{
    val roomSize = getRoomSize(room)
    val amountOfProps = nextInt((roomSize / 60), (roomSize / 30) + 1)
    for (i in 0..amountOfProps)
    {
        val positionToSpawnAt = findRandomFreePositionInRoomPreferablyNearWallsAndOnEdge(room)
        if (positionToSpawnAt != null && room.getSpace(positionToSpawnAt)
                        ?.let { checkIfNoOtherPassagesNearby(room, it) } == true
        )
        {
            val prop = Prop(PropStyle.values().random())
            room.addEntityAt(prop, positionToSpawnAt)
        }
    }
}

fun findRandomFreePositionInRoom(room : Room) : RoomPosition?
{
    var space = room.getSpaces().random()
    var breakCounter = 0
    while (space.getEntity() != null || space.getBase() == null || listOf(Base.lava, Base.water).contains(space.getBase()))
    {
        space = room.getSpaces().random()
        if (breakCounter == 20)
        {
            break
        }
        breakCounter += 1
    }
    if (breakCounter == 20)
    {
        return null
    }
    return space.position
}

fun findRandomFreePositionInRoomPreferablyNearWallsAndOnEdge(room : Room) : RoomPosition?
{
    val spacesNearWalls = room.getSpaces().filter { it.getEntity() !is Wall && room.spaceIsAdjacentToWall(it) }
    val spacesOnEdge = room.edgeSpaces()
    
    fun chooseSpace() : Space
    {
        val chance = nextFloat()
        return if (chance < 0.15) spacesNearWalls.random()
        else if (chance < 0.5) spacesOnEdge.random()
        else room.getSpaces().random()
    }
    
    var space = chooseSpace()
    var breakCounter = 0
    while (space.getEntity() != null || space.getBase() == null || listOf(Base.lava, Base.water).contains(space.getBase()))
    {
        space = chooseSpace()
        if (breakCounter == 20) break
        breakCounter += 1
    }
    
    return if (breakCounter == 20) null
    else space.position
}

fun getRoomSize(room : Room) : Int
{
    return room.getSpaces().size
}

/**
 * Chooses a random Base from allowedBases and returns a list of Bases that look good with it.
 */
fun getRandomBasesThatFitTogether(allowedBases : List<Base>) : List<Base>
{
    val randomBase = allowedBases.random()
    var randomBases = listOf(randomBase)
    if (Base.woodTiles.contains(randomBase))
    {
        randomBases = Base.woodTiles
    }
    else if (Base.grassTiles.contains(randomBase))
    {
        randomBases = Base.grassTiles
    }
    else if (Base.tiledTiles.contains(randomBase))
    {
        randomBases = Base.tiledTiles
    }
    else if (Base.tiledTilesWithBlood.contains(randomBase))
    {
        randomBases = Base.tiledTilesWithBlood
    }
    return randomBases
}

fun randomizeBasesForARoom(room : Room, allowedBases : List<Base>, roomBasesArray : Array<IntArray>, repetitions : Int)
{
    for (i in 1..repetitions)
    {
        var randomY = roomBasesArray.indices.random()
        var randomX = roomBasesArray[randomY].indices.random()
        var pickedPoint = roomBasesArray[randomY][randomX]
        while (pickedPoint == 0)
        {
            randomY = roomBasesArray.indices.random()
            randomX = roomBasesArray[randomY].indices.random()
            pickedPoint = roomBasesArray[randomY][randomX]
        }
        var randomBases = getRandomBasesThatFitTogether(allowedBases)
        val randomSpace = room.getSpace(RoomPosition(randomX, randomY))
        if (randomSpace != null)
        {
            if (Base.woodTiles.contains(randomSpace.getBase()))
            {
                randomBases = Base.woodTiles
            }
            else if (Base.grassTiles.contains(randomSpace.getBase()))
            {
                randomBases = Base.grassTiles
            }
            else if (Base.tiledTiles.contains(randomSpace.getBase()))
            {
                randomBases = Base.tiledTiles
            }
            else if (Base.tiledTilesWithBlood.contains(randomSpace.getBase()))
            {
                randomBases = Base.tiledTilesWithBlood
            }
            recursivelySpreadBaseFromPoint(room, randomBases, randomX, randomY, 13, 4)
        }
        // recursivelySpreadBaseFromPoint(room, randomBases, randomX, randomY, 4, 8)
    }
}

fun recursivelySpreadBaseFromPoint(
        room : Room,
        bases : List<Base>,
        x : Int,
        y : Int,
        probabilityThreshold : Int,
        depth : Int
                                  )
{
    if (depth <= 0)
    {
        if (room.getSpace(x, y) != null && room.getSpace(x, y)?.getEntity() !is Wall && room.getSpace(x, y)
                        ?.getEntity() !is Exit
        ) room.changeBaseAt(bases.random(), x, y)
        return
    }
    val space = room.getSpace(x, y)
    if (space != null)
    {
        if (checkIfNoOtherPassagesNearby(room, space))
        {
            return
        }
    }
    val directions = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1), Pair(1, 1), Pair(-1, 1), Pair(1, -1), Pair(-1, -1))
    
    for ((dx, dy) in directions) {
        if (nextInt(0, 20) > probabilityThreshold) {
            recursivelySpreadBaseFromPoint(room, bases, x + dx, y + dy, probabilityThreshold, depth - 1)
        }
    }
}

fun createBossRoom(furthestRoom : Room, level : Level, bossRoom : Room, levelNumber : Int)
{
    for (width in 0 until  bossRoom.widthInSpaces)
    {
        for (height in 0 until bossRoom.heightInSpaces)
        {
            bossRoom.changeBaseAt(Base.grassStoneDrained1, height, width)
        }
    }
    bossRoom.addWalls(WallStyle.brickRedDark)
    val positionToPlaceLevelExit = findSpaceInBottomLeftCorner(bossRoom, "horizontal", false)
    if (levelNumber < 4)
    {
        val levelExit = LevelExit(
                positionToPlaceLevelExit,
                Direction4.right,
                (levelNumber + 1).toString(),
                ExitStyle.stone,
                activeWhenNoEnemiesAreInRoom = true
                                 )
        bossRoom.addEntityAt(levelExit, positionToPlaceLevelExit)
    }
    addBossRoomPassage(
            level,
            furthestRoom.name,
            findSpaceInBottomRightCorner(furthestRoom, "vertical", false),
            Direction4.up, "boss_room",
            findSpaceInTopLeftCorner(bossRoom, "vertical", false),
                      )
}

fun getPossibleRoomValues(roomData : Array<IntArray>) : List<Int>
{
    val possibleRoomValues = mutableListOf<Int>()
    for (i in roomData)
    {
        for (j in i)
        {
            if (j != 0 && j !in possibleRoomValues)
            {
                possibleRoomValues.add(j)
            }
        }
    }
    return possibleRoomValues
}

fun createPassages(
        rooms : MutableList<Room>,
        patchCenters : List<PatchCenter>,
        possibleRoomsValues : List<Int>,
        level : Level
                  )
{
    val connections = mutableListOf<Int>()
    var i = patchCenters[0]
    while (!areAllRoomsConnected(connections, possibleRoomsValues))
    {
        val currentRoom = findRoomByName(i.value.toString(), rooms)
        val closestPatch = findClosestPatchThatIsNotInConnections(connections, patchCenters, i)
        if (closestPatch != null && currentRoom != null)
        {
            val closestPatchValue = closestPatch.value
            val closestRoomName = closestPatchValue.toString()
            val closestRoom = findRoomByName(closestRoomName, rooms)
            if (closestRoom != null)
            {
                createPassagesBasedOnRelativePosition(i, closestPatch, currentRoom, closestRoom, level)
                addValuesToConnectionsIfNotPresent(connections, i.value, closestPatchValue)
            }
            i = closestPatch
        }
        else
        {
            continue
        }
    }
    
}

fun addValuesToConnectionsIfNotPresent(connections : MutableList<Int>, i : Int, closestPatchValue : Int)
{
    if (i !in connections)
    {
        connections.add(i)
    }
    if (closestPatchValue !in connections)
    {
        connections.add(closestPatchValue)
    }
    
}

fun createPassagesBasedOnRelativePosition(
        currentRoomVal : PatchCenter,
        closestRoomVal : PatchCenter,
        currentRoom : Room,
        closestRoom : Room,
        level : Level
                                         )
{
    if (currentRoomVal.x > closestRoomVal.x)
    {
        if (currentRoomVal.y > closestRoomVal.y)
        {
            //top-left for curr room, bott-right for closest
            val trueFalse = intArrayOf(0, 1)
            if (trueFalse.random() == 1)
            {
                //horizontal
                val currRoomPassageSpace = findSpaceInTopLeftCorner(currentRoom, "horizontal", false)
                val closestRoomPassageSpace = findSpaceInBottomRightCorner(closestRoom, "horizontal", false)
                addRoomPassage(level, currentRoom.name, currRoomPassageSpace, Direction4.right, closestRoom.name, closestRoomPassageSpace, ExitStyle.metal)
                
            }
            else
            {
                //vertical
                val currRoomPassageSpace = findSpaceInTopLeftCorner(currentRoom, "vertical", false)
                val closestRoomPassageSpace = findSpaceInBottomRightCorner(closestRoom, "vertical", false)
                addRoomPassage(level, currentRoom.name, currRoomPassageSpace, Direction4.down, closestRoom.name, closestRoomPassageSpace, ExitStyle.metal)
            }
            print("Passage between rooms : $currentRoomVal (top-left)  and $closestRoomVal (bott-right)\n")
        }
        else
        {
            //top-right curr, bott-left closest
            val trueFalse = intArrayOf(0, 1)
            if (trueFalse.random() == 1)
            {
                //horizontal
                val currRoomPassageSpace = findSpaceInTopRightCorner(currentRoom, "horizontal", false)
                val closestRoomPassageSpace = findSpaceInBottomLeftCorner(closestRoom, "horizontal", false)
                addRoomPassage(level, currentRoom.name, currRoomPassageSpace, Direction4.left, closestRoom.name, closestRoomPassageSpace, ExitStyle.metal)
            }
            else
            {
                //vertical
                val currRoomPassageSpace = findSpaceInTopRightCorner(currentRoom, "vertical", false)
                val closestRoomPassageSpace = findSpaceInBottomLeftCorner(closestRoom, "vertical", false)
                addRoomPassage(level, currentRoom.name, currRoomPassageSpace, Direction4.down, closestRoom.name, closestRoomPassageSpace, ExitStyle.metal)
            }
            print("Passage between rooms : $currentRoomVal (top-right)  and $closestRoomVal (bott-left)\n")
        }
    }
    else
    {
        if (currentRoomVal.y > closestRoomVal.y)
        {
            //bott-left curr, top-right closest
            val trueFalse = intArrayOf(0, 1)
            if (trueFalse.random() == 1)
            {
                //horizontal
                val currRoomPassageSpace = findSpaceInBottomLeftCorner(currentRoom, "horizontal", false)
                val closestRoomPassageSpace = findSpaceInTopRightCorner(closestRoom, "horizontal", false)
                addRoomPassage(level, currentRoom.name, currRoomPassageSpace, Direction4.right, closestRoom.name, closestRoomPassageSpace, ExitStyle.metal)
            }
            else
            {
                //vertical
                val currRoomPassageSpace = findSpaceInBottomLeftCorner(currentRoom, "vertical", false)
                val closestRoomPassageSpace = findSpaceInTopRightCorner(closestRoom, "vertical", false)
                addRoomPassage(level, currentRoom.name, currRoomPassageSpace, Direction4.up, closestRoom.name, closestRoomPassageSpace, ExitStyle.metal)
            }
            print("Passage between rooms : $currentRoomVal (bott-left)  and $closestRoomVal (top-right)\n")
        }
        else
        {
            //bott-right curr, top-left closest
            val trueFalse = intArrayOf(0, 1)
            if (trueFalse.random() == 1)
            {
                //horizontal
                val currRoomPassageSpace = findSpaceInBottomRightCorner(currentRoom, "horizontal", false)
                val closestRoomPassageSpace = findSpaceInTopLeftCorner(closestRoom, "horizontal", false)
                addRoomPassage(level, currentRoom.name, currRoomPassageSpace, Direction4.left, closestRoom.name, closestRoomPassageSpace, ExitStyle.metal)
            }
            else
            {
                //vertical
                val currRoomPassageSpace = findSpaceInBottomRightCorner(currentRoom, "vertical", false)
                val closestRoomPassageSpace = findSpaceInTopLeftCorner(closestRoom, "vertical", false)
                addRoomPassage(level, currentRoom.name, currRoomPassageSpace, Direction4.up, closestRoom.name, closestRoomPassageSpace, ExitStyle.metal)
            }
            print("Passage between rooms : $currentRoomVal (bott-right)  and $closestRoomVal (top-left)\n")
        }
    }
}

fun findSpaceInBottomRightCorner(room : Room, s : String, allowMoreSpaces : Boolean) : RoomPosition
{
    var downEdge = room.heightInSpaces
    var upEdge = 0
    var rightEdge = room.widthInSpaces
    var leftEdge = 0

    if (!allowMoreSpaces)
    {
        downEdge = room.heightInSpaces
        upEdge = downEdge / 2
        rightEdge = room.widthInSpaces
        leftEdge = rightEdge / 2
    }
    val rightSideWallPositions = mutableListOf<RoomPosition>()
    val bottomSideWallPositions = mutableListOf<RoomPosition>()
    // find wall positions
    if (s == "horizontal")
    {
        for (y in upEdge until downEdge)
        {
            for (x in rightEdge downTo  leftEdge)
            {
                if (y != 0)
                {
                    val space = room.getSpace(x, y)
                    if (space != null)
                    {
                        if (checkIfNoOtherPassagesNearby(room, space))
                        {
                            if (space.getBase() != null)
                            {
                                rightSideWallPositions.add(RoomPosition(x, y))
                                break
                            }
                            else
                            {
                                if (space.getEntity() != null)
                                {
                                    if (space.getEntity() is Wall)
                                    {
                                        rightSideWallPositions.add(RoomPosition(x, y))
                                        break
                                    }
                                }
                            }
                        }
                        else
                        {
                            break
                        }
                    }
                }
            }
        }
    }
    else
    {
        for (x in leftEdge until rightEdge)
        {
            for (y in downEdge downTo  upEdge)
            {
                if (x !=0)
                {
                    val space = room.getSpace(x, y)
                    if (space != null)
                    {
                        if (checkIfNoOtherPassagesNearby(room, space))
                        {
                            if (space.getBase() != null)
                            {
                                bottomSideWallPositions.add(RoomPosition(x, y))
                                break
                            }
                            else
                            {
                                if (space.getEntity() != null)
                                {
                                    if (space.getEntity() is Wall)
                                    {
                                        bottomSideWallPositions.add(RoomPosition(x, y))
                                        break
                                    }
                                }
                            }
                        }
                        else
                        {
                            break
                        }
                    }
                }
            }
        }
    }

    if (rightSideWallPositions.size > 2)
    {
        rightSideWallPositions.removeAt(0)
        rightSideWallPositions.removeAt(rightSideWallPositions.size-1)
    }
    if (bottomSideWallPositions.size > 2)
    {
        bottomSideWallPositions.removeAt(0)
        bottomSideWallPositions.removeAt(bottomSideWallPositions.size-1)
    }
    if (s == "horizontal")
    {
        return if (rightSideWallPositions.isNotEmpty())
        {
            rightSideWallPositions.random()
        }
        else
        {
            findSpaceInBottomRightCorner(room, "horizontal", true)
        }
    }
    else
    {
        return if (bottomSideWallPositions.isNotEmpty())
        {
            bottomSideWallPositions.random()
        }
        else
        {
            findSpaceInBottomRightCorner(room, "vertical", true)
        }
    }
}

fun findSpaceInTopLeftCorner(room : Room, s : String, allowMoreSpaces : Boolean) : RoomPosition
{
    var upEdge = 0
    var downEdge = room.heightInSpaces
    var leftEdge = 0
    var rightEdge = room.widthInSpaces
    if (!allowMoreSpaces)
    {
        upEdge = 0
        downEdge = room.heightInSpaces/2
        leftEdge = 0
        rightEdge = room.widthInSpaces/2
    }
    val leftSideWallPositions = mutableListOf<RoomPosition>()
    val topSideWallPositions = mutableListOf<RoomPosition>()
    // find wall positions
    if (s == "horizontal")
    {
        for (y in upEdge until downEdge)
        {
            for (x in leftEdge until rightEdge)
            {
                if (y != 0)
                {
                    val space = room.getSpace(x, y)
                    if (space != null)
                    {
                        if (checkIfNoOtherPassagesNearby(room, space))
                        {
                            if (space.getBase() != null)
                            {
                                leftSideWallPositions.add(RoomPosition(x, y))
                                break
                            }
                            else
                            {
                                if (space.getEntity() != null)
                                {
                                    if (space.getEntity() is Wall)
                                    {
                                        leftSideWallPositions.add(RoomPosition(x, y))
                                        break
                                    }
                                }
                            }
                        }
                        else
                        {
                            break
                        }
                    }
                }
            }
        }
    }
    else
    {
        for (x in leftEdge until rightEdge)
        {
            for (y in upEdge until downEdge)
            {
                if (x !=0)
                {
                    val space = room.getSpace(x, y)
                    if (space != null)
                    {
                        if (checkIfNoOtherPassagesNearby(room, space))
                        {
                            if (space.getBase() != null)
                            {
                                topSideWallPositions.add(RoomPosition(x, y))
                                break
                            }
                            else
                            {
                                if (space.getEntity() != null)
                                {
                                    if (space.getEntity() is Wall)
                                    {
                                        topSideWallPositions.add(RoomPosition(x, y))
                                        break
                                    }
                                }
                            }
                        }
                        else
                        {
                            break
                        }
                    }
                }
            }
        }
    }
    if (leftSideWallPositions.size > 2)
    {
        leftSideWallPositions.removeAt(0)
        leftSideWallPositions.removeAt(leftSideWallPositions.size-1)
    }
    if (topSideWallPositions.size > 2)
    {
        topSideWallPositions.removeAt(0)
        topSideWallPositions.removeAt(topSideWallPositions.size - 1)
    }
    if (s == "horizontal")
    {
        return if (leftSideWallPositions.isNotEmpty())
        {
            leftSideWallPositions.random()
        }
        else
        {
            findSpaceInTopLeftCorner(room, "horizontal", true)
        }
    }
    else
    {
        return if (topSideWallPositions.isNotEmpty())
        {
            topSideWallPositions.random()
        }
        else
        {
            findSpaceInTopLeftCorner(room, "vertical", true)
        }
    }
}

fun findSpaceInTopRightCorner(room : Room, s : String, allowMoreSpaces : Boolean) : RoomPosition
{
    var upEdge = 0
    var downEdge = room.heightInSpaces
    var rightEdge = room.widthInSpaces
    var leftEdge = 0
    if (!allowMoreSpaces)
    {
        upEdge = 0
        downEdge = room.heightInSpaces/2
        rightEdge = room.widthInSpaces
        leftEdge = rightEdge/2
    }
    val rightSideWallPositions = mutableListOf<RoomPosition>()
    val topSideWallPositions = mutableListOf<RoomPosition>()
    // find wall positions
    if (s == "horizontal")
    {
        for (y in upEdge until downEdge)
        {
            for (x in rightEdge downTo  leftEdge)
            {
                if (y != 0)
                {
                    val space = room.getSpace(x, y)
                    if (space != null)
                    {
                        if (checkIfNoOtherPassagesNearby(room, space))
                        {
                            if (space.getBase() != null)
                            {
                                rightSideWallPositions.add(RoomPosition(x, y))
                                break
                            }
                            else
                            {
                                if (space.getEntity() != null)
                                {
                                    if (space.getEntity() is Wall)
                                    {
                                        rightSideWallPositions.add(RoomPosition(x, y))
                                        break
                                    }
                                }
                            }
                        }
                        else
                        {
                            break
                        }
                    }
                }
            }
        }
    }
    else
    {
        for (x in leftEdge until rightEdge)
        {
            for (y in upEdge until downEdge)
            {
                if (x !=0)
                {
                    val space = room.getSpace(x, y)
                    if (space != null)
                    {
                        if (checkIfNoOtherPassagesNearby(room, space))
                        {
                            if (space.getBase() != null)
                            {
                                topSideWallPositions.add(RoomPosition(x, y))
                                break
                            }
                            else
                            {
                                if (space.getEntity() != null)
                                {
                                    if (space.getEntity() is Wall)
                                    {
                                        topSideWallPositions.add(RoomPosition(x, y))
                                        break
                                    }
                                }
                            }
                        }
                        else
                        {
                            break
                        }
                    }
                }
            }
        }
    }
    if (rightSideWallPositions.size > 2)
    {
        rightSideWallPositions.removeAt(0)
        rightSideWallPositions.removeAt(rightSideWallPositions.size-1)
    }
    if (topSideWallPositions.size > 2)
    {
        topSideWallPositions.removeAt(0)
        topSideWallPositions.removeAt(topSideWallPositions.size - 1)
    }
    if (s == "horizontal")
    {
        return if (rightSideWallPositions.isNotEmpty())
        {
            rightSideWallPositions.random()
        }
        else
        {
            findSpaceInTopRightCorner(room, "horizontal", true)
        }
    }
    else
    {
        return if (topSideWallPositions.isNotEmpty())
        {
            topSideWallPositions.random()
        }
        else
        {
            findSpaceInTopRightCorner(room, "vertical", true)
        }
    }
    
}

fun findSpaceInBottomLeftCorner(room : Room, s : String, allowMoreSpaces : Boolean) : RoomPosition
{
    var downEdge = room.heightInSpaces
    var upEdge = 0
    var leftEdge = 0
    var rightEdge = room.widthInSpaces
    if (!allowMoreSpaces)
    {
        downEdge = room.heightInSpaces
        upEdge = downEdge/2
        leftEdge = 0
        rightEdge = room.widthInSpaces/2
    }
    val leftSideWallPositions = mutableListOf<RoomPosition>()
    val bottomSideWallPositions = mutableListOf<RoomPosition>()
    // find wall positions
    if (s == "horizontal")
    {
        for (y in upEdge until downEdge)
        {
            for (x in leftEdge until rightEdge)
            {
                if (y != 0)
                {
                    val space = room.getSpace(x, y)
                    if (space != null)
                    {
                        if (checkIfNoOtherPassagesNearby(room, space))
                        {
                            if (space.getBase() != null)
                            {
                                leftSideWallPositions.add(RoomPosition(x, y))
                                break
                            }
                            else
                            {
                                if (space.getEntity() != null)
                                {
                                    if (space.getEntity() is Wall)
                                    {
                                        leftSideWallPositions.add(RoomPosition(x, y))
                                        break
                                    }
                                }
                            }
                        }
                        else
                        {
                            break
                        }
                    }
                }
            }
        }
    }
    else
    {
        for (x in leftEdge until rightEdge)
        {
            for (y in downEdge downTo  upEdge)
            {
                if (x != 0)
                {
                    val space = room.getSpace(x, y)
                    if (space != null)
                    {
                        if (checkIfNoOtherPassagesNearby(room, space))
                        {
                            if (space.getBase() != null)
                            {
                                bottomSideWallPositions.add(RoomPosition(x, y))
                                break
                            }
                            else
                            {
                                if (space.getEntity() != null)
                                {
                                    if (space.getEntity() is Wall)
                                    {
                                        bottomSideWallPositions.add(RoomPosition(x, y))
                                        break
                                    }
                                }
                            }
                        }
                        else
                        {
                            break
                        }
                    }
                }
            }
        }
    }
    if (leftSideWallPositions.size > 2)
    {
        leftSideWallPositions.removeAt(0)
        leftSideWallPositions.removeAt(leftSideWallPositions.size-1)
    }
    if (bottomSideWallPositions.size > 2)
    {
        bottomSideWallPositions.removeAt(0)
        bottomSideWallPositions.removeAt(bottomSideWallPositions.size - 1)
    }
    if (s == "horizontal")
    {
        return if (leftSideWallPositions.isNotEmpty())
        {
            leftSideWallPositions.random()
        }
        else
        {
            findSpaceInBottomLeftCorner(room, "horizontal", true)
        }
    }
    else
    {
        return if (bottomSideWallPositions.isNotEmpty())
        {
            bottomSideWallPositions.random()
        }
        else
        {
            findSpaceInBottomLeftCorner(room, "vertical", true)
        }
    }
}

fun checkIfNoOtherPassagesNearby(room : Room, space : Space) : Boolean
{
    val entitiesInTheRoom = room.getEntities()
    if (entitiesInTheRoom.isNotEmpty())
    {
        for (entity in entitiesInTheRoom)
        {
            if (entity is RoomExit || entity is Exit)
            {
                val posX = entity.position.x
                val posY = entity.position.y
                if (sqrt((abs(space.position.x - posX) * abs(space.position.x - posX) + abs(space.position.y - posY) * abs(space.position.y - posY)).toDouble()) < 2)
                {
                    return false
                }
            }
        }
    }
    return true
}

fun findRoomByName(roomName : String, rooms : MutableList<Room>) : Room?
{
    for (i in rooms)
    {
        if (i.name == roomName)
        {
            return i
        }
    }
    return null
}

fun findClosestPatchThatIsNotInConnections(connections : MutableList<Int>, patchCenters : List<PatchCenter>, i : PatchCenter) : PatchCenter?
{
    var closestPatchDist = 1000.0
    var closestPatch : PatchCenter? = null
    for (patch in patchCenters)
    {
        if (patch.value != i.value && patch.value !in connections)
        {
            val currentDistBetweenPatches = findDistanceBetweenPatches(i, patch)
            if (currentDistBetweenPatches < closestPatchDist)
            {
                closestPatchDist = currentDistBetweenPatches
                closestPatch = patch
            }
        }
    }
    return closestPatch
    
}

fun findFurthestPatch(patchCenters : List<PatchCenter>, i : PatchCenter) : PatchCenter?
{
    var furthestPatchDist = 0.0
    var furthestPatch : PatchCenter? = null
    for (patch in patchCenters)
    {
        if (patch.value != i.value)
        {
            val currentDistBetweenPatches = findDistanceBetweenPatches(i, patch)
            if (currentDistBetweenPatches > furthestPatchDist)
            {
                furthestPatchDist = currentDistBetweenPatches
                furthestPatch = patch
            }
        }
    }
    return furthestPatch
    
}

fun findDistanceBetweenPatches(i : PatchCenter, patch : PatchCenter) : Double
{
    return sqrt((abs(i.x - patch.x) * abs(i.x - patch.x) + abs(i.y - patch.y) * abs(i.y - patch.y)).toDouble())
}

fun areAllRoomsConnected(connections : MutableList<Int>, possibleRoomsValues : List<Int>) : Boolean
{
    for (i in possibleRoomsValues)
    {
        if (i !in connections)
        {
            return false
        }
    }
    return true
}

fun getMatrixBasedOnCoordinates(roomData : Array<IntArray>, x1 : Int, x2 : Int, y1 : Int, y2 : Int, i : Int) : Array<IntArray>
{
    val result = Array(x2 - x1 + 1) { IntArray(y2 - y1 + 1) { 0 } }
    for (x in x1 until x2)
    {
        for (y in y1 until  y2)
        {
            if (roomData[x][y] == i)
            {
                result[x - x1 + 1][y - y1 + 1] = i
            }
        }
    }
    return result
}

fun getRoomY2(i : Int, roomData : Array<IntArray>) : Int
{
    var highestY = 0
    for (x in roomData.indices)
    {
        for (y in roomData[0].indices)
        {
            if (roomData[x][y] == i && y > highestY)
            {
                highestY = y
            }
        }
    }
    return highestY
}

fun getRoomX2(i : Int, roomData : Array<IntArray>) : Int
{
    var highestX = 0
    for (x in roomData.indices)
    {
        for (y in roomData[0].indices)
        {
            if (roomData[x][y] == i && x > highestX)
            {
                highestX = x
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
        for (y in roomData[0].indices)
        {
            if (roomData[x][y] == i && y < lowestY)
            {
                lowestY = y
            }
        }
    }
    return lowestY
}

fun getRoomX1(i : Int, roomData : Array<IntArray>) : Int
{
    var lowestX = 1000
    for (x in roomData.indices)
    {
        for (y in roomData[0].indices)
        {
            if (roomData[x][y] == i && x < lowestX)
            {
                lowestX = x
            }
        }
    }
    return lowestX
}

fun generatePerlinNoiseMap(width: Int, height: Int, scale: Double, seed: Int): Array<DoubleArray> {
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

data class PatchCenter(val x: Int, val y: Int, val value: Int)

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
    
    val centerX = patchValues.map { it.first }.average().roundToInt()
    val centerY = patchValues.map { it.second }.average().roundToInt()
    
    return PatchCenter(centerX, centerY, roomData[startX][startY])
}


fun perlinNoise(x: Double, y: Double, permutationTable: IntArray): Double {
    val X = (x.toInt() and 255).toByte().toInt()
    val Y = (y.toInt() and 255).toByte().toInt()
    
    val u = fade(x)
    val v = fade(y)
    
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
    var grad = 1.0 + (h and 7)
    if (h and 8 != 0) {
        grad = -grad
    }
    return grad * x
}

fun mergeRooms(roomData: Array<IntArray>) {
    val filterSize = 3
    for (i in 0 until roomData.size - filterSize) {
        for (j in 0 until roomData[0].size - filterSize) {
            val horizontal = roomData[i].slice(j until j + filterSize)
            val vertical = roomData.slice(i until i + filterSize).map { it[j] }
            if (horizontal[0] != 0 && horizontal.last() != 0 && !horizontal.subList(1, filterSize).any { it != 0 }) {
                roomData[i].copyInto(roomData[i], j, 0, filterSize)
            }
            if (vertical[0] != 0 && vertical.last() != 0 && !vertical.subList(1, filterSize - 1).any { it != 0 }) {
                for (k in i until i + filterSize) {
                    roomData[k][j] = vertical[0]
                }
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
    if (x in roomData.indices &&
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


