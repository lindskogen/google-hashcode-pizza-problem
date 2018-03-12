import kotlin.coroutines.experimental.buildSequence
import kotlin.math.abs
import kotlin.math.min

typealias Row = List<Pizza.Ingredient>
typealias Grid = List<Row>

class Pizza(
        private val height: Int,
        private val width: Int,
        private val minCellsPerIngredient: Int,
        private val maxCells: Int,
        private val grid: Grid,
        private val slices: MutableList<Slice> = mutableListOf(),
        val usedCoordinates: MutableSet<Coordinate> = mutableSetOf()) {

    enum class Ingredient {
        Tomato {
            override fun toString(): String {
                return "T"
            }
        },
        Mushroom {
            override fun toString(): String {
                return "M"
            }
        }

    }


    fun solve(): List<Slice> {
        var currentCoord = Coordinate(0, 0)

        while (true) {
            val slicesAtCoord = possibleSlicesAt(currentCoord)
            val bestSlice = takeBestPossibleSlice(slicesAtCoord)
            if (bestSlice == null) {
                usedCoordinates.add(currentCoord)
            } else {
                addSlice(bestSlice)
            }

            currentCoord = nextFreeCoordinate(currentCoord) ?: break
        }

        usedCoordinates.removeIf { true }
        slices.forEach { usedCoordinates.addAll(it.coordinatesList()) }

        expandSlices()

        return slices.toList()
    }

    fun removeSlice(slice: Slice) {
        usedCoordinates.removeAll(slice.coordinatesList())
        slices.remove(slice)
    }

    fun expandSlices() {
        for (it in slices.toList()) {
            this.removeSlice(it)
            var newX = it.second.x
            var newY = it.second.y

            do {
                newY++
                val newSlice = Slice(it.first, Coordinate(newX, newY))
            } while (isValidSlice(newSlice))
            newY--


            do {
                newX++
                val newSlice = Slice(it.first, Coordinate(newX, newY))
            } while (isValidSlice(newSlice))
            newX--

            this.addSlice(Slice(it.first, Coordinate(newX, newY)))
        }
    }

    fun addSlice(slice: Slice) {
        slices.add(slice)
        usedCoordinates.addAll(slice.coordinatesList())
    }

    fun nextFreeCoordinate(coordinate: Coordinate): Coordinate? {

        fun nextCoordinate(coordinate: Coordinate): Coordinate? {
            val (x, y) = coordinate
            return when {
                x >= width && y >= height -> null
                x >= width -> Coordinate(0, y + 1)
                else -> Coordinate(x + 1, y)
            }
        }

        var currentCoordinate = coordinate
        while (usedCoordinates.contains(currentCoordinate)) {
            currentCoordinate = nextCoordinate(currentCoordinate) ?: return null
        }

        return currentCoordinate
    }

    fun takeBestPossibleSlice(slices: List<Slice>): Slice? {
        return slices.maxBy { -it.value() }

    }

    fun possibleSlicesAt(coordinate: Coordinate): List<Slice> {
        val possibleSlices = mutableListOf<Slice>()

        val minCellsInSlice = minCellsPerIngredient * 2


        val xMin = coordinate.x
        val xMax = min(coordinate.x + maxCells, width)

        val yMin = coordinate.y
        val yMax = min(coordinate.y + maxCells, height)

        for (x in xMin until xMax) {
            (yMin until yMax)
                    .map { Slice(coordinate, Coordinate(x, it)) }
                    .filterTo(possibleSlices) { it.area() in minCellsInSlice..maxCells && isValidSlice(it) }
        }

        return possibleSlices.toList()
    }

    fun isValidSlice(slice: Slice): Boolean {
        if (slice.area() < 2 * this.minCellsPerIngredient ||
                slice.area() > this.maxCells ||
                slice.overlap2(usedCoordinates) ||
                slice.second.x >= width || slice.second.y >= height) {
            return false
        }

        var countTomato = 0
        var countMushroom = 0

        for (coord in slice.coordinatesList()) {
            when (this.grid[coord.y][coord.x]) {
                Ingredient.Tomato -> countTomato++
                Ingredient.Mushroom -> countMushroom++
            }
        }

        val enoughOfEachIngredient = countTomato >= this.minCellsPerIngredient && countMushroom >= this.minCellsPerIngredient

        return enoughOfEachIngredient
    }

    fun toGoogleString(): String {
        val res = StringBuilder()
        res.appendln(slices.size)
        slices.forEach {
            res.appendln(it)
        }
        return res.toString()
    }

    override fun toString(): String {
        val res = StringBuilder()
        res.append("Pizza { ")
        res.append("\n\tHeight: ")
        res.append(height)
        res.append("\n\tWidth: ")
        res.append(width)
        res.append("\n\tMinCellsPerIngredient: ")
        res.append(minCellsPerIngredient)
        res.append("\n\tMaxCells: ")
        res.append(maxCells)
        res.append("\n\t")
        grid.forEachIndexed { i, row ->
            row.forEach { cell -> res.append(cell) }
            res.append(if (i != grid.size - 1) "\n\t" else "\n")
        }
        res.append("}")

        return res.toString()
    }

    companion object {

        fun parse(input: String): Pizza {

            val lines = input.trim().lines()
            val header = lines[0].split(" ")
            val unparsedPizza = lines.drop(1)

            val parsedPizza = unparsedPizza.map { line ->
                line.map { cell ->
                    if (cell == 'T') {
                        Ingredient.Tomato
                    } else {
                        Ingredient.Mushroom
                    }
                }
            }

            return Pizza(
                    header[0].toInt(),
                    header[1].toInt(),
                    header[2].toInt(),
                    header[3].toInt(),
                    parsedPizza
            )
        }
    }
}
