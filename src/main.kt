import java.io.File

fun main(args: Array<String>) {

    val pizza = Pizza.parse(File("example.in").readText())
    println("Hello, world!")
    println(pizza)
}