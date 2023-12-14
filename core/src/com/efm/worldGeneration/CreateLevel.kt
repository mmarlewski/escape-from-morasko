import kotlin.math.round

fun main() {
    val mapSize = 100
    val map = Array(mapSize) { Array(mapSize) { 0 } }
    
    partitionMap(map, 0, 0, mapSize, mapSize, 1) // Starting value is 1
    
    // Print the partitioned map (just for visualization)
    for (row in map) {
        for (cell in row) {
            print("$cell ")
        }
        println()
    }
}

fun partitionMap(map: Array<Array<Int>>, startX: Int, startY: Int, width: Int, height: Int, value: Int) {
    // Base case: if the area is small enough, assign the same value to all cells
    if (width <= 2 || height <= 2) {
        for (i in startX until startX + width) {
            for (j in startY until startY + height) {
                map[i][j] = value
            }
        }
        return
    }
    
    // Otherwise, randomly choose a split point for both X and Y dimensions
    val min = round((width/4).toDouble()).toInt()
    val max = round((3*(width/4)).toDouble()).toInt()
    val splitX = startX + (min..max).random()
    val splitY = startY + (min..max).random()
    
    val nextValue = value + 1 // Increase the value for each new segment
    
    partitionMap(map, startX, startY, splitX - startX, splitY - startY, nextValue)
    partitionMap(map, splitX, startY, width - (splitX - startX), splitY - startY, nextValue)
    partitionMap(map, startX, splitY, splitX - startX, height - (splitY - startY), nextValue)
    partitionMap(map, splitX, splitY, width - (splitX - startX), height - (splitY - startY), nextValue)
}
