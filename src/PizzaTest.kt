import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

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


}

