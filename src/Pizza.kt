import kotlin.coroutines.experimental.buildSequence
import kotlin.math.abs
import kotlin.math.min

typealias Slice = Pair<Pizza.Coordinate, Pizza.Coordinate>
typealias Row = List<Pizza.Ingredient>
typealias Grid = List<Row>


fun Slice.area(): Int {
    return (abs(this.first.x - this.second.x) + 1) * (abs(this.first.y - this.second.y) + 1)
}

fun Slice.value(): Int {
    return area()
}

fun Slice.toString(): String {
    return "$first $second"
}

fun Slice.coordinatesList(): Sequence<Pizza.Coordinate> {
    val second = this.second
    val first = this.first
    return buildSequence {
        (first.x..second.x).forEach { x ->
            (first.y..second.y).forEach { y ->
                yield(Pizza.Coordinate(x, y))
            }

        }
    }

}

class Pizza(
        private val height: Int,
        private val width: Int,
        private val minCellsPerIngredient: Int,
        private val maxCells: Int,
        private val grid: Grid,
        private val slices: MutableList<Slice> = mutableListOf(),
        private val usedCoordinates: MutableSet<Coordinate> = mutableSetOf()) {

    data class Coordinate(val x: Int, val y: Int) {
        override fun toString(): String {
            return "$x $y"
        }
    }

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
        var currentCoord = Pizza.Coordinate(0, 0)

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

        return slices.toList()
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
        return slices.maxBy { it.value() }

    }

    fun possibleSlicesAt(coordinate: Coordinate): List<Slice> {
        val possibleSlices = mutableListOf<Slice>()

        val minCellsInSlice = minCellsPerIngredient * 2


        val xMin = coordinate.x
        val xMax = min(coordinate.x + maxCells, width)

        val yMin = coordinate.y
        val yMax = min(coordinate.y + maxCells, height)

        for (x in xMin until xMax) {
            for (y in yMin until yMax) {
                val s = Slice(coordinate, Coordinate(x, y))
                if (s.area() < minCellsInSlice || maxCells < s.area()) {
                    continue
                }

                if (isValidSlice(s)) {
                    possibleSlices.add(s)
                }

            }
        }

        return possibleSlices.toList()
    }


    fun isValidSlice(slice: Slice): Boolean {
        if (slice.area() < 2 * this.minCellsPerIngredient || slice.area() > this.maxCells) {
            return false
        }

        var countTomato = 0
        var countMushroom = 0

        for (coord in slice.coordinatesList()) {
            when (this.grid[coord.y][coord.x]) {
                Ingredient.Tomato -> countTomato++
                Ingredient.Mushroom -> countMushroom++
            }

            if (countTomato >= this.minCellsPerIngredient && countMushroom >= this.minCellsPerIngredient) {
                return true
            }
        }

        return !(countTomato < this.minCellsPerIngredient || countMushroom < this.minCellsPerIngredient)
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