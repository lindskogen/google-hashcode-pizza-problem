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

    fun overlap2(usedCoordinates: Set<Coordinate>): Boolean {
        return this.coordinatesList().any { usedCoordinates.contains(it) }
    }

    fun overlap(other: Slice): Boolean {
        // Left x
        val leftX = Math.max(this.first.x, other.first.x)
        // Right x
        val rightX = Math.min(this.second.x, other.second.x)
        // Bottom y
        val botY = Math.max(this.first.y, other.first.y)
        // TopY
        val topY = Math.min(this.second.y, other.second.y)

        return rightX > leftX && topY > botY

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