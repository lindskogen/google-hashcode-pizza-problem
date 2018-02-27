import kotlin.coroutines.experimental.buildSequence
import kotlin.math.abs

data class Slice(val first: Coordinate, val second: Coordinate) {
    fun area(): Int {
        return (abs(this.first.x - this.second.x) + 1) * (abs(this.first.y - this.second.y) + 1)
    }

    fun value(): Int {
        return area()
    }

    override fun toString(): String {
        return "$first $second"
    }

    fun coordinatesList(): Sequence<Coordinate> {
        val second = this.second
        val first = this.first
        return buildSequence {
            (first.x..second.x).forEach { x ->
                (first.y..second.y).forEach { y ->
                    yield(Coordinate(x, y))
                }

            }
        }

    }
}