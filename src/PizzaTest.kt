import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.io.File

internal class PizzaTest {


    @org.junit.jupiter.api.Test
    fun coordinatesList() {

    }

    @org.junit.jupiter.api.Test
    fun area() {
        assertEquals(6, Slice(Coordinate(0, 0), Coordinate(1, 2)).area())
        assertEquals(1, Slice(Coordinate(0, 0), Coordinate(0, 0)).area())

    }

    @org.junit.jupiter.api.Test
    fun isValidSlice() {
    }

    @org.junit.jupiter.api.Test
    fun isThisOverlapping() {
        //0 72 11 72
        val a = Slice(Coordinate(72,0), Coordinate(72, 11))
        //6 70 6 81
        val b = Slice(Coordinate(70,6), Coordinate(81, 6))
        val pizza = Pizza.parse(File("medium.in").readText())
        pizza.addSlice(a)
        println(a.coordinatesList().toList())
        println(b.coordinatesList().toList())

        println(pizza.usedCoordinates.contains(Coordinate(72, 6)))
        assertFalse(pizza.isValidSlice(b))
        assertTrue(b.overlap2(pizza.usedCoordinates))


    }
}

