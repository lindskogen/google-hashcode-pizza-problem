import java.io.File

fun main(args: Array<String>) {

    val pizza = Pizza.parse(File("example.in").readText())
    println("Hello, world!")
    println(pizza)
    //println(pizza.isValidSlice(Slice(Pizza.Coordinate(0,0), Pizza.Coordinate(1,1))))
    for (i in Slice(Pizza.Coordinate(0,0), Pizza.Coordinate(1,1)).coordinatesList()) {
        println(i)
    }


    pizza.solve()
    println(pizza.toGoogleString())
}