// 9.3 -- commit 2
fun main() {

    println("Hello World")
    println(sum(1,2))
    println(describe(1L))

    val x = 12
    val y = 9
    if (x in 1..y+1) {
        println("fits in range")
    }

    val list = listOf("a", "b", "c")

    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }
    if (list.size !in list.indices) {
        println("list size is out of valid list indices range, too")
    }

    for (x1 in 1..5) {
        print(x1)
    }
    println()
    for (x2 in 1..10 step 2) {
        print(x2)
    }
    println()
    for (x3 in 9 downTo 0 step 3) {
        print(x3)
    }

}

fun sum(a: Int, b: Int) = a + b


fun describe(obj: Any): String =
    when (obj) {
        1          -> "One"
        "Hello"    -> "Greeting"
        is Long    -> "Long"
        !is String -> "Not a string"
        else       -> "Unknown"
    }