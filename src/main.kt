import java.io.File

fun main(args: Array<String>) {

    val challenges = listOf("example")//, "small", "medium", "big")

    challenges.forEach { challenge ->
        val pizza = Pizza.parse(File(challenge + ".in").readText())
        println(pizza)
        //println(pizza.isValidSlice(Slice(Pizza.Coordinate(0,0), Pizza.Coordinate(1,1))))
        for (i in Slice(Coordinate(0, 0), Coordinate(1, 1)).coordinatesList()) {
            println(i)
        }

        pizza.solve()
        println(pizza.toGoogleString())

        File(challenge + ".out").writeText(pizza.toGoogleString())
    }
}
