import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class PizzaTest {



    @org.junit.jupiter.api.Test
    fun coordinatesList() {

    }

    @org.junit.jupiter.api.Test
    fun area() {
        assertEquals(6, Slice(Pizza.Coordinate(0,0), Pizza.Coordinate(1,2)).area())
        assertEquals(1, Slice(Pizza.Coordinate(0,0), Pizza.Coordinate(0,0)).area())

    }


}