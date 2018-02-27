import kotlin.coroutines.experimental.buildSequence
import kotlin.math.abs

typealias Slice = Pair<Pizza.Coordinate, Pizza.Coordinate>
typealias Row = List<Pizza.Ingredient>
typealias Grid = List<Row>

class Pizza(
        private val height: Int,
        private val width: Int,
        private val minCellsPerIngredient: Int,
        private val maxCells: Int,
        private val grid: Grid,
        private val slices: List<Slice> = emptyList()) {

    data class Coordinate(val x: Int, val y: Int)



    fun Slice.area(): Int {
        return abs(this.first.x - this.second.x) * abs(this.first.y - this.second.y)
    }

    fun Slice.coordinatesList(): Sequence<Coordinate> {
        val second = this.second
        val first = this.first
        return buildSequence {
            (first.x .. second.x).forEach { x ->
                (first.y .. second.y).forEach {y ->
                    yield(Coordinate(x,y))
                }

            }
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
    

    fun isValidSlice(slice: Slice): Boolean {
        if (slice.area() < 2*this.minCellsPerIngredient || slice.area() > this.maxCells) {
            return false
        }

        var countTomato = 0
        var countMushroom = 0

        for (coord in slice.coordinatesList()) {
            when(this.grid[coord.x][coord.y]) {
                Ingredient.Tomato -> countTomato++
                Ingredient.Mushroom -> countMushroom++
            }

            if (countTomato >= this.minCellsPerIngredient && countMushroom >= this.minCellsPerIngredient) {
                return true
            }
        }

        return !(countTomato < this.minCellsPerIngredient || countMushroom < this.minCellsPerIngredient)
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