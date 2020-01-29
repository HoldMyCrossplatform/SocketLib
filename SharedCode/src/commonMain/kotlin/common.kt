package com.example

expect fun platformName(): String

fun createApplicationScreenMessage() : String {
    return "Kotlin Rocks on ${platformName()}"
}

fun doSth(block: (String) -> Unit){
    println("Doin sth")
    block("Luke, I am Your message")
    println("Done sth")
}