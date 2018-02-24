import kotlin.math.max

class Pizza(val height: Int, val width: Int, val minCellsPerIngredient: Int, val maxCells: Int, val grid: List<List<Ingredient>>) {

    enum class Ingredient{
        Tomato{
            override fun toString(): String {
                return "T"
            }
        },
        Mushroom{
            override fun toString(): String {
                return "M"
            }
        }

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
            res.append(if (i != grid.size-1) "\n\t" else "\n")
        }
        res.append("}")

        return res.toString()
    }

    companion object {

        fun parse(input: String): Pizza {

            val lines = input.trim().lines()
            val header = lines[0].split(" ")
            val unparsedPizza = lines.drop(1)

            val parsedPizza = unparsedPizza.map{ line ->
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